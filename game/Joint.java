package game;

import game.objectclasses.Part;

import java.awt.Graphics2D;

public class Joint extends GameObject {
    
    public Part part0 = null;
    public Part part1 = null;
    public TFrame C0 = null;

    protected Joint() {
        this.C0 = new TFrame();
    }

    @Override
    public void draw(Graphics2D g) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }
}