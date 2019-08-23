package farmacias.peruanas.com.farmaciaschecklistapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users")
public class Usuario {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    public long id;
    @SerializedName(value = "login")
    public String login;
    @SerializedName(value = "clave")
    public String clave;
    @SerializedName(value = "cod_usu")
    public String cod_usu;
    @SerializedName(value = "sessionKey")
    public String sessionKey;
    @SerializedName(value = "local")
    private String local;
    @SerializedName(value = "tokenUsu")
    private String tokenUsu;
    @SerializedName(value = "codEmpresa")
    private String codEmpresa;

    public Usuario() {
    }

    public Usuario(String cod_usu, String login, String clave, String sessionKey, String codEmpresa) {
        this.cod_usu = cod_usu;
        this.login = login;
        this.clave = clave;
        this.sessionKey = sessionKey;
        this.codEmpresa = codEmpresa;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTokenUsu() {
        return tokenUsu;
    }

    public void setTokenUsu(String tokenUsu) {
        this.tokenUsu = tokenUsu;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }
}
