package game;

import game.objectclasses.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

    public void parseRigFile(String path) {
        Gson gson = new Gson();
        try {
            JsonObject jsonObject = gson.fromJson(new FileReader(path), JsonObject.class);
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                JsonObject partObject = entry.getValue().getAsJsonObject();
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

    }
}
