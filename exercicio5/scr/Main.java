import java.math.BigDecimal;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<FormaPagamento> formas = new ArrayList<>();

        // Cartão VISA de teste que passa em Luhn (ex.: 4111111111111111), validade futura e CVV 3
        formas.add(new CartaoCredito("4111111111111111", "12/2030", "123"));

        // Boleto com 47 dígitos (exemplo fictício)
        formas.add(new Boleto("21290001192110001210904475617405975870000002000".substring(0,47)));

        // Pix com diferentes chaves
        formas.add(new Pix("cliente@email.com"));
        formas.add(new Pix("+5591987654321"));
        formas.add(new Pix("11144477735"));      // CPF válido (exemplo)
        formas.add(new Pix("11222333000181"));   // CNPJ válido (exemplo)
        formas.add(new Pix("2f1a9f36-1c7b-4b6e-a8ea-9f6b7e4c1a2b"));

        // Exemplo inválidos
        formas.add(new CartaoCredito("1234567890123", "01/2020", "12"));  // inválido (Luhn/expirado/CVV)
        formas.add(new Boleto("12345"));                                   // inválido (tamanho)
        formas.add(new Pix("chave-invalida"));                             // inválido

        for (FormaPagamento f : formas) {
            System.out.println("\n== " + f.getClass().getSimpleName() + " ==");

            try {
                String tx = f.processarPagamento(new BigDecimal("199.90"));
                System.out.println("Pagamento OK. Transação: " + tx);
            } catch (PagamentoInvalidoException e) {
                System.out.println("Falha de validação: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }

        // Teste de valor inválido
        try {
            new Pix("cliente@email.com").processarPagamento(new BigDecimal("-10"));
        } catch (PagamentoInvalidoException e) {
            System.out.println("\nValor inválido (teste): " + e.getMessage());
        }
    }
}
