package io.github.gprindevelopment.rest.despesa;

import io.github.gprindevelopment.rest.common.ConsultaBuilder;
import io.github.gprindevelopment.rest.common.Consulta;

import java.util.Map;

public class ConsultaDespesa extends Consulta {

    private final int idDeputado;

    public ConsultaDespesa(Map<String, String> parametros, int idDeputado) {
        super(parametros);
        this.idDeputado = idDeputado;
    }

    public int getIdDeputado() {
        return idDeputado;
    }

    public static class Builder extends ConsultaBuilder<Builder> {

        private final int idDeputado;


        public Builder(int idDeputado) {
            this.idDeputado = idDeputado;
        }

        public Builder anos(int... anos) {
            adicionarParamMultiValores("ano", anos);
            return this;
        }

        public Builder meses(int... meses) {
            adicionarParamMultiValores("mes", meses);
            return this;
        }

        public Builder legislaturas(int... legislaturas) {
            adicionarParamMultiValores("idLegislatura", legislaturas);
            return this;
        }

        @Override
        public ConsultaDespesa build() {
            return new ConsultaDespesa(parametros, idDeputado);
        }

        @Override
        protected Builder getThis() {
            return this;
        }
    }
}
