import java.math.BigDecimal;

public class CalculadoraFrete {
    public BigDecimal calcular(Pedido pedido) {
        if (pedido == null) throw new IllegalArgumentException("Pedido não pode ser nulo.");
        if (pedido.getFreteStrategy() == null) throw new IllegalStateException("Estratégia de frete não definida.");
        return pedido.getFreteStrategy().calcular(pedido);
    }
}

