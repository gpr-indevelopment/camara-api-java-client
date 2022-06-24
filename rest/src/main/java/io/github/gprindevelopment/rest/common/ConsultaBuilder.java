package io.github.gprindevelopment.rest.common;

import io.github.gprindevelopment.core.common.Ordem;

import java.util.HashMap;
import java.util.Map;

public abstract class ConsultaBuilder<T extends ConsultaPaginada> {

    protected Map<String, String> parametros = new HashMap<>();

    protected void adicionarParamMultiValores(String param, int... valores) {
        StringBuilder sb = new StringBuilder();
        for (int valor : valores) {
            sb.append(valor).append(",");
        }
        parametros.put(param, sb.substring(0, sb.length()-1));
    }

    public ConsultaBuilder<T> itens(Integer itens) {
        parametros.put("itens", itens.toString());
        return this;
    }

    public ConsultaBuilder<T> pagina(Integer pagina) {
        parametros.put("pagina", pagina.toString());
        return this;
    }

    public ConsultaBuilder<T> ordenarPor(String campo, Ordem ordem) {
        parametros.put("ordenarPor", campo);
        parametros.put("ordem", ordem.name());
        return this;
    }

    public abstract T build();
}
