package ar.com.develup.desafioclublanacion.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Beneficio implements Serializable {

    private String id;

    @SerializedName("point")
    private Punto punto;

    // Esto viene así nombre=44265.jpg:Tipo=2:Great=0-nombre=I733649.jpg:Tipo=15:Great=0
    //private String urlImagen;

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

    /*
    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
    */

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
}