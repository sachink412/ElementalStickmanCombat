package game.map;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.Game;

public class MapUtility {
    private Image backgroundImage;
    private Image[] maps;

    // Relative path to the directory containing the map files
    private final String MAPS_DIRECTORY = "game/map/maps";
    private GamePanel gamePanel;

    public MapUtility(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        load();
    }

    public void load() {
        try {
            Path directory = Paths.get(MAPS_DIRECTORY);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.{png,jpg,jpeg}")) {
                List<Image> images = new ArrayList<>();
                for (Path entry : stream) {
                    images.add(ImageIO.read(entry.toFile()));
                }
                maps = images.toArray(new Image[0]);
            } catch (IOException e) {
                System.err.println("Error loading map images: " + e.getMessage());
            }
        } catch (InvalidPathException e) {
            System.err.println("Invalid maps directory: " + e.getMessage());
        }

        if (maps.length > 0) {
            backgroundImage = maps[(int) (Math.random() * maps.length)];
        }
    }

    public void unload() {

    }

    public void update() {

    }

    public void render() {
    }

    public Image getBackgroundImage() {
        return backgroundImage.getScaledInstance(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT,
                Image.SCALE_SMOOTH);
    }
}