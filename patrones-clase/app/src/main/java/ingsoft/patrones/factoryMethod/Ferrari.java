package ingsoft.patrones.factoryMethod;

//TODO: Implementar el patrón Factory Method. NO modificar los tests.

public class Ferrari {
    public int calculateDistance(int timeAmount) {
        return new FerrariVelocityCalculator().getVelocity() * timeAmount;
    }
}
