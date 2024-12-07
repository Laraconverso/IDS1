package ingsoft.patrones.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
  private final Calculator calculator = new Calculator();

  @Test
  void testAddition() {
      assertEquals(5, calculator.calculate("add", 2, 3));
  }

  @Test
  void testSubtraction() {
      assertEquals(2, calculator.calculate("subtract", 5, 3));
  }

  @Test
  void testMultiplication() {
      assertEquals(15, calculator.calculate("multiply", 3, 5));
  }

  @Test
  void testDivision() {
      assertEquals(2, calculator.calculate("divide", 6, 3));
  }

  @Test
  void testDivisionByZero() {
      assertThrows(ArithmeticException.class, () -> calculator.calculate("divide", 6, 0));
  }

  @Test
  void testUnknownOperation() {
      assertThrows(IllegalArgumentException.class, () -> calculator.calculate("power", 2, 3));
  }
}
