import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        CalculadoraFrete calc = new CalculadoraFrete();

        // Pedido válido
        Pedido pedido = new Pedido(
                "01001000",          // origem (SP - exemplo)
                "20040002",          // destino (RJ - exemplo)
                new BigDecimal("3.5"),   // 3,5 kg
                new BigDecimal("350.00"),// subtotal
                null                   // estratégia será definida abaixo
        );

        // 1) PAC
        pedido.setFreteStrategy(new PacStrategy());
        System.out.println("PAC: " + calc.calcular(pedido));

        // 2) SEDEX (troca em runtime)
        pedido.setFreteStrategy(new SedexStrategy());
        System.out.println("SEDEX: " + calc.calcular(pedido));

        // 3) Retirada na loja (vai falhar porque destino != origem)
        pedido.setFreteStrategy(new RetiradaNaLojaStrategy());
        try {
            System.out.println("Retirada: " + calc.calcular(pedido));
        } catch (Exception e) {
            System.out.println("Retirada falhou (esperado): " + e.getMessage());
        }

        // 4) Promo: “frete grátis acima de 300” usando lambda + PAC como base
        FreteStrategy promoPac = Fretes.gratisSeAcimaDe(new BigDecimal("300.00"), new PacStrategy());
        pedido.setFreteStrategy(promoPac);
        System.out.println("PAC Promocional (subtotal 350): " + calc.calcular(pedido)); // deve ser 0,00

        // 5) CEP inválido (demonstra exceção)
        try {
            new Pedido("ABCDE999", "01001000", new BigDecimal("1.0"), new BigDecimal("10.00"), new PacStrategy());
        } catch (CepInvalidoException e) {
            System.out.println("CEP inválido detectado (esperado): " + e.getMessage());
        }

        // 6) Retirada correta (destino = origem)
        Pedido retiradaOk = new Pedido("01001000", "01001000", new BigDecimal("2.0"), new BigDecimal("50.00"), new RetiradaNaLojaStrategy());
        System.out.println("Retirada (ok): " + calc.calcular(retiradaOk));
    }
}

