package game.objectclasses;

import game.GameObject;

import java.awt.Graphics2D;

public class Model extends GameObject {
    public String name;

    public Model() {
        super();
    }

    public Model(String name) {
        this.name = name;
    }

    @Override
    public void draw(Graphics2D g2D) {
        assert true;
    }
}