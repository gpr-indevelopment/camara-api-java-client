package io.github.gprindevelopment.core;

import java.net.URI;
import java.time.LocalDate;
import java.util.Objects;

public class Legislatura {

    private final int id;

    private final URI uri;

    private final LocalDate dataInicio;

    private final LocalDate dataFim;

    public Legislatura(int id, URI uri, LocalDate dataInicio, LocalDate dataFim) {
        this.id = id;
        this.uri = uri;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public int getId() {
        return id;
    }

    public URI getUri() {
        return uri;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Legislatura that = (Legislatura) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
