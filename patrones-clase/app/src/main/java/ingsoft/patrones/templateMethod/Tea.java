package ingsoft.patrones.templateMethod;

public class Tea extends Beverage {
  @Override()
  public String prepare() {
    return boilWater() +
           steepTeaBag() +
           pourInCup() +
           addLemon();
  }

  private String boilWater() {
    return "Boiling water\n";
  }

  private String steepTeaBag() {
    return "Steeping the tea\n";
  }

  private String pourInCup() {
    return "Pouring into cup\n";
  }

  private String addLemon() {
    return "Adding lemon\n";
  }
}
