package ingsoft.patrones.state;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class FanTest {
    private Fan fan;

    @BeforeEach
    public void setUp() {
        fan = new Fan();
    }

    @Test
    public void testInitialState() {
        assertEquals("Off", fan.getState());
    }

    @Test
    public void testTurningUpFromOff() {
        fan.turnUp();
        assertEquals("Low", fan.getState());
    }

    @Test
    public void testTurningUpToMedium() {
        fan.turnUp();
        fan.turnUp();
        assertEquals("Medium", fan.getState());
    }

    @Test
    public void testTurningUpToHigh() {
        fan.turnUp();
        fan.turnUp();
        fan.turnUp();
        assertEquals("High", fan.getState());
    }

    @Test
    public void testTurningUpFromHigh() {
        fan.turnUp();
        fan.turnUp();
        fan.turnUp();
        fan.turnUp();
        assertEquals("High", fan.getState());
    }

    @Test
    public void testTurningDownFromHigh() {
        fan.turnUp();
        fan.turnUp();
        fan.turnUp();
        fan.turnDown();
        assertEquals("Medium", fan.getState());
    }

    @Test
    public void testTurningDownToLow() {
        fan.turnUp();
        fan.turnUp();
        fan.turnDown();
        assertEquals("Low", fan.getState());
    }

    @Test
    public void testTurningDownToOff() {
        fan.turnUp();
        fan.turnDown();
        assertEquals("Off", fan.getState());
    }

    @Test
    public void testTurningDownFromOff() {
        fan.turnDown();
        assertEquals("Off", fan.getState());
    }
}
