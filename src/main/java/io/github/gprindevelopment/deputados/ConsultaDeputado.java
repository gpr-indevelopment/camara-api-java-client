package io.github.gprindevelopment.deputados;

import io.github.gprindevelopment.dominio.Estado;
import io.github.gprindevelopment.dominio.Genero;
import io.github.gprindevelopment.http.Consulta;
import io.github.gprindevelopment.http.ConsultaBuilder;
import io.github.gprindevelopment.http.ModoValidacao;

import java.util.Map;

public class ConsultaDeputado extends Consulta {

    protected ConsultaDeputado(Map<String, String> parametros) {
        super(parametros);
    }

    public static class Builder extends ConsultaBuilder<Builder> {

        public Builder() {
            super(ModoValidacao.PERMISSIVO);
        }

        public Builder(ModoValidacao modoValidacao) {
            super(modoValidacao);
        }

        public Builder nome(String nome) {
            adicionarParam("nome", nome);
            return this;
        }

        public Builder estados(Estado... estados) {
            StringBuilder sb = new StringBuilder();
            for (Estado estado : estados) {
                sb.append(estado.name()).append(",");
            }
            adicionarParam("siglaUf", sb.substring(0, sb.length()-1));
            return this;
        }

        public Builder legislaturas(int... legislaturas) {
            adicionarParamMultiValores("idLegislatura", legislaturas);
            return this;
        }

        public Builder genero(Genero genero) {
            adicionarParam("siglaSexo", genero.name().toUpperCase().substring(0, 1));
            return this;
        }

        @Override
        public ConsultaDeputado build() {
            return new ConsultaDeputado(getParametros());
        }

        @Override
        protected Builder getThis() {
            return this;
        }
    }
}
