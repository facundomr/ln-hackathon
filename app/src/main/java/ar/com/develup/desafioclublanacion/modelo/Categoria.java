package ar.com.develup.desafioclublanacion.modelo;

import android.content.Context;

import ar.com.develup.desafioclublanacion.R;

public enum Categoria {

    /**
    PREMIUM(R.string.tarjeta_premium),
    CLASICA(R.string.tarjeta_clasica),
    NINGUNA(R.string.tarjeta_ninguna),
    ;*/

    private int nombreResId;
    private int colorResId;

    Categoria(int nombreResId, int colorResId) {
        this.nombreResId = nombreResId;
    }

    public Categoria getPorNombre(Context context, String nombre) {

        Categoria categoriaEncontrada = null;

        Categoria[] categorias = values();
        for(Categoria unaCategoria : categorias) {

            if (context.getString(unaCategoria.nombreResId).equalsIgnoreCase(nombre)) {
                categoriaEncontrada = unaCategoria;
            }
        }

        return categoriaEncontrada;
    }

    public String getNombre(Context context) {
        return context.getString(this.nombreResId);
    }

    public int getColorResId() {
        return this.colorResId;
    }

}