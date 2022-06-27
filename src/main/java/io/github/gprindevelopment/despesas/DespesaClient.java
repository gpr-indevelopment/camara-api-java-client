package io.github.gprindevelopment.despesas;

import io.github.gprindevelopment.http.Client;
import io.github.gprindevelopment.dominio.Despesa;
import io.github.gprindevelopment.http.ConstantesApiCamara;
import io.github.gprindevelopment.http.Pagina;
import io.github.gprindevelopment.exception.CamaraClientStatusException;
import io.github.gprindevelopment.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.exception.RespostaNaoEsperadaException;

import java.io.IOException;


public class DespesaClient extends Client {

    public Pagina<Despesa> consultar(ConsultaDespesa consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        String[] segmentosPath = new String[]{String.valueOf(consulta.getIdDeputado()), "despesas"};
        return consultarComPaginacao(consulta, ConstantesApiCamara.DEPUTADO_API_URL, Despesa.class, segmentosPath);
    }
}
