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

    @Test
    public void resposta_404_retorna_resposta_vazia_e_nao_um_erro() throws IOException, RespostaNaoEsperadaException, CamaraClientStatusException {
        int statusCode = 404;
        Response resposta = ResponseMother.gerarResponse("Teste", statusCode);

        Call chamada = mock(Call.class);
        when(chamada.execute()).thenReturn(resposta);

        RespostaCamara<String> resultado = client.executarChamada(chamada, String.class);
        assertNull(resultado.getDados());
        assertEquals(statusCode, resultado.getStatusCode());
    }

    @Test
    public void resposta_valida_retorna_objeto_de_dados_com_status_200() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        int statusCode = 200;
        String corpoEsperado = "{\"dados\": \"Teste\"}";
        Response resposta = ResponseMother.gerarResponse(corpoEsperado, statusCode);

        Call chamada = mock(Call.class);
        when(chamada.execute()).thenReturn(resposta);

        RespostaCamara<String> resultado = client.executarChamada(chamada, String.class);
        assertEquals(statusCode, resultado.getStatusCode());
        assertEquals("Teste", resultado.getDados());
    }

    @Test
    public void consultar_por_id_precisa_de_uma_string_base_valida() {
        assertThrows(NullPointerException.class, () -> client.consultarPorId(String.valueOf(9), null, String.class));
        assertThrows(IllegalArgumentException.class, () -> client.consultarPorId(String.valueOf(9), " ", String.class));
        assertThrows(IllegalArgumentException.class, () -> client.consultarPorId(String.valueOf(9), "", String.class));
        assertThrows(IllegalArgumentException.class, () -> client.consultarPorId(String.valueOf(9), "aaa", String.class));
    }

    @Test
    public void consulta_com_paginacao_precisa_de_uma_consulta_e_url_validos() {
        assertThrows(NullPointerException.class, () -> client.consultarComPaginacao(null, "a", String.class));
        assertThrows(IllegalArgumentException.class, () -> client.consultarComPaginacao(new Consulta.Builder().build(), "", String.class));
        assertThrows(IllegalArgumentException.class, () -> client.consultarComPaginacao(new Consulta.Builder().build(), " ", String.class));
        assertThrows(IllegalArgumentException.class, () -> client.consultarComPaginacao(new Consulta.Builder().build(), "aaa", String.class));
    }
}
