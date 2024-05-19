package game.objectclasses;

import game.GameObject;
import java.awt.Graphics2D;

/**
 * This class represents a workspace in the game where game objects can be
 * placed and interacted with.
 * 
 * Think of this like a "pool" of objects that can be used in the game;
 * essentially, all parts that are added are put into this pool.
 */
public class Workspace extends GameObject {
    /**
     * Constructor for Workspace class.
     *
     * @param className The name of the class.
     * @param parent    The parent GameObject of this Workspace.
     */
    public Workspace(String className, GameObject parent) {
        super(className, parent);
    }

    /**
     * Method to draw the Workspace.
     *
     * @param g Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g) {
        assert true; // This assertion is always true and does not affect the program.
    }
}