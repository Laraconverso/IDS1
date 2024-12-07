package ingsoft.patrones.proxy;

public class RealInternetService implements InternetService {
  @Override
  public String connectTo(String url) {
    return "Connecting to " + url;
  }
}
