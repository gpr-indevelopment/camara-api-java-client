package io.github.gprindevelopment.rest.common;

import okhttp3.OkHttpClient;

public class OkHttpClientSingleton {

    private static OkHttpClient instancia;

    public static OkHttpClient getInstancia() {
        if (instancia == null) {
            instancia = new OkHttpClient.Builder().build();
        }
        return instancia;
    }
}
