package io.github.gprindevelopment.legislaturas;

import io.github.gprindevelopment.dominio.Legislatura;
import io.github.gprindevelopment.http.Consulta;
import io.github.gprindevelopment.http.Ordem;
import io.github.gprindevelopment.http.Pagina;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LegislaturaClientTest {

    private static final int ID_LEGISLATURA_CORRENTE = 56;

    private final LegislaturaClient client = new LegislaturaClient();

    @Test
    public void consultar_legislatura_corrente_retorna_56() throws IOException, URISyntaxException, InterruptedException {
        Legislatura legislatura = client.consultarLegislaturaAtual();
        assertEquals(legislatura, construirLegislaturaAtual());
    }

    @Test
    public void consulta_paginada_e_ordenada_de_legislaturas() throws IOException, URISyntaxException, InterruptedException {
        int itens = 10;
        int paginaAtual = 1;
        Consulta consulta = new Consulta.Builder()
                .ordenarPor("id", Ordem.DESC)
                .itens(itens)
                .pagina(paginaAtual)
                .build();
        Pagina<Legislatura> pagina = client.consultar(consulta);
        assertEquals(itens, pagina.size());
        assertEquals(ID_LEGISLATURA_CORRENTE, pagina.getTotal());
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
    public void consulta_sem_parametros_assume_default_da_camara() throws IOException, URISyntaxException, InterruptedException {
        Consulta consulta = new Consulta.Builder().build();
        Pagina<Legislatura> pagina = client.consultar(consulta);
        assertEquals(15, pagina.size());
        assertEquals(ID_LEGISLATURA_CORRENTE, pagina.getTotal());
        assertEquals(1, pagina.getPaginaAtual());
        assertTrue(pagina.temProxima());
        assertTrue(pagina.contains(construirLegislaturaAtual()));
    }

    @Test
    public void consulta_com_pagina_fora_do_limite_retorna_vazia() throws IOException, InterruptedException {
        int paginaAtual = 150;
        Consulta consulta = new Consulta.Builder()
                .ordenarPor("id", Ordem.DESC)
                .itens(10)
                .pagina(paginaAtual)
                .build();
        Pagina<Legislatura> pagina = client.consultar(consulta);
        assertTrue(pagina.isEmpty());
        assertEquals(ID_LEGISLATURA_CORRENTE, pagina.getTotal());
        assertEquals(paginaAtual, pagina.getPaginaAtual());
        assertFalse(pagina.temProxima());
    }

    @Test
    public void consultar_legislatura_por_id_retorna_uma_legislatura() throws IOException, URISyntaxException, InterruptedException {
        Optional<Legislatura> legislaturaOpt = client.consultarLegislaturaPorId(ID_LEGISLATURA_CORRENTE);
        assertTrue(legislaturaOpt.isPresent());
        assertEquals(construirLegislaturaAtual(), legislaturaOpt.get());
    }

    @Test
    public void consultar_legislatura_por_id_quando_nao_existe_retorna_optional_empty() throws IOException, InterruptedException {
        Optional<Legislatura> legislaturaOpt = client.consultarLegislaturaPorId(99999);
        assertTrue(legislaturaOpt.isEmpty());
    }

    private Legislatura construirLegislaturaAtual() throws URISyntaxException {
        return new Legislatura(ID_LEGISLATURA_CORRENTE,
                new URI("https://dadosabertos.camara.leg.br/api/v2/legislaturas/56"),
                LocalDate.of(2019, 2, 1),
                LocalDate.of(2023, 1, 31));
    }
}
