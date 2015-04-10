package ar.com.develup.desafioclublanacion.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.modelo.Categoria;
import ar.com.develup.desafioclublanacion.util.FuentesUtil;

/**
 * Created by mmaisano on 10/04/15.
 */
public class CategoriaAdapter extends BaseAdapter {

    List<Categoria> categorias = new ArrayList<>();

    public CategoriaAdapter() {
        categorias = Arrays.asList(Categoria.values());
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Categoria getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, null);
            viewHolder = new ViewHolder();
            viewHolder.nombre = (TextView) convertView.findViewById(R.id.nombre);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) (convertView.getTag());
        }

        Categoria categoria = categorias.get(position);
        convertView.setBackgroundResource(categoria.getColorResId());
        FuentesUtil.aplicarFuente(FuentesUtil.Fuente.HELVETICA_MEDIUM, viewHolder.nombre, parent.getContext());
        viewHolder.nombre.setText(categoria.getNombre(parent.getContext()).toUpperCase());

        return convertView;
    }

    class ViewHolder {

        TextView nombre;
    }
}
