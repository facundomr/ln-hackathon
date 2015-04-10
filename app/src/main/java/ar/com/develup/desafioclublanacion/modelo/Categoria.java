package ar.com.develup.desafioclublanacion.modelo;

import android.content.Context;

import ar.com.develup.desafioclublanacion.R;

public enum Categoria {


    GASTRONOMIA(R.string.gastronomia, R.color.gastronomia),
    ENTRETENIMIENTO(R.string.entretenimiento, R.color.entretenimiento),
    TURISMO(R.string.turismo, R.color.turismo),
    CUIDADO_PERSONAL(R.string.cuidado_personal, R.color.cuidado_personal),
    MODA(R.string.moda, R.color.moda),
    MAS_CATEGORIAS(R.string.mas_categorias, R.color.mas_categorias),
    ;

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