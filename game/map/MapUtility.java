package game.map;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class MapUtility {
    private Image backgroundImage;
    private Image[] maps;
    private int currentMapIndex = 0; // Index of the currently rendered map

    // Relative path to the directory containing the map files
    private final String MAPS_DIRECTORY = "mapfiles";

    public MapUtility() {
        load();
    }

    public void load() {
        // Load the background images
        Path backgroundPath = Paths.get(MAPS_DIRECTORY);
        List<Image> mapImages = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(backgroundPath)) {
            for (Path file : stream) {
                mapImages.add(ImageIO.read(file.toFile()));
                System.out.println("Loaded map: " + file.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unload() {

    }

    public void update() {

    }

    public void render() {
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }
}