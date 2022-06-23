package io.github.gprindevelopment.rest.deputado;

import io.github.gprindevelopment.core.Deputado;
import io.github.gprindevelopment.core.common.Ordem;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import io.github.gprindevelopment.rest.common.ConsultaPaginada;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DeputadoClientTest {

    private final DeputadoClient client = new DeputadoClient();

    @Test
    public void consulta_deputado_por_id_retorna_deputado() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException {
        int deputado = 76874;
        Optional<Deputado> deputadoOpt = client.consultarDeputadoPorId(deputado);
        assertTrue(deputadoOpt.isPresent());
        assertEquals("Marcelo Ribeiro Freixo", deputadoOpt.get().getNomeFormatado());
        assertEquals("MARCELO RIBEIRO FREIXO", deputadoOpt.get().getNome());
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
        ConsultaPaginada consulta = new ConsultaPaginada.Builder()
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
        Pagina<Deputado> pagina = client.consultar(new ConsultaPaginada.Builder().build());
        assertFalse(pagina.isEmpty());
        assertFalse(pagina.temProxima());
        assertEquals(1, pagina.getPaginaAtual());
    }
}
