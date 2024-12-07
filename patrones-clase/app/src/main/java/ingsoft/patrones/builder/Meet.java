package ingsoft.patrones.builder;

public class Meet implements BurguerPart {
    private BurguerPart part;

    public Meet(BurguerPart part) {
        this.part = part;
    }

    public String getDescription() {
        return this.part.getDescription() + "+Meet";
    }
    
    public double  getCost() {
        return this.part.getCost() + 5;
    }
}
