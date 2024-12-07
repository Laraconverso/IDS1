package ingsoft.patrones.templateMethod;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BeverageTest {
  
  @Test
  void testPrepareCoffee() {
    Beverage coffee = new Coffee();
    assertEquals(
        "Boiling water\n" +
        "Brewing coffee grinds\n" +
        "Pouring into cup\n" +
        "Adding sugar and milk\n", 
        coffee.prepare()
    );
  }

  @Test
  void testPrepareTea() {
    Beverage tea = new Tea();
    assertEquals(
        "Boiling water\n" +
        "Steeping the tea\n" +
        "Pouring into cup\n" +
        "Adding lemon\n",
        tea.prepare()
    );
  }
  
  // @Test
  // void testPrepareHotChocolate() {
  //   Beverage hotChocolate = new HotChocolate();
  //   assertEquals(
  //       "Boiling water\n" +
  //       "Steeping the tea\n" +
  //       "Pouring into cup\n" +
  //       "Adding lemon\n",
  //       hotChocolate.prepare()
  //   );
  // }
}
