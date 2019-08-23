package farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

import farmacias.peruanas.com.farmaciaschecklistapp.base.ApiResponse;
import farmacias.peruanas.com.farmaciaschecklistapp.base.AppExecutors;
import farmacias.peruanas.com.farmaciaschecklistapp.base.NetworkBoundResource;
import farmacias.peruanas.com.farmaciaschecklistapp.base.Resource;
import farmacias.peruanas.com.farmaciaschecklistapp.db.CheckListDB;
import farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas.service.RespuestaService;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Instancia;


public class PreguntasRepository {

    private CheckListDB db;
    private AppExecutors appExecutors;
    private RespuestaService respuestaService;

    public PreguntasRepository(RespuestaService respuestaService, CheckListDB db, AppExecutors appExecutors) {
        this.respuestaService = respuestaService;
        this.db = db;
        this.appExecutors = appExecutors;
    }



    /*fun obtenerOrdenLista(id: String?): LiveData<Resource<List<Orden>>> {
        val data = MediatorLiveData<Resource<List<Orden>>>()
        data.value = Resource.loading(null)
        data.addSource(ordenDao.obtenerListaOrden()) {
            if (it != null) {
                data.value = Resource.success(it)
            }
        }
        return data
    }*/


    public boolean shouldFetch(@Nullable List<Instancia> data) {
        return data == null || data.isEmpty();
    }



    /*LiveData<Resource<List<Instancia>>> getInstaceListaLiveData(String query) {
        MediatorLiveData<Resource<List<Instancia>>> result = new MediatorLiveData<>();
        result.setValue(Resource.loading(null));
        //TODO:: Add method to check if data should be saved. This should apply for search data.
        LiveData<List<Instancia>> dbSource = db.checkListDao().obtenerListaCheckList();
        result.addSource(dbSource, data -> {

            Timber.d("PreguntasRepository : " + data.size());
            if (shouldFetch(data)) {
            } else {
            }
        });
        return result;
    }*/

    LiveData<Resource<List<Instancia>>> getInstaceListaLiveData(String data) {
        return new NetworkBoundResource<List<Instancia>, List<Instancia>>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull List<Instancia> item) {
                db.instanciaDao().insertAllOrders(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Instancia> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Instancia>> loadFromDb() {
                return db.instanciaDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Instancia>>> createCall() {
                return respuestaService.listarChecklists();
            }
        }.asLiveData();
    }

}
