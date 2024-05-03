package game.stickman;

import game.Entity;
import game.Vector2D;

import java.awt.Graphics;

public class Part extends Entity {
    // Properties

    // physical properties
    public String color;
    public double opacity;
    public String partType;
    public double density;
    public double frictionCoefficient; // friction coefficient

    // spacial properties
    public Vector2D position;
    public Vector2D orientation;
    public Vector2D size;

    // tranformative properties
    public Vector2D velocity;
    public Vector2D rotationalVelocity;
    public Vector2D acceleration;
    public Vector2D rotationAcceleration;
    public double gravitationalOffset;

    // constructor
    public Part() {
        this.position = new Vector2D(0, 0);
        this.orientation = new Vector2D(0, 0);
        this.size = new Vector2D(0, 0);
        this.velocity = new Vector2D(0, 0);
        this.rotationalVelocity = new Vector2D(0, 0);
        this.color = "black";
        this.opacity = 1;
        this.partType = "Rect";
        this.density = 1;
        this.frictionCoefficient = 0.5;
        this.acceleration = new Vector2D(0, 0);
        this.rotationAcceleration = new Vector2D(0, 0);
        this.gravitationalOffset = 0;
    }

    public void draw(Graphics g) {
        // TODO: Draw the part

    }

    public void move(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void resize(int width, int height) {
        this.size.x = width;
        this.size.y = height;
    }

    public boolean collides(Part other) {
        // TODO: Implement collision

        return false;
    }

    public double getMass() {
        return this.size.x * this.size.y * this.density;
    }
}