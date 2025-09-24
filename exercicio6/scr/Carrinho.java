import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public final class Carrinho {
    private static final int ESCALA = 2;
    private static final RoundingMode MODO = RoundingMode.HALF_EVEN;
    private static final BigDecimal MAX_CUPOM = new BigDecimal("0.30"); // 30%

    private final List<ItemCarrinho> itens;     // imutável
    private final Moeda moeda;
    private final BigDecimal descontoPercent;   // [0, 0.30]

    private Carrinho(List<ItemCarrinho> itens, Moeda moeda, BigDecimal descontoPercent) {
        if (moeda == null) throw new IllegalArgumentException("Moeda do carrinho não pode ser nula.");
        if (itens == null) throw new IllegalArgumentException("Lista de itens não pode ser nula.");
        BigDecimal d = (descontoPercent == null ? BigDecimal.ZERO : descontoPercent);
        if (d.compareTo(BigDecimal.ZERO) < 0 || d.compareTo(MAX_CUPOM) > 0)
            throw new IllegalArgumentException("Desconto deve estar entre 0% e 30%.");

        // Garante imutabilidade e consistência de moeda
        List<ItemCarrinho> copia = new ArrayList<>();
        for (ItemCarrinho ic : itens) {
            if (ic == null) throw new IllegalArgumentException("Item nulo não permitido.");
            if (ic.getProduto().getPreco().getMoeda() != moeda)
                throw new IllegalArgumentException("Moeda do item diferente da moeda do carrinho.");
            copia.add(ic);
        }
        this.itens = Collections.unmodifiableList(copia);
        this.moeda = moeda;
        this.descontoPercent = d.setScale(4, MODO); // guarda com 4 casas para segurança
    }

    public static Carrinho vazio(Moeda moeda) {
        return new Carrinho(Collections.emptyList(), moeda, BigDecimal.ZERO);
    }

    public List<ItemCarrinho> getItens() { return itens; }
    public Moeda getMoeda() { return moeda; }
    public BigDecimal getDescontoPercent() { return descontoPercent; }

    /** Soma preços (2 casas, HALF_EVEN). */
    public Dinheiro getSubtotal() {
        Dinheiro total = new Dinheiro(BigDecimal.ZERO, moeda);
        for (ItemCarrinho ic : itens) {
            total = total.somar(ic.subtotal());
        }
        return total;
    }

    /** Valor de desconto (limitado por descontoPercent, com arredondamento bancário). */
    public Dinheiro getDescontoValor() {
        BigDecimal sub = getSubtotal().getValor();
        BigDecimal desc = sub.multiply(descontoPercent).setScale(ESCALA, MODO);
        return new Dinheiro(desc, moeda);
    }

    /** Total final = subtotal - desconto. */
    public Dinheiro getTotal() {
        return getSubtotal().subtrair(getDescontoValor());
    }

    /** Retorna novo carrinho com item somado (mesmo produto agrega quantidade). */
    public Carrinho adicionar(Produto produto, int quantidade) {
        if (produto == null) throw new IllegalArgumentException("Produto não pode ser nulo.");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser > 0.");
        if (!this.itens.isEmpty() && produto.getPreco().getMoeda() != this.moeda)
            throw new IllegalArgumentException("Moeda do produto difere da moeda do carrinho.");

        List<ItemCarrinho> nova = new ArrayList<>(this.itens.size() + 1);
        boolean agregado = false;

        for (ItemCarrinho ic : this.itens) {
            if (ic.getProduto().getId().equals(produto.getId())) {
                nova.add(ic.somarQuantidade(quantidade));
                agregado = true;
            } else {
                nova.add(ic);
            }
        }
        if (!agregado) {
            // se carrinho vazio, aceite a moeda do produto
            Moeda m = (this.itens.isEmpty() ? produto.getPreco().getMoeda() : this.moeda);
            if (this.itens.isEmpty() && m != this.moeda) {
                // (só acontece se carrinho criado com moeda diferente do primeiro produto)
                // para simplificar, forçamos a moeda do carrinho ao construir vazio corretamente
                throw new IllegalStateException("Carrinho vazio deve ser criado com a mesma moeda do primeiro produto.");
            }
            nova.add(new ItemCarrinho(produto, quantidade));
        }
        return new Carrinho(nova, this.moeda, this.descontoPercent);
    }

    /** Retorna novo carrinho sem itens do produto informado (por ID). */
    public Carrinho removerPorProdutoId(String produtoId) {
        if (produtoId == null || produtoId.trim().isEmpty())
            throw new IllegalArgumentException("ProdutoId inválido.");
        List<ItemCarrinho> filtrada = this.itens.stream()
                .filter(ic -> !ic.getProduto().getId().equals(produtoId))
                .collect(Collectors.toList());
        return new Carrinho(filtrada, this.moeda, this.descontoPercent);
    }

    /**
     * Aplica cupom (percentual em [0, 30]) e retorna novo carrinho.
     * Ex.: 0.10 = 10%, 0.25 = 25%.
     */
    public Carrinho aplicarCupom(BigDecimal percentual) {
        if (percentual == null) throw new IllegalArgumentException("Percentual não pode ser nulo.");
        if (percentual.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Percentual negativo.");
        BigDecimal limitado = percentual.min(MAX_CUPOM); // cap a 30%
        return new Carrinho(this.itens, this.moeda, limitado);
    }

    @Override
    public String toString() {
        return "Carrinho{itens=" + itens.size() + ", subtotal=" + getSubtotal() +
               ", desconto=" + getDescontoValor() + ", total=" + getTotal() + "}";
    }
}

