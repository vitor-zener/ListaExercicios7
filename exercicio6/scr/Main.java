import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Moeda moeda = Moeda.BRL;

        Produto p1 = new Produto("P1", "Teclado", new Dinheiro(new BigDecimal("120.00"), moeda));
        Produto p2 = new Produto("P2", "Mouse",   new Dinheiro(new BigDecimal("80.00"),  moeda));
        Produto p3 = new Produto("P3", "Headset", new Dinheiro(new BigDecimal("250.00"), moeda));

        Carrinho c0 = Carrinho.vazio(moeda);
        System.out.println("c0: " + c0);

        Carrinho c1 = c0.adicionar(p1, 1).adicionar(p2, 2);
        System.out.println("c1: " + c1); // Teclado(1) + Mouse(2)

        Carrinho c2 = c1.adicionar(p3, 1);
        System.out.println("c2: " + c2); // + Headset(1)

        Carrinho c3 = c2.aplicarCupom(new BigDecimal("0.15")); // 15%
        System.out.println("c3 (15%): " + c3);

        // Tentativa de ultrapassar 30% → limita em 30%
        Carrinho c4 = c3.aplicarCupom(new BigDecimal("0.50")); // 50% ⇒ vira 30%
        System.out.println("c4 (cap 30%): " + c4);

        // Remover um produto
        Carrinho c5 = c4.removerPorProdutoId("P2");
        System.out.println("c5 (remove Mouse): " + c5);

        // Exemplos de erros (tratados)
        try {
            c5.adicionar(p1, 0);
        } catch (Exception e) {
            System.out.println("Erro esperado (quantidade 0): " + e.getMessage());
        }

        try {
            new Dinheiro(new BigDecimal("-1"), moeda);
        } catch (Exception e) {
            System.out.println("Erro esperado (dinheiro negativo): " + e.getMessage());
        }
    }
}

