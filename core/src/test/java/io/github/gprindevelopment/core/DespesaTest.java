package io.github.gprindevelopment.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DespesaTest {

    @Test
    public void despesas_sao_igualadas_pelo_codigo_do_documento_fiscal() {
        Despesa despesa1 = new Despesa(1, 1, 1, 1);
        Despesa despesa2 = new Despesa(2, 1, 2, 2);
        assertEquals(despesa1, despesa2);
    }

    @Test
    public void despesas_tem_que_ter_ano_mes_valor_e_codigo() {
        assertThrows(IllegalArgumentException.class, () -> new Despesa(0, 1, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Despesa(1, 0, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Despesa(1, 1, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> new Despesa(1, 1, 1, 0));

        assertThrows(IllegalArgumentException.class, () -> new Despesa(-1, 1, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Despesa(1, -1, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Despesa(1, 1, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Despesa(1, 1, 1, -1));
    }

    @Test
    public void despesas_sao_ordenadas_por_valor_ascendente_natural_order() {
        Despesa despesa1 = new Despesa(1, 1, 1, 1);
        Despesa despesa2 = new Despesa(2, 2, 1, 1);
        List<Despesa> despesas = new ArrayList<>();
        despesas.add(despesa2);
        despesas.add(despesa1);
        despesas.sort(Comparator.naturalOrder());
        assertEquals(despesa1, despesas.get(0));
    }
}
