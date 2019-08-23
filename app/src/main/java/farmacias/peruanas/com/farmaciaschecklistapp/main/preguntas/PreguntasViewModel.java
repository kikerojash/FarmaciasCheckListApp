package farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import farmacias.peruanas.com.farmaciaschecklistapp.base.AbsentLiveData;
import farmacias.peruanas.com.farmaciaschecklistapp.base.Resource;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Checklist;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Instancia;
import timber.log.Timber;

class PreguntasViewModel extends ViewModel {

    private PreguntasRepository preguntasRepository;


    MutableLiveData<String> query = new MutableLiveData<>();
    LiveData<Resource<List<Instancia>>> instanciaLiveData = getInstanciaLiveData();


    PreguntasViewModel(PreguntasRepository preguntasRepository) {
        this.preguntasRepository = preguntasRepository;
    }

    LiveData<Resource<List<Instancia>>> getInstanciaLiveData() {
        return instanciaLiveData = Transformations.switchMap(query, search -> {
            if (search == null || search.trim().length() == 0) {
                return AbsentLiveData.create();
            } else {
                return preguntasRepository.getInstaceListaLiveData(search);
            }
        });
    }


    void obtenerData(String dataString) {
        Timber.d("dataString : "+ dataString);
        query.setValue(dataString);
    }
}
