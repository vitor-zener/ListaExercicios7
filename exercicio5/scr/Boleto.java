import java.util.regex.Pattern;
import java.math.BigDecimal;

public class Boleto extends FormaPagamento {
    private final String linhaDigitavel; // aceitar 47 ou 48 dígitos
    private static final Pattern DIGITOS = Pattern.compile("\\d+");

    public Boleto(String linhaDigitavel) {
        this.linhaDigitavel = linhaDigitavel != null ? linhaDigitavel.replaceAll("\\s+", "") : null;
    }

    @Override
    public void validarPagamento() {
        if (linhaDigitavel == null || !DIGITOS.matcher(linhaDigitavel).matches()) {
            throw new PagamentoInvalidoException("Linha digitável do boleto deve conter apenas dígitos.");
        }
        int len = linhaDigitavel.length();
        if (len != 47 && len != 48) {
            throw new PagamentoInvalidoException("Linha digitável inválida: tamanho deve ser 47 ou 48 dígitos.");
        }
        // Para o exercício, não calculamos DV dos campos; checagens completas fogem do escopo.
    }

    @Override
    public String processarPagamento(BigDecimal valor) {
        String tx = super.processarPagamento(valor);
        // Simula compensação
        return tx + "-COMP";
    }
}

