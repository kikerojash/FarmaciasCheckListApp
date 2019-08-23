package farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import farmacias.peruanas.com.farmaciaschecklistapp.Injection;
import farmacias.peruanas.com.farmaciaschecklistapp.R;
import farmacias.peruanas.com.farmaciaschecklistapp.base.Resource;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Instancia;
import timber.log.Timber;

public class PreguntasFragment extends Fragment {

    @BindView(R.id.reciclador)
    RecyclerView reciclador;


    private PreguntasViewModel preguntasViewModel;
    private PreguntasFactory preguntasFactory;
    private Unbinder unbinder;

    public static PreguntasFragment newInstance() {
        PreguntasFragment preguntasFragment = new PreguntasFragment();
        Bundle args = new Bundle();
        preguntasFragment.setArguments(args);
        return preguntasFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_preguntas, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // preguntasFactory = Injection.providePreguntasViewModelFactory(requireContext());
    //    preguntasViewModel = new ViewModelProvider(this, preguntasFactory).get(PreguntasViewModel.class);

       // initAdapter();
      //  initViewModel();
    }

    private void initViewModel() {
        preguntasViewModel.obtenerData("obtenerData");
        preguntasViewModel.getInstanciaLiveData().observe(this, this::getListaCheckList);

    }

    private void getListaCheckList(Resource<List<Instancia>> genreResource) {
        if (genreResource != null) {
            switch (genreResource.status) {
                case LOADING:
                    Timber.d("LOADING");
                    break;
                case SUCCESS:
                    Timber.d("SUCCESS");
                    break;
                case ERROR:
                    Timber.d("ERROR");
                    break;
                default:
                    break;
            }
        }

    }

    private void initAdapter() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
