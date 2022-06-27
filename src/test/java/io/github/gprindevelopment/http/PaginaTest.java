package io.github.gprindevelopment.http;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaginaTest {

    @Test
    public void default_pagina_1_quando_nao_for_informado() {
        Pagina<String> pagina = new Pagina<>(new ArrayList<>(), 1, 0);
        assertEquals(1, pagina.getPaginaAtual());
        pagina = new Pagina<>(new ArrayList<>(), 1, null);
        assertEquals(1, pagina.getPaginaAtual());
    }

    @Test
    public void tem_proxima_quando_pagina_atual_vezes_itens_e_menor() {
        Pagina<String> pagina = new Pagina<>(List.of("a", "b"), 6, 2);
        assertTrue(pagina.temProxima());
        pagina = new Pagina<>(List.of("a", "b"), 6, 3);
        assertFalse(pagina.temProxima());
    }

    @Test
    public void se_pagina_atual_tem_zero_itens_nao_tem_proxima() {
        Pagina<String> pagina = new Pagina<>(new ArrayList<>(), 6, 9);
        assertFalse(pagina.temProxima());
    }
}
