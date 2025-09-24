import java.math.BigDecimal;
import java.util.Objects;

public class Funcionario implements Identificavel<Long> {
    private final Long id;
    private final String nome;
    private final BigDecimal salario;

    public Funcionario(Long id, String nome, BigDecimal salario) {
        if (id == null) throw new IllegalArgumentException("ID inválido.");
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome inválido.");
        if (salario == null || salario.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Salário inválido.");
        this.id = id;
        this.nome = nome.trim();
        this.salario = salario;
    }

    @Override
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getSalario() { return salario; }

    @Override
    public String toString() {
        return "Funcionario{id=" + id + ", nome='" + nome + "', salario=" + salario + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; 
        if (!(o instanceof Funcionario)) return false;
        Funcionario f = (Funcionario) o;
        return id.equals(f.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

