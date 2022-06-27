package io.github.gprindevelopment.dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VotacaoTest {

    @Test
    public void votacoes_sao_igualadas_pelo_id() {
        Votacao vot1 = new Votacao("a");
        Votacao vot2 = new Votacao("a");
        assertEquals(vot1, vot2);
    }

    @Test
    public void votacoes_devem_ter_id_valido() {
        assertThrows(NullPointerException.class, () -> new Votacao(null));
        assertThrows(IllegalArgumentException.class, () -> new Votacao(""));
        assertThrows(IllegalArgumentException.class, () -> new Votacao(" "));
    }
}
