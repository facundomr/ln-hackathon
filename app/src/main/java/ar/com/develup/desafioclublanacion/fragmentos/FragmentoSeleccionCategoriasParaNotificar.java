package ar.com.develup.desafioclublanacion.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.modelo.Categoria;
import ar.com.develup.desafioclublanacion.util.FuentesUtil;
import ar.com.develup.desafioclublanacion.util.Preferencias;

import static ar.com.develup.desafioclublanacion.util.FuentesUtil.Fuente.HELVETICA_LIGHT;
import static ar.com.develup.desafioclublanacion.util.FuentesUtil.Fuente.HELVETICA_MEDIUM;

/**
 * Created by mmaisano on 10/04/15.
 */
public class FragmentoSeleccionCategoriasParaNotificar extends FragmentoConfiguracion {

    private static final String LOG_TAG = FragmentoSeleccionCategoriasParaNotificar.class.getSimpleName();
    private CheckBox checkBoxGastronomia;
    private CheckBox checkBoxEntretenimiento;
    private CheckBox checkBoxTurismo;
    private CheckBox checkBoxCuidadoPersonal;
    private CheckBox checkBoxModa;
    private CheckBox checkBoxMasCategorias;

    @Override
    protected int getLayout() {
        return R.layout.fragmento_seleccion_categorias_para_notificar;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.checkBoxGastronomia = (CheckBox) view.findViewById(R.id.checkbox_gastronomia);
        this.checkBoxEntretenimiento = (CheckBox) view.findViewById(R.id.checkbox_entretenimiento);
        this.checkBoxTurismo = (CheckBox) view.findViewById(R.id.checkbox_turismo);
        this.checkBoxCuidadoPersonal = (CheckBox) view.findViewById(R.id.checkbox_cuidado_personal);
        this.checkBoxModa = (CheckBox) view.findViewById(R.id.checkbox_moda);
        this.checkBoxMasCategorias = (CheckBox) view.findViewById(R.id.checkbox_mas_categorias);

        configurarFuentes();

    }

    private void configurarFuentes() {

        Context context = getActivity();
        FuentesUtil.aplicarFuente(HELVETICA_LIGHT, (TextView) getView().findViewById(R.id.selecciona_categorias), context);
        FuentesUtil.aplicarFuente(HELVETICA_MEDIUM, (TextView) getView().findViewById(R.id.label_gastronomia), context);
        FuentesUtil.aplicarFuente(HELVETICA_MEDIUM, (TextView) getView().findViewById(R.id.label_entretenimiento), context);
        FuentesUtil.aplicarFuente(HELVETICA_MEDIUM, (TextView) getView().findViewById(R.id.label_turismo), context);
        FuentesUtil.aplicarFuente(HELVETICA_MEDIUM, (TextView) getView().findViewById(R.id.label_cuidado_personal), context);
        FuentesUtil.aplicarFuente(HELVETICA_MEDIUM, (TextView) getView().findViewById(R.id.label_moda), context);
        FuentesUtil.aplicarFuente(HELVETICA_MEDIUM, (TextView) getView().findViewById(R.id.label_mas_categorias), context);
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
    public void onResume() {
        super.onResume();

        List<Categoria> categorias = Arrays.asList(Categoria.values());

        if (Preferencias.existeString(getActivity(), Preferencias.CATEGORIAS_NOTIFICACION)) {

            Set<Categoria> categoriasSeleccionadas = Preferencias.obtenerCategorias(getActivity());

            if (categoriasSeleccionadas.contains(Categoria.GASTRONOMIA)) {
                checkBoxGastronomia.setChecked(true);
            }
            else {
                checkBoxGastronomia.setChecked(false);
            }

            if (categoriasSeleccionadas.contains(Categoria.ENTRETENIMIENTO)) {
                checkBoxEntretenimiento.setChecked(true);
            }
            else {
                checkBoxEntretenimiento.setChecked(false);
            }

            if (categoriasSeleccionadas.contains(Categoria.TURISMO)) {
                checkBoxTurismo.setChecked(true);
            }
            else {
                checkBoxTurismo.setChecked(false);
            }

            if (categoriasSeleccionadas.contains(Categoria.CUIDADO_PERSONAL)) {
                checkBoxCuidadoPersonal.setChecked(true);
            }
            else {
                checkBoxCuidadoPersonal.setChecked(false);
            }

            if (categoriasSeleccionadas.contains(Categoria.MODA)) {
                checkBoxModa.setChecked(true);
            }
            else {
                checkBoxModa.setChecked(false);
            }

            if (categoriasSeleccionadas.contains(Categoria.MAS_CATEGORIAS)) {
                checkBoxMasCategorias.setChecked(true);
            }
            else {
                checkBoxMasCategorias.setChecked(false);
            }
        }
        else {
            this.checkBoxGastronomia.setChecked(true);
            this.checkBoxEntretenimiento.setChecked(true);
            this.checkBoxTurismo.setChecked(true);
            this.checkBoxCuidadoPersonal.setChecked(true);
            this.checkBoxModa.setChecked(true);
            this.checkBoxMasCategorias.setChecked(true);
        }
    }

    @Override
    public void guardarCambios() {

        Preferencias.guardar(getActivity(), Preferencias.CATEGORIAS_NOTIFICACION, getCategoriasSeleccionadas());
    }

    private Set<Categoria> getCategoriasSeleccionadas() {

        Set<Categoria> categorias = new HashSet<>();

        if (checkBoxGastronomia.isChecked()) {
            categorias.add(Categoria.GASTRONOMIA);
        }
        if (checkBoxCuidadoPersonal.isChecked()) {
            categorias.add(Categoria.CUIDADO_PERSONAL);
        }
        if (checkBoxEntretenimiento.isChecked()) {
            categorias.add(Categoria.ENTRETENIMIENTO);
        }
        if (checkBoxTurismo.isChecked()) {
            categorias.add(Categoria.TURISMO);
        }
        if (checkBoxModa.isChecked()) {
            categorias.add(Categoria.MODA);
        }
        if (checkBoxMasCategorias.isChecked()) {
            categorias.add(Categoria.MAS_CATEGORIAS);
        }

        return categorias;
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
