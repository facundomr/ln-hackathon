package ar.com.develup.desafioclublanacion.fragmentos;

import ar.com.develup.desafioclublanacion.R;

/**
 * Created by mmaisano on 10/04/15.
 */
public class FragmentoConfiguracionFinalizada extends FragmentoConfiguracion {

    @Override
    protected int getLayout() {
        return R.layout.fragmento_configuracion_finalizada;
    }

    @Override
    public String getTitulo() {
        return "";
    }

    @Override
    public boolean valido() {
        return true;
    }

    @Override
    public void guardarCambios() {

    }
}
