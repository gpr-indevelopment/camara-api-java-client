package io.github.gprindevelopment.rest.common;

import io.github.gprindevelopment.core.common.ConstantesCamara;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import io.github.gprindevelopment.rest.mother.ResponseMother;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComponenteClientTest {

    private final ComponenteClient client = new ComponenteClient();

    @Test
    public void se_requisicao_nao_for_status_200_disparar_erro() throws IOException {
        int statusCode = 400;
        String corpoErro = "{mensagem: \"Um erro ocorreu!\"}";
        Response resposta = ResponseMother.gerarResponse(corpoErro, statusCode);

        Call chamada = mock(Call.class);
        when(chamada.execute()).thenReturn(resposta);

        CamaraClientStatusException e = assertThrows(CamaraClientStatusException.class, () -> client.executarChamada(chamada, String.class));
        assertEquals(statusCode, e.getStatusCode());
        assertEquals(corpoErro, e.getCorpoResposta());
    }

    @Test
    public void se_requisicao_nao_for_status_200_e_nao_tiver_corpo_disparar_erro() throws IOException {
        int statusCode = 400;
        Response resposta = ResponseMother.gerarResponse(null, statusCode);

        Call chamada = mock(Call.class);
        when(chamada.execute()).thenReturn(resposta);

        CamaraClientStatusException e = assertThrows(CamaraClientStatusException.class, () -> client.executarChamada(chamada, String.class));
        assertEquals(statusCode, e.getStatusCode());
        assertNull(e.getCorpoResposta());
    }

    @Test
    public void se_requisicao_esperar_resposta_mas_corpo_for_null_disparar_erro() throws IOException {
        Response resposta = ResponseMother.gerarResponse(null, 200);
        Type classeEsperada = String.class;

        Call chamada = mock(Call.class);
        when(chamada.execute()).thenReturn(resposta);

        RespostaNaoEsperadaException e = assertThrows(RespostaNaoEsperadaException.class, () -> client.executarChamada(chamada, classeEsperada));
        assertTrue(e.getMessage().contains("String"));
    }

    @Test
    public void consulta_paginada_disparar_erro_se_nao_houverem_cabecalhos() {
        RespostaCamara<String> respostaCamara1 = new RespostaCamara<>("Algum dado");
        assertThrows(RespostaNaoEsperadaException.class, () -> client.extrairCabecalhoTotalItens(respostaCamara1));

        RespostaCamara<String> respostaCamara2 = new RespostaCamara<>("Algum dado");
        respostaCamara2.setCabecalhos(Headers.of(new HashMap<>()));
        assertThrows(RespostaNaoEsperadaException.class, () -> client.extrairCabecalhoTotalItens(respostaCamara2));
    }

    @Test
    public void consulta_paginada_extrair_total_itens_do_cabecalho() throws RespostaNaoEsperadaException {
        int totalItens = 56;
        RespostaCamara<String> respostaCamara = new RespostaCamara<>("Algum dado");
        respostaCamara.setCabecalhos(Headers.of(ConstantesCamara.CABECALHO_TOTAL_ITENS, String.valueOf(totalItens)));
        int extraido = client.extrairCabecalhoTotalItens(respostaCamara);
        assertEquals(totalItens, extraido);
    }
}
