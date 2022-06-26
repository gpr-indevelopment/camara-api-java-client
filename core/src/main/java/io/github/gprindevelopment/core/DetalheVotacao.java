package io.github.gprindevelopment.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class DetalheVotacao {

    private final String id;

    private LocalDate data;

    private LocalDateTime dataHoraRegistro;

    private String siglaOrgao;

    private int idOrgao;

    private int idEvento;

    private String descricao;

    private String descUltimaAberturaVotacao;

    private int aprovacao;

    public DetalheVotacao(String id) {
        id = Objects.requireNonNull(id);
        if (id.isBlank()) {
            throw new IllegalArgumentException("Um ID de detalhe de votação não pode estar vazio.");
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

    public int getIdOrgao() {
        return idOrgao;
    }

    public void setIdOrgao(int idOrgao) {
        this.idOrgao = idOrgao;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescUltimaAberturaVotacao() {
        return descUltimaAberturaVotacao;
    }

    public void setDescUltimaAberturaVotacao(String descUltimaAberturaVotacao) {
        this.descUltimaAberturaVotacao = descUltimaAberturaVotacao;
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
        DetalheVotacao that = (DetalheVotacao) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
