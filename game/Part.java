package game;

import java.awt.Color;
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

    // identifier property
    public String name;

    // constructor
    public Part() {
        this.position = new Vector2D(0, 0);
        this.orientation = new Vector2D(0, 0);
        this.size = new Vector2D(1, 1);
        this.velocity = new Vector2D(0, 0);
        this.rotationalVelocity = new Vector2D(0, 0);
        this.color = "BLACK";
        this.opacity = 1;
        this.partType = "Rectangle";
        this.density = 1;
        this.frictionCoefficient = 0.5;
        this.acceleration = new Vector2D(0, 0);
        this.rotationAcceleration = new Vector2D(0, 0);
        this.gravitationalOffset = 0;
        this.name = "";
    }

    public void draw(Graphics g) {
        // Set the color of the part
        Color c = Color.decode(this.color);
        g.setColor(c);

        // Draw the part as a rectangle
        int x = (int) this.position.x;
        int y = (int) this.position.y;
        int width = (int) this.size.x;
        int height = (int) this.size.y;

        // If the part is a rectangle
        if (this.partType.equals("Rectangle")) {
            g.fillRect(x, y, width, height);
        }
        // If the part is an ellipse
        else if (this.partType.equals("Ellipse")) {
            g.fillOval(x, y, width, height);
        }
        // If the part is a triangle
        else if (this.partType.equals("Triangle")) {
            int[] xPoints = { x, x + width, x + width / 2 };
            int[] yPoints = { y, y, y + height };
            g.fillPolygon(xPoints, yPoints, 3);
        }
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