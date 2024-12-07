public class Paper implements Movement{

    @Override
    public Movement vs(Rock rock) {
        return this;
    }

    @Override
    public Movement vs(Paper paper) {
        return this;
    }

    @Override
    public Movement vs(Scissors scissors) {
        return scissors;
    }
}
