import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Fretes {
    private Fretes() {}

    /**
     * Wrapper promocional: frete grátis se subtotal >= limite; caso contrário usa a estratégia base.
     * Ex.: Fretes.gratisSeAcimaDe(300, new PacStrategy())
     */
    public static FreteStrategy gratisSeAcimaDe(BigDecimal limite, FreteStrategy base) {
        if (limite == null || limite.signum() < 0) throw new IllegalArgumentException("Limite inválido.");
        BigDecimal lim = limite.setScale(2, RoundingMode.HALF_EVEN);
        return pedido -> (pedido.getSubtotal().compareTo(lim) >= 0)
                ? BigDecimal.ZERO.setScale(2)
                : base.calcular(pedido);
    }
}

