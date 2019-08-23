package farmacias.peruanas.com.farmaciaschecklistapp.login.service;

import farmacias.peruanas.com.farmaciaschecklistapp.model.Usuario;

public class LoginResponse {
    private boolean success;
    private Usuario usuario;
    private String sessionKey;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
