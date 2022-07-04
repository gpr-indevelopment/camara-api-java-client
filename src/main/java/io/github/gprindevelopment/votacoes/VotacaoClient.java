package io.github.gprindevelopment.votacoes;

import io.github.gprindevelopment.dominio.DetalheVotacao;
import io.github.gprindevelopment.dominio.OrientacaoVoto;
import io.github.gprindevelopment.dominio.Votacao;
import io.github.gprindevelopment.dominio.Voto;
import io.github.gprindevelopment.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class VotacaoClient extends Client {

    public VotacaoClient() {
        // Workaround necessário já que a API de votações é lenta. Uso de cache é recomendado.
        super(OkHttpClientSingleton
                .getInstancia()
                .newBuilder()
                .readTimeout(1, TimeUnit.MINUTES)
                .build());
    }

    public Pagina<Votacao> consultar(ConsultaVotacao consulta) throws IOException {
        return consultarComPaginacao(consulta, ConstantesApiCamara.VOTACAO_API_URL, Votacao.class);
    }

    public Optional<DetalheVotacao> consultarDetalhes(String idVotacao) throws IOException {
        validarIdVotacao(idVotacao);
        RespostaCamara<DetalheVotacao> resposta = consultarPorId(idVotacao, ConstantesApiCamara.VOTACAO_API_URL, DetalheVotacao.class);
        return Optional.ofNullable(resposta.getDados());
    }

    public List<Voto> consultarVotos(String idVotacao) throws IOException {
        validarIdVotacao(idVotacao);
        return consultarSemPaginacao(
                new ConsultaVotacao.Builder().build(),
                ConstantesApiCamara.VOTACAO_API_URL,
                Voto.class,
                new String[]{idVotacao, "votos"});
    }

    public List<OrientacaoVoto> consultarOrientacoes(String idVotacao) throws IOException {
        validarIdVotacao(idVotacao);
        return consultarSemPaginacao(
                new ConsultaVotacao.Builder().build(),
                ConstantesApiCamara.VOTACAO_API_URL,
                OrientacaoVoto.class,
                new String[]{idVotacao, "orientacoes"}
        );
    }

    private void validarIdVotacao(String idVotacao) {
        Objects.requireNonNull(idVotacao, "O ID de votação não pode ser null.");
        if (idVotacao.isBlank()) {
            throw new IllegalArgumentException("O ID de votação não pode estar em branco para uma consulta.");
        }
    }
}
