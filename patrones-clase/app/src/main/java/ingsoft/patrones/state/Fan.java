package ingsoft.patrones.state;

public class Fan {
  private static final int OFF = 0;
  private static final int LOW = 1;
  private static final int MEDIUM = 2;
  private static final int HIGH = 3;

  private int state = OFF;

  public void turnUp() {
      if (state < HIGH) {
          state++;
      }
  }

  public void turnDown() {
      if (state > OFF) {
          state--;
      }
  }

  public String getState() {
      switch (state) {
          case OFF:
              return "Off";
          case LOW:
              return "Low";
          case MEDIUM:
              return "Medium";
          case HIGH:
              return "High";
          default:
              return "Unknown";
      }
  }
}