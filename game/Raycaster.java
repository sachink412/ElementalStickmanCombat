package game;

import java.awt.*;
import java.awt.geom.*;
import java.util.HashMap;

import game.objectclasses.*;

public class Raycaster {
    public Vector2D origin;
    public Vector2D direction;
    public double magnitude;
    public Part[] parts;
    final static int resolution = 1000;
    final static double threshold = .01;

    public Raycaster(Vector2D origin, Vector2D direction, double magnitude, Part[] parts) {
        this.origin = origin;
        this.direction = direction;
        this.magnitude = magnitude;
        this.parts = parts;
    }

    public static HashMap<Part, Point2D> castRay(Vector2D origin, Vector2D direction, double magnitude, Part[] parts) {
        direction.normalize();
        Line2D ray = new Line2D.Double(origin.x, origin.y, origin.x + direction.x * magnitude, origin.y + direction.y * magnitude);
        HashMap<Part, Point2D> intersections = new HashMap<Part, Point2D>();
        for (Part part : parts) {
            if (ray.intersects(part.shape.getBounds2D())) {
                System.out.println(part.brickColor + " " + part.shape.toString());
                double cmag = magnitude;
                double x = origin.x;
                double y = origin.y;
                double dx = direction.x;
                double dy = direction.y;
 
                double resCount = 0;
                double strictmag = -.01;
                
                while ((resCount < resolution && Math.abs(cmag-strictmag) <= threshold) || strictmag < 0) {
                    Line2D ray2 = new Line2D.Double(x, y, x + dx * cmag, y + dy * cmag);
                    if (part.shape.intersects(ray2.getBounds2D())) {
                        strictmag = cmag;
                        cmag *= .5;
                    } else {
                        cmag = (cmag+strictmag)/2;
                    }
                    resCount++;
                }
                intersections.put(part, new Point2D.Double(x + dx * strictmag, y + dy * strictmag));
            }
        }

        return intersections;
    }
}
