package game;

import game.objectclasses.Part;

import java.awt.Graphics2D;

public class Joint extends GameObject {

    public Part part0 = null;
    public Part part1 = null;
    public TFrame C0 = null;

    protected Joint(String className, GameObject parent) {
        super(className, parent);
        this.C0 = new TFrame();
    }

    @Override
    public void draw(Graphics2D g) {
        assert true;
    }
}