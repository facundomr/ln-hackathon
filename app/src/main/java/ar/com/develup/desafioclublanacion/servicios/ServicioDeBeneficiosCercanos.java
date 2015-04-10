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
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ar.com.develup.desafioclublanacion.ClubLaNacionApplication;
import ar.com.develup.desafioclublanacion.api.ClubLaNacionAPI;
import ar.com.develup.desafioclublanacion.api.deserializadores.DeserializadorDeCategoria;
import ar.com.develup.desafioclublanacion.api.deserializadores.DeserializadorDeFecha;
import ar.com.develup.desafioclublanacion.api.deserializadores.DeserializadorDePunto;
import ar.com.develup.desafioclublanacion.api.deserializadores.DeserializadorDeTarjetas;
import ar.com.develup.desafioclublanacion.modelo.Beneficio;
import ar.com.develup.desafioclublanacion.modelo.Categoria;
import ar.com.develup.desafioclublanacion.modelo.Punto;
import ar.com.develup.desafioclublanacion.modelo.Tarjeta;
import ar.com.develup.desafioclublanacion.modelo.Tarjetas;
import ar.com.develup.desafioclublanacion.util.SingletonRequestQueue;

public class ServicioDeBeneficiosCercanos extends Service {

    private static final String LOG_TAG = ServicioDeBeneficiosCercanos.class.getSimpleName();

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static long tiempoMinimoEnMilisegundos = 5000;
    private static long distanciaMinimaEnMetros = 1;

    private double latitud = 0.0;
    private double longitud = 0.0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    public void onDestroy() {

        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public void onStart(Intent intent, int startid) {

        Log.d(LOG_TAG, "onStart");

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
                    tiempoMinimoEnMilisegundos,
                    distanciaMinimaEnMetros,
                    locationListener);

        } else {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    tiempoMinimoEnMilisegundos,
                    distanciaMinimaEnMetros,
                    locationListener);
        }

    }

    public class ListenerDeUbicacion implements LocationListener {

        public void onLocationChanged(Location loc) {

            Log.i(LOG_TAG, "Nueva ubicación! lat: " + loc.getLatitude() + " long: " + loc.getLongitude());

            ClubLaNacionApplication aplicacion = (ClubLaNacionApplication) getApplication();
            if(aplicacion.hayConexionAInternet()) {

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ClubLaNacionAPI.BENEFICIOS_CERCANOS
                                            + loc.getLatitude() + "/" + loc.getLongitude(), new Response.Listener<JSONArray>() {

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

                        Log.i(LOG_TAG, "Beneficios cercanos: " + jsonArray);

                        Type collectionType = new TypeToken<List<Beneficio>>() {}.getType();
                        List<Beneficio> beneficios = gson.fromJson(jsonArray.toString(), collectionType);


                        Beneficio beneficio = beneficios.get(0);
                        Log.i(LOG_TAG, "Primer beneficio" + beneficio);
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

        public void onProviderDisabled(String provider) {}

        public void onProviderEnabled(String provider) {}

        public void onStatusChanged(String provider, int status, Bundle extras) {}

    }

}