package farmacias.peruanas.com.farmaciaschecklistapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "local")
public class Local {
    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private String codigoLocal;
    @SerializedName("descripcion")
    private String descripcion;

    public Local() {
    }

    public String getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(String codigoLocal) {
        this.codigoLocal = codigoLocal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
