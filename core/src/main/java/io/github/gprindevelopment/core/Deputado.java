package io.github.gprindevelopment.core;

import io.github.gprindevelopment.core.common.Estado;

import java.net.URI;
import java.net.URL;

public class Deputado {

    private final long id;

    private final URL uri;

    private final String siglaPartido;

    private final URI urlFoto;

    private final String email;

    private final String nome;

    private final int idLegislatura;

    private final Estado siglaUf;

    public Deputado(long id, URL uri, String siglaPartido, URI urlFoto, String email, String nome, int idLegislatura, Estado siglaUf) {
        this.id = id;
        this.uri = uri;
        this.siglaPartido = siglaPartido;
        this.urlFoto = urlFoto;
        this.email = email;
        this.nome = nome;
        this.idLegislatura = idLegislatura;
        this.siglaUf = siglaUf;
    }

    public int getIdLegislatura() {
        return idLegislatura;
    }

    public String getNome() {
        return nome;
    }

    public Estado getSiglaUf() {
        return siglaUf;
    }

    public long getId() {
        return id;
    }

    public URL getUri() {
        return uri;
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public URI getUrlFoto() {
        return urlFoto;
    }

    public String getEmail() {
        return email;
    }

    public String getNomeFormatado() {
        String[] split = nome.split(" ");
        StringBuilder nomeFormatado = new StringBuilder();
        for (String nome : split) {
            nome = nome.toLowerCase();
            nomeFormatado
                    .append(nome.substring(0, 1).toUpperCase())
                    .append(nome.substring(1))
                    .append(" ");
        }
        return nomeFormatado.substring(0, nomeFormatado.length()-1);
    }
}
