package game;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileReader;
import java.util.*;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    Color team = Color.WHITE;

    public int x;
    public int y;
    public int speed;

    HashMap<String, Part> parts = new HashMap<String, Part>();
    private final String RIG_PATH = "info/stickman_rig.json";

    public Player(GamePanel gamePanel, KeyHandler keyHandler, Color team) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.team = team;

        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 0;
        y = Game.HEIGHT;
        speed = 10;
    }

    public void update() {
        if (keyHandler.up) {
            y -= speed;
        }
        if (keyHandler.down) {
            y += speed;
        }
        if (keyHandler.left) {
            x -= speed;
        }
        if (keyHandler.right) {
            x += speed;
        }
    }

    public void draw(Graphics g2D) {

        try {
            // TODO: Implement JSON parsing for the parts
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Define the stickman's body parts
        Part humanoidRootPart = new Part();
        humanoidRootPart.size = new Vector2D(5, 5);
        humanoidRootPart.partType = "Oval";
        humanoidRootPart.color = "#FFFFFF";
        humanoidRootPart.position = new Vector2D(x, y);

        Part upperTorso = new Part();
        upperTorso.size = new Vector2D(4, 6);
        upperTorso.partType = "Rectangle";
        upperTorso.color = "#FFFFFF";
        upperTorso.position = new Vector2D(x, y - 10);

        Part lowerTorso = new Part();
        lowerTorso.size = new Vector2D(4, 6);
        lowerTorso.partType = "Rectangle";
        lowerTorso.color = "#FFFFFF";
        lowerTorso.position = new Vector2D(x, y - 20);

        Part head = new Part();
        head.size = new Vector2D(5, 5);
        head.partType = "Oval";
        head.color = "#FFFFFF";
        head.position = new Vector2D(x, y - 30);

        Part upperRightArm = new Part();
        upperRightArm.size = new Vector2D(2, 6);
        upperRightArm.partType = "Rectangle";
        upperRightArm.color = "#FFFFFF";
        upperRightArm.position = new Vector2D(x + 5, y - 15);

        Part upperLeftArm = new Part();
        upperLeftArm.size = new Vector2D(2, 6);
        upperLeftArm.partType = "Rectangle";
        upperLeftArm.color = "#FFFFFF";
        upperLeftArm.position = new Vector2D(x - 5, y - 15);

        Part lowerRightArm = new Part();
        lowerRightArm.size = new Vector2D(1, 5);
        lowerRightArm.partType = "Rectangle";
        lowerRightArm.color = "#FFFFFF";
        lowerRightArm.position = new Vector2D(x + 5, y - 25);

        Part lowerLeftArm = new Part();
        lowerLeftArm.size = new Vector2D(1, 5);
        lowerLeftArm.partType = "Rectangle";
        lowerLeftArm.color = "#FFFFFF";
        lowerLeftArm.position = new Vector2D(x - 5, y - 25);

        Part upperRightLeg = new Part();
        upperRightLeg.size = new Vector2D(2, 7);
        upperRightLeg.partType = "Rectangle";
        upperRightLeg.color = "#FFFFFF";
        upperRightLeg.position = new Vector2D(x + 2, y - 35);

        Part upperLeftLeg = new Part();
        upperLeftLeg.size = new Vector2D(2, 7);
        upperLeftLeg.partType = "Rectangle";
        upperLeftLeg.color = "#FFFFFF";
        upperLeftLeg.position = new Vector2D(x - 2, y - 35);

        Part lowerRightLeg = new Part();
        lowerRightLeg.size = new Vector2D(2, 6);
        lowerRightLeg.partType = "Rectangle";
        lowerRightLeg.color = "#FFFFFF";
        lowerRightLeg.position = new Vector2D(x + 2, y - 45);

        Part lowerLeftLeg = new Part();
        lowerLeftLeg.size = new Vector2D(2, 6);
        lowerLeftLeg.partType = "Rectangle";
        lowerLeftLeg.color = "#FFFFFF";
        lowerLeftLeg.position = new Vector2D(x - 2, y - 45);

        // Draw the stickman's body parts
        humanoidRootPart.draw(g2D);
        upperTorso.draw(g2D);
        lowerTorso.draw(g2D);
        head.draw(g2D);
        upperRightArm.draw(g2D);
        upperLeftArm.draw(g2D);
        lowerRightArm.draw(g2D);
        lowerLeftArm.draw(g2D);
        upperRightLeg.draw(g2D);
        upperLeftLeg.draw(g2D);
        lowerRightLeg.draw(g2D);
        lowerLeftLeg.draw(g2D);
    }
}
