package io.github.gprindevelopment.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TramitacaoProposicaoTest {

    @Test
    public void tramitacoes_sao_ordenadas_pela_sequencia() {
        TramitacaoProposicao tramit1 = new TramitacaoProposicao();
        TramitacaoProposicao tramit2 = new TramitacaoProposicao();
        tramit1.setSequencia(1);
        tramit2.setSequencia(2);
        List<TramitacaoProposicao> tramitacoes = new ArrayList<>();
        tramitacoes.add(tramit2);
        tramitacoes.add(tramit1);
        tramitacoes.sort(Comparator.naturalOrder());
        assertEquals(tramit1, tramitacoes.get(0));
    }
}
