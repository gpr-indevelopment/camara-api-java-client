package io.github.gprindevelopment.rest.common;

import io.github.gprindevelopment.core.common.Ordem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class ConsultaBuilder<T extends ConsultaBuilder<T>> {

    protected Map<String, String> parametros = new HashMap<>();

    protected void adicionarParamMultiValores(String param, int... valores) {
        long[] valoresLong = Arrays.stream(valores).asLongStream().toArray();
        adicionarParamMultiValores(param, valoresLong);
    }

    protected void adicionarParamMultiValores(String param, long... valores) {
        StringBuilder sb = new StringBuilder();
        for (long valor : valores) {
            sb.append(valor).append(",");
        }
        parametros.put(param, sb.substring(0, sb.length()-1));
    }

    public T itens(Integer itens) {
        parametros.put("itens", itens.toString());
        return getThis();
    }

    public T pagina(Integer pagina) {
        parametros.put("pagina", pagina.toString());
        return getThis();
    }

    public T ordenarPor(String campo, Ordem ordem) {
        parametros.put("ordenarPor", campo);
        parametros.put("ordem", ordem.name());
        return getThis();
    }

    public abstract Consulta build();

    protected abstract T getThis();
}
