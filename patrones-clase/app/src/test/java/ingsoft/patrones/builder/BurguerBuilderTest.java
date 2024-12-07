package ingsoft.patrones.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BurguerBuilderTest {
    @Test
    public void testBurguerWithBreadMeetAndCheese() {
        BurguerPart burguer = new Bread(new Meet(new Cheese()));

        assertEquals("Bread+Cheese+Meet+Bread", burguer.getDescription());
        assertEquals(8, burguer.getCost());
    }
}
