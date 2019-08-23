package farmacias.peruanas.com.farmaciaschecklistapp.login.service;

import java.util.List;

import farmacias.peruanas.com.farmaciaschecklistapp.model.Instancia;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RespuestaService {

    @POST("responder")
    Call<RespuestaChecklistResponse> enviarRespuestaChecklist(@Body Instancia instancia);

    @POST("subirImagenRespuesta")
    @Multipart
    Call<SubirImagenResponse> enviarImagen(@Part("idRespuestaItem") long idRespuestaItem,
                                           @Part("imagen\"; filename=\"imagen.jpg\" ") RequestBody imagen);

    @GET("obtenerRespuestas")
    Call<List<Instancia>> obtenerRespuestas();

}
