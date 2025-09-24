public class Bicicleta implements IMeioTransporte {
    private double velocidade = 0;
    private static final double VELOCIDADE_MAX = 40;

    @Override
    public void acelerar(double incremento) {
        if (incremento <= 0) throw new IllegalArgumentException("Incremento deve ser positivo.");
        if (velocidade + incremento > VELOCIDADE_MAX)
            throw new IllegalStateException("Bicicleta não pode ultrapassar " + VELOCIDADE_MAX + " km/h.");
        velocidade += incremento;
    }

    @Override
    public void frear(double decremento) {
        if (decremento <= 0) throw new IllegalArgumentException("Decremento deve ser positivo.");
        if (velocidade - decremento < 0)
            throw new IllegalStateException("Velocidade não pode ficar negativa.");
        velocidade -= decremento;
    }

    @Override
    public double getVelocidade() {
        return velocidade;
    }

    @Override
    public String toString() {
        return "Bicicleta";
    }
}

