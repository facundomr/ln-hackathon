package ar.com.develup.desafioclublanacion.modelo;

import android.content.Context;

import ar.com.develup.desafioclublanacion.R;

public enum Tarjeta {

    PREMIUM(R.string.tarjeta_premium, R.string.tarjeta_premium_api),
    CLASICA(R.string.tarjeta_clasica, R.string.tarjeta_clasica_api),
    NINGUNA(R.string.tarjeta_ninguna, R.string.tarjeta_ninguna_api),
    ;

    private int nombreResId;
    private int nombreApiResId;

    Tarjeta(int nombreResId, int nombreApiResId) {
        this.nombreResId = nombreResId;
        this.nombreApiResId = nombreApiResId;
    }

    public static Tarjeta getPorNombre(Context context, String nombre) {

        Tarjeta tarjetaEncontrada = null;

        Tarjeta[] tarjetas = values();
        for(Tarjeta unaTarjeta : tarjetas) {

            if (context.getString(unaTarjeta.nombreResId).equalsIgnoreCase(nombre)) {
                tarjetaEncontrada = unaTarjeta;
            }
        }

        return tarjetaEncontrada;
    }

    public static Tarjeta getPorNombreApi(Context context, String nombre) {

        Tarjeta tarjetaEncontrada = null;

        Tarjeta[] tarjetas = values();
        for(Tarjeta unaTarjeta : tarjetas) {

            if (context.getString(unaTarjeta.nombreApiResId).equalsIgnoreCase(nombre)) {
                tarjetaEncontrada = unaTarjeta;
            }
        }

        return tarjetaEncontrada;
    }

}