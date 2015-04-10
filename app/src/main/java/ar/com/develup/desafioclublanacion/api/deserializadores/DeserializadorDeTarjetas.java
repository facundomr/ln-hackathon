package ar.com.develup.desafioclublanacion.api.deserializadores;

import android.content.Context;

import com.android.volley.Response;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ar.com.develup.desafioclublanacion.modelo.Tarjeta;
import ar.com.develup.desafioclublanacion.modelo.Tarjetas;

public class DeserializadorDeTarjetas implements JsonDeserializer<Tarjetas> {

    private Context context;

    @Override
    public Tarjetas deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Tarjetas tarjetas = new Tarjetas();
        List<Tarjeta> listaDeTarjetas = new ArrayList<Tarjeta>();

        String string = json.getAsString();
        String[] tarjetasString = string.split("-");
        for (String tarjetaString : tarjetasString) {

            listaDeTarjetas.add(Tarjeta.getPorNombreApi(this.context, tarjetaString));

        }

        tarjetas.setTarjetas(listaDeTarjetas);

        return tarjetas;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}