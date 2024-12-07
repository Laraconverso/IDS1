package ingsoft.patrones.builder;

public class Cheese implements BurguerPart {

    public String getDescription() {
        return "Cheese";
    }
    
    public double  getCost() {
        return 2;
    }
}
