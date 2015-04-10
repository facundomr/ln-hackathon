package ar.com.develup.desafioclublanacion.modelo;

import android.content.Context;

import ar.com.develup.desafioclublanacion.R;

public enum Tarjeta {

    PREMIUM(R.string.tarjeta_premium),
    CLASICA(R.string.tarjeta_clasica),
    NINGUNA(R.string.tarjeta_ninguna),
    ;

    private int nombreResId;

    Tarjeta(int nombreResId) {
        this.nombreResId = nombreResId;
    }

    public Tarjeta getPorNombre(Context context, String nombre) {

        Tarjeta tarjetaEncontrada = null;

        Tarjeta[] tarjetas = values();
        for(Tarjeta unaTarjeta : tarjetas) {

            if (context.getString(unaTarjeta.nombreResId).equalsIgnoreCase(nombre)) {
                tarjetaEncontrada = unaTarjeta;
            }
        }

        return tarjetaEncontrada;
    }

}