package ingsoft.patrones.factoryMethod;

//TODO: Implementar el patrón Factory Method. NO modificar los tests.

public class Lamborgini {
    public int calculateDistance(int timeAmount) {
        return new LamborginiVelocityCalculator().getVelocity() * timeAmount;
    }
}
