package io.github.gprindevelopment.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DetalheVotacaoTest {

    @Test
    public void detalhes_de_votacao_sao_igualados_pelo_id() {
        DetalheVotacao detalhe1 = new DetalheVotacao("a");
        DetalheVotacao detalhe2 = new DetalheVotacao("a");
        assertEquals(detalhe1, detalhe2);
    }

    @Test
    public void detalhes_de_votacao_tem_que_ter_id_valido() {
        assertThrows(NullPointerException.class, () -> new DetalheVotacao(null));
        assertThrows(IllegalArgumentException.class, () -> new DetalheVotacao(""));
        assertThrows(IllegalArgumentException.class, () -> new DetalheVotacao(" "));
    }
}
