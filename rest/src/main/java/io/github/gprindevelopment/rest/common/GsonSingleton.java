package io.github.gprindevelopment.rest.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;

public class GsonSingleton {

    private static Gson instancia;

    public static Gson getInstancia() {
        if (instancia == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
            instancia = builder.create();
        }
        return instancia;
    }
}
