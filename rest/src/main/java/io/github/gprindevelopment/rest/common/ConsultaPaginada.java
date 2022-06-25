package io.github.gprindevelopment.rest.common;

import java.util.Map;

public class ConsultaPaginada {

    private final Map<String, String> parametros;

    protected ConsultaPaginada(Map<String, String> parametros) {
        this.parametros = parametros;
    }

    public Map<String, String> getParametros() {
        return parametros;
    }

    public static class Builder extends ConsultaBuilder<Builder> {

        @Override
        public ConsultaPaginada build() {
            return new ConsultaPaginada(parametros);
        }

        @Override
        protected Builder getThis() {
            return this;
        }
    }
}
