package ingsoft.patrones.decorator;

public class PizzaWithCheese extends Pizza {
    public String getDescription() {
        return "Pizza + Cheese";
    }
    
    double  getCost() {
        return 10 + 1.5;
    }
}
