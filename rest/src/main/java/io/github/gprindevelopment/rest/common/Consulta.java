package io.github.gprindevelopment.rest.common;

import java.util.Map;

public class Consulta {

    private final Map<String, String> parametros;

    protected Consulta(Map<String, String> parametros) {
        this.parametros = parametros;
    }

    public Map<String, String> getParametros() {
        return parametros;
    }

    public static class Builder extends ConsultaBuilder<Consulta.Builder> {

        @Override
        public Consulta build() {
            return new Consulta(parametros);
        }

        @Override
        protected Builder getThis() {
            return this;
        }
    }
}
