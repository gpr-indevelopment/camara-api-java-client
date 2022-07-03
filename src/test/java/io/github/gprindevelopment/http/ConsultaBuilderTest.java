package io.github.gprindevelopment.http;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsultaBuilderTest {

    @Test
    public void modo_permissivo_ignora_parametros_invalidos() {
        ConsultaBuilder<Consulta.Builder> builder = new Consulta.Builder();
        builder.adicionarParam("chave1", "");
        builder.adicionarParam("chave2", " ");
        builder.adicionarParam("chave3", null);
        builder.itens(null);
        builder.pagina(null);
        builder.ordenarPor(null, null);
        assertTrue(builder.getParametros().isEmpty());
    }

    @Test
    public void modo_rigoroso_valida_entradas_de_parametros() {
        ConsultaBuilder<Consulta.Builder> builder = new Consulta.Builder(ModoValidacao.RIGOROSO);
        assertThrows(IllegalArgumentException.class, () -> builder.adicionarParam("chave1", ""));
        assertThrows(IllegalArgumentException.class, () -> builder.adicionarParam("chave2", " "));
        assertThrows(IllegalArgumentException.class, () -> builder.adicionarParam("chave3", null));
        assertThrows(IllegalArgumentException.class, () -> builder.itens(null));
        assertThrows(IllegalArgumentException.class, () -> builder.pagina(null));
        assertThrows(IllegalArgumentException.class, () -> builder.ordenarPor(null, null));
    }

    @Test
    public void parametros_so_podem_ser_modificados_pelo_usuario_e_nao_pelo_getter() {
        ConsultaBuilder<Consulta.Builder> builder = new Consulta.Builder();
        Map<String, String> parametros = builder.getParametros();
        assertThrows(UnsupportedOperationException.class, () -> parametros.put("chave", "valor"));
    }

    @Test
    public void modo_permissivo_ignora_parametros_multi_valores_invalidos() {
        ConsultaBuilder<Consulta.Builder> builder = new Consulta.Builder();
        List<Integer> entrada = new ArrayList<>();
        entrada.add(null);
        builder.adicionarParamMultiValores("chave1", entrada);
        assertTrue(builder.getParametros().isEmpty());
    }

    @Test
    public void modo_permissivo_ignora_parametros_multi_valores_com_pelo_menos_um_valor_invalido() {
        ConsultaBuilder<Consulta.Builder> builder = new Consulta.Builder();
        List<Integer> entrada = new ArrayList<>();
        entrada.add(null);
        entrada.add(1);
        builder.adicionarParamMultiValores("chave1", entrada);
        assertTrue(builder.getParametros().isEmpty());
    }

    @Test
    public void modo_rigoroso_valida_entradas_de_parametros_multi_valores() {
        ConsultaBuilder<Consulta.Builder> builder = new Consulta.Builder(ModoValidacao.RIGOROSO);
        List<Integer> entrada = new ArrayList<>();
        entrada.add(null);
        assertThrows(IllegalArgumentException.class, () -> builder.adicionarParamMultiValores("chave1", entrada));

        List<Integer> entrada2 = new ArrayList<>();
        entrada2.add(1);
        entrada2.add(null);
        assertThrows(IllegalArgumentException.class, () -> builder.adicionarParamMultiValores("chave1", entrada2));
    }

    @Test
    public void adicionar_param_multi_valores_sem_parametro_ignora() {
        ConsultaBuilder<Consulta.Builder> builder = new Consulta.Builder();
        builder.adicionarParamMultiValores("chave");
        assertTrue(builder.getParametros().isEmpty());
    }

    @Test
    public void adicionar_param_multi_valores_list_vazia_ignora() {
        ConsultaBuilder<Consulta.Builder> builder = new Consulta.Builder();
        builder.adicionarParamMultiValores("chave", new ArrayList<>());
        assertTrue(builder.getParametros().isEmpty());
    }

    @Test
    public void adicionar_param_multi_valores_lista_nula_dispara_erro_em_modo_rigoroso() {
        ConsultaBuilder<Consulta.Builder> builder = new Consulta.Builder(ModoValidacao.RIGOROSO);
        assertThrows(IllegalArgumentException.class, () -> builder.adicionarParamMultiValores("chave", (List<Integer>) null));
    }

    @Test
    public void adicionar_param_multi_valores_lista_nula_ignora_modo_permissivo() {
        ConsultaBuilder<Consulta.Builder> builder = new Consulta.Builder();
        builder.adicionarParamMultiValores("chave", (List<Integer>) null);
        assertTrue(builder.getParametros().isEmpty());
    }
}
