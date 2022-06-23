package io.github.gprindevelopment.rest.legislatura;

import io.github.gprindevelopment.core.Legislatura;
import io.github.gprindevelopment.core.common.Ordem;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LegislaturaClientTest {

    private final LegislaturaClient client = new LegislaturaClient();

    @Test
    public void consultar_legislatura_corrente_retorna_56() throws IOException, CamaraClientStatusException, RespostaNaoEsperadaException, URISyntaxException {
        Legislatura legislatura = client.consultarLegislaturaAtual();
        assertEquals(legislatura, construirLegislaturaAtual());
    }

    @Test
    public void consulta_paginada_e_ordenada_de_legislaturas() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, URISyntaxException {
        int itens = 10;
        int total = 56;
        int paginaAtual = 1;
        ConsultaLegislatura consulta = new ConsultaLegislatura.Builder()
                .ordenarPor("id", Ordem.DESC)
                .itens(itens)
                .pagina(paginaAtual)
                .build();
        Pagina<Legislatura> pagina = client.consultar(consulta);
        assertEquals(itens, pagina.size());
        assertEquals(total, pagina.getTotal());
        assertEquals(paginaAtual, pagina.getPaginaAtual());
        assertTrue(pagina.temProxima());
        assertTrue(pagina.contains(construirLegislaturaAtual()));
    }

    @Test
    public void objeto_de_consulta_nao_pode_ser_null() {
        NullPointerException e = assertThrows(NullPointerException.class, () -> client.consultar(null));
        assertNotNull(e.getMessage());
    }

    @Test
    public void consulta_sem_parametros_assume_default_da_camara() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, URISyntaxException {
        ConsultaLegislatura consulta = new ConsultaLegislatura.Builder().build();
        Pagina<Legislatura> pagina = client.consultar(consulta);
        assertEquals(15, pagina.size());
        assertEquals(56, pagina.getTotal());
        assertEquals(1, pagina.getPaginaAtual());
        assertTrue(pagina.temProxima());
        assertTrue(pagina.contains(construirLegislaturaAtual()));
    }

    @Test
    public void consulta_com_pagina_fora_do_limite_retorna_vazia() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        int paginaAtual = 150;
        ConsultaLegislatura consulta = new ConsultaLegislatura.Builder()
                .ordenarPor("id", Ordem.DESC)
                .itens(10)
                .pagina(paginaAtual)
                .build();
        Pagina<Legislatura> pagina = client.consultar(consulta);
        assertTrue(pagina.isEmpty());
        assertEquals(56, pagina.getTotal());
        assertEquals(paginaAtual, pagina.getPaginaAtual());
        assertFalse(pagina.temProxima());
    }

    private Legislatura construirLegislaturaAtual() throws URISyntaxException {
        return new Legislatura(56,
                new URI("https://dadosabertos.camara.leg.br/api/v2/legislaturas/56"),
                LocalDate.of(2019, 2, 1),
                LocalDate.of(2023, 1, 31));
    }
}
