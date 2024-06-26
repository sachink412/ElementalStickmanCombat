package game.objectclasses;

import game.ColorUtilities;
import game.GameObject;
import game.Stickman;
import game.mechanics.Vector2D;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.*;

public class Part extends GameObject {
    /** PROPERTIES */

    // Physical properties
    public String brickColor;
    public Color color;
    public double opacity;
    public String partType;
    public double density;
    public double frictionCoefficient;
    public Shape shape;
    public String collisionType;
    public boolean anchored;
    public boolean canCollide;
    public boolean canTouch;
    public boolean fill;
    public Stickman stickConnection;
    public HashSet<Part> hitSave;

    // Spatial properties
    public Vector2D position;
    public double orientation;
    public Vector2D size;

    // Tranformative properties
    public Vector2D velocity;
    public Vector2D bodyVel;
    public double rotationalVelocity;
    public Vector2D acceleration;
    public double rotationAcceleration;
    public double gravitationalOffset;

    /**
     * Constructor for the Part class
     * 
     * @param className The name of the class
     * @param parent    The parent object (usually workspace)
     * 
     *                  Note: The constructor does not accept arguments other than
     *                  className and the parent.
     *                  The properties are initialized to default values, but they
     *                  are mutable through direct access and no encapsulation to
     *                  prevent unnecessary overhead.
     */
    public Part(String className, GameObject parent) {
        super(className, parent);
        this.position = new Vector2D(0, 0);
        this.orientation = 0;
        this.size = new Vector2D(1, 1);
        this.velocity = new Vector2D(0, 0);
        this.rotationalVelocity = 0;
        this.brickColor = "Black";
        this.color = new Color(0, 0, 0);
        this.opacity = 1;
        this.partType = "Rectangle";
        this.shape = new Rectangle(0, 0, 1, 1);
        this.density = 1;
        this.frictionCoefficient = 0.5;
        this.acceleration = new Vector2D(0, 0);
        this.rotationAcceleration = 0;
        this.gravitationalOffset = 1;
        this.collisionType = "Bounce";
        this.anchored = false;
        this.canCollide = true;
        this.fill = true;
        this.canTouch = true;
        this.bodyVel = new Vector2D(0, 0);
        this.hitSave = new HashSet<>();
    }

    public void draw(Graphics2D g) {
        // Draw the part as a rectangle
        int x = (int) this.position.x;
        int y = (int) this.position.y;
        int width = (int) this.size.x;
        int height = (int) this.size.y;

        Color colorAlpha = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(),
                (int) (this.opacity * 255));
        g.setColor(colorAlpha);

        // If the part is a rectangle
        if (this.partType.equals("Rectangle")) {
            if (this.shape.getClass() != Rectangle.class) {
                this.shape = new Rectangle(x, y, width, height);
            }
            ((Rectangle) this.shape).x = x;
            ((Rectangle) this.shape).y = y;
            ((Rectangle) this.shape).width = width;
            ((Rectangle) this.shape).height = height;
            g.rotate(Math.toRadians(this.orientation));
            if (this.fill) {
                g.fill((Rectangle) this.shape);
            } else {
                g.draw((Rectangle) this.shape);
            }
        }

        // If the part is an ellipse
        else if (this.partType.equals("Ellipse")) {
            if (this.shape.getClass() != Ellipse2D.class) {
                this.shape = new Ellipse2D.Double(x, y, width, height);
            }
            ((Ellipse2D) this.shape).setFrame(x, y, width, height);
            g.rotate(Math.toRadians(this.orientation));
            if (this.fill) {
                g.fill((Ellipse2D) this.shape);
            } else {
                g.draw((Ellipse2D) this.shape);
            }
        }

        // If the part is a triangle
        else if (this.partType.equals("Triangle")) {
            if (this.shape.getClass() != Polygon.class) {
                this.shape = new Polygon();
            }
            int[] xPoints = { x, x + width, x + width / 2 };
            int[] yPoints = { y + height, y + height, y };
            Polygon triangle = (Polygon) this.shape;
            triangle.xpoints = xPoints;
            triangle.ypoints = yPoints;
            triangle.npoints = 3;
            g.rotate(Math.toRadians(this.orientation));
            if (this.fill) {
                g.fillPolygon(triangle);
            } else {
                g.drawPolygon(triangle);
            }
        }
        if (stickConnection != null) {
            stickConnection.draw(g);
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

    public boolean simpleCollides(Part other) {
        // Check if the two parts overlap
        return other.shape.getBounds().intersects(this.shape.getBounds());
    }

    public boolean collides(Part other) {
        Area area1 = new Area(this.shape);
        Area area2 = new Area(other.shape);
        area1.intersect(area2);

        if (!area1.isEmpty()) {
            return true;
        }

        return false;
    }

    public double getMass() {
        return this.size.x * this.size.y * this.density;
    }

    public void setColor(String color) {
        try {
            Color newcolor = ColorUtilities.getColor(color);
            this.color = new Color(newcolor.getRed(), newcolor.getGreen(), newcolor.getBlue());
            this.brickColor = color;
        } catch (Exception e) {
            this.color = new Color(0, 0, 0);
        }
    }

    public void setColor(int r, int g, int b) {
        this.color = new Color(r, g, b);
        this.brickColor = ColorUtilities.getColorNameFromRgb(r, g, b);
    }
}