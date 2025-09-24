import java.math.BigDecimal;

@FunctionalInterface
public interface FreteStrategy {
    BigDecimal calcular(Pedido pedido);
}

