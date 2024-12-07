public class Scissors implements Movement{

    @Override
    public Movement vs(Rock rock) {
        return rock;
    }

    @Override
    public Movement vs(Paper paper) {
        return this;
    }

    @Override
    public Movement vs(Scissors scissors) {
        return this;
    }
}
