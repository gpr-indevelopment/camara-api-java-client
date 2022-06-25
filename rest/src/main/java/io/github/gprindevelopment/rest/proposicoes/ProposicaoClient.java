package io.github.gprindevelopment.rest.proposicoes;

import com.google.gson.reflect.TypeToken;
import io.github.gprindevelopment.core.Autor;
import io.github.gprindevelopment.core.DetalhesProposicao;
import io.github.gprindevelopment.core.Proposicao;
import io.github.gprindevelopment.core.common.ConstantesCamara;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import io.github.gprindevelopment.rest.common.ComponenteClient;
import io.github.gprindevelopment.rest.common.RequisicaoBuilder;
import io.github.gprindevelopment.rest.common.RespostaCamara;
import okhttp3.Call;
import okhttp3.Request;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProposicaoClient extends ComponenteClient {

    public Pagina<Proposicao> consultar(ConsultaProposicao consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        return consultarComPaginacao(consulta, ConstantesCamara.PROPOSICAO_API_URL, Proposicao.class);
    }

    public List<Autor> consultarAutores(long idProposicao) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        Request requisicao = new RequisicaoBuilder(ConstantesCamara.PROPOSICAO_API_URL).segmentosPath(String.valueOf(idProposicao), "autores").build();
        Call chamada = client.newCall(requisicao);
        RespostaCamara<List<Autor>> resposta = executarChamada(chamada, TypeToken.getParameterized(List.class, Autor.class).getType());
        return resposta.getDados();
    }

    public Optional<DetalhesProposicao> consultarDetalhes(long idProposicao) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        RespostaCamara<DetalhesProposicao> resposta = consultarPorId(idProposicao, ConstantesCamara.PROPOSICAO_API_URL, DetalhesProposicao.class);
        return Optional.ofNullable(resposta.getDados());
    }
}
