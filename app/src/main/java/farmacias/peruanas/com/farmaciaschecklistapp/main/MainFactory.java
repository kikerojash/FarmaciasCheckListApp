package farmacias.peruanas.com.farmaciaschecklistapp.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainFactory implements  ViewModelProvider.Factory {

    private MainRepository mainRepository;

    public MainFactory(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(mainRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
