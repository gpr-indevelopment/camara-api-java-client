package io.github.gprindevelopment.core;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LegislaturaTest {

    @Test
    public void legislaturas_sao_igualadas_pelo_id() throws URISyntaxException {
        Legislatura leg1 = new Legislatura(
                56,
                new URI(ConstantesCamara.BASE_URL),
                LocalDate.of(2020, 1, 2),
                LocalDate.of(2021, 1, 2));
        Legislatura leg2 = new Legislatura(
                56,
                new URI(ConstantesCamara.BASE_URL),
                LocalDate.of(2022, 1, 2),
                LocalDate.of(2024, 1, 2));
        assertEquals(leg1, leg2);
    }
}
