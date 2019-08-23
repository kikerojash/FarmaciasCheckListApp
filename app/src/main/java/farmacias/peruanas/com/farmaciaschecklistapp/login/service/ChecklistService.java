package farmacias.peruanas.com.farmaciaschecklistapp.login.service;

import androidx.lifecycle.LiveData;

import java.util.List;

import farmacias.peruanas.com.farmaciaschecklistapp.base.ApiResponse;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Checklist;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ChecklistService {

    @GET("listar")
    Call<List<Checklist>> listarChecklists();
    //LiveData<ApiResponse<List<Checklist>>> listarChecklists();
}
