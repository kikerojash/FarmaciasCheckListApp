package farmacias.peruanas.com.farmaciaschecklistapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "checklist")
public class Checklist {

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private long id;
    @SerializedName("bienvenida")
    private String bienvenida;
    @SerializedName("finalizacion")
    private String finalizacion;
    @SerializedName("fechaCreacion")
    private String fechaCreacion;
    @SerializedName("requiereAvance")
    private boolean requiereAvance;
    @SerializedName("tiempoVida")
    private int tiempoVida;
    @SerializedName("requiereLocal")
    private boolean requiereLocal;
    @SerializedName("requiereQF")
    private boolean requiereQF;
    @SerializedName("requiereAprobacionQF")
    private boolean requiereAprobacionQF;
    @SerializedName("requiereTecnico")
    private boolean requiereTecnico;
    @SerializedName("requiereAprobacionTecnico")
    private boolean requiereAprobacionTecnico;
    @SerializedName("requiereAprobacionUsuario")
    private boolean requiereAprobacionUsuario;
    @SerializedName("requiereAprobacionResponsable")
    private boolean requiereAprobacionResponsable;

    public Checklist() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBienvenida() {
        return bienvenida;
    }

    public void setBienvenida(String bienvenida) {
        this.bienvenida = bienvenida;
    }

    public String getFinalizacion() {
        return finalizacion;
    }

    public void setFinalizacion(String finalizacion) {
        this.finalizacion = finalizacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isRequiereAvance() {
        return requiereAvance;
    }

    public void setRequiereAvance(boolean requiereAvance) {
        this.requiereAvance = requiereAvance;
    }

    public int getTiempoVida() {
        return tiempoVida;
    }

    public void setTiempoVida(int tiempoVida) {
        this.tiempoVida = tiempoVida;
    }

    public boolean isRequiereLocal() {
        return requiereLocal;
    }

    public void setRequiereLocal(boolean requiereLocal) {
        this.requiereLocal = requiereLocal;
    }

    public boolean isRequiereQF() {
        return requiereQF;
    }

    public void setRequiereQF(boolean requiereQF) {
        this.requiereQF = requiereQF;
    }

    public boolean isRequiereAprobacionQF() {
        return requiereAprobacionQF;
    }

    public void setRequiereAprobacionQF(boolean requiereAprobacionQF) {
        this.requiereAprobacionQF = requiereAprobacionQF;
    }

    public boolean isRequiereTecnico() {
        return requiereTecnico;
    }

    public void setRequiereTecnico(boolean requiereTecnico) {
        this.requiereTecnico = requiereTecnico;
    }

    public boolean isRequiereAprobacionTecnico() {
        return requiereAprobacionTecnico;
    }

    public void setRequiereAprobacionTecnico(boolean requiereAprobacionTecnico) {
        this.requiereAprobacionTecnico = requiereAprobacionTecnico;
    }

    public boolean isRequiereAprobacionUsuario() {
        return requiereAprobacionUsuario;
    }

    public void setRequiereAprobacionUsuario(boolean requiereAprobacionUsuario) {
        this.requiereAprobacionUsuario = requiereAprobacionUsuario;
    }

    public boolean isRequiereAprobacionResponsable() {
        return requiereAprobacionResponsable;
    }

    public void setRequiereAprobacionResponsable(boolean requiereAprobacionResponsable) {
        this.requiereAprobacionResponsable = requiereAprobacionResponsable;
    }
}
