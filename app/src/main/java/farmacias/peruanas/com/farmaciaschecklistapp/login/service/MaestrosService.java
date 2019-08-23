package farmacias.peruanas.com.farmaciaschecklistapp.login.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MaestrosService {

    @GET("obtenerMaestros")
    Call<MaestrosResponse> obtenerMaestros();
}
