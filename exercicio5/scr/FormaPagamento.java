import java.math.BigDecimal;

public abstract class FormaPagamento {

    /** Valida os dados cadastrais da forma de pagamento. */
    public abstract void validarPagamento();

    /**
     * Processa o pagamento para o valor informado.
     * Regras comuns:
     *  - valor não pode ser nulo nem <= 0
     *  - invoca a validação específica da forma
     */
    public String processarPagamento(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PagamentoInvalidoException("Valor do pagamento deve ser positivo.");
        }
        validarPagamento();
        // Simulação de processamento: retorna um ID de transação simples
        return getClass().getSimpleName() + "-TX-" + System.currentTimeMillis();
    }
}

