package io.github.gprindevelopment.rest.despesa;

import io.github.gprindevelopment.core.Despesa;
import io.github.gprindevelopment.core.Legislatura;
import io.github.gprindevelopment.core.common.Pagina;
import io.github.gprindevelopment.core.exception.CamaraClientStatusException;
import io.github.gprindevelopment.core.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.core.exception.RespostaNaoEsperadaException;
import io.github.gprindevelopment.rest.legislatura.LegislaturaClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class DespesaClientTest {

    private final DespesaClient client = new DespesaClient();

    @Test
    public void consulta_por_despesas_de_deputado_na_legislatura_retorna_despesas_paginado() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        int legislatura = 56;
        int idDeputado = 76874;
        int itens = 10;
        int paginaAtual = 1;
        ConsultaDespesa consulta = new ConsultaDespesa.Builder(idDeputado)
                .legislaturas(legislatura)
                .itens(itens)
                .pagina(paginaAtual)
                .build();
        Pagina<Despesa> pagina = client.consultar(consulta);
        assertEquals(itens, pagina.size());
        assertEquals(paginaAtual, pagina.getPaginaAtual());
        pagina.forEach(despesa -> {
            assertNotEquals(0, despesa.getCodDocumento());
            assertFalse(despesa.getNumDocumento().isBlank());
        });
    }

    @Test
    public void consulta_por_despesa_com_varias_legislaturas_retorna_despesas_das_legislaturas() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        LegislaturaClient legislaturaClient = new LegislaturaClient();
        Optional<Legislatura> leg55Opt = legislaturaClient.consultarLegislaturaPorId(55);
        Optional<Legislatura> leg56Opt = legislaturaClient.consultarLegislaturaPorId(56);
        assertTrue(leg55Opt.isPresent());
        assertTrue(leg56Opt.isPresent());

        int idDeputado = 160976;
        int itens = 20;
        int paginaAtual = 1;
        ConsultaDespesa consulta = new ConsultaDespesa.Builder(idDeputado)
                .legislaturas(55, 56)
                .itens(itens)
                .pagina(paginaAtual)
                .build();
        Pagina<Despesa> despesas = client.consultar(consulta);
        assertEquals(itens, despesas.size());
        assertEquals(paginaAtual, despesas.getPaginaAtual());
        despesas.forEach(despesa -> {
            LocalDate limiteInferior = leg55Opt.get().getDataInicio();
            LocalDate limiteSuperior = leg56Opt.get().getDataFim();
            assertTrue(despesa.getDataDocumento().isAfter(YearMonth.from(limiteInferior))
                    && despesa.getDataDocumento().isBefore(YearMonth.from(limiteSuperior)));
        });
    }

    @Test
    public void consultar_despesas_com_ano_e_mes_retorna_datas_corretas() throws RespostaNaoEsperadaException, CamaraClientStatusException, IOException, RecursoNaoExisteException {
        int idDeputado = 160976;
        int itens = 50;
        int paginaAtual = 1;
        int[] anos = new int[]{2020, 2021};
        int[] meses = new int[]{1, 2};
        ConsultaDespesa consulta = new ConsultaDespesa.Builder(idDeputado)
                .anos(anos)
                .meses(meses)
                .itens(itens)
                .pagina(paginaAtual)
                .build();
        Pagina<Despesa> despesas = client.consultar(consulta);
        assertEquals(paginaAtual, despesas.getPaginaAtual());
        despesas.forEach(despesa ->  {
            IntStream anoStream = Arrays.stream(anos);
            IntStream mesStream = Arrays.stream(meses);
            assertTrue(anoStream.anyMatch(ano -> ano == despesa.getAno()));
            assertTrue(mesStream.anyMatch(mes -> mes == despesa.getMes()));
            assertNotEquals(2022, despesa.getAno());
            assertNotEquals(3, despesa.getMes());
            assertNotEquals(0, despesa.getValorDocumento());
        });
    }

    @Test
    public void buscar_despesa_de_deputado_que_nao_existe_dispara_erro() {
        assertThrows(RecursoNaoExisteException.class, () -> client.consultar(new ConsultaDespesa.Builder(0).build()));
    }
}
