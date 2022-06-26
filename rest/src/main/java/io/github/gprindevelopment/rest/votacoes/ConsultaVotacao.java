package io.github.gprindevelopment.rest.votacoes;

import io.github.gprindevelopment.rest.common.Consulta;
import io.github.gprindevelopment.rest.common.ConsultaBuilder;

import java.util.Map;

/**
 * As consultas de votação baseadas em intervalos de tempo, com os campos 'dataInicio' e 'dataFim'
 * foram removidos dessa versão da lib. A API de votações retorna status 504 constantemente
 * quando usamos esses filtros, por mais simples que sejam.
 *
 * Ainda, o time de dados abertos reportou que uma limitação da API obriga que
 * essas datas estejam sempre no mesmo ano.
 *
 * Isso torna dificulta o uso e teste do endpoint de consulta de votações com esses filtros.
 */
public class ConsultaVotacao extends Consulta {

    protected ConsultaVotacao(Map<String, String> parametros) {
        super(parametros);
    }

    public static class Builder extends ConsultaBuilder<ConsultaVotacao.Builder> {

        public Builder proposicoes(long... idProposicoes) {
            adicionarParamMultiValores("idProposicao", idProposicoes);
            return this;
        }

        @Override
        public ConsultaVotacao build() {
            return new ConsultaVotacao(parametros);
        }

        @Override
        protected Builder getThis() {
            return this;
        }
    }
}
