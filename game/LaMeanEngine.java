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
                HashMap<Part, Point2D> intersections = Raycaster.castRay(part.position, part.velocity, part.velocity.mag(),
                        new Part[] { otherPart });
                for (Part key : intersections.keySet()) {
                    System.out.println("INTERSECT");
                    Point2D intersection = intersections.get(key);
                    Vector2D interPos = new Vector2D(intersection.getX(), intersection.getY());
                    // get normal
                    Vector2D normal = new Vector2D(part.position.x - interPos.x, part.position.y - interPos.y);
                    normal.normalize();
                    // get tangent
                    Vector2D tangent = new Vector2D(-normal.y, normal.x);

                    // use conservation of momentum to calculate new velocities
                    double partNormal = part.velocity.dot(normal);
                    double otherPartNormal = otherPart.velocity.dot(normal);

                    double partTangent = part.velocity.dot(tangent);
                    double otherPartTangent = otherPart.velocity.dot(tangent);

                    double partMass = part.getMass();
                    double otherPartMass = otherPart.getMass();

                    double partNormalFinal = (partNormal * (partMass - otherPartMass) + 2 * otherPartMass * otherPartNormal)
                            / (partMass + otherPartMass);
                    double otherPartNormalFinal = (otherPartNormal * (otherPartMass - partMass) + 2 * partMass * partNormal)
                            / (partMass + otherPartMass);
                    
                    part.velocity = new Vector2D(normal.x * partNormalFinal + tangent.x * partTangent,
                            normal.y * partNormalFinal + tangent.y * partTangent);
                    if (!otherPart.anchored) {
                    otherPart.velocity = new Vector2D(normal.x * otherPartNormalFinal + tangent.x * otherPartTangent,
                            normal.y * otherPartNormalFinal + tangent.y * otherPartTangent);    
                    }
                    
                    // move parts out of each other
                    
                    double overlapX = part.size.x + otherPart.size.x - Math.abs(part.position.x - otherPart.position.x);
                    double overlapY = part.size.y + otherPart.size.y - Math.abs(part.position.y - otherPart.position.y);
                    double overlap = Math.min(overlapX, overlapY);
                    double overlapXSign = Math.signum(part.position.x - otherPart.position.x);
                    double overlapYSign = Math.signum(part.position.y - otherPart.position.y);
                    part.position.add(new Vector2D(overlap * overlapXSign * normal.x, overlap * overlapYSign * normal.y));
                    otherPart.position.add(new Vector2D(-overlap * overlapXSign * normal.x, -overlap * overlapYSign * normal.y));

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

                                        if (parts.size() == 0) {
                                            collisionMap.remove(part);
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
}