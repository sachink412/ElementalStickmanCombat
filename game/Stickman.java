package game;

import game.LaMeanEngine.CollisionManager;
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
import java.lang.Thread;

public class Stickman {
    public Element element;
    public String name;
    public String color;
    public Model model;
    public double health = 100;
    public KeyInfo keys;
    private final String SPRITE_SHEET_PATH = "game/assets/images/sprites.png";
    private BufferedImage spriteSheet;
    private BufferedImage[][] spriteArray;
    private HashMap<String, BufferedImage[]> spriteMap = new HashMap<String, BufferedImage[]>();
    private List<BufferedImage> spriteList = new ArrayList<BufferedImage>();
    private Image currentSprite;
    public Part hrp;
    private boolean moving = false;
    private boolean attacking = false;
    public BodyVelocity rightVelocity;
    public BodyVelocity leftVelocity;
    public Game game;
    private double jumpCD = 0;

    public Stickman(Game game, Element element, String name, String color, KeyInfo keyInfo) {
        this.element = element;
        this.name = name;
        this.color = color;
        this.model = new Model();
        this.keys = keyInfo;
        // head, humanoidrootpart, torso, leftarm, rightarm, leftleg, rightleg
        try {
            try {
                rightVelocity = (BodyVelocity) Instance.create("BodyVelocity");
                leftVelocity = (BodyVelocity) Instance.create("BodyVelocity");
                rightVelocity.velocity = new Vector2D(10, 0);
                leftVelocity.velocity = new Vector2D(-10, 0);
            } catch (Exception e) {
                System.out.println("Error creating BodyVelocity");
                e.printStackTrace();
                return;
            }
            Part hrp = (Part) Instance.create("Part", game.workspace);
            hrp.size = new Vector2D(83, 100);
            hrp.position = new Vector2D(200, 100);
            hrp.setColor("Red");
            hrp.name = "HumanoidRootPart";
            hrp.partType = "Rectangle";
            hrp.opacity = 0.5;
            hrp.stickConnection = this;
            this.hrp = hrp;
            loadSprites();
            playAnimation("idle", 1000);

        } catch (Exception e) {
            System.out.println("Error creating stickman");
        }

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
        spriteArray[0] = Arrays.copyOf(spriteArray[0], 1);
        spriteArray[1] = Arrays.copyOf(spriteArray[1], 8);
        spriteMap.put("idle", spriteArray[0]);
        spriteMap.put("motion", spriteArray[1]);
        spriteMap.put("attacks", spriteArray[2]);
    }

    public TreeMap<Integer, Object[]> priorityMap = new TreeMap<Integer, Object[]>();

    public void playAnimation(String animationName, double length) {
        stopAnimation(animationName);
        BufferedImage[] animation = spriteMap.get(animationName);
        int animPriority = 0;
        if (animationName.equals("attacks")) {
            animPriority = 0;
        } else if (animationName.equals("motion")) {
            animPriority = 1;
        } else {
            animPriority = 2;
        }
        priorityMap.put(animPriority, new Object[] { animation, length, 0, .05, animationName });
        System.out.println(animationName);
        System.out.println(length);
        System.out.println(animation.length);
        currentSprite = animation[0];
    }

    public synchronized void stopAnimation(String animationName) {
        for (Map.Entry<Integer, Object[]> entry : priorityMap.entrySet()) {
            Object[] animation = entry.getValue();
            String animName = (String) animation[4];
            if (animName == animationName) {
                priorityMap.remove(entry.getKey());
            }
        }
    }

    public void update() {
        if (keys.up) {
            CollisionManager cm = game.gamePanel.engine.collisionManager;
            HashMap<Part, HashSet<Part>> collisionMap = cm.collisionMap;
            if (collisionMap.containsKey(hrp)) {
                HashSet<Part> collidingParts = collisionMap.get(hrp);
                for (Part part : collidingParts) {
                    if (part.name == "Ground") {
                        if (jumpCD <= 0) {
                            hrp.velocity = hrp.velocity.sub(new Vector2D(0, 30));
                            jumpCD = 1;
                        }
                    }
                }
            }
        }

        if (jumpCD > 0) {
            jumpCD -= 0.01;
        }
        if (keys.right) {
            hrp.addChild(rightVelocity);
        } else {
            hrp.removeChild(rightVelocity);
        }

        if (keys.left) {
            hrp.addChild(leftVelocity);
        } else {
            hrp.removeChild(leftVelocity);
        }

        if (keys.right || keys.left) {
            playAnimation("motion", 1000);
        } else {
            stopAnimation("motion");
        }

        for (Map.Entry<Integer, Object[]> entry : priorityMap.entrySet()) {
            Object[] animation = entry.getValue();
            BufferedImage[] frames = (BufferedImage[]) animation[0];
            double length = (double) animation[1];
            int currentFrame = (int) animation[2];
            double frameRate = (double) animation[3];
            String animationName = (String) animation[4];
            if ((currentFrame >= frames.length) && (frames.length < 1000)) {
                if (animationName == "attacks") {
                    priorityMap.remove(0);
                } else {
                    if (animationName == "motion") {
                        priorityMap.remove(1);
                        playAnimation(animationName, length);
                    }
                }
            } else {
                Thread animUpdate = new Thread() {
                    public void run() {
                        currentSprite = frames[currentFrame];
                        animation[2] = currentFrame + 1;
                        animation[3] = frameRate;
                        try {
                            Thread.sleep((long) (frameRate));
                        } catch (InterruptedException e) {
                            System.out.println("Error sleeping thread");
                        }
                    }
                };
                animUpdate.start();
            }
        }

    }

    public void draw(Graphics2D g) {
        g.drawImage(currentSprite, (int) hrp.position.x, (int) hrp.position.y + 5, null);
    }
}
