package ingsoft.patrones.decorator;

public class PizzaWithCheeseAndPeperoni extends Pizza {
    public String getDescription() {
        return "Pizza + Cheese + Peperoni";
    }
    
    double  getCost() {
        return 10 + 1.5 + 2;
    }
}
