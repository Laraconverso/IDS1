public class Rock implements Movement{


    @Override
    public Movement vs(Rock rock) {
        return this;
    }

    @Override
    public Movement vs(Paper paper) {
        return paper;
    }

    @Override
    public Movement vs(Scissors scissors) {
        return this;
    }
}
