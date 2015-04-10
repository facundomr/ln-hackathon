package ar.com.develup.desafioclublanacion.adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;
import java.util.List;

import ar.com.develup.desafioclublanacion.fragmentos.FragmentoBasico;
import ar.com.develup.desafioclublanacion.fragmentos.FragmentoSeleccionCategoriasParaNotificar;
import ar.com.develup.desafioclublanacion.fragmentos.FragmentoConfiguracionFinalizada;
import ar.com.develup.desafioclublanacion.fragmentos.FragmentoSeleccionTarjeta;

/**
 * Created by mmaisano on 10/04/15.
 */
public class ConfiguracionInicialAdapter extends FragmentPagerAdapter {

    private List<FragmentoBasico> fragmentos = new LinkedList<FragmentoBasico>();

    public ConfiguracionInicialAdapter(FragmentManager fragmentManager) {

        super(fragmentManager);

        fragmentos.add(new FragmentoSeleccionTarjeta());
        fragmentos.add(new FragmentoSeleccionCategoriasParaNotificar());
        fragmentos.add(new FragmentoConfiguracionFinalizada());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentos.get(position);
    }

    @Override
    public int getCount() {
        return fragmentos.size();
    }
}
