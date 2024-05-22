package game;

import game.mechanics.Vector2D;
import game.objectclasses.*;

import java.awt.Color;

public class Bot {
    GamePanel gamePanel;
    KeyInfo keyHandler;

    public int speed;
    public int health;
    public Color team;
    public Stickman stickman;
    public double runCD = 0;
    public double attackCD = 0;

    public Bot(GamePanel gamePanel, KeyInfo keyHandler, Color team, Workspace workspace) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.team = team;
        Element[] elements = Element.values();
        Element randomElement = elements[(int) (Math.random() * elements.length)];
        this.stickman = new Stickman(gamePanel.game, randomElement, "Bot", team.toString(), keyHandler);
        keyHandler.leftRight = false;
        this.stickman.hrp.position = new Vector2D(Game.WINDOW_WIDTH - 400, 100);
        this.stickman.speed = .5;
    }

    public void update() {
        runCD -= 1 / 60.0;
        this.stickman.update();
        trackPlayer();
    }

    public void trackPlayer() {
        Player player = gamePanel.player;
        if (player.stickman.hrp.position.x < this.stickman.hrp.position.x) {
            keyHandler.leftRight = false;
        } else {
            keyHandler.leftRight = true;
        }

        if (player.stickman.hrp.position.y < this.stickman.hrp.position.y) {
            keyHandler.up = true;
        } else {
            keyHandler.up = false;
        }

        if (player.stickman.hrp.position.distanceFrom(this.stickman.hrp.position) < 60) {
            keyHandler.q = true;
            runCD = .5;
        } else {
            keyHandler.q = false;
        }
        if (player.stickman.hrp.position.distanceFrom(this.stickman.hrp.position) < 290) {
            keyHandler.one = true;
        } else {
            keyHandler.one = false;
        }
        if (player.stickman.hrp.position.distanceFrom(this.stickman.hrp.position) < 600) {
            keyHandler.two = true;
        } else {
            keyHandler.two = false;
        }
        if (player.stickman.hrp.position.x < this.stickman.hrp.position.x && (runCD <= 0)) {
            keyHandler.left = true;
        } else {
            keyHandler.left = false;
        }
        if (player.stickman.hrp.position.x > this.stickman.hrp.position.x && (runCD <= 0)) {
            keyHandler.right = true;
        } else {
            keyHandler.right = false;
        }
    }
}