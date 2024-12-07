package ingsoft.patrones.adapter;

public class WavPlayer implements AdvancedMediaPlayer {
  @Override
  public String playWav(String fileName) {
    return "Playing wav file. Name: " + fileName;
  }

  @Override
  public String playOgg(String fileName) {
    return "Invalid media. " + " format not supported";
  }
}
