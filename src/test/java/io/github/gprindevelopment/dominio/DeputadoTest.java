package io.github.gprindevelopment.dominio;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeputadoTest {

    @Test
    public void deputados_sao_iguais_se_id_for_igual() throws MalformedURLException {
        Deputado deputado1 = new Deputado(1);
        Deputado deputado2 = new Deputado(1);
        assertEquals(deputado1, deputado2);
    }

    @Test
    public void deputados_sao_ordenados_por_id() throws MalformedURLException {
        Deputado deputado1 = new Deputado(1);
        Deputado deputado2 = new Deputado(2);
        List<Deputado> deputados = new ArrayList<>();
        deputados.add(deputado2);
        deputados.add(deputado1);
        deputados.sort(Comparator.naturalOrder());
        assertEquals(deputado1, deputados.get(0));
        assertEquals(deputado2, deputados.get(1));
    }

    @Test
    public void deputados_tem_que_ter_id_maior_igual_a_zero() {
        assertThrows(IllegalArgumentException.class, () -> new Deputado(0));
        assertThrows(IllegalArgumentException.class, () -> new Deputado(-1));
    }
}
