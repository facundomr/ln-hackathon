package ar.com.develup.desafioclublanacion.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.util.FuentesUtil;

import static ar.com.develup.desafioclublanacion.util.FuentesUtil.Fuente.HELVETICA_BOLD;
import static ar.com.develup.desafioclublanacion.util.FuentesUtil.Fuente.HELVETICA_LIGHT;

/**
 * Created by mmaisano on 10/04/15.
 */
public class FragmentoConfiguracionExtra extends FragmentoConfiguracion {

    @Override
    protected int getLayout() {
        return R.layout.fragmento_configuracion_extra;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configurarFuentes();
    }

    private void configurarFuentes() {

        Context context = getActivity();
        FuentesUtil.aplicarFuente(HELVETICA_LIGHT, (TextView) getView().findViewById(R.id.label_cercania), context);
        FuentesUtil.aplicarFuente(HELVETICA_LIGHT, (TextView) getView().findViewById(R.id.label_maximas_notificaciones), context);
        FuentesUtil.aplicarFuente(HELVETICA_LIGHT, (TextView) getView().findViewById(R.id.label_horario_habilitado), context);
        FuentesUtil.aplicarFuente(HELVETICA_BOLD, (TextView) getView().findViewById(R.id.valor_distancia_maxima), context);
        FuentesUtil.aplicarFuente(HELVETICA_BOLD, (TextView) getView().findViewById(R.id.valor_maximas_notificaciones), context);
        FuentesUtil.aplicarFuente(HELVETICA_BOLD, (TextView) getView().findViewById(R.id.valor_horario_habilitado), context);

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

    @Override
    public boolean tieneAdelante() {
        return true;
    }

    @Override
    public boolean tieneAtras() {
        return true;
    }
}
