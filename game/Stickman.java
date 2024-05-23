package game;

import game.mechanics.Vector2D;
import game.mechanics.Debris;
import game.mechanics.LaMeanEngine.CollisionManager;
import game.objectclasses.*;
import game.sound.SoundUtility;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.lang.Thread;

public class Stickman {
    public Element element;
    public String name;
    public Color color;
    public Model model;
    public int health = 100;
    public KeyInfo keys;
    private final String SPRITE_SHEET_PATH = "game/assets/images/sprites.png";
    private BufferedImage spriteSheet;
    private BufferedImage[][] spriteArray;
    private HashMap<String, BufferedImage[]> spriteMap = new HashMap<String, BufferedImage[]>();
    private List<BufferedImage> spriteList = new ArrayList<BufferedImage>();
    private BufferedImage currentSprite;
    public Part hrp;
    private boolean moving = false;
    private boolean attacking = false;
    public BodyVelocity rightVelocity;
    public BodyVelocity leftVelocity;
    public Game game;
    private double jumpCD = 0;
    private double punchCD = 0;
    public double speed = 1;
    private final SoundUtility SOUND = new SoundUtility();
    public boolean iterating = false;
    public boolean lookingRight = true;

    public Stickman(Game game, Element element, String name, Color color, KeyInfo keyInfo) {
        this.element = element;
        this.name = name;
        this.color = color;
        this.model = new Model();
        this.keys = keyInfo;

        try {
            try {
                rightVelocity = (BodyVelocity) Instance.create("BodyVelocity");
                leftVelocity = (BodyVelocity) Instance.create("BodyVelocity");
                rightVelocity.velocity = new Vector2D(10 * speed, 0);
                leftVelocity.velocity = new Vector2D(-10 * speed, 0);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Part hrp = (Part) Instance.create("Part", game.workspace);
            hrp.size = new Vector2D(83, 100);
            hrp.position = new Vector2D(400, 100);
            hrp.setColor("Red");
            hrp.name = "HumanoidRootPart";
            hrp.partType = "Rectangle";
            hrp.opacity = 0;
            hrp.stickConnection = this;
            this.hrp = hrp;
            this.game = game;
            loadSprites();
            playAnimation("idle", 1000);
        } catch (Exception e) {
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
        Iterator it = priorityMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Object[]> entry = (Map.Entry<Integer, Object[]>) it.next();
            Object[] animation = entry.getValue();
            String animName = (String) animation[4];
            if (animName == animationName && animationName != "idle") {
                return;
            }
        }
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
        currentSprite = animation[0];
    }

    public synchronized void stopAnimation(String animationName) {
        Iterator it = priorityMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Object[]> entry = (Map.Entry<Integer, Object[]>) it.next();
            Object[] animation = entry.getValue();
            String animName = (String) animation[4];
            if (animName == animationName) {
                if (!iterating) {
                    it.remove();
                }
            }
        }
    }

    public BufferedImage flipHorizontally(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }

    public synchronized void update() {

        if (keys.q) {
            try {
                SOUND.play("punch");
                skillTrigger("punch");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (keys.one) {
            try {
                if (element == Element.FIRE) {
                    skillTrigger("Fireball");

                } else if (element == Element.WATER) {
                    skillTrigger("Waterball");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (keys.two) {
            try {
                if (element == Element.FIRE) {
                    skillTrigger("Fire Wave");

                } else if (element == Element.WATER) {
                    skillTrigger("Tsunami");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (keys.up) {
            CollisionManager cm = game.gamePanel.engine.collisionManager;
            HashMap<Part, HashSet<Part>> collisionMap = cm.collisionMap;
            if (collisionMap.containsKey(hrp)) {
                HashSet<Part> collidingParts = collisionMap.get(hrp);
                for (Part part : collidingParts) {
                    if (part.name == "Ground") {
                        if (jumpCD <= 0) {
                            hrp.velocity = hrp.velocity.sub(new Vector2D(0, 13));
                            jumpCD = .2;
                        }
                    }
                }
            }
        }

        if (jumpCD > 0) {
            jumpCD -= 1 / 60.0;
        }

        if (punchCD > 0) {
            punchCD -= 1 / 60.0;
        }

        if (fireballCD > 0) {
            fireballCD -= 1 / 60.0;
        }

        if (waterballCD > 0) {
            waterballCD -= 1 / 60.0;
        }
        if (firewaveCD > 0) {
            firewaveCD -= 1 / 60.0;
        }
        if (tsunamiCD > 0) {
            tsunamiCD -= 1 / 60.0;
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
        Iterator it = priorityMap.entrySet().iterator();
        iterating = true;
        while (it.hasNext()) {

            Map.Entry<Integer, Object[]> entry = (Map.Entry<Integer, Object[]>) it.next();
            Object[] animation = entry.getValue();
            BufferedImage[] frames = (BufferedImage[]) animation[0];
            double length = (double) animation[1];
            int currentFrame = (int) animation[2];
            double frameRate = (double) animation[3];
            String animationName = (String) animation[4];
            if ((currentFrame >= frames.length) && (frames.length < 1000)) {
                if (animationName == "attacks") {
                    it.remove();
                } else {
                    if (animationName == "motion") {
                        it.remove();
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
        iterating = false;
        if (priorityMap.firstEntry().getKey() == 2) {
            currentSprite = spriteMap.get("idle")[0];
        }
    }

    public double fireballCD = 0;
    public double waterballCD = 0;
    public double firewaveCD = 0;
    public double tsunamiCD = 0;

    public void skillTrigger(String skillName) throws Exception {
        if (skillName == "punch" && punchCD <= 0) {
            punchCD = .3;
            playAnimation("attacks", 1000);
            Part hitBox = (Part) Instance.create("Part", game.workspace);
            hitBox.size = new Vector2D(75, 75);
            hitBox.position = new Vector2D(hrp.position.x, hrp.position.y);
            hitBox.setColor("Red");
            hitBox.name = "HitBox";
            hitBox.partType = "Rectangle";
            hitBox.opacity = 0;
            hitBox.anchored = true;
            hitBox.stickConnection = this;
            SOUND.stop("punch");
            Debris.addDebris(hitBox, .1);
        }

        if (skillName == "Fireball" && fireballCD <= 0) {
            Part fireball = (Part) Instance.create("Part", game.workspace);
            playAnimation("attacks", 1000);
            fireballCD = 7;
            fireball.size = new Vector2D(100, 100);
            fireball.position = new Vector2D(hrp.position.x, hrp.position.y);
            fireball.setColor("Orange");
            fireball.name = "Fireball";
            fireball.partType = "Ellipse";
            fireball.opacity = 0.5;
            fireball.stickConnection = this;
            Debris.addDebris(fireball, 2);
            BodyVelocity fireballVelocity = (BodyVelocity) Instance.create("BodyVelocity", fireball);
            fireballVelocity.velocity = new Vector2D(lookingRight ? 10 : -10, 0);
            fireballVelocity.restrictY = true;
        }
        if (skillName == "Waterball" && waterballCD <= 0) {
            waterballCD = 4;
            Part waterball = (Part) Instance.create("Part", game.workspace);
            playAnimation("attacks", 1000);
            waterball.size = new Vector2D(50, 50);
            waterball.position = new Vector2D(hrp.position.x, hrp.position.y);
            waterball.setColor("Blue");
            waterball.name = "Waterball";
            waterball.partType = "Ellipse";
            waterball.opacity = 0.5;
            waterball.stickConnection = this;
            Debris.addDebris(waterball, 2);
            BodyVelocity waterballVelocity = (BodyVelocity) Instance.create("BodyVelocity", waterball);
            waterballVelocity.velocity = new Vector2D(lookingRight ? 15 : -15, 0);
            waterballVelocity.restrictY = true;
        }

        if (skillName == "Fire Wave" && firewaveCD <= 0) {
            firewaveCD = 12;
            playAnimation("attacks", 1000);
            Part fireWave = (Part) Instance.create("Part", game.workspace);
            fireWave.size = new Vector2D(3000, 85);
            fireWave.position = new Vector2D(lookingRight ? hrp.position.x : hrp.position.x - 3000, hrp.position.y);
            fireWave.name = "fireWave";
            fireWave.setColor("Orange");
            fireWave.stickConnection = this;
            Debris.addDebris(fireWave, 1.5);
            fireWave.anchored = true;
            new Thread(() -> {
                try {
                    for (int i = 0; i < 85; i++) {
                        fireWave.size.sub(new Vector2D(0, 1));
                        fireWave.position.add(new Vector2D(0, 0.5));
                        fireWave.opacity -= 1 / 85.0;
                        Thread.sleep(15);
                        if (i % 3 == 0) {
                            fireWave.hitSave = new HashSet<>();
                        }
                        {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        if (skillName == "Tsunami" && tsunamiCD <= 0) {
            tsunamiCD = 15;
            new Thread(() -> {
                for (int i = 0; i <= 2; i++) {
                    try {
                        playAnimation("attacks", 1000);
                        Part tsunami = (Part) Instance.create("Part", game.workspace);
                        tsunami.partType = "Triangle";
                        tsunami.size = new Vector2D(300, 400);
                        tsunami.name = "Tsunami";
                        tsunami.position = new Vector2D(this.hrp.position.x, this.hrp.position.y);
                        tsunami.stickConnection = this;
                        BodyVelocity bodyVel = (BodyVelocity) Instance.create("BodyVelocity", tsunami);
                        bodyVel.velocity = new Vector2D(lookingRight ? 20 : -20, 0);
                        bodyVel.restrictY = true;
                        tsunami.setColor("Blue");
                        Part tsunamiFoam = (Part) Instance.create("Part", tsunami);
                        tsunamiFoam.size = new Vector2D(140, 5);
                        tsunamiFoam.setColor("White");
                        tsunamiFoam.canTouch = false;
                        RigidJoint newJoint = (RigidJoint) Instance.create("RigidJoint", tsunami);
                        newJoint.part0 = tsunami;
                        newJoint.part1 = tsunamiFoam;
                        newJoint.C0 = new TFrame(new Vector2D(75, 10), 0);
                        Debris.addDebris(tsunami, 2);
                        Thread.sleep(300);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static BufferedImage dye(BufferedImage image, Color color) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dyed.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(color);
        g.fillRect(0, 0, w, h);
        g.dispose();
        return dyed;
    }

    public void draw(Graphics2D g) {
        BufferedImage pasteSprite = currentSprite;
        if (keys.leftRight) {
            lookingRight = true;
        } else {
            pasteSprite = flipHorizontally(currentSprite);
            lookingRight = false;
        }
        if (this.color != Color.BLACK) {
            pasteSprite = dye(pasteSprite, color);
        }
        g.drawImage(pasteSprite, (int) hrp.position.x, (int) hrp.position.y + 5, null);
    }
}