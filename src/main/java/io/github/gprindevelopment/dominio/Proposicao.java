package io.github.gprindevelopment.dominio;

import java.net.URI;
import java.util.Objects;

public class Proposicao implements Comparable<Proposicao> {

    private final long id;

    private URI uri;

    private String siglaTipo;

    private int codTipo;

    private int numero;

    private int ano;

    private String ementa;

    public Proposicao(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Proposições devem ter ID maior que 0");
        }
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public URI getUri() {
        return uri;
    }

    public String getSiglaTipo() {
        return siglaTipo;
    }

    public int getCodTipo() {
        return codTipo;
    }

    public int getNumero() {
        return numero;
    }

    public int getAno() {
        return ano;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public void setSiglaTipo(String siglaTipo) {
        this.siglaTipo = siglaTipo;
    }

    public void setCodTipo(int codTipo) {
        this.codTipo = codTipo;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposicao that = (Proposicao) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Proposicao o) {
        return Long.compare(this.id, o.id);
    }
}
