public class Produto {
    private String nome;
    private double preco;
    private int quantidadeEmEstoque;

    public Produto(String nome, double preco, int quantidadeEmEstoque) {
        setNome(nome);
        setPreco(preco);
        setQuantidadeEmEstoque(quantidadeEmEstoque);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        this.nome = nome;
    }

    public double getPreco() { return preco; }
    public void setPreco(double preco) {
        if (preco < 0)
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        this.preco = preco;
    }

    public int getQuantidadeEmEstoque() { return quantidadeEmEstoque; }
    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
        if (quantidadeEmEstoque < 0)
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa.");
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    /** Aplica desconto entre 0 e 50% */
    public void aplicarDesconto(double porcentagem) {
        if (porcentagem < 0 || porcentagem > 50)
            throw new DescontoInvalidoException(
                "Desconto inválido: permitido apenas entre 0% e 50%."
            );
        double fator = 1 - (porcentagem / 100.0);
        setPreco(preco * fator);
    }

    @Override
    public String toString() {
        return "Produto{nome='" + nome + "', preco=" + preco +
               ", quantidadeEmEstoque=" + quantidadeEmEstoque + "}";
    }
}

