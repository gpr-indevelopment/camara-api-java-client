package io.github.gprindevelopment.rest.deputado;

import io.github.gprindevelopment.core.Deputado;
import io.github.gprindevelopment.core.common.Estado;
import io.github.gprindevelopment.core.common.Genero;
import io.github.gprindevelopment.core.common.Ordem;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DeputadoClientTest {

    private final DeputadoClient client = new DeputadoClient();

    @Test
    public void consulta_deputado_por_id_retorna_deputado() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        int deputado = 76874;
        Optional<Deputado> deputadoOpt = client.consultarDeputadoPorId(deputado);
        assertTrue(deputadoOpt.isPresent());
        assertEquals("Marcelo Freixo", deputadoOpt.get().getNomeFormatado());
        assertEquals("Marcelo Freixo", deputadoOpt.get().getNome());
    }

    @Test
    public void consulta_por_deputado_que_nao_existe_retorna_vazio() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        int deputado = 99999999;
        Optional<Deputado> deputadoOpt = client.consultarDeputadoPorId(deputado);
        assertTrue(deputadoOpt.isEmpty());
    }

    @Test
    public void consulta_paginada_de_deputados_retorna_uma_pagina_de_deputados() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        int itens = 10;
        int paginaAtual = 1;
        ConsultaDeputado consulta = new ConsultaDeputado.Builder()
                .ordenarPor("id", Ordem.DESC)
                .itens(itens)
                .pagina(paginaAtual)
                .build();
        Pagina<Deputado> pagina = client.consultar(consulta);
        assertEquals(itens, pagina.size());
        assertEquals(paginaAtual, pagina.getPaginaAtual());
        assertTrue(pagina.temProxima());
        pagina.forEach(deputado -> assertNotNull(deputado.getNome()));
    }

    @Test
    public void consulta_paginada_sem_parametros_usa_default_da_camara() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        Pagina<Deputado> pagina = client.consultar(new ConsultaDeputado.Builder().build());
        assertFalse(pagina.isEmpty());
        assertFalse(pagina.temProxima());
        assertEquals(1, pagina.getPaginaAtual());
    }

    @Test
    public void consulta_paginada_por_nome_retorna_deputados_corretos() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        String nome = "Freixo";
        ConsultaDeputado consulta = new ConsultaDeputado.Builder()
                .nome(nome)
                .ordenarPor("id", Ordem.DESC)
                .pagina(1)
                .itens(10)
                .build();
        Pagina<Deputado> pagina = client.consultar(consulta);
        assertEquals(1, pagina.size());
        assertTrue(pagina.get(0).getNomeFormatado().contains(nome));
    }

    @Test
    public void consulta_paginada_por_id_legislatura_retorna_deputados_corretos() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        int itens = 20;
        int paginaAtual = 1;
        int[] legislaturasEsperadas = new int[]{56,55};
        ConsultaDeputado consulta = new ConsultaDeputado.Builder()
                .legislaturas(legislaturasEsperadas)
                .ordenarPor("nome", Ordem.DESC)
                .pagina(paginaAtual)
                .itens(itens)
                .build();
        Pagina<Deputado> pagina = client.consultar(consulta);
        assertEquals(itens, pagina.size());
        assertEquals(paginaAtual, pagina.getPaginaAtual());
        pagina.forEach(deputado -> {
            int legislaturaDeputado = deputado.getIdLegislatura();
            IntStream stream = Arrays.stream(legislaturasEsperadas);
            assertTrue(stream.anyMatch(a -> a == legislaturaDeputado));
        });
    }

    @Test
    public void consulta_paginada_por_uf_retorna_deputados_do_estado() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        int itens = 20;
        int paginaAtual = 1;
        Estado estadoConsulta = Estado.RJ;
        ConsultaDeputado consulta = new ConsultaDeputado.Builder()
                .estados(Estado.RJ)
                .ordenarPor("nome", Ordem.DESC)
                .pagina(paginaAtual)
                .itens(itens)
                .build();
        Pagina<Deputado> pagina = client.consultar(consulta);
        assertEquals(itens, pagina.size());
        assertEquals(paginaAtual, pagina.getPaginaAtual());
        pagina.forEach(deputado -> assertEquals(estadoConsulta, deputado.getSiglaUf()));
    }

    @Test
    public void consulta_paginada_por_multiplos_ufs_retorna_deputados_dos_estados() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        int itens = 20;
        int paginaAtual = 1;
        Estado[] estadosEsperados = new Estado[]{Estado.RJ, Estado.SP};
        ConsultaDeputado consulta = new ConsultaDeputado.Builder()
                .estados(estadosEsperados)
                .ordenarPor("nome", Ordem.DESC)
                .pagina(paginaAtual)
                .itens(itens)
                .build();
        Pagina<Deputado> pagina = client.consultar(consulta);
        assertEquals(itens, pagina.size());
        assertEquals(paginaAtual, pagina.getPaginaAtual());
        pagina.forEach(deputado -> {
            Estado estadoDeputado = deputado.getSiglaUf();
            Stream<Estado> stream = Arrays.stream(estadosEsperados);
            assertTrue(stream.anyMatch(a -> a.equals(estadoDeputado)));
        });
    }

    @Test
    public void consulta_paginada_por_genero_retorna_deputados_do_genero() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        int itens = 20;
        int paginaAtual = 1;
        Genero generoEsperado = Genero.FEM;
        ConsultaDeputado consulta = new ConsultaDeputado.Builder()
                .legislaturas(56)
                .genero(generoEsperado)
                .ordenarPor("nome", Ordem.DESC)
                .pagina(paginaAtual)
                .itens(itens)
                .build();
        Pagina<Deputado> pagina = client.consultar(consulta);
        assertEquals(itens, pagina.size());
        assertEquals(paginaAtual, pagina.getPaginaAtual());
        assertTrue(pagina.stream().anyMatch(deputado -> deputado.getNome().equals("Vivi Reis")));
        assertTrue(pagina.stream().anyMatch(deputado -> deputado.getNome().equals("Tia Eron")));
        assertTrue(pagina.stream().anyMatch(deputado -> deputado.getNome().equals("Tereza Nelma")));
    }
}
