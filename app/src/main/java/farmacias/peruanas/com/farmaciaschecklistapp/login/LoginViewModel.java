package farmacias.peruanas.com.farmaciaschecklistapp.login;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.HashMap;
import timber.log.Timber;

class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository;

    MutableLiveData<String> validateUsuario = new MutableLiveData<>();
    MutableLiveData<HashMap<String, Object>> onLoginOK = new MutableLiveData<>();
    MutableLiveData<HashMap<String, Object>> onUserPreferencia = new MutableLiveData<>();

    LoginViewModel(LoginRepository repository) {
        this.loginRepository = repository;
    }

    void validarUsuario(String usuario, String clave) {
        Timber.d("LoginViewModel : " + usuario + "/ pasword : " + clave);
        if (usuario.isEmpty()) {
            validateUsuario.postValue("Ingrese Usuario");
            return;
        } else if (clave.isEmpty()) {
            validateUsuario.postValue("Ingrese Clave");
            return;
        }
        Timber.d("ingresoDatosCorrectos");
        loginRepository.initLoginUser(usuario, clave, validateUsuario,onLoginOK,onUserPreferencia);
    }
}
