package ar.com.develup.desafioclublanacion.modelo;

import java.util.Date;

public class Beneficio {

    private String id;
    private String nombre;

    // Esto viene así "tarjeta":"Classic-Premium"
    private Tarjeta tarjeta;

    private String tipo;
    private Categoria categoria;
    private String subcategoria;

    // Esto viene así "point":[-34.53258,-58.46859]
    private Punto punto;

    // Esto viene así nombre=44265.jpg:Tipo=2:Great=0-nombre=I733649.jpg:Tipo=15:Great=0
    private String urlImagen;

    private Date desde;
    private Date hasta;

    private Establecimiento establecimiento;





}
