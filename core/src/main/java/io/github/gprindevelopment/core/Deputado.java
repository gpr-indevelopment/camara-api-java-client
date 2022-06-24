package io.github.gprindevelopment.core;

import io.github.gprindevelopment.core.common.Estado;

import java.net.URL;
import java.util.Objects;

public class Deputado implements Comparable<Deputado> {

    private final int id;

    private final URL uri;

    private final String siglaPartido;

    private final URL urlFoto;

    private final String email;

    private final String nome;

    private final int idLegislatura;

    private final Estado siglaUf;

    public Deputado(int id, URL uri, String siglaPartido, URL urlFoto, String email, String nome, int idLegislatura, Estado siglaUf) {
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

    public int getId() {
        return id;
    }

    public URL getUri() {
        return uri;
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public URL getUrlFoto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deputado deputado = (Deputado) o;
        return id == deputado.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Deputado o) {
        return Integer.compare(this.id, o.id);
    }
}
