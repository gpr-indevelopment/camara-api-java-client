package io.github.gprindevelopment.rest.proposicoes;

import io.github.gprindevelopment.rest.common.ConsultaBuilder;
import io.github.gprindevelopment.rest.common.ConsultaPaginada;

import java.util.Map;

public class ConsultaProposicao extends ConsultaPaginada {

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
