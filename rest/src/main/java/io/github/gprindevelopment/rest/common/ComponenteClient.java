package io.github.gprindevelopment.rest.common;

import com.google.gson.reflect.TypeToken;
import io.github.gprindevelopment.core.common.ConstantesCamara;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;

public class ComponenteClient {

    protected <T> RespostaCamara<T> executarChamada(Call chamada, Type tipoResposta) throws IOException, CamaraClientStatusException, RespostaNaoEsperadaException {
        try (Response resposta = chamada.execute()) {
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
}
