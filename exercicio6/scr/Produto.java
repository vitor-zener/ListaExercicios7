import java.util.Objects;

public final class Produto {
    private final String id;     // pode ser um UUID ou código
    private final String nome;
    private final Dinheiro preco;

    public Produto(String id, String nome, Dinheiro preco) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID inválido.");
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome inválido.");
        if (preco == null) throw new IllegalArgumentException("Preço não pode ser nulo.");
        this.id = id.trim();
        this.nome = nome.trim();
        this.preco = preco;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public Dinheiro getPreco() { return preco; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto p = (Produto) o;
        return id.equals(p.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

