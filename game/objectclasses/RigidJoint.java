package game.objectclasses;

import game.GameObject;
import game.Joint;

// Subclass of joint that maintains rigidity with other parts
public class RigidJoint extends Joint {
    public RigidJoint(String className, GameObject parent) {
        super(className, parent);
    }

}