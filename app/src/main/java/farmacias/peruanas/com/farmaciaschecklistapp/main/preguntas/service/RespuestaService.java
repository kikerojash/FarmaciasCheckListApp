package farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas.service;

import androidx.lifecycle.LiveData;

import java.util.List;

import farmacias.peruanas.com.farmaciaschecklistapp.base.ApiResponse;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Instancia;
import retrofit2.http.GET;

public interface RespuestaService {

    @GET("obtenerRespuestas")
    LiveData<ApiResponse<List<Instancia>>> listarChecklists();
}
