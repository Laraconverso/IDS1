package ingsoft.patrones.factoryMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FerrariTest {
    private final Ferrari car = new Ferrari();

    @Test
    public void testCalculateDistanceFor1Minute() {
        assertEquals(30, car.calculateDistance(3));
    }
}
