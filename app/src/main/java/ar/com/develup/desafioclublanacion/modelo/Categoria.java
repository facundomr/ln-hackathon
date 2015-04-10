package ar.com.develup.desafioclublanacion.modelo;

import android.content.Context;

import ar.com.develup.desafioclublanacion.R;

public enum Categoria {


    GASTRONOMIA(R.string.gastronomia, R.string.gastronomia_api,  R.color.gastronomia),
    ENTRETENIMIENTO(R.string.entretenimiento, R.string.entretenimiento_api, R.color.entretenimiento),
    TURISMO(R.string.turismo, R.string.turismo_api, R.color.turismo),
    CUIDADO_PERSONAL(R.string.cuidado_personal, R.string.cuidado_personal_api, R.color.cuidado_personal),
    MODA(R.string.moda, R.string.moda_api, R.color.moda),
    MAS_CATEGORIAS(R.string.mas_categorias, R.string.mas_categorias_api, R.color.mas_categorias),
    ;

    private int nombreResId;
    private int nombreApiResId;
    private int colorResId;
    private boolean seleccionadaParaNotificaciones;

    Categoria(int nombreResId, int nombreApiResId, int colorResId) {
        this.nombreResId = nombreResId;
        this.nombreApiResId = nombreApiResId;
        this.colorResId = colorResId;
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


    public static Categoria  getPorNombreApi(Context context, String nombre) {

        Categoria tarjetaEncontrada = null;

        Categoria[] tarjetas = values();
        for(Categoria unaTarjeta : tarjetas) {

            if (context.getString(unaTarjeta.nombreApiResId).equalsIgnoreCase(nombre)) {
                tarjetaEncontrada = unaTarjeta;
            }
        }

        return tarjetaEncontrada;
    }

    public String getNombre(Context context) {
        return context.getString(this.nombreResId);
    }

    public int getColorResId() {
        return this.colorResId;
    }

    public boolean isSeleccionadaParaNotificaciones() {
        return seleccionadaParaNotificaciones;
    }

    public void setSeleccionadaParaNotificaciones(boolean seleccionadaParaNotificaciones) {
        this.seleccionadaParaNotificaciones = seleccionadaParaNotificaciones;
    }
}