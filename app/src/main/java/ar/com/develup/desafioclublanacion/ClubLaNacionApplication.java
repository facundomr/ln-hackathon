package ar.com.develup.desafioclublanacion;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import ar.com.develup.desafioclublanacion.servicios.ServicioDeBeneficiosCercanos;
import hotchemi.android.rate.AppRate;

public class  ClubLaNacionApplication extends Application {

    private static final String LOG_TAG = ClubLaNacionApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        configurarRateUs();
        configurarUniversalImageLoader();

        Intent servicio = new Intent(this, ServicioDeBeneficiosCercanos.class);
        this.startService(servicio);
    }

    private void configurarUniversalImageLoader() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);
    }

    private void configurarRateUs() {

        AppRate.with(this)
                .setInstallDays(10)
                .setLaunchTimes(4)
                .setRemindInterval(4)
                .setShowNeutralButton(true)
                .setDebug(false)
                .monitor();
    }

    public void mostrarImagen(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView);
    }

    public boolean hayConexionAInternet() {
        return ((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
