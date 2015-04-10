package ar.com.develup.desafioclublanacion.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.adaptadores.ConfiguracionInicialAdapter;
import ar.com.develup.desafioclublanacion.servicios.ServicioDeBeneficiosCercanos;

/**
 * Created by mmaisano on 10/04/15.
 */
public class ActividadConfiguracionInicial extends ActividadBasica {

    private static final String LOG_TAG = ActividadConfiguracionInicial.class.getSimpleName();
    private ViewPager configuracionInicialViewPager;
    private ConfiguracionInicialAdapter configuracionInicialAdapter;

    @Override
    protected int getLayout() {
        return R.layout.actividad_configuracion_inicial;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.configuracionInicialViewPager = (ViewPager) findViewById(R.id.instruccionesConfiguracionViewPager);
        this.configuracionInicialAdapter = new ConfiguracionInicialAdapter(getSupportFragmentManager());
        this.configuracionInicialViewPager.setAdapter(this.configuracionInicialAdapter);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(configuracionInicialViewPager);
    }
}
