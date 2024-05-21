package game;

import game.objectclasses.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.*;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Player {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    // Stores a collection of renderable parts representing the player.
    public Model playerModel = new Model();

    public int x;
    public int y;
    public int speed;
    public int health;
    public Color team;

    private Workspace workspace;

    private final String SPRITE_SHEET_PATH = "game/assets/images/sprites.png";
    private Image[] sprites;
    private int currentSpriteIndex = 0;
    private BufferedImage spriteSheet;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, Color team, Workspace workspace) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.team = team;
        this.workspace = workspace;

        this.x = 0;
        this.y = 0;
        this.health = 100;
        this.speed = 5;
    }

    private void loadSprites() {
        try {
            File spriteSheetFile = new File(SPRITE_SHEET_PATH);
            if (!spriteSheetFile.exists()) {
                throw new FileNotFoundException("Sprite sheet not found at " + SPRITE_SHEET_PATH);
            } else {
                spriteSheet = ImageIO.read(spriteSheetFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // if (keyHandler.up) {
        // y -= speed;
        // }
        // if (keyHandler.down) {
        // y += speed;
        // }
        // if (keyHandler.left) {
        // x -= speed;
        // }
        // if (keyHandler.right) {
        // x += speed;
        // }
    }

    public void draw(Graphics2D g2D) {
        // g2D.drawImage(sprites[currentSpriteIndex], x, y, null);
    }
}