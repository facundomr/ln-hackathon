package ar.com.develup.desafioclublanacion.servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
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
import java.util.Set;

import ar.com.develup.desafioclublanacion.ClubLaNacionApplication;
import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.actividades.ActividadPrincipal;
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
import ar.com.develup.desafioclublanacion.util.DistanciaUtil;
import ar.com.develup.desafioclublanacion.util.FechaUtil;
import ar.com.develup.desafioclublanacion.util.Preferencias;
import ar.com.develup.desafioclublanacion.util.SingletonRequestQueue;

public class ServicioDeBeneficiosCercanos extends Service {

    private static final String LOG_TAG = ServicioDeBeneficiosCercanos.class.getSimpleName();
    private static final long TIEMPO_MINIMO = 5000;
    private static final long DISTANCIA_MINIMA = 1;

    private Integer distanciaALosBeneficios = 10000;

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

        this.distanciaALosBeneficios = Preferencias.getDistanciaMaxima(this);

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

    private void obtenerBeneficiosCercanos(final Location ubicacionDelUsuario) {

        Log.i(LOG_TAG, "Nueva ubicación! lat: " + ubicacionDelUsuario.getLatitude() + " long: " + ubicacionDelUsuario.getLongitude());

        ClubLaNacionApplication aplicacion = (ClubLaNacionApplication) getApplication();
        if(aplicacion.hayConexionAInternet()) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ClubLaNacionAPI.BENEFICIOS_CERCANOS
                                        + ubicacionDelUsuario.getLatitude()
                                        + "/" + ubicacionDelUsuario.getLongitude()
                                        + "/" + distanciaALosBeneficios, new Response.Listener<JSONArray>() {

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

                    Beneficio relevante = buscarBeneficioRelevante(beneficios, ubicacionDelUsuario);

                    if (relevante != null) {

                        String distanciaString = calcularDistancia(relevante, ubicacionDelUsuario);
                        mostrarNotificacion(relevante, distanciaString);
                    }

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

    private Beneficio buscarBeneficioRelevante(List<Beneficio> beneficios, Location ubicacionDelUsuario) {

        Date ultimaNotificacionMostrada = Preferencias.obtenerFechaUltimaModificacion(ServicioDeBeneficiosCercanos.this);
        if (FechaUtil.pasoAyerOAntes(ultimaNotificacionMostrada)) {

            Preferencias.borrarIdsBeneficiosMostrados(this);
        }


        Beneficio beneficioAMostrar = null;

        if(Preferencias.existeString(this, Preferencias.CATEGORIAS_NOTIFICACION)
                && Preferencias.existeString(this, Preferencias.TARJETA)) {

            Set<Categoria> categoriasDelUsuario = Preferencias.obtenerCategorias(ServicioDeBeneficiosCercanos.this);
            Tarjeta tarjetaDelusuario = Preferencias.obtenerTarjeta(ServicioDeBeneficiosCercanos.this);


            for (int i = 0; i < beneficios.size() && beneficioAMostrar == null; i++) {
                Beneficio unBeneficio = beneficios.get(i);

                if (categoriasDelUsuario.contains(unBeneficio.getDetalle().getCategoria())
                        && unBeneficio.getDetalle().getTarjetas().getTarjetas().contains(tarjetaDelusuario)
                        && !Preferencias.obtenerIdsDeBeneficiosMostrados(this).contains(unBeneficio.getId())) {

                    beneficioAMostrar = unBeneficio;
                }
            }
        }

        return beneficioAMostrar;
    }

    private String calcularDistancia(Beneficio beneficio, Location ubicacionDelUsuario) {

        Location desde = new Location(ubicacionDelUsuario);
        desde.setLatitude(beneficio.getPunto().getLatitud());
        desde.setLongitude(beneficio.getPunto().getLongitud());

        float distancia = desde.distanceTo(ubicacionDelUsuario);

        return DistanciaUtil.getDistanciaConFormato(distancia);
    }

    private void mostrarNotificacion(Beneficio beneficio, String distancia) {

        Intent mostrarBeneficio = new Intent(this, ActividadPrincipal.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, mostrarBeneficio, PendingIntent.FLAG_CANCEL_CURRENT);

        String mensaje = "A sólo " + distancia + "! " + beneficio.getDetalle().getDescripcion();

        NotificationCompat.BigTextStyle notificationStyle = new NotificationCompat.BigTextStyle().bigText(mensaje);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(beneficio.getDetalle().getNombre() + " - " + beneficio.getDetalle().getTipo())
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setStyle(notificationStyle)
                .setContentIntent(notificationPendingIntent)
                .setVibrate(new long[] {500, 1000});

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());

        Preferencias.guardarBeneficioMostrado(this, beneficio.getId());
    }

}