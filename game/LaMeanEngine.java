// collision detection, movement, joints, speed calculations, etc.

package game;

import game.objectclasses.*;

import java.util.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

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
        // get all of the gameobjects in descendants that are of class RigidJoint, and
        // put them in a container

        ArrayList<RigidJoint> joints = new ArrayList<RigidJoint>();
        for (GameObject descendant : descendants) {
            if (descendant instanceof RigidJoint) {
                joints.add((RigidJoint) descendant);
            }
        }
        for (GameObject descendant : descendants) {
            if (descendant instanceof Part) {
                Part part = (Part) descendant;
                Part connectedPart = null;
                Joint connectedJoint = null;
                for (RigidJoint joint : joints) {
                    if (joint.part1 == part) {
                        connectedPart = joint.part0;
                        connectedJoint = joint;
                    }
                }
                if (!part.anchored && connectedPart == null) {
                    part.velocity.add(new Vector2D(part.acceleration.x, part.acceleration.y + GRAVITY));
                    part.position.add(part.velocity);
                    part.rotationalVelocity += part.rotationAcceleration;
                    part.orientation += part.rotationalVelocity;
                    if (part.position.y > (Game.WINDOW_HEIGHT) - (Game.WINDOW_HEIGHT * 0.125) - part.size.y) {
                        part.position.y = (Game.WINDOW_HEIGHT) - (Game.WINDOW_HEIGHT * 0.125) - part.size.y;
                        part.velocity.y = 0;
                    }
                } else if (connectedPart != null) {
                    TFrame C0 = connectedJoint.C0;

                    Vector2D partPos = new Vector2D(connectedPart.position.x + C0.positionVector.x,
                            connectedPart.position.y + C0.positionVector.y);
                    double partOri = connectedPart.orientation + C0.orientation;

                    part.position = partPos;
                    part.orientation = partOri;

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