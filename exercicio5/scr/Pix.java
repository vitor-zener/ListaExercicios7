import java.util.regex.Pattern;
import java.math.BigDecimal;

public class Pix extends FormaPagamento {
    private final String chave; // email, telefone, CPF, CNPJ ou UUID
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern FONE  = Pattern.compile("^\\+?[0-9]{10,14}$");
    private static final Pattern UUID  = Pattern.compile("^[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}$");

    public Pix(String chave) {
        this.chave = chave != null ? chave.trim() : null;
    }

    @Override
    public void validarPagamento() {
        if (chave == null || chave.isEmpty()) {
            throw new PagamentoInvalidoException("Chave Pix não informada.");
        }
        String k = chave.replaceAll("\\s+", "");

        if (EMAIL.matcher(k).matches()) return;
        if (FONE.matcher(k).matches())  return;
        if (UUID.matcher(k).matches())  return;

        String soDigitos = k.replaceAll("\\D", "");
        if (soDigitos.length() == 11 && cpfValido(soDigitos)) return;
        if (soDigitos.length() == 14 && cnpjValido(soDigitos)) return;

        throw new PagamentoInvalidoException("Chave Pix inválida (use email, telefone, CPF, CNPJ ou UUID).");
    }

    private static boolean cpfValido(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}") || cpf.chars().distinct().count() == 1) return false;
        int d1 = dvCpf(cpf, 9, 10);
        int d2 = dvCpf(cpf, 10, 11);
        return d1 == (cpf.charAt(9) - '0') && d2 == (cpf.charAt(10) - '0');
    }

    private static int dvCpf(String cpf, int len, int pesoInicial) {
        int soma = 0, peso = pesoInicial;
        for (int i = 0; i < len; i++) soma += (cpf.charAt(i) - '0') * (peso--);
        int r = (soma * 10) % 11;
        return (r == 10) ? 0 : r;
    }

    private static boolean cnpjValido(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}") || cnpj.chars().distinct().count() == 1) return false;
        int d1 = dvCnpj(cnpj, new int[]{5,4,3,2,9,8,7,6,5,4,3,2});
        int d2 = dvCnpj(cnpj, new int[]{6,5,4,3,2,9,8,7,6,5,4,3,2});
        return d1 == (cnpj.charAt(12) - '0') && d2 == (cnpj.charAt(13) - '0');
    }

    private static int dvCnpj(String cnpj, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) soma += (cnpj.charAt(i) - '0') * pesos[i];
        int r = soma % 11;
        return (r < 2) ? 0 : (11 - r);
    }

    @Override
    public String processarPagamento(BigDecimal valor) {
        String tx = super.processarPagamento(valor);
        // Simula liquidação instantânea
        return tx + "-PIX";
    }
}

