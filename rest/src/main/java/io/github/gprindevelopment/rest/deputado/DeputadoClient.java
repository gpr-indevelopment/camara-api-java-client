package io.github.gprindevelopment.rest.deputado;

import io.github.gprindevelopment.core.Deputado;
import io.github.gprindevelopment.core.common.ConstantesCamara;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import io.github.gprindevelopment.rest.common.ComponenteClient;
import io.github.gprindevelopment.rest.common.ConsultaPaginada;
import io.github.gprindevelopment.rest.common.RespostaCamara;

import java.io.IOException;
import java.util.Optional;

public class DeputadoClient extends ComponenteClient {

    public Optional<Deputado> consultarDeputadoPorId(int id) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        RespostaCamara<Deputado> resposta = consultarPorId(id, ConstantesCamara.DEPUTADO_API_URL, Deputado.class);
        return Optional.ofNullable(resposta.getDados());
    }

    public Pagina<Deputado> consultar(ConsultaPaginada consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        return consultarComPaginacao(consulta, ConstantesCamara.DEPUTADO_API_URL, Deputado.class);
    }
}
