package game;

import game.objectclasses.*;

import java.awt.Color;

public class Player {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public int speed;
    public Color team;
    public Stickman stickman;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, Color team, Workspace workspace) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.team = team;
        this.stickman = new Stickman(gamePanel.game, Element.FIRE, "Player", team.toString(), keyHandler);
    }

    public void update() {
        this.stickman.update();
    }
}