import java.math.BigDecimal;
import java.math.RoundingMode;

public class Pedido {
    private final String cepOrigem;     // validado (8 dígitos)
    private final String cepDestino;    // validado (8 dígitos)
    private final BigDecimal pesoKg;    // > 0
    private final BigDecimal subtotal;  // >= 0
    private FreteStrategy freteStrategy;

    public Pedido(String cepOrigem, String cepDestino, BigDecimal pesoKg, BigDecimal subtotal, FreteStrategy estrategia) {
        this.cepOrigem  = CepUtils.validar(cepOrigem);
        this.cepDestino = CepUtils.validar(cepDestino);
        if (pesoKg == null || pesoKg.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Peso (kg) deve ser positivo.");
        if (subtotal == null || subtotal.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Subtotal não pode ser negativo.");
        this.pesoKg = pesoKg.setScale(3, RoundingMode.HALF_EVEN);  // kg com 3 casas
        this.subtotal = subtotal.setScale(2, RoundingMode.HALF_EVEN);
        this.freteStrategy = estrategia; // pode ser definida depois via setter
    }

    public String getCepOrigem() { return cepOrigem; }
    public String getCepDestino() { return cepDestino; }
    public BigDecimal getPesoKg() { return pesoKg; }
    public BigDecimal getSubtotal() { return subtotal; }

    public FreteStrategy getFreteStrategy() { return freteStrategy; }
    public void setFreteStrategy(FreteStrategy freteStrategy) { this.freteStrategy = freteStrategy; }
}

