import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import java.math.BigDecimal;

public class CartaoCredito extends FormaPagamento {
    private final String numero;   // apenas dígitos (13–19), Luhn válido
    private final String validade; // "MM/AA" ou "MM/AAAA", não expirada
    private final String cvv;      // 3–4 dígitos
    private static final Pattern DIGITOS = Pattern.compile("\\d+");

    public CartaoCredito(String numero, String validade, String cvv) {
        this.numero = numero != null ? numero.replaceAll("\\s+", "") : null;
        this.validade = validade != null ? validade.trim() : null;
        this.cvv = cvv != null ? cvv.trim() : null;
    }

    @Override
    public void validarPagamento() {
        if (numero == null || !DIGITOS.matcher(numero).matches()
                || numero.length() < 13 || numero.length() > 19) {
            throw new PagamentoInvalidoException("Número de cartão inválido.");
        }
        if (!luhnOk(numero)) {
            throw new PagamentoInvalidoException("Falha na validação Luhn do cartão.");
        }

        if (cvv == null || !DIGITOS.matcher(cvv).matches()
                || (cvv.length() != 3 && cvv.length() != 4)) {
            throw new PagamentoInvalidoException("CVV inválido.");
        }

        // validade: MM/AA ou MM/AAAA, e não expirada (considera o último dia do mês)
        YearMonth ym = parseValidade(validade);
        YearMonth agora = YearMonth.now();
        if (ym.isBefore(agora)) {
            throw new PagamentoInvalidoException("Cartão expirado.");
        }
    }

    private static boolean luhnOk(String s) {
        int soma = 0; boolean dup = false;
        for (int i = s.length() - 1; i >= 0; i--) {
            int n = s.charAt(i) - '0';
            if (dup) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            soma += n;
            dup = !dup;
        }
        return soma % 10 == 0;
    }

    private static YearMonth parseValidade(String v) {
        if (v == null) throw new PagamentoInvalidoException("Validade não informada.");
        String val = v.trim();
        try {
            // tenta MM/uu (ano 2 dígitos)
            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("MM/uu");
            YearMonth ym = YearMonth.parse(val, f2);
            // ajusta século automaticamente (YearMonth usa 20xx); serve para o exercício
            return ym;
        } catch (DateTimeParseException e) {
            // tenta MM/uuuu
            try {
                DateTimeFormatter f4 = DateTimeFormatter.ofPattern("MM/uuuu");
                return YearMonth.parse(val, f4);
            } catch (DateTimeParseException ex) {
                throw new PagamentoInvalidoException("Formato de validade inválido (use MM/AA ou MM/AAAA).");
            }
        }
    }

    @Override
    public String processarPagamento(BigDecimal valor) {
        String tx = super.processarPagamento(valor);
        // Simulação de autorização
        return tx + "-AUTH";
    }
}

