package io.github.gprindevelopment.dominio;

import io.github.gprindevelopment.http.ConstantesApiCamara;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LegislaturaTest {

    @Test
    public void legislaturas_sao_igualadas_pelo_id_e_datas() throws URISyntaxException {
        Legislatura leg1 = new Legislatura(
                56,
                new URI(ConstantesApiCamara.BASE_URL),
                LocalDate.of(2020, 1, 2),
                LocalDate.of(2021, 1, 2));
        Legislatura leg2 = new Legislatura(
                56,
                new URI(ConstantesApiCamara.BASE_URL + "/algo"),
                LocalDate.of(2020, 1, 2),
                LocalDate.of(2021, 1, 2));
        assertEquals(leg1, leg2);
        Legislatura leg3 = new Legislatura(
                56,
                new URI(ConstantesApiCamara.BASE_URL),
                LocalDate.of(2020, 1, 2),
                LocalDate.of(2022, 1, 2));
        assertNotEquals(leg1, leg3);
    }

    @Test
    public void legislatura_precisa_de_id_e_datas() {
        assertThrows(IllegalArgumentException.class, () -> new Legislatura(
                -1,
                new URI(ConstantesApiCamara.BASE_URL),
                LocalDate.of(2020, 1, 2),
                LocalDate.of(2021, 1, 2)));
        assertThrows(IllegalArgumentException.class, () -> new Legislatura(
                0,
                new URI(ConstantesApiCamara.BASE_URL),
                LocalDate.of(2020, 1, 2),
                LocalDate.of(2021, 1, 2)));
        assertThrows(NullPointerException.class, () -> new Legislatura(
                1,
                new URI(ConstantesApiCamara.BASE_URL),
                null,
                LocalDate.of(2021, 1, 2)));
        assertThrows(NullPointerException.class, () -> new Legislatura(
                1,
                new URI(ConstantesApiCamara.BASE_URL),
                LocalDate.of(2020, 1, 2),
                null));
    }
}
