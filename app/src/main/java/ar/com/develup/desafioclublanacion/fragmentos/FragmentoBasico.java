package ar.com.develup.desafioclublanacion.fragmentos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.develup.desafioclublanacion.ClubLaNacionApplication;
import ar.com.develup.desafioclublanacion.actividades.ActividadBasica;

public abstract class FragmentoBasico extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getActivity(), getLayout() , null);
    }

    protected abstract int getLayout();
    public abstract String getTitulo();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (actualizaTitulo() && isAdded() && ((ActividadBasica)activity).getSupportActionBar() != null ) {
            ((ActividadBasica)activity).getSupportActionBar().setTitle(getTitulo());
        }
    }

    protected boolean actualizaTitulo() {
        return true;
    }

    public ClubLaNacionApplication getAplicacion() {
        return ((ActividadBasica)getActivity()).getAplicacion();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
