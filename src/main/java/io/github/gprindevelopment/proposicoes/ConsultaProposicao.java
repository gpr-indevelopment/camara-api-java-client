package io.github.gprindevelopment.proposicoes;

import io.github.gprindevelopment.http.ConsultaBuilder;
import io.github.gprindevelopment.http.Consulta;

import java.util.Map;

public class ConsultaProposicao extends Consulta {

    public ConsultaProposicao(Map<String, String> parametros) {
        super(parametros);
    }

    public static class Builder extends ConsultaBuilder<Builder> {

        public Builder idAutores(int... autores) {
            adicionarParamMultiValores("idDeputadoAutor", autores);
            return this;
        }

        @Override
        public ConsultaProposicao build() {
            return new ConsultaProposicao(parametros);
        }

        @Override
        protected Builder getThis() {
            return this;
        }
    }
}
