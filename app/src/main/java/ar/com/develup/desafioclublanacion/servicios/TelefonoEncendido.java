package ar.com.develup.desafioclublanacion.servicios;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class TelefonoEncendido extends BroadcastReceiver {

    private final String TAG = TelefonoEncendido.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intentReceived) {

        Log.i(TAG, "Teléfono encendido, iniciando servicio de Beneficios Cercanos");

        Intent intent = new Intent(context, ServicioDeBeneficiosCercanos.class);
        context.startService(intent);
    }

}