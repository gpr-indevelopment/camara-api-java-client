package io.github.gprindevelopment.rest.despesa;

import io.github.gprindevelopment.core.Despesa;
import io.github.gprindevelopment.core.common.ConstantesCamara;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import io.github.gprindevelopment.rest.common.ComponenteClient;

import java.io.IOException;


public class DespesaClient extends ComponenteClient {

    public Pagina<Despesa> consultar(ConsultaDespesa consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        String[] segmentosPath = new String[]{String.valueOf(consulta.getIdDeputado()), "despesas"};
        return consultarComPaginacao(consulta, ConstantesCamara.DEPUTADO_API_URL, Despesa.class, segmentosPath);
    }
}
