package io.github.gprindevelopment.rest.common;

import okhttp3.Headers;

public class RespostaCamara<T> {

    private Headers cabecalhos;
    private final T dados;

    public RespostaCamara(T dados) {
        this.dados = dados;
    }

    public T getDados() {
        return dados;
    }

    public void setCabecalhos(Headers cabecalhos) {
        this.cabecalhos = cabecalhos;
    }

    public Headers getCabecalhos() {
        return cabecalhos;
    }
}
