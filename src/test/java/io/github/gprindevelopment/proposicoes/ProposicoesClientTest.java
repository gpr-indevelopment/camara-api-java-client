package io.github.gprindevelopment.proposicoes;

import io.github.gprindevelopment.dominio.Autor;
import io.github.gprindevelopment.dominio.DetalhesProposicao;
import io.github.gprindevelopment.dominio.Proposicao;
import io.github.gprindevelopment.dominio.TramitacaoProposicao;
import io.github.gprindevelopment.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.http.Pagina;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProposicoesClientTest {

    private final ProposicaoClient client = new ProposicaoClient();

    @Test
    public void consultar_proposicoes_por_deputado_autor_retorna_proposicoes() throws IOException {
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
    public void consultar_proposicao_por_id_retorna_detalhes() throws IOException {
        long idProposicao = 2191128;
        Optional<DetalhesProposicao> detalhesOpt = client.consultarDetalhes(idProposicao);
        assertTrue(detalhesOpt.isPresent());
        DetalhesProposicao detalhes = detalhesOpt.get();
        assertEquals(idProposicao, detalhes.getId());
        assertNotNull(detalhes.getUrlInteiroTeor());
        assertNotNull(detalhes.getStatusProposicao());
        assertNotNull(detalhes.getStatusProposicao().getDataHora());
        assertEquals(2019, detalhes.getDataApresentacao().getYear());
        assertEquals(2, detalhes.getDataApresentacao().getMonthValue());
        assertEquals(5, detalhes.getDataApresentacao().getDayOfMonth());
        assertEquals(15, detalhes.getDataApresentacao().getHour());
        assertEquals(42, detalhes.getDataApresentacao().getMinute());
    }

    @Test
    public void consultar_proposicao_por_id_nao_existente_retorna_vazio() throws IOException {
        long idProposicao = 1;
        Optional<DetalhesProposicao> detalhesOpt = client.consultarDetalhes(idProposicao);
        assertTrue(detalhesOpt.isEmpty());
    }

    @Test
    public void consultar_tramitacao_de_proposicao_retorna_lista_sem_paginacao() throws IOException {
        long idProposicao = 2191128;
        ConsultaTramitacao consulta = new ConsultaTramitacao.Builder(idProposicao).build();
        List<TramitacaoProposicao> tramitacoes = client.consultarTramitacoes(consulta);
        assertFalse(tramitacoes.isEmpty());
        TramitacaoProposicao tramitacao = tramitacoes.get(0);
        assertNotNull(tramitacao.getDataHora());
        assertTrue(tramitacao.getSequencia() > 0);
        assertNotNull(tramitacao.getDespacho());
        assertNotNull(tramitacao.getDescricaoTramitacao());
    }

    @Test
    public void consultar_tramitacao_de_proposicao_por_data_retorna_tramitacoes_nas_datas() throws IOException {
        long idProposicao = 2191128;
        LocalDate dataInicio = LocalDate.of(2019, 2, 1);
        LocalDate dataFim = LocalDate.of(2019, 3, 1);
        ConsultaTramitacao consulta = new ConsultaTramitacao.Builder(idProposicao)
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .build();
        List<TramitacaoProposicao> tramitacoes = client.consultarTramitacoes(consulta);
        for (TramitacaoProposicao tramitacao : tramitacoes) {
            assertTrue(tramitacao.getDataHora().isAfter(dataInicio.atTime(0, 1))
                    && tramitacao.getDataHora().isBefore(dataFim.atTime(0, 1)));
        }
    }

    @Test
    public void consultar_tramitacao_de_proposicao_que_nao_existe_dispara_erro() {
        long idProposicao = 1;
        ConsultaTramitacao consulta = new ConsultaTramitacao.Builder(idProposicao).build();
        assertThrows(RecursoNaoExisteException.class, () -> client.consultarTramitacoes(consulta));
    }

    @Test
    public void consultar_autores_de_proposicao_que_nao_existe_dispara_erro() {
        long idProposicao = 1;
        assertThrows(RecursoNaoExisteException.class, () -> client.consultarAutores(idProposicao));
    }
}
