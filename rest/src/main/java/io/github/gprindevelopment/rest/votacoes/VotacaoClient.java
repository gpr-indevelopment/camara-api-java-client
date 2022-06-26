package io.github.gprindevelopment.rest.votacoes;

import io.github.gprindevelopment.core.DetalheVotacao;
import io.github.gprindevelopment.core.OrientacaoVoto;
import io.github.gprindevelopment.core.Votacao;
import io.github.gprindevelopment.core.Voto;
import io.github.gprindevelopment.core.common.ConstantesCamara;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import io.github.gprindevelopment.rest.common.ComponenteClient;
import io.github.gprindevelopment.rest.common.RespostaCamara;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class VotacaoClient extends ComponenteClient {

    public VotacaoClient() {
        // Workaround necessário já que a API de votações é lenta. Uso de cache é recomendado.
        super(new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build());
    }

    public Pagina<Votacao> consultar(ConsultaVotacao consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, RecursoNaoExisteException, IOException {
        return consultarComPaginacao(consulta, ConstantesCamara.VOTACAO_API_URL, Votacao.class);
    }

    public Optional<DetalheVotacao> consultarDetalhes(String idVotacao) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        RespostaCamara<DetalheVotacao> resposta = consultarPorId(idVotacao, ConstantesCamara.VOTACAO_API_URL, DetalheVotacao.class);
        return Optional.ofNullable(resposta.getDados());
    }

    public List<Voto> consultarVotos(String idVotacao) throws RespostaNaoEsperadaException, CamaraClientStatusException, RecursoNaoExisteException, IOException {
        return consultarSemPaginacao(
                new ConsultaVotacao.Builder().build(),
                ConstantesCamara.VOTACAO_API_URL,
                Voto.class,
                new String[]{idVotacao, "votos"});
    }

    public List<OrientacaoVoto> consultarOrientacoes(String idVotacao) throws RespostaNaoEsperadaException, CamaraClientStatusException, RecursoNaoExisteException, IOException {
        return consultarSemPaginacao(
                new ConsultaVotacao.Builder().build(),
                ConstantesCamara.VOTACAO_API_URL,
                OrientacaoVoto.class,
                new String[]{idVotacao, "orientacoes"}
        );
    }
}
