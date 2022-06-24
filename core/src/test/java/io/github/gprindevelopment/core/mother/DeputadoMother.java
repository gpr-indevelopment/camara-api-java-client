package io.github.gprindevelopment.core.mother;

import io.github.gprindevelopment.core.Deputado;
import io.github.gprindevelopment.core.common.Estado;

import java.net.MalformedURLException;
import java.net.URL;

public class DeputadoMother {

    public static Deputado gerarDeputadoSimples(int id, String nome) throws MalformedURLException {
        URL uri = new URL("https://dadosabertos.camara.leg.br/api/v2/deputados/76874");
        URL urlFoto = new URL("https://www.camara.leg.br/internet/deputado/bandep/76874.jpg");
        return new Deputado(id, uri, "PT", urlFoto, "algum@email.com", nome, 56, Estado.RJ);
    }
}
