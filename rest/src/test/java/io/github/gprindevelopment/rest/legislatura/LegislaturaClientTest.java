package io.github.gprindevelopment.rest.legislatura;

import io.github.gprindevelopment.core.Legislatura;
import io.github.gprindevelopment.rest.common.CamaraClientStatusException;
import io.github.gprindevelopment.rest.common.RespostaNaoEsperadaException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LegislaturaClientTest {

    private final LegislaturaClient client = new LegislaturaClient();

    @Test
    public void consultar_legislatura_corrente_retorna_56() throws IOException, CamaraClientStatusException, RespostaNaoEsperadaException {
        Legislatura legislatura = client.procurarLegislaturaCorrente();
        int legislaturaCorrente = 56;
        assertEquals(legislaturaCorrente, legislatura.getId());
        assertEquals(LocalDate.of(2019, 2, 1), legislatura.getDataInicio());
        assertEquals(LocalDate.of(2023, 1, 31), legislatura.getDataFim());
        assertNotNull(legislatura.getUri());
    }
}
