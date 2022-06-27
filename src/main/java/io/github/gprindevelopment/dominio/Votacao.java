package io.github.gprindevelopment.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Votacao {

    private final String id;

    private LocalDate data;

    private LocalDateTime dataHoraRegistro;

    private String siglaOrgao;

    private String descricao;

    private int aprovacao;

    public Votacao(String id) {
        id = Objects.requireNonNull(id);
        if (id.isBlank()) {
            throw new IllegalArgumentException("Um ID de votação não pode estar em branco.");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDateTime getDataHoraRegistro() {
        return dataHoraRegistro;
    }

    public void setDataHoraRegistro(LocalDateTime dataHoraRegistro) {
        this.dataHoraRegistro = dataHoraRegistro;
    }

    public String getSiglaOrgao() {
        return siglaOrgao;
    }

    public void setSiglaOrgao(String siglaOrgao) {
        this.siglaOrgao = siglaOrgao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getAprovacao() {
        return aprovacao;
    }

    public void setAprovacao(int aprovacao) {
        this.aprovacao = aprovacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Votacao votacao = (Votacao) o;
        return id.equals(votacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
