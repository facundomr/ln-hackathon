package ar.com.develup.desafioclublanacion.actividades;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.fragmentos.FragmentoConfiguracion;
import ar.com.develup.desafioclublanacion.fragmentos.FragmentoConfiguracionExtra;
import ar.com.develup.desafioclublanacion.fragmentos.FragmentoSeleccionCategoriasParaNotificar;
import ar.com.develup.desafioclublanacion.fragmentos.FragmentoSeleccionTarjeta;

/**
 * Created by mmaisano on 10/04/15.
 */
public class ActividadConfiguracionInicial extends ActividadBasica {

    private static final String LOG_TAG = ActividadConfiguracionInicial.class.getSimpleName();
    private List<FragmentoConfiguracion> fragmentos = new LinkedList<>();
    private FragmentoSeleccionTarjeta fragmentoSeleccionTarjeta = new FragmentoSeleccionTarjeta();
    private FragmentoSeleccionCategoriasParaNotificar fragmentoSeleccionCategoriasParaNotificar = new FragmentoSeleccionCategoriasParaNotificar();
    private FragmentoConfiguracionExtra fragmentoConfiguracionExtra = new FragmentoConfiguracionExtra();
    private int indiceFragmentoActual;
    private View siguiente;
    private View anterior;

    private View.OnClickListener siguienteFragmento = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            FragmentoConfiguracion fragmentoActual = fragmentos.get(indiceFragmentoActual);

            if (fragmentoActual.valido()) {

                fragmentoActual.guardarCambios();

                if (indiceFragmentoActual < fragmentos.size() - 1) {
                    indiceFragmentoActual ++;
                    colocarFragmento(fragmentos.get(indiceFragmentoActual));
                }
                else if (indiceFragmentoActual == fragmentos.size() - 1) {

                }
            }
        }
    };

    private View.OnClickListener fragmentoAnterior = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            FragmentoConfiguracion fragmentoActual = fragmentos.get(indiceFragmentoActual);
            fragmentoActual.guardarCambios();

            if (indiceFragmentoActual > 0) {
                indiceFragmentoActual --;
                colocarFragmento(fragmentos.get(indiceFragmentoActual));
            }
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.actividad_configuracion_inicial;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.siguiente = findViewById(R.id.avanzar);
        this.siguiente.setOnClickListener(this.siguienteFragmento);

        this.anterior = findViewById(R.id.retroceder);
        this.anterior.setOnClickListener(this.fragmentoAnterior);

        fragmentos.add(fragmentoSeleccionTarjeta);
        fragmentos.add(fragmentoSeleccionCategoriasParaNotificar);
        fragmentos.add(fragmentoConfiguracionExtra);
        colocarFragmento(fragmentos.get(indiceFragmentoActual));
    }

    private void colocarFragmento(FragmentoConfiguracion fragmentoConfiguracion) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_fragmento, fragmentoConfiguracion);
        fragmentTransaction.commitAllowingStateLoss();

        configurarAnteriorSiguiente(fragmentoConfiguracion);
    }

    private void configurarAnteriorSiguiente(FragmentoConfiguracion fragmentoConfiguracion) {

        if (fragmentoConfiguracion.tieneAdelante()) {
            this.siguiente.setVisibility(View.VISIBLE);
        }
        else {
            this.siguiente.setVisibility(View.INVISIBLE);
        }

        if (fragmentoConfiguracion.tieneAtras()) {
            this.anterior.setVisibility(View.VISIBLE);
        }
        else {
            this.anterior.setVisibility(View.INVISIBLE);
        }
    }
}
