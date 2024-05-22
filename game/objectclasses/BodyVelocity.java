package game.objectclasses;

import game.GameObject;
import game.mechanics.Vector2D;

import java.awt.Graphics2D;

// class that maintains constant velocity
// for a body in the game
// it can be released, with an easing time to smoothly stop the body
// it can be set to a new velocity, with an easing time to smoothly change the velocity
// this object will be children of a part
public class BodyVelocity extends GameObject {
    // fields
    public Vector2D velocity;
    public boolean released;
    public double easingTime;

    // constructor
    public BodyVelocity(String className, GameObject parent) {
        super(className, parent);
        this.velocity = new Vector2D(0, 0);
        this.released = false;
        this.easingTime = 0;
    }

    // method to release the body
    public void release() {
        this.released = true;
    }

    // method to set the velocity of the body
    public void setVelocity(Vector2D velocity, double easingTime) {
        this.velocity = velocity;
        this.easingTime = easingTime;
    }

    public void draw(Graphics2D g2D) {
        assert true;
    }
}
