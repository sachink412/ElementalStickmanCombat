package game;

import java.awt.Graphics2D;

public class GameObject extends Entity {

    // properties
    public String name;
    public String className;
    protected GameObject[] children;
    protected GameObject parent;

    // constructors
    public GameObject() {
        super();
        this.name = "GameObject";
        this.className = "GameObject";
        this.children = new GameObject[0];
        this.parent = null;
    }

    public GameObject(String className, String name) {
        super();
        this.name = name;
        this.className = className;
        this.children = new GameObject[0];
        this.parent = null;
    }

    // set the parent of this GameObject
    public void setParent(GameObject parent) {
        this.parent = parent;

        GameObject[] newChildren = new GameObject[parent.children.length + 1];
        for (int i = 0; i < parent.children.length; i++) {
            newChildren[i] = parent.children[i];
        }
        newChildren[parent.children.length] = this;
        parent.children = newChildren;

    }

    // retrieve the parent of this GameObject
    public GameObject getParent() {
        return this.parent;
    }

    // check if a GameObject is a child of this GameObject
    public GameObject isChild(GameObject child) {
        if (child.getParent() == this) {
            return child;
        }
        return null;
    }

    // retrieve all children of this GameObject
    public GameObject[] getChildren() {
        // remove any children that have a parent that is not this GameObject
        for (int i = 0; i < this.children.length; i++) {
            if (this.children[i].getParent() != this) {
                GameObject[] newChildren = new GameObject[this.children.length - 1];
                for (int j = 0; j < i; j++) {
                    newChildren[j] = this.children[j];
                }
                for (int j = i + 1; j < this.children.length; j++) {
                    newChildren[j - 1] = this.children[j];
                }
                this.children = newChildren;
            }
        }

        return this.children;
    }

    // retrieve all descendants of this GameObject
    public GameObject[] getDescendants() {
        GameObject[] descendants = new GameObject[0];
        for (int i = 0; i < this.children.length; i++) {
            GameObject[] childDescendants = this.children[i].getDescendants();
            GameObject[] newDescendants = new GameObject[descendants.length + childDescendants.length + 1];
            for (int j = 0; j < descendants.length; j++) {
                newDescendants[j] = descendants[j];
            }
            newDescendants[descendants.length] = this.children[i];
            for (int j = 0; j < childDescendants.length; j++) {
                newDescendants[descendants.length + j + 1] = childDescendants[j];
            }
            descendants = newDescendants;
        }
        return descendants;
    }

    public void draw(Graphics2D g2d) {
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }
}
