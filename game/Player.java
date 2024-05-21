package game;

import game.objectclasses.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Player {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    Color team = Color.WHITE;

    // Stores a collection of renderable parts representing the player.
    public Model playerModel = new Model();

    public int x = 0;
    public int y = Game.HEIGHT;
    public int speed = 1;

    // private final String RIG_PATH = "info/stickman_rig.json";

    private Workspace workspace;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, Color team, Workspace workspace) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.team = team;
        this.workspace = workspace;

        try {
            createPlayer(workspace);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void createPlayer(Workspace workspace)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Part head = (Part) Instance.create("Part", this.workspace);
        head.partType = "Ellipse";
        head.position = new Vector2D(0, 0);
        head.size = new Vector2D(10, 10);
        head.color = team;

        Part body = (Part) Instance.create("Part", this.workspace);
        body.partType = "Rectangle";
        body.position = new Vector2D(0, 0);
        body.size = new Vector2D(10, 10);
        body.color = team;

        Part leftArm = (Part) Instance.create("Part", this.workspace);
        leftArm.partType = "Rectangle";
        leftArm.position = new Vector2D(0, 0);
        leftArm.size = new Vector2D(10, 10);
        leftArm.color = team;

        Part rightArm = (Part) Instance.create("Part", this.workspace);
        rightArm.partType = "Rectangle";
        rightArm.position = new Vector2D(0, 0);
        rightArm.size = new Vector2D(10, 10);
        rightArm.color = team;

        Part leftLeg = (Part) Instance.create("Part", this.workspace);
        leftLeg.partType = "Rectangle";
        leftLeg.position = new Vector2D(0, 0);
        leftLeg.size = new Vector2D(10, 10);
        leftLeg.color = team;

        Part rightLeg = (Part) Instance.create("Part", this.workspace);
        rightLeg.partType = "Rectangle";
        rightLeg.position = new Vector2D(0, 0);
        rightLeg.size = new Vector2D(10, 10);
        rightLeg.color = team;

        playerModel.add(head);
        playerModel.add(body);
        playerModel.add(leftArm);
        playerModel.add(rightArm);
        playerModel.add(leftLeg);
        playerModel.add(rightLeg);
    }

    public void parseRigFile(String path) {
        Gson gson = new Gson();
        try {
            JsonObject jsonObject = gson.fromJson(new FileReader(path), JsonObject.class);
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                JsonObject partObject = entry.getValue().getAsJsonObject();
                System.out.println(partObject.toString());
                String partType = partObject.get("partType").getAsString();
                JsonArray sizeArray = partObject.get("size").getAsJsonArray();
                int sizeX = sizeArray.get(0).getAsInt();
                int sizeY = sizeArray.get(1).getAsInt();
                Part part = new Part(partType, null);
                part.position = new Vector2D(0, 0);
                part.size = new Vector2D(sizeX, sizeY);
                part.color = team;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        g2D.setColor(team);
        playerModel.draw(g2D);
    }
} 