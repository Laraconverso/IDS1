package ingsoft.patrones.proxy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InternetServiceTest {
  @Test
  public void testConnectTo() {
    InternetService internetService = new RealInternetService();
    String website = "example.com";
    assertEquals("Connecting to " + website, internetService.connectTo(website));
  }

  @Test
  public void testProxyConnectsToBannedSite() {
    // TODO: Complete this test and use the assertion below
    // assertEquals(
    //   "Access denied: Connection to " + website + " is not allowed",
    //   internetService.connectTo(website)
    // );
  }

  @Test
  public void testProxyConnectsToAllowedSite() {
    // TODO: Complete this test and use the assertion below
    // assertEquals("Connecting to " + website, internetService.connectTo(website));
  }
}
