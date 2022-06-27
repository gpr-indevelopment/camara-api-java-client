package io.github.gprindevelopment.proposicoes;

import io.github.gprindevelopment.http.Client;
import io.github.gprindevelopment.http.RespostaCamara;
import io.github.gprindevelopment.dominio.Autor;
import io.github.gprindevelopment.dominio.DetalhesProposicao;
import io.github.gprindevelopment.dominio.Proposicao;
import io.github.gprindevelopment.dominio.TramitacaoProposicao;
import io.github.gprindevelopment.http.ConstantesApiCamara;
import io.github.gprindevelopment.http.Pagina;
import io.github.gprindevelopment.exception.CamaraClientStatusException;
import io.github.gprindevelopment.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.exception.RespostaNaoEsperadaException;
import io.github.gprindevelopment.http.Consulta;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProposicaoClient extends Client {

    public Pagina<Proposicao> consultar(ConsultaProposicao consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        return consultarComPaginacao(consulta, ConstantesApiCamara.PROPOSICAO_API_URL, Proposicao.class);
    }

    public List<Autor> consultarAutores(long idProposicao) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        return consultarSemPaginacao(new Consulta.Builder().build(),
                ConstantesApiCamara.PROPOSICAO_API_URL,
                Autor.class,
                new String[]{String.valueOf(idProposicao), "autores"});
    }

    public Optional<DetalhesProposicao> consultarDetalhes(long idProposicao) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        RespostaCamara<DetalhesProposicao> resposta = consultarPorId(String.valueOf(idProposicao), ConstantesApiCamara.PROPOSICAO_API_URL, DetalhesProposicao.class);
        return Optional.ofNullable(resposta.getDados());
    }

    public List<TramitacaoProposicao> consultarTramitacoes(ConsultaTramitacao consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        return consultarSemPaginacao(consulta,
                ConstantesApiCamara.PROPOSICAO_API_URL,
                TramitacaoProposicao.class,
                new String[]{String.valueOf(consulta.getIdProposicao()), "tramitacoes"});
    }
}