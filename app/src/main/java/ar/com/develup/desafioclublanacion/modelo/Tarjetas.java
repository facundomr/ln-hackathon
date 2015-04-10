package ar.com.develup.desafioclublanacion.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tarjetas implements Serializable {

    private List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();


    public List<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }

}