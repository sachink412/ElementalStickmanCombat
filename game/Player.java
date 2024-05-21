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

    public int speed;
    public int health;
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