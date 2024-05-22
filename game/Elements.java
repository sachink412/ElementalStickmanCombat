package game;

import java.util.HashMap;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Elements {
    public HashMap<String, Image> attackImages = new HashMap<String, Image>();

    private final String ATTACK_IMAGES_DIRECTORY = "game/assets/attacks";

    public Elements() {
        load();
    }

    private void load() {
        try {
            Path directory = Paths.get(ATTACK_IMAGES_DIRECTORY);

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                for (Path file : stream) {
                    String fileName = file.getFileName().toString();
                    String attackName = fileName.substring(0, fileName.lastIndexOf('.'));
                    Image image = ImageIO.read(file.toFile());
                    attackImages.put(attackName, image);
                }
            } catch (IOException e) {
                System.out.println("Error loading attack images: " + e);
            }
        } catch (Exception e) {
            System.out.println("Error loading attack images: " + e);
        }
    }
}