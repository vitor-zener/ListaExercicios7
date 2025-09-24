import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Gerente("Ana", new BigDecimal("10000")));
        funcionarios.add(new Desenvolvedor("Bruno", new BigDecimal("8000")));
        funcionarios.add(new Desenvolvedor("Carla", new BigDecimal("6000")));
        funcionarios.add(new Gerente("Diego", new BigDecimal("15000")));

        for (Funcionario f : funcionarios) {
            System.out.println(
                f.getNome() + " - Salário: " + f.getSalario()
                + " - Bônus: " + f.calcularBonus()
            );
        }
    }
}

