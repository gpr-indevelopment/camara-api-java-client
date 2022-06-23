package io.github.gprindevelopment.core;

public class Deputado {

    private final String nome;

    public Deputado(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
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
