public class Main {
    public static void main(String[] args) {
        Produto p = new Produto("Headset", 200.00, 5);

        System.out.println("Preço inicial: " + p.getPreco());

        // descontos válidos
        p.aplicarDesconto(10);
        System.out.println("Após 10% de desconto: " + p.getPreco());

        p.aplicarDesconto(50);
        System.out.println("Após 50% de desconto adicional: " + p.getPreco());

        // descontos inválidos
        try {
            p.aplicarDesconto(-5);
        } catch (Exception e) {
            System.out.println("Erro (desconto -5%): " + e.getMessage());
        }

        try {
            p.aplicarDesconto(60);
        } catch (Exception e) {
            System.out.println("Erro (desconto 60%): " + e.getMessage());
        }
    }
}
