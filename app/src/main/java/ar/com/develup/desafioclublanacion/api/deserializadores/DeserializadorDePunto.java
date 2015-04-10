package ar.com.develup.desafioclublanacion.api.deserializadores;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.com.develup.desafioclublanacion.modelo.Punto;

public class DeserializadorDePunto implements JsonDeserializer<Punto> {

    @Override
    public Punto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonArray array = json.getAsJsonArray();

        Punto punto = new Punto();
        punto.setLatitud(array.get(0).getAsDouble());
        punto.setLongitud(array.get(1).getAsDouble());

        return punto;
    }

}