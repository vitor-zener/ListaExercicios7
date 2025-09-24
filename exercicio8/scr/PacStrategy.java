import java.math.BigDecimal;
import java.math.RoundingMode;

public class PacStrategy implements FreteStrategy {

    private static final BigDecimal BASE = new BigDecimal("10.00");
    private static final BigDecimal POR_KG = new BigDecimal("7.00");
    private static final BigDecimal MULT_INTER_REGIAO = new BigDecimal("1.20");
    private static final BigDecimal LIMITE_KG = new BigDecimal("50.000");

    @Override
    public BigDecimal calcular(Pedido pedido) {
        if (pedido.getPesoKg().compareTo(LIMITE_KG) > 0)
            throw new IllegalArgumentException("PAC: peso excede 50 kg.");

        BigDecimal valor = BASE.add(POR_KG.multiply(pedido.getPesoKg()));
        boolean interRegional = !CepUtils.mesmaRegiao(pedido.getCepOrigem(), pedido.getCepDestino());
        if (interRegional) {
            valor = valor.multiply(MULT_INTER_REGIAO);
        }
        return valor.setScale(2, RoundingMode.HALF_EVEN);
    }
}

