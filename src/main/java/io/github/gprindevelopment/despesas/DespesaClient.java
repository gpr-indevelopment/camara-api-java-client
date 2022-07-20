package io.github.gprindevelopment.despesas;

import io.github.gprindevelopment.dominio.Despesa;
import io.github.gprindevelopment.http.CamaraClient;
import io.github.gprindevelopment.http.ConstantesApiCamara;
import io.github.gprindevelopment.http.Pagina;

import java.io.IOException;


public class DespesaClient extends CamaraClient {

    public Pagina<Despesa> consultar(ConsultaDespesa consulta) throws IOException, InterruptedException {
        String[] segmentosPath = new String[]{String.valueOf(consulta.getIdDeputado()), "despesas"};
        return consultarComPaginacao(consulta, ConstantesApiCamara.DEPUTADO_API_URL, Despesa.class, segmentosPath);
    }
}
