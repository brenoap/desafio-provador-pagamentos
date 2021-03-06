package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique;

import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.DadosAdiantamento;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.DadosTransacao;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.StatusRecebivel.aguardando_liberacao_fundos;
import static br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.StatusRecebivel.pago;
import static br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.MetodoPagamento.CREDITO;
import static br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.MetodoPagamento.DEBITO;
import static org.junit.jupiter.api.Assertions.*;

public class RecebivelFactoryTest {

    private final RecebivelFactory factory = RecebivelFactory.getInstance();

    @Test
    public void deveCriarUmaInstanciaEstaticaDaClasseValida() {
        assertNotNull(factory);
    }

    @Test
    public void deveCriarRecebivelCorretoQuandoOMetodoDePagamentoForDebito() {
        BigDecimal valor = BigDecimal.valueOf(200.0);
        List<Recebivel> recebivel = factory.criar(List.of(new DadosTransacao(
                valor
                , DEBITO,
                "22222",
                "ELIAS",
                LocalDate.now(),
                222,
                2020)),
                new ArrayList<>()
        );
        assertEquals(0.03, recebivel.get(0).getDesconto());
        assertEquals(pago, recebivel.get(0).getStatusRecebivel());
        assertEquals(valor, recebivel.get(0).getValorOriginal());
        assertEquals(BigDecimal.valueOf(Double.valueOf(String.valueOf(valor)) / 1.03).setScale(2, RoundingMode.CEILING), recebivel.get(0).getValorASerRecebido());
        assertEquals(LocalDate.now(), recebivel.get(0).getDataRecebimento());
    }

    @Test
    public void deveCriarRecebivelCorretoQuandoOMetodoDePagamentoForDebitoComAdiantamento() {
        BigDecimal valor = BigDecimal.valueOf(200.0);
        List<Recebivel> recebivel = factory.criar(List.of(new DadosTransacao(
                valor
                , DEBITO,
                "22222",
                "ELIAS",
                LocalDate.now(),
                222,
                2020)
                ),
                List.of(new DadosAdiantamento(
                        2020,
                        0.01)
                )
        );
        assertEquals(0.04, recebivel.get(0).getDesconto());
        assertEquals(pago, recebivel.get(0).getStatusRecebivel());
        assertEquals(valor, recebivel.get(0).getValorOriginal());
        assertEquals(BigDecimal.valueOf(Double.valueOf(String.valueOf(valor)) / 1.03).setScale(2, RoundingMode.CEILING), recebivel.get(0).getValorASerRecebido());
        assertEquals(LocalDate.now(), recebivel.get(0).getDataRecebimento());
    }

    @Test
    public void deveCriarRecebivelCorretoQuandoOMetodoDePagamentoForCredito() {
        BigDecimal valor = BigDecimal.valueOf(200.0);
        List<Recebivel> recebivel = factory.criar(List.of(new DadosTransacao(
                valor
                , CREDITO,
                "22222",
                "ELIAS",
                LocalDate.now(),
                222,
                2020)),
                new ArrayList<>()
        );
        assertEquals(0.05, recebivel.get(0).getDesconto());
        assertEquals(aguardando_liberacao_fundos, recebivel.get(0).getStatusRecebivel());
        assertEquals(valor, recebivel.get(0).getValorOriginal());
        assertEquals(BigDecimal.valueOf(Double.valueOf(String.valueOf(valor)) / 1.05).setScale(2, RoundingMode.CEILING), recebivel.get(0).getValorASerRecebido());
        assertEquals(LocalDate.now().plusDays(30), recebivel.get(0).getDataRecebimento());
    }

    @Test
    public void deveCriarRecebivelCorretoQuandoOMetodoDePagamentoForCreditoComAdiantamento() {
        BigDecimal valor = BigDecimal.valueOf(200.0);
        List<Recebivel> recebivel = factory.criar(List.of(new DadosTransacao(
                valor,
                CREDITO,
                "22222",
                "ELIAS",
                LocalDate.now(),
                222,
                2020)),
                List.of(new DadosAdiantamento(
                        2020,
                        0.04)
                )
        );
        assertEquals(0.09, recebivel.get(0).getDesconto());
        assertEquals(aguardando_liberacao_fundos, recebivel.get(0).getStatusRecebivel());
        assertEquals(valor, recebivel.get(0).getValorOriginal());
        assertEquals(BigDecimal.valueOf(Double.valueOf(String.valueOf(valor)) / 1.09).setScale(2, RoundingMode.CEILING), recebivel.get(0).getValorASerRecebido());
        assertEquals(LocalDate.now().plusDays(30), recebivel.get(0).getDataRecebimento());
    }
}