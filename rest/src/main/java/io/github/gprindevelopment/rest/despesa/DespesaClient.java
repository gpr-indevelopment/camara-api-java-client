package io.github.gprindevelopment.rest.despesa;

import io.github.gprindevelopment.core.Despesa;
import io.github.gprindevelopment.core.common.ConstantesCamara;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import io.github.gprindevelopment.rest.common.ComponenteClient;

import java.io.IOException;


public class DespesaClient extends ComponenteClient {

    public Pagina<Despesa> consultar(ConsultaDespesa consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        String url = String.format(ConstantesCamara.DESPESAS_API_FORMATO_URL, consulta.getIdDeputado());
        return consultarComPaginacao(consulta, url, Despesa.class);
    }
}
