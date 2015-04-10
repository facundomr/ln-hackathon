package ar.com.develup.desafioclublanacion.fragmentos;

/**
 * Created by mmaisano on 10/04/15.
 */
public abstract class FragmentoConfiguracion extends FragmentoBasico {

    public abstract boolean valido();
    public abstract void guardarCambios();
    public abstract boolean tieneAdelante();
    public abstract boolean tieneAtras();
}
