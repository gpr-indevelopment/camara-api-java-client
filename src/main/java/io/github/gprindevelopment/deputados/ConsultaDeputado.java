package io.github.gprindevelopment.deputados;

import io.github.gprindevelopment.http.ConsultaBuilder;
import io.github.gprindevelopment.dominio.Estado;
import io.github.gprindevelopment.dominio.Genero;
import io.github.gprindevelopment.http.Consulta;

import java.util.Map;

public class ConsultaDeputado extends Consulta {

    protected ConsultaDeputado(Map<String, String> parametros) {
        super(parametros);
    }

    public static class Builder extends ConsultaBuilder<Builder> {

        public Builder nome(String nome) {
            parametros.put("nome", nome);
            return this;
        }

        public Builder estados(Estado... estados) {
            StringBuilder sb = new StringBuilder();
            for (Estado estado : estados) {
                sb.append(estado.name()).append(",");
            }
            parametros.put("siglaUf", sb.substring(0, sb.length()-1));
            return this;
        }

        public Builder legislaturas(int... legislaturas) {
            adicionarParamMultiValores("idLegislatura", legislaturas);
            return this;
        }

        public Builder genero(Genero genero) {
            parametros.put("siglaSexo", genero.name().toUpperCase().substring(0, 1));
            return this;
        }

        @Override
        public ConsultaDeputado build() {
            return new ConsultaDeputado(parametros);
        }

        @Override
        protected Builder getThis() {
            return this;
        }
    }
}