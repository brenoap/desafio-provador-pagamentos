package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto;

public class DadosAdiantamento {

    public final int idTransacao;

    public final double valorAdiantamento;

    public DadosAdiantamento(int idTransacao, double valorAdiantamento){
        this.idTransacao = idTransacao;
        this.valorAdiantamento = valorAdiantamento;
    };

    public int getIdTransacao() {
        return idTransacao;
    }

    public double getValorAdiantamento() {
        return valorAdiantamento;
    }
}
