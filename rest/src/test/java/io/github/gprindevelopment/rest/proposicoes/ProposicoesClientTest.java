package io.github.gprindevelopment.rest.proposicoes;

import io.github.gprindevelopment.core.Autor;
import io.github.gprindevelopment.core.DetalhesProposicao;
import io.github.gprindevelopment.core.Proposicao;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProposicoesClientTest {

    private final ProposicaoClient client = new ProposicaoClient();

    @Test
    public void consultar_proposicoes_por_deputado_autor_retorna_proposicoes() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        int deputadoAutor = 160976;
        int itens = 3;
        int paginaAtual = 1;
        ConsultaProposicao consulta = new ConsultaProposicao.Builder()
                .itens(itens)
                .pagina(paginaAtual)
                .idAutores(deputadoAutor)
                .build();

        Pagina<Proposicao> proposicoes = client.consultar(consulta);
        assertEquals(itens, proposicoes.size());
        assertEquals(paginaAtual, proposicoes.getPaginaAtual());
        for (Proposicao proposicao : proposicoes) {
            List<Autor> autores = client.consultarAutores(proposicao.getId());
            String idDeputadoAutorString = String.valueOf(deputadoAutor);
            assertTrue(autores.stream().anyMatch(autor -> autor.getUri().toString().contains(idDeputadoAutorString)));
        }
    }

    @Test
    public void consultar_proposicao_por_id_retorna_detalhes() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        long idProposicao = 2191128;
        Optional<DetalhesProposicao> detalhesOpt = client.consultarDetalhes(idProposicao);
        assertTrue(detalhesOpt.isPresent());
        DetalhesProposicao detalhes = detalhesOpt.get();
        assertEquals(idProposicao, detalhes.getId());
        assertNotNull(detalhes.getUrlInteiroTeor());
        assertNotNull(detalhes.getDataApresentacao());
        assertNotNull(detalhes.getStatusProposicao());
        assertNotNull(detalhes.getStatusProposicao().getDataHora());
    }

    @Test
    public void consultar_proposicao_por_id_nao_existente_retorna_vazio() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        long idProposicao = 1;
        Optional<DetalhesProposicao> detalhesOpt = client.consultarDetalhes(idProposicao);
        assertTrue(detalhesOpt.isEmpty());
    }
}
