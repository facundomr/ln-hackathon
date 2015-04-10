package ar.com.develup.desafioclublanacion.api.deserializadores;

import android.content.Context;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ar.com.develup.desafioclublanacion.modelo.Categoria;
import ar.com.develup.desafioclublanacion.modelo.Tarjeta;
import ar.com.develup.desafioclublanacion.modelo.Tarjetas;

public class DeserializadorDeCategoria implements JsonDeserializer<Categoria> {

    private Context context;

    @Override
    public Categoria deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        String string = json.getAsString();
        Categoria categoria = Categoria.getPorNombreApi(this.context, string);

        return categoria;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}