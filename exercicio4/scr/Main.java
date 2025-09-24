import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<IMeioTransporte> meios = new ArrayList<>();
        meios.add(new Carro());
        meios.add(new Bicicleta());
        meios.add(new Trem());

        for (IMeioTransporte m : meios) {
            System.out.println("=== " + m + " ===");
            try {
                m.acelerar(20);
                m.acelerar(15);
                System.out.println("Velocidade após acelerar: " + m.getVelocidade() + " km/h");

                m.frear(10);
                System.out.println("Velocidade após frear: " + m.getVelocidade() + " km/h");

                // Tentativa de erro para demonstrar exceção
                m.frear(1000);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
            System.out.println();
        }
    }
}
