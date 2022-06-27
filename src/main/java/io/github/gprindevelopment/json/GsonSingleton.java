package io.github.gprindevelopment.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.gprindevelopment.dominio.Deputado;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GsonSingleton {

    private static Gson instancia;

    public static Gson getInstancia() {
        if (instancia == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
            builder.registerTypeAdapter(Deputado.class, new DeputadoDeserializer());
            builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
            instancia = builder.create();
        }
        return instancia;
    }
}
