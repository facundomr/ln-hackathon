package ar.com.develup.desafioclublanacion.actividades;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.fragmentos.FragmentoBasico;
import ar.com.develup.desafioclublanacion.fragmentos.FragmentoConfiguracion;
import ar.com.develup.desafioclublanacion.fragmentos.FragmentoConfiguracionFinalizada;
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
    private FragmentoConfiguracionFinalizada fragmentoConfiguracionFinalizada = new FragmentoConfiguracionFinalizada();
    private int indiceFragmentoActual;
    private View siguiente;

    private View.OnClickListener siguienteFragmento = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (fragmentos.get(indiceFragmentoActual).valido()) {

                if (indiceFragmentoActual < fragmentos.size() - 1) {
                    indiceFragmentoActual ++;
                    colocarFragmento(fragmentos.get(indiceFragmentoActual));
                }
                else if (indiceFragmentoActual == fragmentos.size() - 1) {

                }
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

        fragmentos.add(fragmentoSeleccionTarjeta);
        fragmentos.add(fragmentoSeleccionCategoriasParaNotificar);
        fragmentos.add(fragmentoConfiguracionFinalizada);
        colocarFragmento(fragmentos.get(indiceFragmentoActual));
    }

    private void colocarFragmento(FragmentoBasico fragmentoBasico) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_fragmento, fragmentoBasico);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
