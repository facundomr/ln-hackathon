package ar.com.develup.desafioclublanacion.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Calendar;

import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.util.DistanciaUtil;
import ar.com.develup.desafioclublanacion.util.FuentesUtil;
import ar.com.develup.desafioclublanacion.util.Preferencias;

import static ar.com.develup.desafioclublanacion.util.FuentesUtil.Fuente.HELVETICA_BOLD;
import static ar.com.develup.desafioclublanacion.util.FuentesUtil.Fuente.HELVETICA_LIGHT;

/**
 * Created by mmaisano on 10/04/15.
 */
public class FragmentoConfiguracionExtra extends FragmentoConfiguracion {

    public static final int MAX_DISTANCIA = 5000;
    private static final int MAX_NOTIFICACIONES = 10;
    private View layoutDistanciaMaxima;
    private View layoutMaximasNotificaciones;
    private View layoutRangoHorario;
    private TextView valorDistanciaMaxima;
    private TextView valorMaximasNotificaciones;
    private TextView valorRangoHorario;
    private Integer distanciaMaxima;
    private Integer maximasNotificaciones;
    private Calendar desdeHora;
    private Calendar hastaHora;
    private View.OnClickListener cambiarDistanciaMaxima = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mostrarDialogoDistanciaMaxima();
        }
    };
    private View.OnClickListener cambiarMaximasNotificaciones = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mostrarDialogoMaximasNotificaciones();
        }
    };
    private View.OnClickListener cambiarRangoHorario = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    protected int getLayout() {
        return R.layout.fragmento_configuracion_extra;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        this.layoutDistanciaMaxima = view.findViewById(R.id.layout_distancia_maxima);
        this.layoutMaximasNotificaciones = view.findViewById(R.id.layout_maximo_notificaciones);
        this.layoutRangoHorario = view.findViewById(R.id.layout_horario_habilitado);
        this.valorDistanciaMaxima = (TextView) view.findViewById(R.id.valor_distancia_maxima);
        this.valorMaximasNotificaciones = (TextView) view.findViewById(R.id.valor_maximas_notificaciones);
        this.valorRangoHorario = (TextView) view.findViewById(R.id.valor_horario_habilitado);

        configurarFuentes();

        this.desdeHora = Calendar.getInstance();
        this.hastaHora = Calendar.getInstance();

        this.layoutDistanciaMaxima.setOnClickListener(this.cambiarDistanciaMaxima);
        this.layoutMaximasNotificaciones.setOnClickListener(this.cambiarMaximasNotificaciones);
        this.layoutRangoHorario.setOnClickListener(this.cambiarRangoHorario);
    }


    private void configurarFuentes() {

        Context context = getActivity();
        FuentesUtil.aplicarFuente(HELVETICA_LIGHT, (TextView) getView().findViewById(R.id.label_cercania), context);
        FuentesUtil.aplicarFuente(HELVETICA_LIGHT, (TextView) getView().findViewById(R.id.label_maximas_notificaciones), context);
        FuentesUtil.aplicarFuente(HELVETICA_LIGHT, (TextView) getView().findViewById(R.id.label_horario_habilitado), context);
        FuentesUtil.aplicarFuente(HELVETICA_BOLD, this.valorDistanciaMaxima, context);
        FuentesUtil.aplicarFuente(HELVETICA_BOLD, this.valorMaximasNotificaciones, context);
        FuentesUtil.aplicarFuente(HELVETICA_BOLD, this.valorRangoHorario, context);
    }

    @Override
    public void onResume() {

        super.onResume();

        distanciaMaxima = Preferencias.getDistanciaMaxima(getActivity());
        maximasNotificaciones = Preferencias.getMaximasNotificaciones(getActivity());

        actualizarEnPantalla();
    }

    private void actualizarEnPantalla() {

        this.valorDistanciaMaxima.setText(darFormatoDistanciaMaxima(this.distanciaMaxima));
        this.valorMaximasNotificaciones.setText(darFormatoMaximasNotificaciones(this.maximasNotificaciones));

    }

    private String darFormatoMaximasNotificaciones(Integer maximasNotificaciones) {

        String notificacionesConFormato = "";

        if (maximasNotificaciones.equals(Integer.MAX_VALUE)) {
            notificacionesConFormato = getString(R.string.notificaciones_ilimitadas);
        }
        else {
            notificacionesConFormato = maximasNotificaciones + " " + getString(R.string.notificaciones);
        }

        return notificacionesConFormato;
    }

    private String darFormatoDistanciaMaxima(Integer distanciaMaxima) {

        String distanciaConFormato = "";

        if (distanciaMaxima.equals(Integer.MAX_VALUE)) {
            distanciaConFormato = getString(R.string.ilimitado);
        }
        else {
            distanciaConFormato = DistanciaUtil.getDistanciaConFormato(distanciaMaxima);
        }

        return distanciaConFormato;
    }

    public void mostrarDialogoDistanciaMaxima() {

        View preferencias = View.inflate(getActivity(), R.layout.layout_dialogo_distancia_maxima, null);
        final SeekBar radioSeekBar = (SeekBar) preferencias.findViewById(R.id.radioSeekBar);
        final TextView radioValue = (TextView) preferencias.findViewById(R.id.radio_value);
        radioValue.setText(darFormatoDistanciaMaxima(this.distanciaMaxima));
        radioSeekBar.setMax(MAX_DISTANCIA);
        radioSeekBar.setProgress(distanciaMaxima.equals(Integer.MAX_VALUE) ? radioSeekBar.getMax() : distanciaMaxima - 1);

        radioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == radioSeekBar.getMax()) {
                    radioValue.setText(darFormatoDistanciaMaxima(Integer.MAX_VALUE));
                }
                else{
                    radioValue.setText(darFormatoDistanciaMaxima(progress + 1));
                }
            }

            @Override
            public void onStartTrackingTouch (SeekBar seekBar){
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(getString(R.string.dialogo_radio_titulo))
                .positiveText(R.string.aceptar)
                .positiveColor(getResources().getColor(android.R.color.tertiary_text_dark))
                .negativeColor(getResources().getColor(android.R.color.tertiary_text_dark))
                .negativeText(getString(R.string.cancelar))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        super.onPositive(dialog);
                        if (radioSeekBar.getProgress() == radioSeekBar.getMax()) {
                            distanciaMaxima = Integer.MAX_VALUE;
                        }
                        else {
                            distanciaMaxima = radioSeekBar.getProgress() + 1;
                        }
                        actualizarEnPantalla();
                    }
                })
                .customView(preferencias, false);

        builder.build().show();
    }

    public void mostrarDialogoMaximasNotificaciones() {

        View preferencias = View.inflate(getActivity(), R.layout.layout_dialogo_maximas_notificaciones, null);
        final SeekBar radioSeekBar = (SeekBar) preferencias.findViewById(R.id.notificacionesSeekBar);
        final TextView notificacionesValue = (TextView) preferencias.findViewById(R.id.notificaciones_value);
        notificacionesValue.setText(darFormatoMaximasNotificaciones(this.maximasNotificaciones));
        radioSeekBar.setMax(MAX_NOTIFICACIONES);
        radioSeekBar.setProgress(maximasNotificaciones.equals(Integer.MAX_VALUE) ? radioSeekBar.getMax() : maximasNotificaciones - 1);

        radioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == radioSeekBar.getMax()) {
                    notificacionesValue.setText(darFormatoMaximasNotificaciones(Integer.MAX_VALUE));
                }
                else{
                    notificacionesValue.setText(darFormatoMaximasNotificaciones(progress + 1));
                }
            }

            @Override
            public void onStartTrackingTouch (SeekBar seekBar){
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(getString(R.string.dialogo_maximas_notificaciones_titulo))
                .positiveText(R.string.aceptar)
                .positiveColor(getResources().getColor(android.R.color.tertiary_text_dark))
                .negativeColor(getResources().getColor(android.R.color.tertiary_text_dark))
                .negativeText(getString(R.string.cancelar))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        super.onPositive(dialog);
                        if (radioSeekBar.getProgress() == radioSeekBar.getMax()) {
                            maximasNotificaciones = Integer.MAX_VALUE;
                        }
                        else {
                            maximasNotificaciones = radioSeekBar.getProgress() + 1;
                        }
                        actualizarEnPantalla();
                    }
                })
                .customView(preferencias, false);

        builder.build().show();
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

        Preferencias.guardarDistanciaMaxima(getActivity(), this.distanciaMaxima);
        Preferencias.guardarNotificacionesMaximas(getActivity(), this.maximasNotificaciones);
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
