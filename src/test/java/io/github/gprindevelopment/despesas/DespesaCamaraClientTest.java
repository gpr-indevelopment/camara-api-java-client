package io.github.gprindevelopment.despesas;

import io.github.gprindevelopment.dominio.Despesa;
import io.github.gprindevelopment.dominio.Legislatura;
import io.github.gprindevelopment.exception.RecursoNaoExisteException;
import io.github.gprindevelopment.http.Pagina;
import io.github.gprindevelopment.legislaturas.LegislaturaCamaraClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DespesaCamaraClientTest {

    private final DespesaCamaraClient client = new DespesaCamaraClient();

    @Test
    public void consulta_por_despesas_de_deputado_na_legislatura_retorna_despesas_paginado() throws IOException, InterruptedException {
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
    public void consulta_por_despesa_com_varias_legislaturas_retorna_despesas_das_legislaturas() throws IOException, InterruptedException {
        LegislaturaCamaraClient legislaturaClient = new LegislaturaCamaraClient();
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
    public void consultar_despesas_com_ano_e_mes_retorna_datas_corretas() throws IOException, InterruptedException {
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

    @Test
    public void deve_ser_possivel_usar_a_consulta_de_despesas_com_ano_e_mes_em_lista() throws IOException, InterruptedException {
        int idDeputado = 160976;
        int itens = 50;
        int paginaAtual = 1;
        List<Integer> anos = List.of(2020, 2021);
        List<Integer> meses = List.of(1, 2);
        ConsultaDespesa consulta = new ConsultaDespesa.Builder(idDeputado)
                .anos(anos)
                .meses(meses)
                .itens(itens)
                .pagina(paginaAtual)
                .build();
        Pagina<Despesa> despesas = client.consultar(consulta);
        assertEquals(paginaAtual, despesas.getPaginaAtual());
        despesas.forEach(despesa ->  {
            Stream<Integer> anoStream = anos.stream();
            Stream<Integer> mesStream = meses.stream();
            assertTrue(anoStream.anyMatch(ano -> ano == despesa.getAno()));
            assertTrue(mesStream.anyMatch(mes -> mes == despesa.getMes()));
            assertNotEquals(2022, despesa.getAno());
            assertNotEquals(3, despesa.getMes());
            assertNotEquals(0, despesa.getValorDocumento());
        });
    }
}
