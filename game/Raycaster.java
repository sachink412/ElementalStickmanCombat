package game;

import java.awt.*;
import java.awt.geom.*;
import game.objectclasses.*;

// Class that handles raycasting, with origin point Vector2D and direction Vector2D, and magnitude Vector2D, along with a list of parts to check for intersections
// Raycaster should have static methods, such as castRay, that take in the origin, direction, magnitude, and list ofparts and return the first intersection point
// use all of the parts in workspace to check for intersections
public class Raycaster {
    public Vector2D origin;
    public Vector2D direction;
    public Vector2D magnitude;
    public Part[] parts;

    public Raycaster(Vector2D origin, Vector2D direction, Vector2D magnitude, Part[] parts) {
        this.origin = origin;
        this.direction = direction;
        this.magnitude = magnitude;
        this.parts = parts;
    }

    public static Vector2D castRay(Vector2D origin, Vector2D direction, Vector2D magnitude, Part[] parts) {
        // should return the first intersection point, a Point2D, with intersection on
        // the X and Y axis
        // try utilizing the intersects method from the Shape class
        // try to make a Line2D that is the ray, and check for intersections with all
        // parts
        // it should be looking for the first intersection point, and return that point
        // if no intersection is found, return null
        //

        return null;
    }
}
