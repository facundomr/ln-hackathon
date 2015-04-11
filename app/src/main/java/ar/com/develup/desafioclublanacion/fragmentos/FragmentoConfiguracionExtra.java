package ar.com.develup.desafioclublanacion.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Calendar;

import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.servicios.ServicioDeBeneficiosCercanos;
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
            mostrarDialogoRangoHorario();
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
        desdeHora = Preferencias.getNotificacionesDesdeHora(getActivity());
        hastaHora = Preferencias.getNotificacionesHastaHora(getActivity());

        actualizarEnPantalla();
    }

    private void actualizarEnPantalla() {

        this.valorDistanciaMaxima.setText(darFormatoDistanciaMaxima(this.distanciaMaxima));
        this.valorMaximasNotificaciones.setText(darFormatoMaximasNotificaciones(this.maximasNotificaciones));
        this.valorRangoHorario.setText(darFormatoRangoHorario());
    }

    private String darFormatoRangoHorario() {
        return desdeHora.get(Calendar.HOUR_OF_DAY) + ":" + darFormatoMinuto(desdeHora.get(Calendar.MINUTE)) + "hs. - " +
                hastaHora.get(Calendar.HOUR_OF_DAY) + ":" + darFormatoMinuto(hastaHora.get(Calendar.MINUTE)) + "hs.";
    }

    private String darFormatoMinuto(Integer minuto) {

        String formatoMinuto = minuto.toString();

        if (minuto < 10) {
            formatoMinuto = "0" + formatoMinuto;
        }

        return formatoMinuto;
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

        View viewDialogoDistanciaMaxima = View.inflate(getActivity(), R.layout.layout_dialogo_distancia_maxima, null);
        final SeekBar radioSeekBar = (SeekBar) viewDialogoDistanciaMaxima.findViewById(R.id.radioSeekBar);
        final TextView radioValue = (TextView) viewDialogoDistanciaMaxima.findViewById(R.id.radio_value);
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
                .customView(viewDialogoDistanciaMaxima, false);

        builder.build().show();
    }

    public void mostrarDialogoMaximasNotificaciones() {

        View viewDialogoMaximasNotificaciones = View.inflate(getActivity(), R.layout.layout_dialogo_maximas_notificaciones, null);
        final SeekBar radioSeekBar = (SeekBar) viewDialogoMaximasNotificaciones.findViewById(R.id.notificacionesSeekBar);
        final TextView notificacionesValue = (TextView) viewDialogoMaximasNotificaciones.findViewById(R.id.notificaciones_value);
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
                .customView(viewDialogoMaximasNotificaciones, false);

        builder.build().show();
    }

    private void mostrarDialogoRangoHorario() {

        View viewDialogoRangoHorario = View.inflate(getActivity(), R.layout.layout_dialogo_rango_horario, null);
        final TimePicker desde = (TimePicker) viewDialogoRangoHorario.findViewById(R.id.timepicker_desde);
        final TimePicker hasta = (TimePicker) viewDialogoRangoHorario.findViewById(R.id.timepicker_hasta);
        desde.setIs24HourView(true);
        hasta.setIs24HourView(true);
        desde.setCurrentHour(desdeHora.get(Calendar.HOUR_OF_DAY));
        desde.setCurrentMinute(desdeHora.get(Calendar.MINUTE));
        hasta.setCurrentHour(hastaHora.get(Calendar.HOUR_OF_DAY));
        hasta.setCurrentMinute(hastaHora.get(Calendar.MINUTE));

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .positiveText(R.string.aceptar)
                .positiveColor(getResources().getColor(android.R.color.tertiary_text_dark))
                .negativeColor(getResources().getColor(android.R.color.tertiary_text_dark))
                .negativeText(getString(R.string.cancelar))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        if (valido(desde, hasta)) {

                            desdeHora.set(Calendar.HOUR_OF_DAY, desde.getCurrentHour());
                            desdeHora.set(Calendar.MINUTE, desde.getCurrentMinute());

                            hastaHora.set(Calendar.HOUR_OF_DAY, hasta.getCurrentHour());
                            hastaHora.set(Calendar.MINUTE, hasta.getCurrentMinute());
                            
                            actualizarEnPantalla();
                        }
                        else {

                            Toast.makeText(getActivity(), getString(R.string.horario_invalido), Toast.LENGTH_SHORT).show();
                        }
                    }

                    private boolean valido(TimePicker desde, TimePicker hasta) {
                        
                        int horaDesde = desde.getCurrentHour();
                        int minutoDesde = desde.getCurrentMinute();
                        int horaHasta = hasta.getCurrentHour();
                        int minutoHasta = hasta.getCurrentMinute();

                        boolean acepta = (horaDesde < horaHasta)
                                            || (horaDesde == horaHasta && minutoDesde < minutoHasta);
                        
                        return acepta;
                    }
                })
                .customView(viewDialogoRangoHorario, true);

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
        Preferencias.guardarNotificacionesDesde(getActivity(), this.desdeHora);
        Preferencias.guardarNotificacionesHasta(getActivity(), this.hastaHora);

        getActivity().stopService(new Intent(getActivity(), ServicioDeBeneficiosCercanos.class));
        getActivity().startService(new Intent(getActivity(), ServicioDeBeneficiosCercanos.class));
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
