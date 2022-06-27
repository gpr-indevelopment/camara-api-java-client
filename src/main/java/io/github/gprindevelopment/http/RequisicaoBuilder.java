package io.github.gprindevelopment.http;

import okhttp3.HttpUrl;
import okhttp3.Request;

import java.util.Objects;

public class RequisicaoBuilder {

    private Consulta consulta;

    private String[] segmentosPath;

    private final String urlBase;

    public RequisicaoBuilder(String urlBase) {
        this.urlBase = urlBase;
    }

    public RequisicaoBuilder segmentosPath(String... segmentosPath) {
        this.segmentosPath = segmentosPath;
        return this;
    }

    public RequisicaoBuilder consulta(Consulta consulta) {
        this.consulta = consulta;
        return this;
    }

    public Request build() {
        HttpUrl.Builder urlBuilder = construirUrl(urlBase).newBuilder();
        adicionarSegmentos(urlBuilder);
        adicionarParametrosDeConsulta(urlBuilder);
        return new Request.Builder()
                .url(urlBuilder.build().toString())
                .header("accept", "application/json")
                .build();
    }

    private void adicionarParametrosDeConsulta(HttpUrl.Builder urlBuilder) {
        if (consulta != null) {
            consulta.getParametros().forEach(urlBuilder::addQueryParameter);
        }
    }

    private void adicionarSegmentos(HttpUrl.Builder urlBuilder) {
        if (segmentosPath != null) {
            for (String segmento : segmentosPath) {
                urlBuilder.addEncodedPathSegments(segmento);
            }
        }
    }

    private HttpUrl construirUrl(String urlBase) {
        urlBase = Objects.requireNonNull(urlBase, "Uma consulta por ID precisa de uma URL base para concatenação do ID");
        if (urlBase.isBlank()) {
            throw new IllegalArgumentException("A URL base para concatenação do ID na consulta por ID não pode estar em branco");
        }
        HttpUrl url = HttpUrl.parse(urlBase);
        if (url == null) {
            String mensagem = String.format("A URL base %s informada não pode ser interpretada como uma URL", urlBase);
            throw new IllegalArgumentException(mensagem);
        }
        return url;
    }
}
