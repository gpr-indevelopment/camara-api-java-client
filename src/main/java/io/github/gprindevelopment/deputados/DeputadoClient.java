package io.github.gprindevelopment.deputados;

import io.github.gprindevelopment.http.Client;
import io.github.gprindevelopment.http.RespostaCamara;
import io.github.gprindevelopment.dominio.Deputado;
import io.github.gprindevelopment.http.ConstantesApiCamara;
import io.github.gprindevelopment.http.Pagina;
import io.github.gprindevelopment.exception.CamaraClientStatusException;
import io.github.gprindevelopment.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.exception.RespostaNaoEsperadaException;

import java.io.IOException;
import java.util.Optional;

public class DeputadoClient extends Client {

    public Optional<Deputado> consultarDeputadoPorId(int id) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        RespostaCamara<Deputado> resposta = consultarPorId(String.valueOf(id), ConstantesApiCamara.DEPUTADO_API_URL, Deputado.class);
        return Optional.ofNullable(resposta.getDados());
    }

    public Pagina<Deputado> consultar(ConsultaDeputado consulta) throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        return consultarComPaginacao(consulta, ConstantesApiCamara.DEPUTADO_API_URL, Deputado.class);
    }
}
