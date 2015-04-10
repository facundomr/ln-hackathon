package ar.com.develup.desafioclublanacion.actividades;

import android.content.Intent;
import android.os.Bundle;

import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.util.Preferencias;

public class ActividadPrincipal extends ActividadBasica {

    @Override
    protected int getLayout() {
        return R.layout.actividad_principal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (hayConfiguracion()) {
            //TODO:Immplementar
        }
        else {

            Intent intent = new Intent(this, ActividadConfiguracionInicial.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean hayConfiguracion() {

        return hayTarjetaSeleccionada() && hayCategoriasSeleccionadas();
    }

    private boolean hayCategoriasSeleccionadas() {
        return true;
    }

    private boolean hayTarjetaSeleccionada() {
        return Preferencias.existeString(this,Preferencias.TARJETA);
    }
}
