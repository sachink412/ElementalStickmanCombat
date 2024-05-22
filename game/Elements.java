package game;

import java.util.HashMap;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class Elements {
    private final String ATTACK_IMAGES_DIRECTORY = "game/assets/attacks";
    public HashMap<String, Image> attackAndImages = new HashMap<String, Image>();
    public HashMap<Element, Image[]> elementAndAttacks = new HashMap<Element, Image[]>();

    public Elements() {
        load();
    }

    private void load() {
        // Load the thumbnails for each attack
        try {
            File directory = new File(ATTACK_IMAGES_DIRECTORY);
            File[] files = directory.listFiles();
            for (File file : files) {
                Image image = ImageIO.read(file);
                String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
                attackAndImages.put(fileName, image);
            }
        } catch (IOException e) {
            System.err.println("Error loading attack images: " + e.getMessage());
        }

        elementAndAttacks.put(Element.FIRE,
                new Image[] { attackAndImages.get("fireball"), attackAndImages.get("inferno") });
        elementAndAttacks.put(Element.WATER, new Image[] { attackAndImages.get("waterball"),
                attackAndImages.get("tsunami") });
    }
}