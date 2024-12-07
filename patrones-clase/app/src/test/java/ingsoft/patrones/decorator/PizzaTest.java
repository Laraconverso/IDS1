package ingsoft.patrones.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PizzaTest {
    @Test
    public void testCreateSimplePizza() {
        Pizza pizza = new Pizza();

        assertEquals("Pizza", pizza.getDescription());
        assertEquals(10, pizza.getCost());
    }

    @Test
    public void testCreatePizzaWithCheese() {
        Pizza pizza = new PizzaWithCheese();

        assertEquals("Pizza + Cheese", pizza.getDescription());
        assertEquals(11.5, pizza.getCost());
    }

    @Test
    public void testCreatePizzaWithCheeseAndPeperoni() {
        Pizza pizza = new PizzaWithCheeseAndPeperoni();

        assertEquals("Pizza + Cheese + Peperoni", pizza.getDescription());
        assertEquals(13.5, pizza.getCost());
    }
}
