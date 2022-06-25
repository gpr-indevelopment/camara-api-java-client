package io.github.gprindevelopment.core;

import java.net.URI;

public class Autor {

    private final URI uri;

    private final String nome;

    private final String tipo;

    private final int ordemAssinatura;

    private final int proponente;

    public Autor(URI uri, String nome, String tipo, int ordemAssinatura, int proponente) {
        this.uri = uri;
        this.nome = nome;
        this.tipo = tipo;
        this.ordemAssinatura = ordemAssinatura;
        this.proponente = proponente;
    }

    public URI getUri() {
        return uri;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getOrdemAssinatura() {
        return ordemAssinatura;
    }

    public int getProponente() {
        return proponente;
    }
}
