public class Main {
    public static void main(String[] args) {
        Produto p1 = new Produto("Notebook", 3500.00, 10);
        System.out.println("Produto criado: " + p1);

        p1.setPreco(3799.90);
        p1.setQuantidadeEmEstoque(12);
        System.out.println("Após alterações: " + p1);

        try {
            p1.setPreco(-50);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao definir preço: " + e.getMessage());
        }

        try {
            p1.setNome("");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao definir nome: " + e.getMessage());
        }

        try {
            p1.setQuantidadeEmEstoque(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao definir quantidade: " + e.getMessage());
        }
    }
}
