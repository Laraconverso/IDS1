package ingsoft.patrones.visitor;

import java.util.List;

public class ShapeCalculator {
  public static double getTotalArea(List<Shape> shapes) {
    double totalArea = 0;
    for (Shape shape : shapes) {
      totalArea += shape.getArea();
    }
    return totalArea;
  }
}
