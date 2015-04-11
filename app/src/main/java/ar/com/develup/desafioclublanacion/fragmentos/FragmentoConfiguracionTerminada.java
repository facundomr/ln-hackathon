package ar.com.develup.desafioclublanacion.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.util.FuentesUtil;

import static ar.com.develup.desafioclublanacion.util.FuentesUtil.Fuente.HELVETICA_BOLD;
import static ar.com.develup.desafioclublanacion.util.FuentesUtil.Fuente.HELVETICA_LIGHT;

public class FragmentoConfiguracionTerminada extends FragmentoConfiguracion {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getActivity();
        FuentesUtil.aplicarFuente(HELVETICA_LIGHT, (TextView) getView().findViewById(R.id.listo), context);
        FuentesUtil.aplicarFuente(HELVETICA_LIGHT, (TextView) getView().findViewById(R.id.ya_estas_listo), context);
        FuentesUtil.aplicarFuente(HELVETICA_BOLD, (TextView) getView().findViewById(R.id.gracias), context);
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
        return false;
    }

    @Override
    public boolean tieneAtras() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragmento_configuracion_terminada;
    }

    @Override
    public String getTitulo() {
        return null;
    }
}