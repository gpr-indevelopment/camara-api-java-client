package io.github.gprindevelopment.rest.common;

import com.google.gson.*;
import io.github.gprindevelopment.core.Deputado;

import java.lang.reflect.Type;

public class DeputadoDeserializer implements JsonDeserializer<Deputado> {

    @Override
    public Deputado deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Complementar com os demais campos do Deputado
        JsonObject jsonObject = (JsonObject) json;
        JsonElement elementoNome = jsonObject.get("nome");
        if (elementoNome == null) {
            elementoNome = jsonObject.get("nomeCivil");
        }
        return new Deputado(elementoNome.getAsString());
    }
}
