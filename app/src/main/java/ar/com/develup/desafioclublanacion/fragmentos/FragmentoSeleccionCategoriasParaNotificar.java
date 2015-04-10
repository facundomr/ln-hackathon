package ar.com.develup.desafioclublanacion.fragmentos;

import ar.com.develup.desafioclublanacion.R;

/**
 * Created by mmaisano on 10/04/15.
 */
public class FragmentoSeleccionCategoriasParaNotificar extends FragmentoConfiguracion {

    @Override
    protected int getLayout() {
        return R.layout.fragmento_seleccion_categorias_para_notificar;
    }

    @Override
    public String getTitulo() {
        return null;
    }

    @Override
    public boolean valido() {
        return true;
    }

    @Override
    public void guardarCambios() {

    }
}
