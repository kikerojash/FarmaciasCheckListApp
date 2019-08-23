package farmacias.peruanas.com.farmaciaschecklistapp.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import farmacias.peruanas.com.farmaciaschecklistapp.Injection;
import farmacias.peruanas.com.farmaciaschecklistapp.main.MainActivity;
import farmacias.peruanas.com.farmaciaschecklistapp.R;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Usuario;
import farmacias.peruanas.com.farmaciaschecklistapp.utils.AppConstants;
import farmacias.peruanas.com.farmaciaschecklistapp.utils.SecurePreferences;
import timber.log.Timber;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String ELIMINAR_CREDENCIALES = "ELIMINAR_CREDENCIALES";
    public static final String USERNAME_KEY = "USERNAME_KEY";
    public static final String PREV_USERNAME_KEY = "PREV_USERNAME_KEY";
    public static final String ABRIR_LISTADO = "ABRIR_LISTADO";
    public static final String USERNAME_ID = "USERNAME_ID";

    public static final String KEY_SINCRONIZACION = "KEY_SINCRONIZACION";

    public static final int SINCRONIZACION_NINGUNA = 0;
    public static final int SINCRONIZACION_TODO = 1;
    public static final int SINCRONIZACION_INSTANCIAS = 2;


    private LoginViewModel loginViewModel;
    private LoginFactory loginFactory;
    @BindView(R.id.et_usuario)
    EditText editTextUsuario;
    @BindView(R.id.et_clave)
    EditText editTextClave;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    private AlertDialog alert;
    private ProgressDialog pd;
    private SecurePreferences securePreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        securePreferences = new SecurePreferences(LoginActivity.this, AppConstants.KEY_PREFERENCES_CHECKLIST, true);
        btnLogin.setOnClickListener(this);
        loginFactory = Injection.provideViewModelFactory(this);
        loginViewModel = new ViewModelProvider(this, loginFactory).get(LoginViewModel.class);
        initValidateLogin();
        initViewModel();
    }

    private void initValidateLogin() {
        if (securePreferences.isLoggedIn()) {
            ActivityCompat.finishAffinity(LoginActivity.this);
            Intent intent = new Intent(this,
                    MainActivity.class);
            startActivity(intent);
        }
        Timber.d("checkLogin : " + securePreferences.isLoggedIn());
    }

    private void initViewModel() {
        loginViewModel.validateUsuario.observeForever(this::mostrarMensaje);
        loginViewModel.onLoginOK.observeForever(this::initStartMainActivity);
        loginViewModel.onUserPreferencia.observeForever(this::initPreferencias);
    }

    private void initPreferencias(HashMap<String, Object> stringObjectHashMap) {
        Usuario usuario = (Usuario) stringObjectHashMap.get("usuario");
        String sessionKey = (String) stringObjectHashMap.get("sessionKey");
        securePreferences.createLoginSession(usuario.getLogin(),
                usuario.getCodEmpresa(),
                sessionKey);
    }

    private void initStartMainActivity(HashMap<String, Object> usuarioStringHashMap) {
        Usuario usuario = (Usuario) usuarioStringHashMap.get("usuario");
        String sessionKey = (String) usuarioStringHashMap.get("sessionKey");
        /*securePreferences.createLoginSession(usuario.getLogin(),
                usuario.getCodEmpresa(),
                sessionKey);*/
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
        Intent intent = new Intent(this,
                MainActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        Timber.d("usuario : " + usuarioStringHashMap.get("usuario") + " / " +
                "empresa : " + usuarioStringHashMap.get("empresa"));
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
        if (alert != null && alert.isShowing()) {
            alert.dismiss();
        }
    }

    @OnClick()
    public void onClick(View view) {
        int itemId = view.getId();
        if (itemId == R.id.btnLogin) {
            initLogin();
        }
    }

    private void initLogin() {
        pd = ProgressDialog.show(this, getString(R.string.pd_titulo),
                getString(R.string.mensaje_iniciando_sesion));
        if (!verificarConectividad()) {
            onLoginNoConexion();
            Timber.d("No Conexion");
            return;
        }
        String usuario = editTextUsuario.getText().toString();
        String clave = editTextClave.getText().toString();
        loginViewModel.validarUsuario(usuario, clave);
    }

    private void onLoginNoConexion() {
        alert = new AlertDialog.Builder(this)
                .setTitle(R.string.alert_titulo_error)
                .setMessage(R.string.mensaje_login_sin_conexion)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                })
                .show();
    }

    private void mostrarMensaje(String mensaje) {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
        Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public boolean verificarConectividad() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // ActivityCompat.finishAffinity(LoginActivity.this);
    }
}
