package io.github.gprindevelopment.deputados;

import io.github.gprindevelopment.dominio.Deputado;
import io.github.gprindevelopment.http.CamaraClient;
import io.github.gprindevelopment.http.ConstantesApiCamara;
import io.github.gprindevelopment.http.Pagina;
import io.github.gprindevelopment.http.RespostaCamara;

import java.io.IOException;
import java.util.Optional;

public class DeputadoClient extends CamaraClient {

    public Optional<Deputado> consultarDeputadoPorId(int id) throws IOException, InterruptedException {
        RespostaCamara<Deputado> resposta = consultarPorId(String.valueOf(id), ConstantesApiCamara.DEPUTADO_API_URL, Deputado.class);
        return Optional.ofNullable(resposta.getDados());
    }

    public Pagina<Deputado> consultar(ConsultaDeputado consulta) throws IOException, InterruptedException {
        return consultarComPaginacao(consulta, ConstantesApiCamara.DEPUTADO_API_URL, Deputado.class);
    }
}
