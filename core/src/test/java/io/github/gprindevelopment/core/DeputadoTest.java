package io.github.gprindevelopment.core;

import io.github.gprindevelopment.core.mother.DeputadoMother;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeputadoTest {

    @Test
    public void deputados_sao_iguais_se_id_for_igual() throws MalformedURLException {
        Deputado deputado1 = DeputadoMother.gerarDeputadoSimples(1, "Gabriel");
        Deputado deputado2 = DeputadoMother.gerarDeputadoSimples(1, "Gabriel Pimenta");
        assertEquals(deputado1, deputado2);
    }

    @Test
    public void deputados_sao_ordenados_por_id() throws MalformedURLException {
        Deputado deputado1 = DeputadoMother.gerarDeputadoSimples(1, "Gabriel");
        Deputado deputado2 = DeputadoMother.gerarDeputadoSimples(2, "Gabriel Pimenta");
        List<Deputado> deputados = new ArrayList<>();
        deputados.add(deputado2);
        deputados.add(deputado1);
        deputados.sort(Comparator.naturalOrder());
        assertEquals(deputado1, deputados.get(0));
        assertEquals(deputado2, deputados.get(1));
    }
}
