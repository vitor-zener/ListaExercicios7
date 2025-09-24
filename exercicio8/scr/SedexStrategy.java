import java.math.BigDecimal;
import java.math.RoundingMode;

public class SedexStrategy implements FreteStrategy {

    private static final BigDecimal BASE = new BigDecimal("20.00");
    private static final BigDecimal POR_KG = new BigDecimal("12.00");
    private static final BigDecimal MULT_INTER_REGIAO = new BigDecimal("1.40");
    private static final BigDecimal LIMITE_KG = new BigDecimal("30.000");

    @Override
    public BigDecimal calcular(Pedido pedido) {
        if (pedido.getPesoKg().compareTo(LIMITE_KG) > 0)
            throw new IllegalArgumentException("Sedex: peso excede 30 kg.");

        BigDecimal valor = BASE.add(POR_KG.multiply(pedido.getPesoKg()));
        boolean interRegional = !CepUtils.mesmaRegiao(pedido.getCepOrigem(), pedido.getCepDestino());
        if (interRegional) {
            valor = valor.multiply(MULT_INTER_REGIAO);
        }
        return valor.setScale(2, RoundingMode.HALF_EVEN);
    }
}

