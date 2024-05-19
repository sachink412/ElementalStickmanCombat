package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileReader;
import java.util.*;

import game.objectclasses.*;

public class Player {
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

    public void draw(Graphics2D g2D) {

    }
}
