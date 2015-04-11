package ar.com.develup.desafioclublanacion.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        }
    };
    private View.OnClickListener cambiarMaximasNotificaciones = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

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
        this.layoutMaximasNotificaciones = view.findViewById(R.id.label_maximas_notificaciones);
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

        this.valorDistanciaMaxima.setText(darFormatoDistanciaMaxima());
        this.valorMaximasNotificaciones.setText(darFormatoMaximasNotificaciones());

    }

    private String darFormatoMaximasNotificaciones() {

        String notificacionesConFormato = "";

        if (this.maximasNotificaciones.equals(Integer.MAX_VALUE)) {
            notificacionesConFormato = getString(R.string.notificaciones_ilimitadas);
        }
        else {
            notificacionesConFormato = this.maximasNotificaciones + " " + getString(R.string.notificaciones);
        }
        
        return notificacionesConFormato;
    }

    private String darFormatoDistanciaMaxima() {

        String distanciaConFormato = "";

        if (this.distanciaMaxima.equals(Integer.MAX_VALUE)) {
            distanciaConFormato = getString(R.string.ilimitado);
        }
        else {
            distanciaConFormato = DistanciaUtil.getDistanciaConFormato(this.distanciaMaxima);
        }

        return distanciaConFormato;
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
