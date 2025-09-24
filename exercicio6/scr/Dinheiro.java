import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Dinheiro {
    private static final int ESCALA = 2;
    private static final RoundingMode MODO = RoundingMode.HALF_EVEN;

    private final BigDecimal valor; // sempre normalizado para 2 casas
    private final Moeda moeda;

    public Dinheiro(BigDecimal valor, Moeda moeda) {
        if (moeda == null) throw new IllegalArgumentException("Moeda não pode ser nula.");
        if (valor == null) throw new IllegalArgumentException("Valor não pode ser nulo.");
        if (valor.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Valor não pode ser negativo.");
        this.moeda = moeda;
        // Normaliza com 2 casas e HALF_EVEN (arredondamento bancário)
        this.valor = valor.setScale(ESCALA, MODO);
    }

    public BigDecimal getValor() { return valor; }
    public Moeda getMoeda() { return moeda; }

    private void checkMoeda(Dinheiro outro) {
        if (outro == null || this.moeda != outro.moeda) {
            throw new IllegalArgumentException("Moedas incompatíveis.");
        }
    }

    public Dinheiro somar(Dinheiro outro) {
        checkMoeda(outro);
        return new Dinheiro(this.valor.add(outro.valor), this.moeda);
    }

    public Dinheiro subtrair(Dinheiro outro) {
        checkMoeda(outro);
        BigDecimal r = this.valor.subtract(outro.valor);
        if (r.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Resultado negativo não permitido.");
        return new Dinheiro(r, this.moeda);
    }

    public Dinheiro multiplicar(BigDecimal fator) {
        if (fator == null) throw new IllegalArgumentException("Fator não pode ser nulo.");
        if (fator.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Fator negativo não permitido.");
        return new Dinheiro(this.valor.multiply(fator).setScale(ESCALA, MODO), this.moeda);
    }

    public Dinheiro multiplicar(int fatorInteiro) {
        if (fatorInteiro < 0) throw new IllegalArgumentException("Fator negativo não permitido.");
        return multiplicar(new BigDecimal(fatorInteiro));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dinheiro)) return false;
        Dinheiro d = (Dinheiro) o;
        // compara por moeda e valor numérico (normalizado)
        return this.moeda == d.moeda && this.valor.compareTo(d.valor) == 0;
    }

    @Override
    public int hashCode() {
        // usa valor normalizado e moeda
        return Objects.hash(this.moeda, this.valor.stripTrailingZeros());
    }

    @Override
    public String toString() {
        return moeda + " " + valor.toPlainString();
    }
}

