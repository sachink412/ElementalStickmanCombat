package game.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;

public class SoundUtility {
    private final String SOUNDS_DIRECTORY = "game/sound/sounds";

    public HashMap<String, Clip> audio = new HashMap<>();

    public SoundUtility() {
        load();
    }

    private void load() {
        try {
            Path directory = Paths.get(SOUNDS_DIRECTORY);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.wav")) {
                for (Path entry : stream) {
                    String fileName = entry.getFileName().toString();
                    String soundName = fileName.substring(0, fileName.lastIndexOf('.'));
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(entry.toFile());
                    clip.open(inputStream);
                    audio.put(soundName, clip);
                }
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                System.out.println("Error loading sound files: " + e.getMessage());
            }
        } catch (InvalidPathException e) {
            System.out.println("Invalid sounds directory: " + e.getMessage());
        }
    }

    public void play(String sound) {
        Clip clip = audio.get(sound);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void stop(String sound) {
        Clip clip = audio.get(sound);
        if (clip != null) {
            clip.stop();
        }
    }
}