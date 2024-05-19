package game.objectclasses;

import game.GameObject;

import java.util.HashSet;
import java.util.Set;
import java.awt.Graphics2D;

public class Model extends GameObject {
    public String name;
    private Set<Part> parts = new HashSet<>();

    public Model() {
        super();
    }

    public Model(String name) {
        this.name = name;
    }

    public void add(Part part) {
        parts.add(part);
    }

    public Set<Part> getParts() {
        return parts;
    }

    @Override
    public void draw(Graphics2D g2D) {
        for (Part part : parts) {
            part.draw(g2D);
        }
    }
}