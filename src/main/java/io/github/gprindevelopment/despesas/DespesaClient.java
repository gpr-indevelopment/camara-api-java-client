package io.github.gprindevelopment.despesas;

import io.github.gprindevelopment.dominio.Despesa;
import io.github.gprindevelopment.http.Client;
import io.github.gprindevelopment.http.ConstantesApiCamara;
import io.github.gprindevelopment.http.Pagina;

import java.io.IOException;


public class DespesaClient extends Client {

    public Pagina<Despesa> consultar(ConsultaDespesa consulta) throws IOException {
        String[] segmentosPath = new String[]{String.valueOf(consulta.getIdDeputado()), "despesas"};
        return consultarComPaginacao(consulta, ConstantesApiCamara.DEPUTADO_API_URL, Despesa.class, segmentosPath);
    }
}
