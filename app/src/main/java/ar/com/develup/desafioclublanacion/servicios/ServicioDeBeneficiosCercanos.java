package ar.com.develup.desafioclublanacion.servicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import ar.com.develup.desafioclublanacion.ClubLaNacionApplication;
import ar.com.develup.desafioclublanacion.api.ClubLaNacionAPI;
import ar.com.develup.desafioclublanacion.api.deserializadores.DeserializadorDeCategoria;
import ar.com.develup.desafioclublanacion.api.deserializadores.DeserializadorDeFecha;
import ar.com.develup.desafioclublanacion.api.deserializadores.DeserializadorDePunto;
import ar.com.develup.desafioclublanacion.api.deserializadores.DeserializadorDeTarjetas;
import ar.com.develup.desafioclublanacion.modelo.Beneficio;
import ar.com.develup.desafioclublanacion.modelo.Categoria;
import ar.com.develup.desafioclublanacion.modelo.Punto;
import ar.com.develup.desafioclublanacion.modelo.Tarjetas;
import ar.com.develup.desafioclublanacion.util.SingletonRequestQueue;

public class ServicioDeBeneficiosCercanos extends Service {

    private static final String LOG_TAG = ServicioDeBeneficiosCercanos.class.getSimpleName();
    private static final long TIEMPO_MINIMO = 5000;
    private static final long DISTANCIA_MINIMA = 1;
    private static final String DISTANCIA_A_LOS_BENEFICIOS = "1000";

    private LocationManager locationManager;
    private LocationListener locationListener;

    private double latitud = 0.0;
    private double longitud = 0.0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDestroy() {}

    @Override
    public void onStart(Intent intent, int startid) {

        setLocationManager();
    }


    public void setLocationManager() {

        boolean gpsHabilitado = false;

        if (locationManager == null || locationListener == null) {

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            gpsHabilitado = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            locationListener = new ListenerDeUbicacion();
        }

        Log.i(LOG_TAG, "GPS activo: " + String.valueOf(gpsHabilitado));

        if (gpsHabilitado) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    TIEMPO_MINIMO,
                    DISTANCIA_MINIMA,
                    locationListener);

        } else {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    TIEMPO_MINIMO,
                    DISTANCIA_MINIMA,
                    locationListener);

        }

        Location ultima = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (ultima == null) {
            ultima = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (ultima != null) {
            Log.i(LOG_TAG, "El teléfono guarda una última ubicación conocida, pidiendo Beneficios...");
            obtenerBeneficiosCercanos(ultima);

        } else {
            Log.i(LOG_TAG, "El teléfono no tiene una última ubicación conocida guardada");
        }

    }

    public class ListenerDeUbicacion implements LocationListener {

        public void onLocationChanged(Location loc) {

            obtenerBeneficiosCercanos(loc);
        }

        public void onProviderDisabled(String provider) {}

        public void onProviderEnabled(String provider) {}

        public void onStatusChanged(String provider, int status, Bundle extras) {}

    }

    private void obtenerBeneficiosCercanos(Location loc) {

        Log.i(LOG_TAG, "Nueva ubicación! lat: " + loc.getLatitude() + " long: " + loc.getLongitude());

        ClubLaNacionApplication aplicacion = (ClubLaNacionApplication) getApplication();
        if(aplicacion.hayConexionAInternet()) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ClubLaNacionAPI.BENEFICIOS_CERCANOS
                                        + loc.getLatitude()
                                        + "/" + loc.getLongitude()
                                        + "/" + DISTANCIA_A_LOS_BENEFICIOS, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArray) {

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Date.class, new DeserializadorDeFecha());
                    gsonBuilder.registerTypeAdapter(Punto.class, new DeserializadorDePunto());

                    DeserializadorDeTarjetas deserializadorDeTarjetas = new DeserializadorDeTarjetas();
                    deserializadorDeTarjetas.setContext(ServicioDeBeneficiosCercanos.this);
                    gsonBuilder.registerTypeAdapter(Tarjetas.class, deserializadorDeTarjetas);

                    DeserializadorDeCategoria deserializadorDeCategoria = new DeserializadorDeCategoria();
                    deserializadorDeCategoria.setContext(ServicioDeBeneficiosCercanos.this);
                    gsonBuilder.registerTypeAdapter(Categoria.class, deserializadorDeCategoria);

                    Gson gson = gsonBuilder.create();

                    Type collectionType = new TypeToken<List<Beneficio>>() {}.getType();
                    List<Beneficio> beneficios = gson.fromJson(jsonArray.toString(), collectionType);
                    Log.i(LOG_TAG, "Beneficios obtenidos " + beneficios.size());

                    // TODO: Operar con este
                    Beneficio beneficio = beneficios.get(0);

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            Log.e(LOG_TAG, "Error intentando obtener los Beneficios cercanos: " + volleyError.getCause());
                        }
                    });

            SingletonRequestQueue.getInstance(ServicioDeBeneficiosCercanos.this).addToRequestQueue(jsonArrayRequest);

            }
    }

}