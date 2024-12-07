package ingsoft.patrones.visitor;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class ShapeTest {
  @Test
  public void testTotalArea() {
    List<Shape> shapes = Arrays.asList(
      new Circle(5),
      new Rectangle(4, 6),
      new Triangle(3, 4)
    );

    double expectedTotalArea = Math.PI * 5 * 5 + 4 * 6 + 0.5 * 3 * 4;
    double actualTotalArea = ShapeCalculator.getTotalArea(shapes);
    
    assertEquals(expectedTotalArea, actualTotalArea, 0.001);
  }
}
