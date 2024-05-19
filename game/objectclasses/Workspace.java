package game.objectclasses;

import game.GameObject;

import java.awt.Graphics2D;

public class Workspace extends GameObject {
    public Workspace(String className, GameObject parent) {
        super(className, parent);
    }

    public void draw(Graphics2D g) {
        assert true;
    }
}
