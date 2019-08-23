package farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import farmacias.peruanas.com.farmaciaschecklistapp.R;
import farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas.adapter.holder.PreguntasHolder;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Instancia;

public class PreguntasAdapter extends RecyclerView.Adapter<PreguntasHolder> {

    private List<Instancia> instanciaList;


    @NonNull
    @Override
    public PreguntasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preguntas, parent, false);
        return new PreguntasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreguntasHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return instanciaList.size();
    }
}
