package farmacias.peruanas.com.farmaciaschecklistapp.main.respuestas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import farmacias.peruanas.com.farmaciaschecklistapp.R;

public class RespuestasFragment extends Fragment {

    public static RespuestasFragment newInstance() {
        RespuestasFragment preguntasFragment = new RespuestasFragment();
        Bundle args = new Bundle();
        preguntasFragment.setArguments(args);
        return preguntasFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_respuestas, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initAdapter();
    }
}
