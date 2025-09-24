import java.math.BigDecimal;
import java.util.Objects;

public class Produto implements Identificavel<String> {
    private final String id;
    private final String nome;
    private final BigDecimal preco;

    public Produto(String id, String nome, BigDecimal preco) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID inválido.");
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome inválido.");
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Preço inválido.");
        this.id = id.trim();
        this.nome = nome.trim();
        this.preco = preco;
    }

    @Override
    public String getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }

    @Override
    public String toString() {
        return "Produto{id='" + id + "', nome='" + nome + "', preco=" + preco + "}";
    }

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
