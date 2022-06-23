package io.github.gprindevelopment.core;

import io.github.gprindevelopment.core.common.Estado;

public class Deputado {

    private final String nome;

    private final int idLegislatura;

    private final Estado siglaUf;

    public Deputado(String nome, int idLegislatura, Estado siglaUf) {
        this.nome = nome;
        this.idLegislatura = idLegislatura;
        this.siglaUf = siglaUf;
    }

    public int getIdLegislatura() {
        return idLegislatura;
    }

    public String getNome() {
        return nome;
    }

    public Estado getSiglaUf() {
        return siglaUf;
    }

    public String getNomeFormatado() {
        String[] split = nome.split(" ");
        StringBuilder nomeFormatado = new StringBuilder();
        for (String nome : split) {
            nome = nome.toLowerCase();
            nomeFormatado
                    .append(nome.substring(0, 1).toUpperCase())
                    .append(nome.substring(1))
                    .append(" ");
        }
        return nomeFormatado.substring(0, nomeFormatado.length()-1);
    }
}
