package io.github.gprindevelopment.http;

import okhttp3.Headers;

public class RespostaCamara<T> {

    private Headers cabecalhos;
    private final T dados;

    private int statusCode;

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

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
