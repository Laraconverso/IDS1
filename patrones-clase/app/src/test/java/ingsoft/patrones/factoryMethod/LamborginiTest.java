package ingsoft.patrones.factoryMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LamborginiTest {
    private final Lamborgini car = new Lamborgini();

    @Test
    public void testCalculateDistanceFor1Minute() {
        assertEquals(15, car.calculateDistance(3));
    }
}
