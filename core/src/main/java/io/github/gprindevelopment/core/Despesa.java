package io.github.gprindevelopment.core;

import java.time.LocalDate;

public class Despesa {

    private final long codDocumento;

    private final String numDocumento;

    private final LocalDate dataDocumento;

    public Despesa(long codDocumento, String numDocumento, LocalDate dataDocumento) {
        this.codDocumento = codDocumento;
        this.numDocumento = numDocumento;
        this.dataDocumento = dataDocumento;
    }

    public LocalDate getDataDocumento() {
        return dataDocumento;
    }

    public long getCodDocumento() {
        return codDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }
}
