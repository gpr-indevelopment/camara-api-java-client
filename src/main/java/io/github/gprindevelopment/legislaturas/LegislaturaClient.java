package io.github.gprindevelopment.legislaturas;

import io.github.gprindevelopment.http.*;
import io.github.gprindevelopment.dominio.Legislatura;
import io.github.gprindevelopment.exception.CamaraClientStatusException;
import io.github.gprindevelopment.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.exception.RespostaNaoEsperadaException;

import java.io.IOException;
import java.util.Optional;

public class LegislaturaClient extends Client {

    public Legislatura consultarLegislaturaAtual() throws IOException, CamaraClientStatusException, RespostaNaoEsperadaException, RecursoNaoExisteException {
        Consulta consulta = new Consulta.Builder()
                .itens(1)
                .pagina(1)
                .ordenarPor("id", Ordem.DESC)
                .build();
        return consultar(consulta).get(0);
    }

    public Optional<Legislatura> consultarLegislaturaPorId(int id) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        RespostaCamara<Legislatura> resposta = consultarPorId(String.valueOf(id), ConstantesApiCamara.LEGISLATURA_API_URL, Legislatura.class);
        return Optional.ofNullable(resposta.getDados());
    }

    public Pagina<Legislatura> consultar(Consulta consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        return consultarComPaginacao(consulta, ConstantesApiCamara.LEGISLATURA_API_URL, Legislatura.class);
    }
}