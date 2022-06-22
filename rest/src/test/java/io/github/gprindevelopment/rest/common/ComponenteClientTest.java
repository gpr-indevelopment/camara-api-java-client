package io.github.gprindevelopment.rest.common;

import io.github.gprindevelopment.rest.mother.ResponseMother;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;

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

        CamaraClientStatusException e = assertThrows(CamaraClientStatusException.class, () -> client.parseResultadoChamada(chamada, String.class));
        assertEquals(statusCode, e.getStatusCode());
        assertEquals(corpoErro, e.getCorpoResposta());
    }

    @Test
    public void se_requisicao_nao_for_status_200_e_nao_tiver_corpo_disparar_erro() throws IOException {
        int statusCode = 400;
        Response resposta = ResponseMother.gerarResponse(null, statusCode);

        Call chamada = mock(Call.class);
        when(chamada.execute()).thenReturn(resposta);

        CamaraClientStatusException e = assertThrows(CamaraClientStatusException.class, () -> client.parseResultadoChamada(chamada, String.class));
        assertEquals(statusCode, e.getStatusCode());
        assertNull(e.getCorpoResposta());
    }

    @Test
    public void se_requisicao_esperar_resposta_mas_corpo_for_null_disparar_erro() throws IOException {
        Response resposta = ResponseMother.gerarResponse(null, 200);
        Type classeEsperada = String.class;

        Call chamada = mock(Call.class);
        when(chamada.execute()).thenReturn(resposta);

        RespostaNaoEsperadaException e = assertThrows(RespostaNaoEsperadaException.class, () -> client.parseResultadoChamada(chamada, classeEsperada));
        assertTrue(e.getMessage().contains("String"));
    }
}
