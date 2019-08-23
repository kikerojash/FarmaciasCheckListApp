package farmacias.peruanas.com.farmaciaschecklistapp.login;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import farmacias.peruanas.com.farmaciaschecklistapp.base.AppExecutors;
import farmacias.peruanas.com.farmaciaschecklistapp.db.CheckListDB;
import farmacias.peruanas.com.farmaciaschecklistapp.login.service.ChecklistService;
import farmacias.peruanas.com.farmaciaschecklistapp.login.service.LoginResponse;
import farmacias.peruanas.com.farmaciaschecklistapp.login.service.LoginService;
import farmacias.peruanas.com.farmaciaschecklistapp.login.service.MaestrosService;
import farmacias.peruanas.com.farmaciaschecklistapp.login.service.RespuestaService;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Checklist;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Instancia;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Usuario;
import farmacias.peruanas.com.farmaciaschecklistapp.utils.CryptoUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class LoginRepository {

    private CheckListDB db;
    private LoginService loginService;
    private ChecklistService checklistService;
    private MaestrosService maestrosService;
    private RespuestaService respuestaService;
    private AppExecutors appExecutors;

    public LoginRepository(CheckListDB checkListDB,
                           LoginService service,
                           ChecklistService checklistService,
                           MaestrosService maestrosService,
                           RespuestaService respuestaService,
                           AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        this.db = checkListDB;
        this.loginService = service;
        this.checklistService = checklistService;
        this.maestrosService = maestrosService;
        this.respuestaService = respuestaService;
    }


    void initLoginUser(String user, String clave,
                       MutableLiveData<String> validateUsuario,
                       MutableLiveData<HashMap<String, Object>> onLoginOK,
                       MutableLiveData<HashMap<String, Object>> onUserPreferencia) {
        String passwordEncriptado = CryptoUtil.encriptarSHA256(clave);
        Usuario usuario1 = new Usuario("0", user, passwordEncriptado, null, null);
        loginService.login(usuario1).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse == null) {
                    validateUsuario.postValue("Errores con nuestros servidores, Contactar con Roy");
                    return;
                }
                if (loginResponse.isSuccess()) {
                    String validandEmpresa = loginResponse.getUsuario().getCodEmpresa();
                    Timber.d("validandEmpresa : " + validandEmpresa);
                    HashMap<String, Object> stringHashMap = new HashMap<>();
                    stringHashMap.put("usuario", loginResponse.getUsuario());
                    stringHashMap.put("empresa", validandEmpresa);
                    stringHashMap.put("sessionKey", loginResponse.getSessionKey());
                    onUserPreferencia.postValue(stringHashMap);
                    appExecutors.networkIO()
                            .execute(() -> {
                                db.usuarioDao().insertUser(new
                                        Usuario(loginResponse.getUsuario().getCod_usu(),
                                        loginResponse.getUsuario().getLogin(),
                                        loginResponse.getUsuario().getClave(),
                                        loginResponse.getSessionKey(),
                                        validandEmpresa));
                                obtenerDataRemote(loginResponse.getSessionKey(),
                                        validateUsuario,
                                        onLoginOK,
                                        stringHashMap,
                                        db);
                                // onLoginOK.postValue(stringHashMap);
                            });
                } else {
                    if (response.code() == 401) { // unauthorized
                        validateUsuario.postValue("No 401 : ErrorLogin");
                        Timber.d("No 401 : ErrorLogin");
                    } else {
                        String mensajeError;
                        try {
                            Timber.d("try : ErrorLogin");
                            mensajeError = response.errorBody().string();
                        } catch (IOException e) {
                            Timber.d("mensajeError :ErrorLogin ");
                            mensajeError = response.errorBody().toString();
                        }
                        validateUsuario.postValue(mensajeError + " :ErrorLogin");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                validateUsuario.postValue(t.getMessage() + " :: ErrorLogin");
            }
        });
    }

    private void obtenerDataRemote(String sessionKey,
                                   MutableLiveData<String> validateUsuario,
                                   MutableLiveData<HashMap<String, Object>> onLoginOK,
                                   HashMap<String, Object> stringHashMapResult,
                                   CheckListDB db) {


        this.respuestaService.obtenerRespuestas().enqueue(new Callback<List<Instancia>>() {
            @Override
            public void onResponse(Call<List<Instancia>> call, Response<List<Instancia>> response) {
                String mensajeError = null;
                if (response.isSuccessful()) {
                    //noinspection unchecked
                    new GuardarInstanciasTask(validateUsuario, onLoginOK, stringHashMapResult, false, db)
                            .execute(response.body());
                } else {
                    mensajeError = response.message();
                    validateUsuario.postValue(mensajeError);
                }
            }

            @Override
            public void onFailure(Call<List<Instancia>> call, Throwable t) {
                validateUsuario.postValue(t.getLocalizedMessage());
            }
        });
    }


    class GuardarInstanciasTask extends AsyncTask<List<Instancia>, Void, Void> {
        private boolean actualizar;
        private MutableLiveData<String> validateUsuario;
        private MutableLiveData<HashMap<String, Object>> onLoginOK;
        private HashMap<String, Object> stringHashMapResult;
        private CheckListDB checkListDB;

        public GuardarInstanciasTask(MutableLiveData<String> validateUsuario,
                                     MutableLiveData<HashMap<String, Object>> onLoginOK,
                                     HashMap<String, Object> stringHashMapResult,
                                     boolean b,
                                     CheckListDB db) {
            this.validateUsuario = validateUsuario;
            this.onLoginOK = onLoginOK;
            this.stringHashMapResult = stringHashMapResult;
            this.actualizar = b;
            this.checkListDB = db;
        }


        @Override
        protected Void doInBackground(List<Instancia>... params) {
            try {
                guardarInstancias(params[0], actualizar, checkListDB);
            } catch (Exception e) {
                validateUsuario.postValue(e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            /*OpenHelperManager.releaseHelper();
            this.dbHelper = null;
            verificarLlamarView(mensajeErrorGuardarInstancias, mensajeErrorChecklists,
                    mensajeErrorMaestros);*/
        }
    }

    private void guardarInstancias(List<Instancia> instanciaList, boolean actualizar, CheckListDB db) {
        try {
            for (Instancia instancia : instanciaList) {
                Timber.d("instancia : " + instancia.getId());
            }
        } catch (Exception e) {
            Timber.d("Exception : " + e.getLocalizedMessage());
        }


    }


}
