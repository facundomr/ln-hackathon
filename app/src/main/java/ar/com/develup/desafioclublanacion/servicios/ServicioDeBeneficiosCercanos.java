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

import java.util.Timer;
import java.util.TimerTask;

public class ServicioDeBeneficiosCercanos extends Service {

    private static final String LOG_TAG = ServicioDeBeneficiosCercanos.class.getSimpleName();

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static long tiempoMinimoEnMilisegundos = 5000;
    private static long distanciaMinimaEnMetros = 10;

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
        }

        public void onProviderDisabled(String provider) {}

        public void onProviderEnabled(String provider) {}

        public void onStatusChanged(String provider, int status, Bundle extras) {}

    }

}