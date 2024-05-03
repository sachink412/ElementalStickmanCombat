package game;

// CHANGED
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler kH;
    Color team = Color.WHITE;
    HashMap<String, Part> parts = new HashMap<String, Part>();

    public int x, y, speed;

    public Player(GamePanel gp, KeyHandler kH, Color team) {
        this.gp = gp;
        this.kH = kH;
        this.team = team;

        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 0;
        y = Game.HEIGHT;
        speed = 10;
    }

    public void update() {
        if (kH.up) {
            y -= speed;
        } else if (kH.down) {
            y += speed;
        } else if (kH.left) {
            x -= speed;
        } else if (kH.right) {
            x += speed;
        }
    }

    public void draw(Graphics g2D) {
        final int[] root = new int[] { x, y };
        final Part[] body = new Part[] { new Part() };
    }
}
