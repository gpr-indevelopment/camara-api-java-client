package io.github.gprindevelopment.rest.legislatura;

import com.google.gson.reflect.TypeToken;
import io.github.gprindevelopment.core.common.ConstantesCamara;
import io.github.gprindevelopment.core.Legislatura;
import io.github.gprindevelopment.core.common.Ordem;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.rest.common.ComponenteClient;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import io.github.gprindevelopment.rest.common.RespostaCamara;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LegislaturaClient extends ComponenteClient {

    private final OkHttpClient client;

    private static final HttpUrl BASE_URL = Objects.requireNonNull(HttpUrl.parse(ConstantesCamara.LEGISLATURA_API_URL));

    public LegislaturaClient(OkHttpClient client) {
        this.client = client;
    }

    public LegislaturaClient() {
        this.client = new OkHttpClient();
    }

    public Legislatura consultarLegislaturaAtual() throws IOException, CamaraClientStatusException, RespostaNaoEsperadaException {
        ConsultaLegislatura consulta = new ConsultaLegislatura.Builder()
                .itens(1)
                .pagina(1)
                .ordenarPor("id", Ordem.DESC)
                .build();
        return consultar(consulta).get(0);
    }

    public Optional<Legislatura> consultarLegislaturaPorId(int id) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        HttpUrl.Builder urlBuilder = BASE_URL.newBuilder()
                .addEncodedPathSegments(String.valueOf(id));
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .header("accept", "application/json")
                .build();
        Call chamada = client.newCall(request);
        Type tipoEsperado = new TypeToken<Legislatura>() {}.getType();
        RespostaCamara<Legislatura> resposta = executarChamada(chamada, tipoEsperado);
        return Optional.ofNullable(resposta.getDados());
    }

    public Pagina<Legislatura> consultar(ConsultaLegislatura consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        Objects.requireNonNull(consulta, "O objeto de consulta não pode ser null. Favor utilizar um objeto vazio se desejar uma consulta sem parâmetros.");
        HttpUrl.Builder urlBuilder = BASE_URL.newBuilder();
        if (consulta.getOrdem() != null) {
            urlBuilder
                    .addQueryParameter("ordem", consulta.getOrdem().name())
                    .addQueryParameter("ordenarPor", consulta.getOrdenarPor());
        }
        if (consulta.getPagina() != null) {
            urlBuilder.addQueryParameter("pagina", String.valueOf(consulta.getPagina()));
        }
        if (consulta.getItens() != null) {
            urlBuilder.addQueryParameter("itens", String.valueOf(consulta.getItens()));
        }
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .header("accept", "application/json")
                .build();
        Call chamada = client.newCall(request);
        Type tipoEsperado = new TypeToken<List<Legislatura>>() {}.getType();
        RespostaCamara<List<Legislatura>> resposta = executarChamada(chamada, tipoEsperado);
        return new Pagina<>(resposta.getDados(),
                extrairCabecalhoTotalItens(resposta),
                consulta.getPagina());
    }
}
