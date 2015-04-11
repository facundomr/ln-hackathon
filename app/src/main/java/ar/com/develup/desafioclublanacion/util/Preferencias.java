package ar.com.develup.desafioclublanacion.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ar.com.develup.desafioclublanacion.modelo.Categoria;
import ar.com.develup.desafioclublanacion.modelo.Tarjeta;
import ar.com.develup.desafioclublanacion.servicios.ServicioDeBeneficiosCercanos;

/**
 * Created by mmaisano on 10/04/15.
 */
public class Preferencias {

    private static final String NOMBRE_PREFERENCIAS = "PREFERENCIAS_CLUB_LA_NACION";
    public static final String TARJETA = "TARJETA";
    public static final String CATEGORIAS_NOTIFICACION = "CATEGORIAS_NOTIFICACION";
    public static final String DISTANCIA_MAXIMA = "DISTANCIA_MAXIMA";
    public static final String NOTIFICACIONES_MAXIMAS = "NOTIFICACIONES_MAXIMAS";
    public static final String RANGO_HORARIO = "RANGO_HORARIO";
    private static final String BENEFICIOS_MOSTRADOS = "BENEFICIOS_MOSTRADOS";
    private static final String FECHA_ULTIMA_NOTIFICACION ="FECHA_ULTIMA_NOTIFICACION";

    public static void guardar(Context context, String clave, String valor) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(clave,valor);
        editor.commit();
    }

    public static void guardar(Context context, String clave, Set valor) {

        String valorString = new Gson().toJson(valor);
        guardar(context, clave, valorString);
    }

    public static boolean existeString(Context context, String clave) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
        return sharedPreferences.contains(clave) && !sharedPreferences.getString(clave, "").trim().isEmpty();
    }

    public static String obtenerString(Context context, String clave) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(clave, "");
    }

    public static Set<Categoria> obtenerCategorias(Context context) {

        String json = obtenerString(context, CATEGORIAS_NOTIFICACION);
        Set<Categoria> categorias = new Gson().fromJson(json, new TypeToken<Set<Categoria>>() {
        }.getType());

        return categorias;
    }

    public static Tarjeta obtenerTarjeta(Context context) {

        return Tarjeta.valueOf(Preferencias.obtenerString(context, Preferencias.TARJETA));
    }

    public static Integer getDistanciaMaxima(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(DISTANCIA_MAXIMA, Integer.MAX_VALUE);
    }

    public static Integer getMaximasNotificaciones(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(NOTIFICACIONES_MAXIMAS, Integer.MAX_VALUE);
    }

    public static Set<String> obtenerIdsDeBeneficiosMostrados(Context context) {

        String json = obtenerString(context, BENEFICIOS_MOSTRADOS);
        Set<String> ids = new Gson().fromJson(json, new TypeToken<Set<String>>() {
        }.getType());

        if (ids == null) {
            ids = new HashSet<String>();
        }

        return ids;
    }

    public static void guardarBeneficioMostrado(Context context, String id) {

        Set<String> ids = obtenerIdsDeBeneficiosMostrados(context);
        ids.add(id);
        String valorString = new Gson().toJson(ids);

        guardar(context, BENEFICIOS_MOSTRADOS, valorString);

        guardarFechaUltimaModificacion(context, new Date());
    }

    public static Date obtenerFechaUltimaModificacion(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);

        return new Date(sharedPreferences.getLong(FECHA_ULTIMA_NOTIFICACION, 0));
    }

    public static void guardarFechaUltimaModificacion(Context context, Date fecha) {

        context.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE).edit().putLong(FECHA_ULTIMA_NOTIFICACION, fecha.getTime()).apply();
    }

    public static void borrarIdsBeneficiosMostrados(Context context) {

        context.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE).edit().remove(BENEFICIOS_MOSTRADOS).commit();
    }
}
