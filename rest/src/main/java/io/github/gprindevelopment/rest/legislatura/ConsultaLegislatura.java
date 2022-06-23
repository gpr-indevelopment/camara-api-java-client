package io.github.gprindevelopment.rest.legislatura;

import io.github.gprindevelopment.core.common.Ordem;

public class ConsultaLegislatura {

    private final Integer itens;

    private final Integer pagina;

    private final String ordenarPor;

    private final Ordem ordem;

    private ConsultaLegislatura(Integer itens, Integer pagina, String ordenarPor, Ordem ordem) {
        this.itens = itens;
        this.pagina = pagina;
        this.ordenarPor = ordenarPor;
        this.ordem = ordem;
    }

    public Integer getItens() {
        return itens;
    }

    public Integer getPagina() {
        return pagina;
    }

    public String getOrdenarPor() {
        return ordenarPor;
    }

    public Ordem getOrdem() {
        return ordem;
    }

    public static class Builder {

        private Integer itens;

        private Integer pagina;

        private String ordenarPor;

        private Ordem ordem;

        public Builder itens(Integer itens) {
            this.itens = itens;
            return this;
        }

        public Builder pagina(Integer pagina) {
            this.pagina = pagina;
            return this;
        }

        public Builder ordenarPor(String campo, Ordem ordem) {
            this.ordenarPor = campo;
            this.ordem = ordem;
            return this;
        }

        public ConsultaLegislatura build() {
            return new ConsultaLegislatura(itens, pagina, ordenarPor, ordem);
        }
    }
}
