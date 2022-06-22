package io.github.gprindevelopment.rest.legislatura;

import com.google.gson.reflect.TypeToken;
import io.github.gprindevelopment.core.ConstantesCamara;
import io.github.gprindevelopment.core.Legislatura;
import io.github.gprindevelopment.rest.common.CamaraClientStatusException;
import io.github.gprindevelopment.rest.common.ComponenteClient;
import io.github.gprindevelopment.rest.common.RespostaNaoEsperadaException;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class LegislaturaClient extends ComponenteClient {

    private final OkHttpClient client;

    private static final HttpUrl BASE_URL = Objects.requireNonNull(HttpUrl.parse(ConstantesCamara.LEGISLATURA_API_URL));

    public LegislaturaClient(OkHttpClient client) {
        this.client = client;
    }

    public LegislaturaClient() {
        this.client = new OkHttpClient();
    }

    public Legislatura procurarLegislaturaCorrente() throws IOException, CamaraClientStatusException, RespostaNaoEsperadaException {
        HttpUrl.Builder urlBuilder = BASE_URL.newBuilder()
                .addQueryParameter("ordem", "DESC")
                .addQueryParameter("ordenarPor", "id");
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .header("accept", "application/json")
                .build();
        Call chamada = client.newCall(request);
        Type tipoEsperado = new TypeToken<List<Legislatura>>() {}.getType();
        List<Legislatura> legislaturas = parseResultadoChamada(chamada, tipoEsperado);
        return legislaturas.get(0);
    }
}
