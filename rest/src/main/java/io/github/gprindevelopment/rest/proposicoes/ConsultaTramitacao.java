package io.github.gprindevelopment.rest.proposicoes;

import io.github.gprindevelopment.rest.common.Consulta;
import io.github.gprindevelopment.rest.common.ConsultaBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ConsultaTramitacao extends Consulta {

    private final long idProposicao;

    public ConsultaTramitacao(Map<String, String> parametros, long idProposicao) {
        super(parametros);
        this.idProposicao = idProposicao;
    }

    public long getIdProposicao() {
        return idProposicao;
    }

    public static class Builder extends ConsultaBuilder<ConsultaTramitacao.Builder> {

        private final long idProposicao;

        private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        public Builder(long idProposicao) {
            this.idProposicao = idProposicao;
        }

        public Builder dataInicio(LocalDate dataInicio) {
            parametros.put("dataInicio", formatoData.format(dataInicio));
            return this;
        }
        public Builder dataFim(LocalDate dataFim) {
            parametros.put("dataFim", formatoData.format(dataFim));
            return this;
        }

        @Override
        public ConsultaTramitacao build() {
            return new ConsultaTramitacao(parametros, idProposicao);
        }

        @Override
        protected Builder getThis() {
            return this;
        }
    }
}
