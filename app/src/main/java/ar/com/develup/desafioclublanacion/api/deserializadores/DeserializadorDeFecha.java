package ar.com.develup.desafioclublanacion.api.deserializadores;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeserializadorDeFecha implements JsonDeserializer<Date> {

    // 2015-04-12T12:00:00.000Z
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Date fecha = null;

        try {

            fecha = sdf.parse(json.getAsString());

        } catch (ParseException e) {

            fecha = new Date();
        }

        return fecha;
    }

}