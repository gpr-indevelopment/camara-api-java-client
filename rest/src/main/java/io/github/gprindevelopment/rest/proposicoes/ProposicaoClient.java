package io.github.gprindevelopment.rest.proposicoes;

import io.github.gprindevelopment.core.Autor;
import io.github.gprindevelopment.core.DetalhesProposicao;
import io.github.gprindevelopment.core.Proposicao;
import io.github.gprindevelopment.core.TramitacaoProposicao;
import io.github.gprindevelopment.core.common.ConstantesCamara;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import io.github.gprindevelopment.rest.common.ComponenteClient;
import io.github.gprindevelopment.rest.common.Consulta;
import io.github.gprindevelopment.rest.common.RespostaCamara;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProposicaoClient extends ComponenteClient {

    public Pagina<Proposicao> consultar(ConsultaProposicao consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        return consultarComPaginacao(consulta, ConstantesCamara.PROPOSICAO_API_URL, Proposicao.class);
    }

    public List<Autor> consultarAutores(long idProposicao) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        return consultarSemPaginacao(new Consulta.Builder().build(),
                ConstantesCamara.PROPOSICAO_API_URL,
                Autor.class,
                new String[]{String.valueOf(idProposicao), "autores"});
    }

    public Optional<DetalhesProposicao> consultarDetalhes(long idProposicao) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        RespostaCamara<DetalhesProposicao> resposta = consultarPorId(String.valueOf(idProposicao), ConstantesCamara.PROPOSICAO_API_URL, DetalhesProposicao.class);
        return Optional.ofNullable(resposta.getDados());
    }

    public List<TramitacaoProposicao> consultarTramitacoes(ConsultaTramitacao consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        return consultarSemPaginacao(consulta,
                ConstantesCamara.PROPOSICAO_API_URL,
                TramitacaoProposicao.class,
                new String[]{String.valueOf(consulta.getIdProposicao()), "tramitacoes"});
    }
}
