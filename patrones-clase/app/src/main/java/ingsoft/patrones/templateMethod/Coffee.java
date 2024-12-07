package ingsoft.patrones.templateMethod;

public class Coffee extends Beverage {
  @Override()
  public String prepare() {
    return boilWater() +
           brewCoffeeGrinds() +
           pourInCup() +
           addSugarAndMilk();
  }

  private String boilWater() {
    return "Boiling water\n";
  }

  private String brewCoffeeGrinds() {
    return "Brewing coffee grinds\n";
  }

  private String pourInCup() {
    return "Pouring into cup\n";
  }

  private String addSugarAndMilk() {
    return "Adding sugar and milk\n";
  }
}
