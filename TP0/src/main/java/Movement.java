public interface Movement {
    Movement vs(Rock rock);
    Movement vs(Paper paper);
    Movement vs(Scissors scissors);
}
