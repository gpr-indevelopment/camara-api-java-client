package io.github.gprindevelopment.rest.deputado;

import io.github.gprindevelopment.rest.common.ConsultaBuilder;
import io.github.gprindevelopment.rest.common.ConsultaPaginada;

import java.util.Map;

public class ConsultaDeputado extends ConsultaPaginada {

    protected ConsultaDeputado(Map<String, String> parametros) {
        super(parametros);
    }

    public static class Builder extends ConsultaBuilder<ConsultaDeputado> {

        public Builder nome(String nome) {
            parametros.put("nome", nome);
            return this;
        }

        public Builder legislaturas(int... legislaturas) {
            StringBuilder sb = new StringBuilder();
            for (int legislatura : legislaturas) {
                sb.append(legislatura).append(",");
            }
            parametros.put("idLegislatura", sb.substring(0, sb.length()-1));
            return this;
        }

        @Override
        public ConsultaDeputado build() {
            return new ConsultaDeputado(parametros);
        }
    }
}
