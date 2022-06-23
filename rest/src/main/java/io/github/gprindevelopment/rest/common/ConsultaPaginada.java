package io.github.gprindevelopment.rest.common;

import io.github.gprindevelopment.core.common.Ordem;

import java.util.HashMap;
import java.util.Map;

public class ConsultaPaginada {

    private Map<String, String> parametros;

    private ConsultaPaginada(Map<String, String> parametros) {
        this.parametros = parametros;
    }

    public Map<String, String> getParametros() {
        return parametros;
    }

    public static class Builder {

        private Map<String, String> parametros = new HashMap<>();

        public ConsultaPaginada.Builder itens(Integer itens) {
            parametros.put("itens", itens.toString());
            return this;
        }

        public ConsultaPaginada.Builder pagina(Integer pagina) {
            parametros.put("pagina", pagina.toString());
            return this;
        }

        public ConsultaPaginada.Builder ordenarPor(String campo, Ordem ordem) {
            parametros.put("ordenarPor", campo);
            parametros.put("ordem", ordem.name());
            return this;
        }

        public ConsultaPaginada build() {
            return new ConsultaPaginada(parametros);
        }
    }
}
