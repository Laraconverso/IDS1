package ingsoft.patrones.adapter;

public class OggPlayer implements AdvancedMediaPlayer {
  @Override
  public String playWav(String fileName) {
    return "Invalid media. " + " format not supported";
  }

  @Override
  public String playOgg(String fileName) {
    return "Playing ogg file. Name: " + fileName;
  }
}
