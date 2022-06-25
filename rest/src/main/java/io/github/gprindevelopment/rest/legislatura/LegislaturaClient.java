package io.github.gprindevelopment.rest.legislatura;

import io.github.gprindevelopment.core.Legislatura;
import io.github.gprindevelopment.core.common.Ordem;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.rest.common.ComponenteClient;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import io.github.gprindevelopment.rest.common.Consulta;
import io.github.gprindevelopment.rest.common.RespostaCamara;

import java.io.IOException;
import java.util.Optional;

import static io.github.gprindevelopment.core.common.ConstantesCamara.LEGISLATURA_API_URL;

public class LegislaturaClient extends ComponenteClient {

    public Legislatura consultarLegislaturaAtual() throws IOException, CamaraClientStatusException, RespostaNaoEsperadaException, RecursoNaoExisteException {
        Consulta consulta = new Consulta.Builder()
                .itens(1)
                .pagina(1)
                .ordenarPor("id", Ordem.DESC)
                .build();
        return consultar(consulta).get(0);
    }

    public Optional<Legislatura> consultarLegislaturaPorId(int id) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        RespostaCamara<Legislatura> resposta = consultarPorId(id, LEGISLATURA_API_URL, Legislatura.class);
        return Optional.ofNullable(resposta.getDados());
    }

    public Pagina<Legislatura> consultar(Consulta consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        return consultarComPaginacao(consulta, LEGISLATURA_API_URL, Legislatura.class);
    }
}
