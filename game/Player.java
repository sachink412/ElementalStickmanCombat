package game;

import game.objectclasses.*;

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

    public int x;
    public int y;
    public int speed;
    public int health;
    public Color team;

    private Workspace workspace;

    private final String SPRITE_SHEET_PATH = "game/assets/images/sprites.png";
    private BufferedImage spriteSheet;
    private BufferedImage[][] spriteArray;
    private HashMap<String, BufferedImage[]> spriteMap = new HashMap<String, BufferedImage[]>();
    private List<BufferedImage> spriteList = new ArrayList<BufferedImage>();
    private Image currentSprite;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, Color team, Workspace workspace) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.team = team;
        this.workspace = workspace;

        this.x = 0;
        this.y = 0;
        this.health = 100;
        this.speed = 5;

        loadSprites();
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

        // Extract the sprites from the sprite sheet.
        int spriteWidth = 83;
        int spriteHeight = 100;

        int spritesAcross = spriteSheet.getWidth() / spriteWidth;
        int spritesDown = spriteSheet.getHeight() / spriteHeight;

        spriteArray = new BufferedImage[spritesDown][spritesAcross];

        for (int i = 0; i < spritesDown; i++) {
            for (int j = 0; j < spritesAcross; j++) {
                spriteArray[i][j] = spriteSheet.getSubimage(j * spriteWidth, i * spriteHeight, spriteWidth,
                        spriteHeight);
            }
        }

        // Extract each row and put them in the hashmap.
        spriteMap.put("idle", spriteArray[0]);
        spriteMap.put("motion", spriteArray[1]);
        spriteMap.put("attacks", spriteArray[2]);
    }

    public void update() {
        if (keyHandler.up) {
            this.y -= speed;
        }
        if (keyHandler.down) {
            this.y += speed;
            currentSprite = spriteMap.get("motion")[1];
        }
        if (keyHandler.left) {
            x -= speed;
            currentSprite = spriteMap.get("motion")[2];
        }
        if (keyHandler.right) {
            x += speed;
            currentSprite = spriteMap.get("motion")[3];
        }

        // Add gravity:
        if (y < Game.WINDOW_HEIGHT - 100) {
            y += 1;
        }
    }

    public void draw(Graphics2D g2D) {
        g2D.drawImage(spriteMap.get("idle")[0], x, y, null);
    }
}