import java.math.BigDecimal;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // Repositório de Produto (ID = String)
        InMemoryRepository<Produto, String> repoProdutos = new InMemoryRepository<>();
        repoProdutos.salvar(new Produto("P1", "Teclado", new BigDecimal("120.00")));
        repoProdutos.salvar(new Produto("P2", "Mouse",   new BigDecimal("80.00")));

        System.out.println("Produtos (listarTodos): " + repoProdutos.listarTodos());
        Optional<Produto> p1 = repoProdutos.buscarPorId("P1");
        System.out.println("Buscar P1: " + p1.orElse(null));

        // remoção existente
        repoProdutos.remover("P2");
        System.out.println("Após remover P2: " + repoProdutos.listarTodos());

        // tentativa de remover inexistente
        try {
            repoProdutos.remover("P3"); // não existe
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("Esperado ao remover inexistente (Produto): " + e.getMessage());
        }

        // Repositório de Funcionario (ID = Long)
        InMemoryRepository<Funcionario, Long> repoFunc = new InMemoryRepository<>();
        repoFunc.salvar(new Funcionario(1L, "Ana",   new BigDecimal("10000")));
        repoFunc.salvar(new Funcionario(2L, "Bruno", new BigDecimal("8000")));

        System.out.println("Funcionários (listarTodos): " + repoFunc.listarTodos());
        System.out.println("Buscar ID=1: " + repoFunc.buscarPorId(1L).orElse(null));

        // remoção existente
        repoFunc.remover(2L);
        System.out.println("Após remover 2L: " + repoFunc.listarTodos());

        // tentativa de remover inexistente
        try {
            repoFunc.remover(999L);
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("Esperado ao remover inexistente (Funcionario): " + e.getMessage());
        }

        // Teste de imutabilidade da lista retornada
        var listaImutavel = repoProdutos.listarTodos();
        System.out.println("Lista imutável (ok para ler): " + listaImutavel);
        try {
            listaImutavel.add(new Produto("P3", "Headset", new BigDecimal("250.00"))); // deve falhar
        } catch (UnsupportedOperationException e) {
            System.out.println("Imutabilidade confirmada em listarTodos(): " + e.getClass().getSimpleName());
        }
    }
}

