package ar.com.develup.desafioclublanacion.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Beneficio implements Serializable {

    private String id;
    @SerializedName("point")
    private Punto punto;
    private String imagen;
    private Date desde;
    private Date hasta;
    private Establecimiento establecimiento;
    @SerializedName("beneficio")
    private DetalleBeneficio detalle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Punto getPunto() {
        return punto;
    }

    public void setPunto(Punto punto) {
        this.punto = punto;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUrlImagen() {

        String campoEncontrado = "";

        String[] campos = this.imagen.split(":");
        for (String campo : campos) {
            if (campo.contains("nombre=")) {

                campoEncontrado = campo;
            }
        }

        String[] labelYNombre = campoEncontrado.split("=");

        return labelYNombre[1];
    }

}