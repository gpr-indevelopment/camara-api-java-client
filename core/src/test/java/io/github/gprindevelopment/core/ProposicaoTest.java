package io.github.gprindevelopment.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProposicaoTest {

    @Test
    public void proposicoes_sao_igualadas_pelo_id() {
        Proposicao prop1 = new Proposicao(1);
        Proposicao prop2 = new Proposicao(1);
        assertEquals(prop1, prop2);
    }

    @Test
    public void proposicoes_sao_ordenadas_pelo_id() {
        Proposicao prop1 = new Proposicao(1);
        Proposicao prop2 = new Proposicao(2);
        List<Proposicao> proposicoes = new ArrayList<>();
        proposicoes.add(prop2);
        proposicoes.add(prop1);
        proposicoes.sort(Comparator.naturalOrder());
        assertEquals(prop1, proposicoes.get(0));
    }

    @Test
    public void proposicoes_nao_podem_ter_id_menor_igual_a_zero() {
        assertThrows(IllegalArgumentException.class, () -> new Proposicao(0));
        assertThrows(IllegalArgumentException.class, () -> new Proposicao(-1));
    }
}
