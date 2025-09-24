import java.math.BigDecimal;

public class RetiradaNaLojaStrategy implements FreteStrategy {
    @Override
    public BigDecimal calcular(Pedido pedido) {
        // Exigimos que o “destino” seja o mesmo da loja (origem) para representar retirada
        if (!pedido.getCepOrigem().equals(pedido.getCepDestino())) {
            throw new IllegalStateException("Retirada na loja: destino deve ser o CEP da loja (igual à origem).");
        }
        return BigDecimal.ZERO.setScale(2);
    }
}

