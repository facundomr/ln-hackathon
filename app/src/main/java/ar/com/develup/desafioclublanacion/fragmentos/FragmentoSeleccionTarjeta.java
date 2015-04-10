package ar.com.develup.desafioclublanacion.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.modelo.Tarjeta;
import ar.com.develup.desafioclublanacion.util.FuentesUtil;
import ar.com.develup.desafioclublanacion.util.Preferencias;

import static ar.com.develup.desafioclublanacion.util.FuentesUtil.Fuente.HELVETICA_LIGHT;
import static ar.com.develup.desafioclublanacion.util.FuentesUtil.Fuente.HELVETICA_MEDIUM;

/**
 * Created by mmaisano on 10/04/15.
 */
public class FragmentoSeleccionTarjeta extends FragmentoConfiguracion {

    private static final String LOG_TAG = FragmentoSeleccionTarjeta.class.getSimpleName();
    private Tarjeta tarjetaSeleccionada = Tarjeta.NINGUNA;
    private View tarjetaClasica;
    private View tarjetaPremium;
    private View.OnClickListener seleccionarClasica = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            tarjetaPremium.setSelected(false);
            tarjetaClasica.setSelected(true);
            tarjetaSeleccionada = Tarjeta.CLASICA;

        }
    };
    private View.OnClickListener seleccionarPremium = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            tarjetaClasica.setSelected(false);
            tarjetaPremium.setSelected(true);
            tarjetaSeleccionada = Tarjeta.PREMIUM;
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.fragmento_seleccion_tarjeta;
    }

    @Override
    public String getTitulo() {
        return "";
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        this.tarjetaClasica = view.findViewById(R.id.classic);
        this.tarjetaPremium = view.findViewById(R.id.premium);

        this.tarjetaClasica.setOnClickListener(this.seleccionarClasica);
        this.tarjetaPremium.setOnClickListener(this.seleccionarPremium);
        configurarFuentes();
    }

    private void configurarFuentes() {

        Context context = getActivity();
        FuentesUtil.aplicarFuente(HELVETICA_MEDIUM, (TextView) getView().findViewById(R.id.bienvenido_a), context);
        FuentesUtil.aplicarFuente(HELVETICA_MEDIUM, (TextView) getView().findViewById(R.id.nombre_aplicacion), context);
        FuentesUtil.aplicarFuente(HELVETICA_LIGHT, (TextView) getView().findViewById(R.id.descripcion_aplicacion), context);
        FuentesUtil.aplicarFuente(HELVETICA_LIGHT, (TextView) getView().findViewById(R.id.selecciona_tarjetas), context);

    }

    @Override
    public boolean valido() {

        boolean valido = tarjetaSeleccionada != Tarjeta.NINGUNA;

        if (!valido) {
            mostrarDialogoSeleccionarTarjeta();
        }

        return valido;
    }

    @Override
    public void guardarCambios() {

        Log.i(LOG_TAG, "Guardando cambios");

        if (tarjetaSeleccionada == Tarjeta.NINGUNA) {
            mostrarDialogoSeleccionarTarjeta();
        }
        else {
            guardarTarjetaSeleccionada();
        }
    }

    private void guardarTarjetaSeleccionada() {

        Preferencias.guardar(getActivity(), Preferencias.TARJETA, String.valueOf(tarjetaSeleccionada));
    }

    private void mostrarDialogoSeleccionarTarjeta() {

        new MaterialDialog.Builder(getActivity())
                .content(getString(R.string.selecciona_tarjeta_para_continuar))
                .positiveText(R.string.aceptar)
                .show();
    }
}
