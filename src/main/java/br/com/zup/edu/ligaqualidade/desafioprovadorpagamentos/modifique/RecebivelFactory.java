package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique;

import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.DadosAdiantamento;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.DadosTransacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.StatusRecebivel.aguardando_liberacao_fundos;
import static br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.StatusRecebivel.pago;

public class RecebivelFactory {
    private RecebivelFactory() {}

    private static final RecebivelFactory instance;

    static {
        instance = new RecebivelFactory();
    }

    public static RecebivelFactory getInstance() {
        return instance;
    }

    public List<Recebivel> criar(List<DadosTransacao> transacoes, List<DadosAdiantamento> adiantamentos) {
        final List<Recebivel> recebiveis = new ArrayList<>();
        Recebivel recebivel;

        for (int i = 0; i < transacoes.size(); i++) {
            if (adiantamentos.size() > 0) {
                recebivel = criarComAdiantamento(transacoes.get(i), adiantamentos.get(i));
            } else {
                recebivel = criar(transacoes.get(i));
            }
            recebiveis.add(recebivel);
        }

        return recebiveis;
    }

    private Recebivel criar(DadosTransacao transacao) {
        Recebivel recebivel;
        if (transacao.metodoEhDebito()) {
            recebivel = new Recebivel(pago, LocalDate.now(), transacao.valor, 0.03);
        } else if (transacao.metodoEhCredito()) {
            recebivel = new Recebivel(aguardando_liberacao_fundos, LocalDate.now().plusDays(30), transacao.valor, 0.05);
        } else {
            throw new RuntimeException("Não foi possível criar um recebível para esse transação (" + transacao.toString() + ")");
        }

        return recebivel;
    }

    private Recebivel criarComAdiantamento(DadosTransacao transacao, DadosAdiantamento adiantamento) {
        Recebivel recebivel;
        if (transacao.metodoEhDebito()) {
            recebivel = new Recebivel(pago, LocalDate.now(), transacao.valor, (0.03+adiantamento.getValorAdiantamento()));
        } else if (transacao.metodoEhCredito()) {
            recebivel = new Recebivel(pago, LocalDate.now(), transacao.valor, (0.05+adiantamento.getValorAdiantamento()));
        } else {
            throw new RuntimeException("Não foi possível criar um recebível para esse transação (" + transacao.toString() + ")");
        }

        return recebivel;
    }
}
