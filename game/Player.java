package game;

import game.objectclasses.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

import com.google.gson.Gson;

public class Player {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    Color team = Color.WHITE;

    public int x;
    public int y;
    public int speed;

    HashMap<String, Part> parts = new HashMap<String, Part>();
    // private final String RIG_PATH = "info/stickman_rig.json";

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

    public void draw(Graphics2D g2D) {
        // Draw the head
        g2D.drawOval(x, y, 20, 20);
        // Draw the body
        g2D.drawLine(x + 10, y + 20, x + 10, y + 50);
        // Draw the arms
        g2D.drawLine(x - 10, y + 30, x + 30, y + 30);
        // Draw the legs
        g2D.drawLine(x + 10, y + 50, x - 10, y + 70);
        g2D.drawLine(x + 10, y + 50, x + 30, y + 70);
    }
}
