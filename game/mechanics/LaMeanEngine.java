// collision detection, movement, joints, speed calculations, etc.

package game.mechanics;

import game.Game;
import game.GameObject;
import game.Instance;
import game.Joint;
import game.TFrame;
import game.objectclasses.*;

import java.util.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;

public class LaMeanEngine {
    public Game game;
    final double GRAVITY = .98 / 3;

    public CollisionManager collisionManager = new CollisionManager();

    public LaMeanEngine(Game game) {
        this.game = game;
    }

    public void step() {
        if (!game.gamePanel.isVisible()) {
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
                    Vector2D bodyVel = new Vector2D(0, 0);
                    boolean restrictX = false;
                    boolean restrictY = false;
                    for (GameObject child : part.getChildren()) {
                        if (child instanceof BodyVelocity) {
                            bodyVel.add(((BodyVelocity) child).velocity);
                            if (((BodyVelocity) child).restrictX) {
                                restrictX = true;
                            }
                            if (((BodyVelocity) child).restrictY) {
                                restrictY = true;
                            }
                        }
                    }
                    part.velocity.add(new Vector2D(part.acceleration.x, part.acceleration.y + GRAVITY));
                    double xVel = part.velocity.x + bodyVel.x;
                    double yVel = part.velocity.y + bodyVel.y;
                    if (restrictX) {
                        xVel *= 0;
                    }
                    if (restrictY) {
                        yVel *= 0;
                    }
                    part.position.add(new Vector2D(part.velocity.x + bodyVel.x,
                            part.velocity.y + bodyVel.y));
                    part.rotationalVelocity += part.rotationAcceleration;
                    part.orientation += part.rotationalVelocity;
                    if (part.position.y > (Game.WINDOW_HEIGHT) - (Game.WINDOW_HEIGHT * 0.249) - part.size.y) {
                        part.position.y = (Game.WINDOW_HEIGHT) - (Game.WINDOW_HEIGHT * 0.249) - part.size.y;
                        part.velocity.y = 0;
                    }
                    if (part.name != "Tsunami") {
                        if (part.position.x < 0) {
                            part.position.x = 0;
                            part.velocity.x = 0;
                        }
                        if (part.position.x > Game.WINDOW_WIDTH - part.size.x) {
                            part.position.x = Game.WINDOW_WIDTH - part.size.x;
                            part.velocity.x = 0;
                        }
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

        public void onCollision(Part part, Part otherPart) throws Exception {
            if (part.name == "HitBox" && otherPart.name == "HumanoidRootPart"
                    && otherPart.stickConnection != part.stickConnection) {

                if (!part.hitSave.contains(otherPart)) {
                    boolean lookingRight = part.stickConnection.lookingRight;
                    otherPart.stickConnection.health -= 2;
                    new Thread(() -> {
                        BodyVelocity bodyVel = null;
                        try {
                            bodyVel = (BodyVelocity) Instance.create("BodyVelocity", otherPart);
                            bodyVel.velocity = new Vector2D(lookingRight ? 5 : -5, 0);
                        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException
                                | InstantiationException | IllegalAccessException | IllegalArgumentException
                                | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        Debris.addDebris(bodyVel, .5);
                    }).start();
                }
            }
            if (part.name == "Fireball" && otherPart.name == "HumanoidRootPart"
                    && otherPart.stickConnection != part.stickConnection) {

                if (!part.hitSave.contains(otherPart)) {
                    boolean lookingRight = part.stickConnection.lookingRight;
                    otherPart.stickConnection.health -= 15;
                    new Thread(() -> {
                        BodyVelocity bodyVel = null;
                        try {
                            bodyVel = (BodyVelocity) Instance.create("BodyVelocity", otherPart);
                            bodyVel.velocity = new Vector2D(lookingRight ? 10 : -10, 15);
                        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException
                                | InstantiationException | IllegalAccessException | IllegalArgumentException
                                | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        Debris.addDebris(bodyVel, .7);
                        part.anchored = true;
                        part.canTouch = false;
                        Debris.addDebris(part, 1);
                        new Thread(() -> {
                            try {
                                for (int i = 0; i < 10; i++) {
                                    part.size.add(new Vector2D(5, 5));
                                    part.opacity = 1 - (i / 10.0);
                                    Thread.sleep(60);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }).start();
                }
            }

            if (part.name == "Waterball" && otherPart.name == "HumanoidRootPart"
                    && otherPart.stickConnection != part.stickConnection) {

                if (!part.hitSave.contains(otherPart)) {
                    boolean lookingRight = part.stickConnection.lookingRight;
                    otherPart.stickConnection.health -= 7;
                    new Thread(() -> {
                        BodyVelocity bodyVel = null;
                        try {
                            bodyVel = (BodyVelocity) Instance.create("BodyVelocity", otherPart);
                            bodyVel.velocity = new Vector2D(lookingRight ? 8 : -8, 6);
                        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException
                                | InstantiationException | IllegalAccessException | IllegalArgumentException
                                | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        Debris.addDebris(bodyVel, .5);
                        part.anchored = true;
                        part.canTouch = false;
                        Debris.addDebris(part, 1);
                        new Thread(() -> {
                            try {
                                for (int i = 0; i < 10; i++) {
                                    part.size.add(new Vector2D(3, 3));
                                    part.opacity = 1 - (i / 10.0);
                                    Thread.sleep(50);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }).start();
                }
            }

            part.hitSave.add(otherPart);
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