package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique;

import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.DadosAdiantamento;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.DadosTransacao;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.MetodoPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DadosAdiantamentoFactory {

    private DadosAdiantamentoFactory() {
    }

    private static final DadosAdiantamentoFactory instance;

    static {
        instance = new DadosAdiantamentoFactory();
    }

    public static DadosAdiantamentoFactory getInstance() {
        return instance;
    }

    /**
     *
     * @param infoAdiantamentos
     *  dados das transações. A String está formatada da seguinte maneira:
     * 	<b>"idTransacao,taxa"</b>
     * @return DadosAdiantamento
     */
    public List<DadosAdiantamento> criar(List<String> infoAdiantamentos) {
        final List<DadosAdiantamento> adiantamentos = new ArrayList<>();
        for (String dados : infoAdiantamentos) {
            String[] adiantamentosEmTexto = dados.split(",");
            DadosAdiantamento adiantamento = criar(adiantamentosEmTexto);
            adiantamentos.add(adiantamento);
        }

        return adiantamentos;
    }

    private DadosAdiantamento criar(String[] adiantamento) {
        int idTransacao = Integer.parseInt(adiantamento[0]);
        double valorAdiantamento = Double.parseDouble(adiantamento[1]);

        return new DadosAdiantamento(idTransacao, valorAdiantamento);
    }
}
