package io.github.gprindevelopment.dominio;

import java.net.URI;

public class OrientacaoVoto {

    private String orientacaoVoto;

    private String codTipoLideranca;

    private String siglaPartidoBloco;

    private int codPartidoBloco;

    private URI uriPartidoBloco;

    public String getOrientacaoVoto() {
        return orientacaoVoto;
    }

    public void setOrientacaoVoto(String orientacaoVoto) {
        this.orientacaoVoto = orientacaoVoto;
    }

    public String getCodTipoLideranca() {
        return codTipoLideranca;
    }

    public void setCodTipoLideranca(String codTipoLideranca) {
        this.codTipoLideranca = codTipoLideranca;
    }

    public String getSiglaPartidoBloco() {
        return siglaPartidoBloco;
    }

    public void setSiglaPartidoBloco(String siglaPartidoBloco) {
        this.siglaPartidoBloco = siglaPartidoBloco;
    }

    public int getCodPartidoBloco() {
        return codPartidoBloco;
    }

    public void setCodPartidoBloco(int codPartidoBloco) {
        this.codPartidoBloco = codPartidoBloco;
    }

    public URI getUriPartidoBloco() {
        return uriPartidoBloco;
    }

    public void setUriPartidoBloco(URI uriPartidoBloco) {
        this.uriPartidoBloco = uriPartidoBloco;
    }
}
