package farmacias.peruanas.com.farmaciaschecklistapp.login.service;


import java.util.List;

import farmacias.peruanas.com.farmaciaschecklistapp.model.Local;

public class MaestrosResponse {

    private List<Local> locales;

    public MaestrosResponse() {
    }

    public List<Local> getLocales() {
        return locales;
    }

    public void setLocales(List<Local> locales) {
        this.locales = locales;
    }
}
