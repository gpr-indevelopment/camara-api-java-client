package io.github.gprindevelopment.http;

import io.github.gprindevelopment.exception.CamaraClientStatusException;
import io.github.gprindevelopment.exception.RespostaInesperadaException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CamaraClientTest {

    private final HttpClient httpClient = mock(HttpClient.class);

    private final CamaraClient camaraClient = new CamaraClient(httpClient);

    private final HttpRequest requisicaoDummy = HttpRequest.newBuilder(URI.create("http://google.com")).build();
    @Test
    public void se_requisicao_nao_for_status_200_disparar_erro() throws IOException, InterruptedException {
        int statusCode = 400;
        String corpoErro = "{mensagem: \"Um erro ocorreu!\"}";

        HttpResponse<String> respostaEsperada = mock(HttpResponse.class);
        when(httpClient.send(requisicaoDummy, HttpResponse.BodyHandlers.ofString())).thenReturn(respostaEsperada);
        when(respostaEsperada.statusCode()).thenReturn(statusCode);
        when(respostaEsperada.body()).thenReturn(corpoErro);

        CamaraClientStatusException e = assertThrows(CamaraClientStatusException.class, () -> camaraClient.executarChamada(requisicaoDummy, String.class));
        assertEquals(statusCode, e.getStatusCode());
        assertEquals(corpoErro, e.getCorpoResposta());
    }

    @Test
    public void se_requisicao_nao_for_status_200_e_nao_tiver_corpo_disparar_erro() throws IOException, InterruptedException {
        int statusCode = 400;

        HttpResponse<String> respostaEsperada = mock(HttpResponse.class);
        when(httpClient.send(requisicaoDummy, HttpResponse.BodyHandlers.ofString())).thenReturn(respostaEsperada);
        when(respostaEsperada.statusCode()).thenReturn(statusCode);

        CamaraClientStatusException e = assertThrows(CamaraClientStatusException.class, () -> camaraClient.executarChamada(requisicaoDummy, String.class));
        assertEquals(statusCode, e.getStatusCode());
        assertNull(e.getCorpoResposta());
    }

    @Test
    public void se_requisicao_esperar_resposta_mas_corpo_for_null_disparar_erro() throws IOException, InterruptedException {
        int statusCode = 200;
        Type classeEsperada = String.class;

        HttpResponse<String> respostaEsperada = mock(HttpResponse.class);
        when(httpClient.send(requisicaoDummy, HttpResponse.BodyHandlers.ofString())).thenReturn(respostaEsperada);
        when(respostaEsperada.statusCode()).thenReturn(statusCode);

        RespostaInesperadaException e = assertThrows(RespostaInesperadaException.class, () -> camaraClient.executarChamada(requisicaoDummy, classeEsperada));
        assertTrue(e.getMessage().contains("String"));
    }

    @Test
    public void consulta_paginada_disparar_erro_se_nao_houverem_cabecalhos() {
        RespostaCamara<String> respostaCamara1 = new RespostaCamara<>("Algum dado");
        assertThrows(RespostaInesperadaException.class, () -> camaraClient.extrairCabecalhoTotalItens(respostaCamara1));

        RespostaCamara<String> respostaCamara2 = new RespostaCamara<>("Algum dado");
        assertThrows(RespostaInesperadaException.class, () -> camaraClient.extrairCabecalhoTotalItens(respostaCamara2));
    }

    @Test
    public void consulta_paginada_extrair_total_itens_do_cabecalho() {
        int totalItens = 56;
        RespostaCamara<String> respostaCamara = new RespostaCamara<>("Algum dado");
        Map<String, List<String>> mapaCabecalho = Map.of(ConstantesApiCamara.CABECALHO_TOTAL_ITENS, List.of(String.valueOf(totalItens)));
        respostaCamara.setCabecalhos(mapaCabecalho);
        int extraido = camaraClient.extrairCabecalhoTotalItens(respostaCamara);
        assertEquals(totalItens, extraido);
    }

    @Test
    public void resposta_404_retorna_resposta_vazia_e_nao_um_erro() throws IOException, InterruptedException {
        int statusCode = 404;

        HttpResponse<String> respostaEsperada = mock(HttpResponse.class);
        when(httpClient.send(requisicaoDummy, HttpResponse.BodyHandlers.ofString())).thenReturn(respostaEsperada);
        when(respostaEsperada.statusCode()).thenReturn(statusCode);
        when(respostaEsperada.headers()).thenReturn(HttpHeaders.of(Map.of(), (val1, val2) -> true));

        RespostaCamara<String> resultado = camaraClient.executarChamada(requisicaoDummy, String.class);
        assertNull(resultado.getDados());
        assertEquals(statusCode, resultado.getStatusCode());
    }

    @Test
    public void resposta_valida_retorna_objeto_de_dados_com_status_200() throws IOException, InterruptedException {
        int statusCode = 200;
        String corpoEsperado = "{\"dados\": \"Teste\"}";

        HttpResponse<String> respostaEsperada = mock(HttpResponse.class);
        when(httpClient.send(requisicaoDummy, HttpResponse.BodyHandlers.ofString())).thenReturn(respostaEsperada);
        when(respostaEsperada.statusCode()).thenReturn(statusCode);
        when(respostaEsperada.body()).thenReturn(corpoEsperado);
        when(respostaEsperada.headers()).thenReturn(HttpHeaders.of(Map.of(), (val1, val2) -> true));

        RespostaCamara<String> resultado = camaraClient.executarChamada(requisicaoDummy, String.class);
        assertEquals(statusCode, resultado.getStatusCode());
        assertEquals("Teste", resultado.getDados());
    }

    @Test
    public void consultar_por_id_precisa_de_uma_string_base_valida() {
        assertThrows(NullPointerException.class, () -> camaraClient.consultarPorId(String.valueOf(9), null, String.class));
        assertThrows(IllegalArgumentException.class, () -> camaraClient.consultarPorId(String.valueOf(9), " ", String.class));
        assertThrows(IllegalArgumentException.class, () -> camaraClient.consultarPorId(String.valueOf(9), "", String.class));
        assertThrows(IllegalArgumentException.class, () -> camaraClient.consultarPorId(String.valueOf(9), "aaa", String.class));
    }

    @Test
    public void consulta_com_paginacao_precisa_de_uma_consulta_e_url_validos() {
        assertThrows(NullPointerException.class, () -> camaraClient.consultarComPaginacao(null, "a", String.class));
        assertThrows(IllegalArgumentException.class, () -> camaraClient.consultarComPaginacao(new Consulta.Builder().build(), "", String.class));
        assertThrows(IllegalArgumentException.class, () -> camaraClient.consultarComPaginacao(new Consulta.Builder().build(), " ", String.class));
        assertThrows(IllegalArgumentException.class, () -> camaraClient.consultarComPaginacao(new Consulta.Builder().build(), "aaa", String.class));
    }
}
