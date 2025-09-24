import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository<T extends Identificavel<ID>, ID> implements IRepository<T, ID> {

    private final Map<ID, T> store;

    public InMemoryRepository() {
        // ConcurrentHashMap dá segurança básica caso você use em múltiplas threads
        this.store = new ConcurrentHashMap<>();
    }

    @Override
    public T salvar(T entidade) {
        Objects.requireNonNull(entidade, "Entidade não pode ser nula.");
        ID id = Objects.requireNonNull(entidade.getId(), "ID não pode ser nulo.");
        store.put(id, entidade);
        return entidade;
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        Objects.requireNonNull(id, "ID não pode ser nulo.");
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<T> listarTodos() {
        // cópia + unmodifiable para garantir imutabilidade da lista retornada
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }

    @Override
    public void remover(ID id) throws EntidadeNaoEncontradaException {
        Objects.requireNonNull(id, "ID não pode ser nulo.");
        T removido = store.remove(id);
        if (removido == null) {
            throw new EntidadeNaoEncontradaException("Entidade com ID '" + id + "' não encontrada para remoção.");
        }
    }
}

