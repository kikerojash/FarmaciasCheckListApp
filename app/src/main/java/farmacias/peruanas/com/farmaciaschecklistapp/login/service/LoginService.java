package farmacias.peruanas.com.farmaciaschecklistapp.login.service;

import farmacias.peruanas.com.farmaciaschecklistapp.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    String PREFERENCES_LOGIN = "PREFERENCES_LOGIN";
    String SESSION_KEY = "SESSION_KEY";

    @POST("loginMobile")
    Call<LoginResponse> login(@Body Usuario usuario);
}
