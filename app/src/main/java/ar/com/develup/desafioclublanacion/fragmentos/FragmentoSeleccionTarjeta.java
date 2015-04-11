package ar.com.develup.desafioclublanacion.fragmentos;

import android.content.Context;
import android.os.Bundle;
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

            seleccionarTarjetaClasica();

        }
    };

    private View.OnClickListener seleccionarPremium = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            seleccionarTarjetaPremium();
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

    @Override
    public void onResume() {

        super.onResume();

        if (Preferencias.existeString(getActivity(), Preferencias.TARJETA)) {

            tarjetaSeleccionada = Tarjeta.valueOf(Preferencias.obtenerString(getActivity(), Preferencias.TARJETA));

            if (tarjetaSeleccionada == Tarjeta.PREMIUM) {
                seleccionarTarjetaPremium();
            }
            else if (tarjetaSeleccionada == Tarjeta.CLASICA) {
                seleccionarTarjetaClasica();
            }
        }
    }

    private void configurarFuentes() {

        Context context = getActivity();
        FuentesUtil.aplicarFuente(HELVETICA_MEDIUM, (TextView) getView().findViewById(R.id.listo), context);
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

        if (tarjetaSeleccionada == Tarjeta.NINGUNA) {
            mostrarDialogoSeleccionarTarjeta();
        }
        else {
            guardarTarjetaSeleccionada();
        }
    }

    @Override
    public boolean tieneAdelante() {
        return true;
    }

    @Override
    public boolean tieneAtras() {
        return false;
    }

    private void guardarTarjetaSeleccionada() {

        Preferencias.guardar(getActivity(), Preferencias.TARJETA, String.valueOf(tarjetaSeleccionada));
    }

    private void mostrarDialogoSeleccionarTarjeta() {

        new MaterialDialog.Builder(getActivity())
                .content(getString(R.string.selecciona_tarjeta_para_continuar))
                .positiveText(R.string.aceptar)
                .contentColorRes(android.R.color.background_dark)
                .positiveColorRes(android.R.color.background_dark)
                .show();
    }

    private void seleccionarTarjetaPremium() {
        tarjetaClasica.setSelected(false);
        tarjetaPremium.setSelected(true);
        tarjetaSeleccionada = Tarjeta.PREMIUM;
    }

    private void seleccionarTarjetaClasica() {
        tarjetaPremium.setSelected(false);
        tarjetaClasica.setSelected(true);
        tarjetaSeleccionada = Tarjeta.CLASICA;
    }
}
