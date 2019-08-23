package farmacias.peruanas.com.farmaciaschecklistapp.login;

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
//import farmacias.peruanas.com.farmaciaschecklistapp.login.service.RespuestaService;
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
    //private RespuestaService respuestaService;
    private AppExecutors appExecutors;

    public LoginRepository(CheckListDB checkListDB,
                           LoginService service,
                           ChecklistService checklistService,
                           MaestrosService maestrosService,
                        //  RespuestaService respuestaService,
                           AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        this.db = checkListDB;
        this.loginService = service;
        this.checklistService = checklistService;
        this.maestrosService = maestrosService;
        //this.respuestaService = respuestaService;
    }


    void initLoginUser(String user, String clave, MutableLiveData<String> validateUsuario, MutableLiveData<HashMap<String, Object>> onLoginOK) {
        String passwordEncriptado = CryptoUtil.encriptarSHA256(clave);
        Usuario usuario1 = new Usuario("0", user, passwordEncriptado, null, null);
        loginService.login(usuario1).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if(loginResponse==null){
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
                    appExecutors.networkIO()
                            .execute(() -> {
                                db.usuarioDao().insertUser(new
                                        Usuario(loginResponse.getUsuario().getCod_usu(),
                                        loginResponse.getUsuario().getLogin(),
                                        loginResponse.getUsuario().getClave(),
                                        loginResponse.getSessionKey(),
                                        validandEmpresa));
                                /*obtenerDataRemote(loginResponse.getSessionKey(),
                                        validateUsuario,
                                        onLoginOK,
                                        stringHashMap);*/
                                onLoginOK.postValue(stringHashMap);
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
                                   HashMap<String, Object> stringHashMapResult) {


       /* checklistService.listarChecklists().enqueue(new Callback<List<Checklist>>() {
            @Override
            public void onResponse(Call<List<Checklist>> call, Response<List<Checklist>> response) {
                String mensajeError = null;
                if (response.isSuccessful()) {
                    try {
                        guardarChecklistsDB(response.body(), sessionKey, validateUsuario, onLoginOK, stringHashMapResult);
                    } catch (Exception e) {
                        mensajeError = e.getLocalizedMessage();
                        Timber.d("MIFARMA", "error al sincronizar checklists", e);
                    }
                } else {
                    mensajeError = response.message();
                }
                if (mensajeError != null) {
                    validateUsuario.postValue(mensajeError + " :ErrorCheckList");
                }
            }

            @Override
            public void onFailure(Call<List<Checklist>> call, Throwable t) {
                validateUsuario.postValue(t.getMessage() + "onFailure:ErrorCheckList");
            }
        });*/

    }

    private void guardarChecklistsDB(List<Checklist> checklistList,
                                     String sessionKey,
                                     MutableLiveData<String> validateUsuario,
                                     MutableLiveData<HashMap<String, Object>> onLoginOK,
                                     HashMap<String, Object> stringHashMapResult) {
        try {
            appExecutors.networkIO()
                    .execute(() -> {
                        db.checkListDao().insertAllOrders(checklistList);
                        onLoginOK.postValue(stringHashMapResult);
                    });
        } catch (Exception e) {
            validateUsuario.postValue(e.getLocalizedMessage() + ":ErrorCheckList");
        }


    }

  /*  private void obtenerDataRemote(String sessionKey) {


    }*/
}
