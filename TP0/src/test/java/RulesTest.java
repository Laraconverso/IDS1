import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RulesTest {
    private Rock rock = new Rock();
    private Paper paper = new Paper();
    private Scissors scissors = new Scissors();

    @Test
    public void rockBeatsScissors() {
        assertEquals(rock, rock.vs(scissors));
    }

    @Test
    public void rockLostWithPaper() {
        assertEquals(paper, rock.vs(paper));
    }

    @Test
    public void rockTie() {
        assertEquals(rock, rock.vs(new Rock()));
    }

    @Test
    public void scissorsBeatsPaper() {
        assertEquals(scissors, scissors.vs(paper));
    }

    @Test
    public void scissorsLostWithRock() {
        assertEquals(rock, scissors.vs(rock));
    }

    @Test
    public void scissorsTie() {
        assertEquals(scissors, scissors.vs(new Scissors()));
    }

    @Test
    public void paperBeatsRock() {
        assertEquals(paper, paper.vs(rock));
    }

    @Test
    public void paperLostWithScissors() {
        assertEquals(scissors, paper.vs(scissors));
    }

    @Test
    public void paperTie() {
        assertEquals(paper, paper.vs(new Paper()));
    }


}
