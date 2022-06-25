package io.github.gprindevelopment.rest.common;

import com.google.gson.reflect.TypeToken;
import io.github.gprindevelopment.core.common.ConstantesCamara;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class ComponenteClient {

    protected final OkHttpClient client;

    public ComponenteClient(OkHttpClient client) {
        this.client = client;
    }

    public ComponenteClient() {
        this.client = new OkHttpClient();
    }

    protected <T> RespostaCamara<T> executarChamada(Call chamada, Type tipoResposta) throws IOException, CamaraClientStatusException, RespostaNaoEsperadaException {
        try (Response resposta = chamada.execute()) {
            if (resposta.code() == 404) {
                RespostaCamara<T> resultadoVazio = new RespostaCamara<>(null);
                resultadoVazio.setCabecalhos(resposta.headers());
                resultadoVazio.setStatusCode(resposta.code());
                return resultadoVazio;
            }
            if (resposta.code() != 200) {
                throw new CamaraClientStatusException(resposta.code(),
                        resposta.body() != null ? resposta.body().string() : null);
            }
            ResponseBody body = resposta.body();
            if (body == null) {
                String mensagem = String.format("A resposta é null e o tipo esperado é %s.", tipoResposta.getTypeName());
                throw new RespostaNaoEsperadaException(mensagem);
            }
            String json = body.string();
            Type tipoEmbrulhado = TypeToken.getParameterized(RespostaCamara.class, tipoResposta).getType();
            RespostaCamara<T> resultado = GsonSingleton.getInstancia().fromJson(json, tipoEmbrulhado);
            resultado.setCabecalhos(resposta.headers());
            resultado.setStatusCode(resposta.code());
            return resultado;
        }
    }

    protected int extrairCabecalhoTotalItens(RespostaCamara resposta) throws RespostaNaoEsperadaException {
        if (resposta.getCabecalhos() == null) {
            throw new RespostaNaoEsperadaException("O objeto de resposta da câmara não tem cabeçalhos.");
        }
        String valorCabecalho = resposta.getCabecalhos().get(ConstantesCamara.CABECALHO_TOTAL_ITENS);
        if (valorCabecalho == null || valorCabecalho.isBlank()) {
            throw new RespostaNaoEsperadaException("Uma consulta páginada esperava o cabeçalho x-total-count do número total de itens, mas ele está vazio.");
        }
        return Integer.parseInt(valorCabecalho);
    }

    protected <T> RespostaCamara<T> consultarPorId(long id, String urlBase, Type tipoEsperado) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        Request requisicao = new RequisicaoBuilder(urlBase).segmentosPath(String.valueOf(id)).build();
        Call chamada = client.newCall(requisicao);
        return executarChamada(chamada, tipoEsperado);
    }

    protected <T> List<T> consultarSemPaginacao(Consulta consulta, String urlBase, Type tipoEsperado, String... segmentosPath) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        RespostaCamara<List<T>> resposta = executarConsulta(consulta, urlBase, tipoEsperado, segmentosPath);
        return resposta.getDados();
    }

    protected <T> Pagina<T> consultarComPaginacao(Consulta consulta, String urlBase, Type tipoEsperado, String... segmentosPath) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        RespostaCamara<List<T>> resposta = executarConsulta(consulta, urlBase, tipoEsperado, segmentosPath);
        return new Pagina<>(resposta.getDados(),
                extrairCabecalhoTotalItens(resposta),
                extrairPaginaDaConsulta(consulta));
    }

    private <T> RespostaCamara<List<T>> executarConsulta(Consulta consulta, String urlBase, Type tipoEsperado, String... segmentosPath) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        Request requisicao = prepararConsulta(consulta, urlBase, segmentosPath);
        Call chamada = client.newCall(requisicao);
        RespostaCamara<List<T>> resposta = executarChamada(chamada, TypeToken.getParameterized(List.class, tipoEsperado).getType());
        if (resposta.getStatusCode() == 404) {
            throw new RecursoNaoExisteException("O recurso solicitado não existe: " + requisicao.url());
        }
        return resposta;
    }

    private Request prepararConsulta(Consulta consulta, String urlBase, String... segmentosPath) {
        Objects.requireNonNull(consulta, "O objeto de consulta não pode ser null. Favor utilizar um objeto vazio se desejar uma consulta sem parâmetros.");
        return new RequisicaoBuilder(urlBase)
                .consulta(consulta)
                .segmentosPath(segmentosPath)
                .build();
    }

    private int extrairPaginaDaConsulta(Consulta consulta) {
        String paginaString = consulta.getParametros().get("pagina");
        if (paginaString == null || paginaString.isBlank()) {
            paginaString = "1";
        }
        return Integer.parseInt(paginaString);
    }
}
