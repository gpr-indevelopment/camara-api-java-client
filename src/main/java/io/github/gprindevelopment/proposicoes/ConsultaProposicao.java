package io.github.gprindevelopment.proposicoes;

import io.github.gprindevelopment.http.Consulta;
import io.github.gprindevelopment.http.ConsultaBuilder;
import io.github.gprindevelopment.http.ModoValidacao;

import java.util.Map;

public class ConsultaProposicao extends Consulta {

    public ConsultaProposicao(Map<String, String> parametros) {
        super(parametros);
    }

    public static class Builder extends ConsultaBuilder<Builder> {

        public Builder() {
            super(ModoValidacao.PERMISSIVO);
        }

        public Builder(ModoValidacao modoValidacao) {
            super(modoValidacao);
        }

        public Builder idAutores(int... autores) {
            adicionarParamMultiValores("idDeputadoAutor", autores);
            return this;
        }

        @Override
        public ConsultaProposicao build() {
            return new ConsultaProposicao(getParametros());
        }

        @Override
        protected Builder getThis() {
            return this;
        }
    }
}
