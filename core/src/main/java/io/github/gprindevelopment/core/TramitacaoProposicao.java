package io.github.gprindevelopment.core;

import java.time.LocalDateTime;

public class TramitacaoProposicao implements Comparable<TramitacaoProposicao> {

    private LocalDateTime dataHora;

    private int sequencia;

    private String siglaOrgao;

    private String regime;

    private String descricaoTramitacao;

    private int codTipoTramitacao;

    private String despacho;

    private String ambito;

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public String getSiglaOrgao() {
        return siglaOrgao;
    }

    public void setSiglaOrgao(String siglaOrgao) {
        this.siglaOrgao = siglaOrgao;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getDescricaoTramitacao() {
        return descricaoTramitacao;
    }

    public void setDescricaoTramitacao(String descricaoTramitacao) {
        this.descricaoTramitacao = descricaoTramitacao;
    }

    public int getCodTipoTramitacao() {
        return codTipoTramitacao;
    }

    public void setCodTipoTramitacao(int codTipoTramitacao) {
        this.codTipoTramitacao = codTipoTramitacao;
    }

    public String getDespacho() {
        return despacho;
    }

    public void setDespacho(String despacho) {
        this.despacho = despacho;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    @Override
    public int compareTo(TramitacaoProposicao o) {
        return Integer.compare(sequencia, o.sequencia);
    }
}
