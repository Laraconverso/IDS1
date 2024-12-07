package ingsoft.patrones.adapter;

public class AudioPlayer implements MediaPlayer {
  @Override
  public String play(String audioType, String fileName) {
    if (audioType.equals("mp3")) {
      return "Playing mp3 file. Name: " + fileName;
    } else {
      return "Invalid media. " + audioType + " format not supported";
    }
  }
}
