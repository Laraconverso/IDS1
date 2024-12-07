package ingsoft.patrones.builder;

public class Bread implements BurguerPart {
    private BurguerPart part;

    public Bread(BurguerPart part) {
        this.part = part;
    }

    public String getDescription() {
        return "Bread+" + this.part.getDescription() + "+Bread";
    }
    
    public double  getCost() {
        return this.part.getCost() + 1;
    }
}
