package io.github.gprindevelopment.dominio;

import java.time.LocalDateTime;

public class Voto {

    private String tipoVoto;

    private LocalDateTime dataRegistroVoto;

    // Este campo estava representado com underline ao fim na base da Câmara.
    private Deputado deputado_;

    public String getTipoVoto() {
        return tipoVoto;
    }

    public void setTipoVoto(String tipoVoto) {
        this.tipoVoto = tipoVoto;
    }

    public LocalDateTime getDataRegistroVoto() {
        return dataRegistroVoto;
    }

    public void setDataRegistroVoto(LocalDateTime dataRegistroVoto) {
        this.dataRegistroVoto = dataRegistroVoto;
    }

    public Deputado getDeputado() {
        return deputado_;
    }

    public void setDeputado(Deputado deputado) {
        this.deputado_ = deputado;
    }
}
