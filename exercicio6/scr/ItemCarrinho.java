public final class ItemCarrinho {
    private final Produto produto;
    private final int quantidade; // > 0

    public ItemCarrinho(Produto produto, int quantidade) {
        if (produto == null) throw new IllegalArgumentException("Produto não pode ser nulo.");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser > 0.");
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }

    public Dinheiro subtotal() {
        return produto.getPreco().multiplicar(quantidade);
    }

    public ItemCarrinho somarQuantidade(int delta) {
        if (delta <= 0) throw new IllegalArgumentException("Delta deve ser > 0.");
        return new ItemCarrinho(this.produto, this.quantidade + delta);
    }
}

