package ar.com.develup.desafioclublanacion.util;

/**
 * Created by mmaisano on 10/04/15.
 */
public class DistanciaUtil {

    public static String getDistanciaConFormato(float distancia) {

        String distanciaString = Math.round(distancia) + " metros";
        if (distancia >= 1000f) {
            distanciaString = Math.round(distancia / 1000f) + "km";
        }

        return  distanciaString;
    }
}
