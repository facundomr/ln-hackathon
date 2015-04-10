package ar.com.develup.desafioclublanacion.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mmaisano on 10/04/15.
 */
public class Preferencias {

    private static final String NOMBRE_PREFERENCIAS = "PREFERENCIAS_CLUB_LA_NACION";
    public static final String TARJETA = "TARJETA";

    public static void guardar(Context context, String clave, String valor) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(clave,valor);
        editor.commit();
    }

    public static boolean existeString(Context context, String clave) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
        return sharedPreferences.contains(clave) && !sharedPreferences.getString(clave, "").trim().isEmpty();
    }

    public static String obtenerString(Context context, String clave) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(clave, "");
    }
}
