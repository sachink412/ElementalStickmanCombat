// collision detection, movement, joints, speed calculations, etc.

package game;

import game.objectclasses.*;

import java.util.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;

public class LaMeanEngine {
    public Game game;
    final double GRAVITY = .98 / 40;

    public CollisionManager collisionManager = new CollisionManager();

    public LaMeanEngine(Game game) {
        this.game = game;
    }

    public void step() {
        if (!game.titleScreen.currentButton.toString().equals("Dev") || !game.gamePanel.isVisible()) {
            return;
        }

        GameObject[] descendants = game.workspace.getDescendants();

        for (GameObject descendant : descendants) {
            if (descendant instanceof Part) {
                Part part = (Part) descendant;
                if (!part.anchored) {
                    part.velocity.add(new Vector2D(part.acceleration.x, part.acceleration.y + GRAVITY));
                    part.position.add(part.velocity);
                    part.rotationalVelocity += part.rotationAcceleration;
                    part.orientation += part.rotationalVelocity;
                }
            }
        }

        try {
            collisionManager.checkCollisions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class CollisionManager {
        public boolean contains(Shape shape, Point point) {
            return shape.contains(point);
        }

        public HashMap<Part, HashSet<Part>> collisionMap = new HashMap<Part, HashSet<Part>>();

        public Set<ArrayList<?>> getIntersections(Part part, Part otherPart) throws Exception {
            return Intersector.getShapeIntersections(part.shape, otherPart.shape);
        }

        public void onCollision(Part part, Part otherPart) throws Exception {
            // get intersection points
            if (part.canTouch && otherPart.canTouch) {
                Set<ArrayList<?>> intersections = null;
                try {
                    intersections = getIntersections(part, otherPart);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (intersections != null) {
                    for (ArrayList<?> intersection : intersections) {
                        Point2D.Double point = (Point2D.Double) intersection.get(0);
                        Point2D.Double otherPoint = (Point2D.Double) intersection.get(1);
                        Line2D.Double line = new Line2D.Double(point, otherPoint);
                        part.position.add(new Vector2D(line.getX1() - line.getX2(), line.getY1() - line.getY2()));
                    }
                }
            }
        }

        public void checkCollisions() throws Exception {
            GameObject[] descendants = game.workspace.getDescendants();
            for (GameObject descendant : descendants) {
                if (descendant instanceof Part) {
                    Part part = (Part) descendant;
                    for (GameObject otherDescendant : descendants) {
                        if (otherDescendant instanceof Part) {
                            Part otherPart = (Part) otherDescendant;
                            if (part != otherPart) {
                                if (part.collides(otherPart)) {
                                    if (collisionMap.containsKey(part)) {
                                        collisionMap.get(part).add(otherPart);
                                    } else {
                                        HashSet<Part> parts = new HashSet<Part>();
                                        parts.add(otherPart);
                                        collisionMap.put(part, parts);
                                    }
                                    onCollision(part, otherPart);
                                } else {
                                    if (collisionMap.containsKey(part)) {
                                        HashSet<Part> parts = collisionMap.get(part);
                                        parts.remove(otherPart);
                                        collisionMap.put(part, parts);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}