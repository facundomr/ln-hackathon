package ar.com.develup.desafioclublanacion.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetalleBeneficio implements Serializable {

    private Long id;
    private String nombre;
    @SerializedName("tarjeta")
    private Tarjetas tarjetas;
    private String tipo;
    private Categoria categoria;
    private String subcategoria;
    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Tarjetas getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(Tarjetas tarjetas) {
        this.tarjetas = tarjetas;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}