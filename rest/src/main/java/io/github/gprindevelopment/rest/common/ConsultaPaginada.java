package io.github.gprindevelopment.rest.common;

import io.github.gprindevelopment.core.common.Ordem;

import java.util.HashMap;
import java.util.Map;

public class ConsultaPaginada {

    private Map<String, String> parametros;

    protected ConsultaPaginada(Map<String, String> parametros) {
        this.parametros = parametros;
    }

    public Map<String, String> getParametros() {
        return parametros;
    }

    public static class Builder extends ConsultaBuilder<ConsultaPaginada> {

        @Override
        public ConsultaPaginada build() {
            return new ConsultaPaginada(parametros);
        }
    }
}
