package game;

import java.awt.Graphics2D;
import java.util.*;

public abstract class GameObject {
    // properties
    public String name;
    public String className;
    protected HashSet<GameObject> children;
    protected GameObject parent;

    // constructors
    public GameObject() {
        super();
        this.name = "GameObject";
        this.className = "GameObject";
        this.children = new HashSet<GameObject>();
        this.parent = null;
    }

    public GameObject(String className, GameObject parent) {
        super();
        this.name = className;
        this.className = className;
        this.children = new HashSet<GameObject>();
        if (parent != null) {
            this.setParent(parent);
        } else {
            this.parent = null;
        }
    }

    public GameObject[] getChildren() {
        return children.toArray(new GameObject[0]);
    }

    public GameObject[] getDescendants() {
        ArrayList<GameObject> descendants = new ArrayList<GameObject>();
        for (GameObject child : children) {
            GameObject[] childDescendants = child.getDescendants();
            descendants.add(child);
            descendants.addAll(Arrays.asList(childDescendants));
        }
        return descendants.toArray(new GameObject[0]);
    }

    public void setParent(GameObject parent) {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
        this.parent = parent;
        parent.children.add(this);
    }

    public GameObject getParent() {
        return parent;
    }

    public GameObject isChild(GameObject child) {
        if (child.getParent() == this) {
            return child;
        }
        return null;
    }

    public GameObject getChild(String name) {
        for (GameObject child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    public GameObject addChild(GameObject child) {
        children.add(child);
        child.parent = this;
        return child;
    }
    public GameObject getDescendant(String name) {
        for (GameObject child : children) {
            if (child.name.equals(name)) {
                return child;
            }
            GameObject descendant = child.getDescendant(name);
            if (descendant != null) {
                return descendant;
            }
        }
        return null;
    }

    public abstract void draw(Graphics2D g);
}
