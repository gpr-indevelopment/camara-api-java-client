package io.github.gprindevelopment.rest.votacoes;

import io.github.gprindevelopment.core.DetalheVotacao;
import io.github.gprindevelopment.core.OrientacaoVoto;
import io.github.gprindevelopment.core.Votacao;
import io.github.gprindevelopment.core.Voto;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VotacaoClientTest {

    private final VotacaoClient client = new VotacaoClient();

    @Test
    public void consultar_votacao_por_id_de_proposicao_retorna_votacoes() throws RespostaNaoEsperadaException, CamaraClientStatusException, RecursoNaoExisteException, IOException {
        long idProposicao = 2293449;
        int itens = 2;
        int paginaAtual = 1;
        ConsultaVotacao consulta = new ConsultaVotacao.Builder()
                .proposicoes(idProposicao)
                .itens(itens)
                .pagina(paginaAtual)
                .build();
        Pagina<Votacao> votacoes = client.consultar(consulta);
        assertEquals(itens, votacoes.size());
        assertEquals(paginaAtual, votacoes.getPaginaAtual());
        assertTrue(votacoes.temProxima());
        votacoes.forEach(votacao -> {
            assertNotNull(votacao.getId());
            assertNotNull(votacao.getDescricao());
            assertNotNull(votacao.getDataHoraRegistro());
        });
    }

    @Test
    public void consultar_votacoes_para_proposicao_que_nao_existe_retorna_vazio() throws RespostaNaoEsperadaException, CamaraClientStatusException, RecursoNaoExisteException, IOException {
        long idProposicao = 1;
        ConsultaVotacao consulta = new ConsultaVotacao.Builder()
                .proposicoes(idProposicao)
                .build();
        Pagina<Votacao> votacoes = client.consultar(consulta);
        assertTrue(votacoes.isEmpty());
    }

    @Test
    public void consultar_detalhes_de_votacao_retorna_detalhes() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        String idVotacao = "2293449-250";
        Optional<DetalheVotacao> detalheOpt = client.consultarDetalhes(idVotacao);
        assertTrue(detalheOpt.isPresent());
        DetalheVotacao detalhe = detalheOpt.get();
        assertEquals(idVotacao, detalhe.getId());
        assertNotNull(detalhe.getDescUltimaAberturaVotacao());
        assertNotNull(detalhe.getDescricao());
        assertNotNull(detalhe.getDataHoraRegistro());
    }

    @Test
    public void consultar_detalhes_de_votacao_que_nao_existe_retorna_vazio() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        String idVotacao = "1";
        Optional<DetalheVotacao> detalheOpt = client.consultarDetalhes(idVotacao);
        assertTrue(detalheOpt.isEmpty());
    }

    @Test
    public void consultar_votos_retorna_lista_nao_paginada_de_votos() throws RespostaNaoEsperadaException, CamaraClientStatusException, RecursoNaoExisteException, IOException {
        String idVotacao = "2293449-250";
        List<Voto> votos = client.consultarVotos(idVotacao);
        assertFalse(votos.isEmpty());
        votos.forEach(voto -> {
            assertNotNull(voto.getTipoVoto());
            assertNotNull(voto.getDataRegistroVoto());
            assertNotNull(voto.getDeputado());
        });
    }

    @Test
    public void consultar_votos_de_uma_votacao_que_nao_existe_dispara_erro() {
        String idVotacao = "1";
        assertThrows(RecursoNaoExisteException.class, () -> client.consultarVotos(idVotacao));
    }

    @Test
    public void consultar_orientacoes_de_votacao_retorna_lista_nao_paginada() throws RespostaNaoEsperadaException, CamaraClientStatusException, RecursoNaoExisteException, IOException {
        String idVotacao = "2293449-250";
        List<OrientacaoVoto> orientacoes = client.consultarOrientacoes(idVotacao);
        assertFalse(orientacoes.isEmpty());
        orientacoes.forEach(orientacao -> {
            assertNotNull(orientacao.getOrientacaoVoto());
            assertNotNull(orientacao.getSiglaPartidoBloco());
            assertTrue(orientacao.getCodPartidoBloco() >= 0);
            assertNotNull(orientacao.getCodTipoLideranca());
        });
    }

    @Test
    public void consultar_orientacoes_de_votacao_que_nao_existe_dispara_erro() {
        String idVotacao = "1";
        assertThrows(RecursoNaoExisteException.class, () -> client.consultarOrientacoes(idVotacao));
    }

    @Test
    public void apis_de_votacao_devem_receber_strings_preenchidas_como_id() {
        assertThrows(IllegalArgumentException.class, () -> client.consultarVotos(""));
        assertThrows(IllegalArgumentException.class, () -> client.consultarVotos(" "));
        assertThrows(IllegalArgumentException.class, () -> client.consultarOrientacoes(""));
        assertThrows(IllegalArgumentException.class, () -> client.consultarOrientacoes(" "));
        assertThrows(IllegalArgumentException.class, () -> client.consultarDetalhes(""));
        assertThrows(IllegalArgumentException.class, () -> client.consultarDetalhes(" "));

        assertThrows(NullPointerException.class, () -> client.consultarDetalhes(null));
        assertThrows(NullPointerException.class, () -> client.consultarVotos(null));
        assertThrows(NullPointerException.class, () -> client.consultarOrientacoes(null));
    }
}
