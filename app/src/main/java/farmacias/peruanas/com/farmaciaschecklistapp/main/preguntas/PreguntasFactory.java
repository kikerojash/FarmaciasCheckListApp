package farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PreguntasFactory implements ViewModelProvider.Factory {

    private PreguntasRepository preguntasRepository;

    public PreguntasFactory(PreguntasRepository preguntasRepository) {
        this.preguntasRepository = preguntasRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PreguntasViewModel.class)) {
            return (T) new PreguntasViewModel(preguntasRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
