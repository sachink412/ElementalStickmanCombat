package game.map;

import java.awt.Graphics;
import java.awt.Image;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class RenderedMap extends Map {
    private Image background;
    private Image[] maps;
    private int currentMapIndex = 0; // Index of the currently rendered map

    public RenderedMap() {
        background = null;
        maps = null;
    }

    public void load() {
        Path mapFilesDirectory = Paths.get("mapfiles");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(mapFilesDirectory)) {
            List<Image> loadedMaps = new ArrayList<>();
            for (Path file : stream) {
                Image map = ImageIO.read(file.toFile());
                loadedMaps.add(map);
                System.out.println("Loaded map file: " + file.getFileName());
            }
            maps = loadedMaps.toArray(new Image[0]);
        } catch (Exception e) {
            System.out.println("Error loading map files: " + e.getMessage());
        }
    }

    // Render the specific map from the loaded maps using the mapIndex
    public void renderMap(int mapIndex) {
        if (maps != null && mapIndex >= 0 && mapIndex < maps.length) {
            currentMapIndex = mapIndex;
        } else {
            System.out.println("Map index out of bounds. Cannot render map.");
        }
    }

    public void update() {
        // Add any updates for the map here, such as animations or moving elements
    }

    public void render(Graphics g) {
        if (maps != null && maps.length > 0) {
            g.drawImage(maps[currentMapIndex], 0, 0, null); // Draw the current map onto the screen
        } else {
            System.out.println("No maps loaded to render.");
        }
    }

    // Added getter for currentMapIndex
    public int getCurrentMapIndex() {
        return currentMapIndex;
    }

    @Override
    public void unload() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unload'");
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    public Object getBackground() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBackground'");
    }
}