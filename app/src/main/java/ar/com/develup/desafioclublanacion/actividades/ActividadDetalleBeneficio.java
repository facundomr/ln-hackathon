package ar.com.develup.desafioclublanacion.actividades;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import ar.com.develup.desafioclublanacion.ClubLaNacionApplication;
import ar.com.develup.desafioclublanacion.R;
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
import ar.com.develup.desafioclublanacion.util.FechaUtil;
import ar.com.develup.desafioclublanacion.util.FuentesUtil;
import ar.com.develup.desafioclublanacion.util.Preferencias;
import ar.com.develup.desafioclublanacion.util.SingletonRequestQueue;

public class ActividadDetalleBeneficio extends ActividadBasica {

    private static final String LOG_TAG = ActividadDetalleBeneficio.class.getSimpleName();
    private String idBeneficio;
    private ImageView foto;
    private TextView tipoBeneficio;
    private TextView descripcion;
    private TextView validoDesde;
    private TextView validoHasta;
    private TextView noVolverAMostrar;
    private TextView volverAMostrar;
    private TextView comoLlegar;
    private View contenedorPrincipal;
    private ProgressWheel progressWheel;
    private SupportMapFragment mapa;
    private OnMapReadyCallback mapaCargado = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {

           ubicarBeneficio();
        }
    };
    private Beneficio beneficio;
    private View.OnClickListener noVolverAMostrarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            noVolverAMostrarBeneficio();

            new MaterialDialog.Builder(ActividadDetalleBeneficio.this)
                    .content(getString(R.string.no_veras_este_beneficio))
                    .positiveText(R.string.aceptar)
                    .negativeText(R.string.deshacer)
                    .contentColorRes(android.R.color.background_dark)
                    .positiveColorRes(android.R.color.background_dark)
                    .negativeColorRes(android.R.color.darker_gray)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            volverAMostrarBeneficio();
                        }
                    })
                    .show();
        }
    };

    private View.OnClickListener volverAMostrarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            volverAMostrarBeneficio();

            new MaterialDialog.Builder(ActividadDetalleBeneficio.this)
                    .content(getString(R.string.veras_este_beneficio))
                    .positiveText(R.string.aceptar)
                    .negativeText(R.string.deshacer)
                    .contentColorRes(android.R.color.background_dark)
                    .positiveColorRes(android.R.color.background_dark)
                    .negativeColorRes(android.R.color.darker_gray)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            noVolverAMostrarBeneficio();
                        }
                    })
                    .show();
        }
    };
    private View.OnClickListener comoLlegarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Double latitud = beneficio.getPunto().getLatitud();
            Double longitud = beneficio.getPunto().getLongitud();

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=" + latitud + "," + longitud));
            startActivity(intent);
        }
    };

    private void noVolverAMostrarBeneficio() {
        Preferencias.noMostrarBeneficio(ActividadDetalleBeneficio.this, beneficio.getId());
        noVolverAMostrar.setVisibility(View.INVISIBLE);
        volverAMostrar.setVisibility(View.VISIBLE);
    }

    private void volverAMostrarBeneficio() {
        Preferencias.volverAMostrarBeneficio(ActividadDetalleBeneficio.this, beneficio.getId());
        noVolverAMostrar.setVisibility(View.VISIBLE);
        volverAMostrar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayout() {
        return R.layout.actividad_detalle_beneficio;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(getString(R.string.titlo_actividad_detalle_beneficio));

        if(getIntent().getExtras() != null) {
            idBeneficio = getIntent().getExtras().getString("idBeneficio");
        }

        this.obtenerDetalleBeneficio();
        this.foto = (ImageView) findViewById(R.id.imagen_beneficio);
        this.tipoBeneficio = (TextView) findViewById(R.id.tipo_beneficio);
        this.validoDesde = (TextView) findViewById(R.id.valido_desde);
        this.validoHasta = (TextView) findViewById(R.id.valido_hasta);
        this.descripcion = (TextView) findViewById(R.id.descripcion_beneficio);
        this.noVolverAMostrar = (TextView) findViewById(R.id.no_volver_a_mostrar);
        this.volverAMostrar = (TextView) findViewById(R.id.volver_a_mostrar);
        this.contenedorPrincipal = findViewById(R.id.contenedor_principal);
        this.progressWheel = (ProgressWheel) findViewById(R.id.progressBar);
        this.comoLlegar = (TextView) findViewById(R.id.como_llegar);
        this.mapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        this.mapa.getMapAsync(this.mapaCargado);
        this.noVolverAMostrar.setOnClickListener(this.noVolverAMostrarListener);
        this.volverAMostrar.setOnClickListener(this.volverAMostrarListener);
        this.comoLlegar.setOnClickListener(this.comoLlegarListener);

        configurarFuentes();
    }

    private void configurarFuentes() {

        FuentesUtil.aplicarFuente(FuentesUtil.Fuente.HELVETICA_LIGHT, tipoBeneficio, this);
        FuentesUtil.aplicarFuente(FuentesUtil.Fuente.HELVETICA_LIGHT, (TextView) findViewById(R.id.label_desde), this);
        FuentesUtil.aplicarFuente(FuentesUtil.Fuente.HELVETICA_LIGHT, (TextView) findViewById(R.id.label_hasta), this);
        FuentesUtil.aplicarFuente(FuentesUtil.Fuente.HELVETICA_LIGHT, descripcion, this);
        FuentesUtil.aplicarFuente(FuentesUtil.Fuente.HELVETICA_LIGHT_ITALIC, validoDesde, this);
        FuentesUtil.aplicarFuente(FuentesUtil.Fuente.HELVETICA_LIGHT_ITALIC, validoHasta, this);
        FuentesUtil.aplicarFuente(FuentesUtil.Fuente.HELVETICA_MEDIUM, noVolverAMostrar, this);
        FuentesUtil.aplicarFuente(FuentesUtil.Fuente.HELVETICA_MEDIUM, comoLlegar, this);
    }

    private void obtenerDetalleBeneficio() {

        //TODO: Borrar estas líneas, son de testing
        if(idBeneficio == null || idBeneficio.isEmpty()) {
            idBeneficio = "369114_113036";
        }
        //TODO: Borrar estas líneas, son de testing

        ClubLaNacionApplication aplicacion = (ClubLaNacionApplication) getApplication();
        if(aplicacion.hayConexionAInternet()) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ClubLaNacionAPI.DETALLE_BENEFICIO
                    + idBeneficio, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArray) {

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Date.class, new DeserializadorDeFecha());
                    gsonBuilder.registerTypeAdapter(Punto.class, new DeserializadorDePunto());

                    DeserializadorDeTarjetas deserializadorDeTarjetas = new DeserializadorDeTarjetas();
                    deserializadorDeTarjetas.setContext(ActividadDetalleBeneficio.this);
                    gsonBuilder.registerTypeAdapter(Tarjetas.class, deserializadorDeTarjetas);

                    DeserializadorDeCategoria deserializadorDeCategoria = new DeserializadorDeCategoria();
                    deserializadorDeCategoria.setContext(ActividadDetalleBeneficio.this);
                    gsonBuilder.registerTypeAdapter(Categoria.class, deserializadorDeCategoria);

                    Gson gson = gsonBuilder.create();

                    Type collectionType = new TypeToken<List<Beneficio>>() {}.getType();
                    List<Beneficio> beneficios = gson.fromJson(jsonArray.toString(), collectionType);
                    Log.i(LOG_TAG, "Beneficios obtenidos " + beneficios.size());

                    Beneficio buscado = null;
                    if (beneficios.size() > 0) {

                        buscado = beneficios.get(0);
                        mostrarDetalle(buscado);

                    } else {
                        Log.e(LOG_TAG, "La API no devolvió un Beneficio para el id: " + idBeneficio);
                    }

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            Log.e(LOG_TAG, "Error intentando obtener el detalle del Beneficios : " + idBeneficio);
                        }
                    });

            SingletonRequestQueue.getInstance(ActividadDetalleBeneficio.this).addToRequestQueue(jsonArrayRequest);

        }
    }

    private void mostrarDetalle(Beneficio beneficio) {

        this.progressWheel.setVisibility(View.GONE);
        this.contenedorPrincipal.setVisibility(View.VISIBLE);
        this.beneficio = beneficio;

        this.ubicarBeneficio();

        getSupportActionBar().setTitle(beneficio.getEstablecimiento().getNombre());
        getAplicacion().mostrarImagen(beneficio.getUrlImagen(), foto);
        tipoBeneficio.setText(beneficio.getDetalle().getTipo());
        descripcion.setText(beneficio.getDetalle().getDescripcion());

        if (beneficio.getDetalle().getTarjetas().getTarjetas().contains(Tarjeta.CLASICA)) {
            findViewById(R.id.tarjeta_classic).setVisibility(View.VISIBLE);
        }

        if (beneficio.getDetalle().getTarjetas().getTarjetas().contains(Tarjeta.PREMIUM)) {
            findViewById(R.id.tarjeta_premium).setVisibility(View.VISIBLE);
        }

        validoDesde.setText(FechaUtil.formatear(beneficio.getDesde()));
        validoHasta.setText(FechaUtil.formatear(beneficio.getHasta()));
    }

    private void ubicarBeneficio() {

        if (this.beneficio != null) {

            LatLng beneficioLatLng = new LatLng(beneficio.getPunto().getLatitud(), beneficio.getPunto().getLongitud());
            mapa.getMap().setMyLocationEnabled(true);
            mapa.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(beneficioLatLng, 16));

            mapa.getMap().addMarker(new MarkerOptions()
                    .title(this.beneficio.getDetalle().getNombre())
                    .position(beneficioLatLng));
        }
    }



}