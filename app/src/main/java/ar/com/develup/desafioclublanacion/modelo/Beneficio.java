package ar.com.develup.desafioclublanacion.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Beneficio implements Serializable {

    private String id;
    private String nombre;

    // Esto viene así "tarjeta":"Classic-Premium"
    //private Tarjeta tarjeta;

    private String tipo;
    private Categoria categoria;
    private String subcategoria;

    @SerializedName("point")
    private Punto punto;

    // Esto viene así nombre=44265.jpg:Tipo=2:Great=0-nombre=I733649.jpg:Tipo=15:Great=0
    //private String urlImagen;

    private Date desde;
    private Date hasta;
    private Establecimiento establecimiento;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipo() {
        return tipo;
    }

    /*
        public Tarjeta getTarjeta() {
            return tarjeta;
        }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
    */

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
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