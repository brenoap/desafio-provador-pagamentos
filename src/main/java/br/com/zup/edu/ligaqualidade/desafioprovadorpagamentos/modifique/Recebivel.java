package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Recebivel {
    private final StatusRecebivel statusRecebivel;
    private final LocalDate dataRecebimento;
    private final BigDecimal valorOriginal;
    private final BigDecimal valorASerRecebido;
    private final double desconto;

    public Recebivel(StatusRecebivel statusRecebivel, LocalDate dataRecebimento, BigDecimal valorOriginal, double desconto) {
        this.statusRecebivel = statusRecebivel;
        this.dataRecebimento = dataRecebimento;
        this.valorOriginal = valorOriginal;
        this.valorASerRecebido = BigDecimal.valueOf(valorOriginal.doubleValue() / (1+desconto)).setScale(2, RoundingMode.CEILING);
        this.desconto = desconto;
    }

    public StatusRecebivel getStatusRecebivel() {
        return statusRecebivel;
    }

    public LocalDate getDataRecebimento() {
        return dataRecebimento;
    }

    public BigDecimal getValorOriginal() {
        return valorOriginal;
    }

    public BigDecimal getValorASerRecebido() {
        return valorASerRecebido;
    }

    public double getDesconto() {
        return desconto;
    }

    /**
     *
     * @return {status, valorOriginal, valorASerRecebido, data}
     */
    public String[] toArray() {
        return new String[]{statusRecebivel.name(),
                valorOriginal.toString(),
                valorASerRecebido.toString(),
                dataRecebimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))};
    }
}
