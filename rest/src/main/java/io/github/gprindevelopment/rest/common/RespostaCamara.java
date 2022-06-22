package io.github.gprindevelopment.rest.common;

public class RespostaCamara<T> {

    private final T dados;

    public RespostaCamara(T dados) {
        this.dados = dados;
    }

    public T getDados() {
        return dados;
    }
}
