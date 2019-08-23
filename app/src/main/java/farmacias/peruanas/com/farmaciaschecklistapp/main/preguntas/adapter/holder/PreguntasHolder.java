package farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas.adapter.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public class PreguntasHolder extends RecyclerView.ViewHolder {

    public PreguntasHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
