import java.math.BigDecimal;

public abstract class Funcionario {
    protected String nome;
    protected BigDecimal salario;

    public Funcionario(String nome, BigDecimal salario) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        if (salario == null || salario.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Salário deve ser positivo.");

        this.nome = nome;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    /** Método que cada subclasse deverá implementar */
    public abstract BigDecimal calcularBonus();
}
