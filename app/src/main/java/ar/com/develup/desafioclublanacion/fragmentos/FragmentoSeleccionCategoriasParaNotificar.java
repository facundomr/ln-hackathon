package ar.com.develup.desafioclublanacion.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.adapter.CategoriaAdapter;
import ar.com.develup.desafioclublanacion.util.FuentesUtil;

import static ar.com.develup.desafioclublanacion.util.FuentesUtil.Fuente.HELVETICA_LIGHT;

/**
 * Created by mmaisano on 10/04/15.
 */
public class FragmentoSeleccionCategoriasParaNotificar extends FragmentoConfiguracion {

    private ListView categoriasListView;
    private CategoriaAdapter categoriaAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragmento_seleccion_categorias_para_notificar;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configurarFuentes();

        this.categoriasListView = (ListView) view.findViewById(R.id.categorias_listview);
        this.categoriaAdapter = new CategoriaAdapter();
        this.categoriasListView.setAdapter(categoriaAdapter);
    }

    private void configurarFuentes() {

        Context context = getActivity();
        FuentesUtil.aplicarFuente(HELVETICA_LIGHT, (TextView) getView().findViewById(R.id.selecciona_categorias), context);
    }

    @Override
    public String getTitulo() {
        return null;
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
