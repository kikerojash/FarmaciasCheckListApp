package farmacias.peruanas.com.farmaciaschecklistapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;

@Entity(tableName = "instancia")
public class Instancia {

    public static final int CREADO = 0;
    public static final int GUARDADO = 1;
    public static final int ENVIADO = 2;
    public static final int CANCELADO = 3;

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private long id;
    @SerializedName("estado")
    private String estado;
    @SerializedName("fechaCreacion")
    private String fechaCreacion;
    @SerializedName("fechaGuardado")
    private String fechaGuardado;
    @SerializedName("fechaFinalizacion")
    private String fechaFinalizacion; //Date
    @SerializedName("idServidor")
    private String idServidor;
    @SerializedName("pendienteSincronizacion")
    private String pendienteSincronizacion;
    @SerializedName("aprobacionQF")
    private String aprobacionQF;
    @SerializedName("aprobacionT")
    private String aprobacionT;
    @SerializedName("aprobacionU")
    private String aprobacionU;


  /*
    public String getNombreEstadoFecha() {
     String nombreEstadoFecha = "";
        switch (this.estado) {
            case CREADO:
                nombreEstadoFecha = "Creado";
                break;
            case GUARDADO:
                nombreEstadoFecha = "Guardado";
                if (fechaGuardado != null) {
                    nombreEstadoFecha += (" el " + dateFormat.format(fechaGuardado));
                }
                break;
            case ENVIADO:
                //nombreEstadoFecha = pendienteSincronizacion ? "Pendiente de sincronizar" : "Enviado";
                nombreEstadoFecha ="";
                if (fechaFinalizacion != null) {
                    nombreEstadoFecha += (" el " + dateFormat.format(fechaFinalizacion));
                }
                break;
        }
        return nombreEstadoFecha;
    }

    public Instancia() {
        this.estado = CREADO;
    }

    public Instancia(long id, int estado) {
        this.id = id;
        this.estado = estado;
    }*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaGuardado() {
        return fechaGuardado;
    }

    public void setFechaGuardado(String fechaGuardado) {
        this.fechaGuardado = fechaGuardado;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(String idServidor) {
        this.idServidor = idServidor;
    }

    public String getPendienteSincronizacion() {
        return pendienteSincronizacion;
    }

    public void setPendienteSincronizacion(String pendienteSincronizacion) {
        this.pendienteSincronizacion = pendienteSincronizacion;
    }

    public String getAprobacionQF() {
        return aprobacionQF;
    }

    public void setAprobacionQF(String aprobacionQF) {
        this.aprobacionQF = aprobacionQF;
    }

    public String getAprobacionT() {
        return aprobacionT;
    }

    public void setAprobacionT(String aprobacionT) {
        this.aprobacionT = aprobacionT;
    }

    public String getAprobacionU() {
        return aprobacionU;
    }

    public void setAprobacionU(String aprobacionU) {
        this.aprobacionU = aprobacionU;
    }
}
