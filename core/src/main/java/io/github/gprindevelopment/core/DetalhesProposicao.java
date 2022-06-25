package io.github.gprindevelopment.core;

import java.net.URL;
import java.time.LocalDateTime;

public class DetalhesProposicao {

    private final long id;

    private String siglaTipo;

    private int codTipo;

    private int numero;

    private int ano;

    private String ementa;

    private LocalDateTime dataApresentacao;

    private StatusProposicao statusProposicao;

    private String descricaoTipo;

    private String keywords;

    private URL urlInteiroTeor;

    public DetalhesProposicao(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    public LocalDateTime getDataApresentacao() {
        return dataApresentacao;
    }

    public StatusProposicao getStatusProposicao() {
        return statusProposicao;
    }

    public String getDescricaoTipo() {
        return descricaoTipo;
    }

    public String getKeywords() {
        return keywords;
    }

    public URL getUrlInteiroTeor() {
        return urlInteiroTeor;
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

    public void setDataApresentacao(LocalDateTime dataApresentacao) {
        this.dataApresentacao = dataApresentacao;
    }

    public void setStatusProposicao(StatusProposicao statusProposicao) {
        this.statusProposicao = statusProposicao;
    }

    public void setDescricaoTipo(String descricaoTipo) {
        this.descricaoTipo = descricaoTipo;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setUrlInteiroTeor(URL urlInteiroTeor) {
        this.urlInteiroTeor = urlInteiroTeor;
    }
}
