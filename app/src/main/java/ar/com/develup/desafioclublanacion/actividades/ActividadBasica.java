package ar.com.develup.desafioclublanacion.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import ar.com.develup.desafioclublanacion.ClubLaNacionApplication;
import ar.com.develup.desafioclublanacion.R;
import ar.com.develup.desafioclublanacion.util.SingletonRequestQueue;

public abstract class ActividadBasica extends ActionBarActivity {

    protected Toolbar toolbar;

    protected void navegar(Class<? extends Activity> actividad) {

        Intent intent = new Intent(this, actividad);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setTitle(R.string.app_name);
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            setSupportActionBar(toolbar);
        }
    }

    protected abstract int getLayout();

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
    }

    public ClubLaNacionApplication getAplicacion() {
        return (ClubLaNacionApplication) getApplicationContext();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SingletonRequestQueue.getInstance(this).cancelAllRequests();
    }
}
