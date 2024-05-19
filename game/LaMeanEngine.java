// collision detection, movement, joints, speed calculations, etc.

package game;

import game.objectclasses.*;

import java.util.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.HashSet;

public class LaMeanEngine {
    public Game game;
    final double GRAVITY = .98 / 2;
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
            Set<ArrayList<?>> intersections = null;
            try {
                intersections = getIntersections(part, otherPart);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // conservation of momentum.
            double mass1 = part.getMass();
            double mass2 = otherPart.getMass();
            Vector2D velocity1 = part.velocity;
            Vector2D velocity2 = otherPart.velocity;
            Vector2D momentum1 = velocity1.mul(mass1);
            Vector2D momentum2 = velocity2.mul(mass2);
            Vector2D totalMomentum = momentum1.add(momentum2);

            double totalMass = mass1 + mass2;

            // get the normal vector of the collision
            Vector2D normal = new Vector2D(0, 0);
            for (ArrayList<?> intersection : intersections) {
                Point intersectionPoint = (Point) intersection.get(0);

                normal = new Vector2D(part.position.x - intersectionPoint.x, part.position.y - intersectionPoint.y);

            }
            normal.normalize();

            // get the tangent vector of the collision
            Vector2D tangent = new Vector2D(-normal.y, normal.x);

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