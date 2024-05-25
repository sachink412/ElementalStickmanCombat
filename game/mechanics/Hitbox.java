package game.mechanics;

import game.Game;
import game.GameObject;
import game.objectclasses.*;

import java.util.*;
import java.awt.geom.*;

public class Hitbox {
    public double x;
    public double y;
    public double width;
    public double height;
    public Game game;

    public Hitbox(double x, double y, double width, double height, Game game) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public ArrayList<Part> getParts() {
        Area hitbox = new Area(new Rectangle2D.Double(x, y, width, height));
        ArrayList<Part> parts = new ArrayList<Part>();
        
        for (GameObject part : game.workspace.getDescendants()) {
            Part partA = (Part) part;
            if (partA.shape.intersects(hitbox.getBounds2D())) {
                parts.add(partA);
            }
        }

        return parts;
    }
}
