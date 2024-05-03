package game;

public class GameObject extends Entity {
    public String name;
    public String className;
    public GameObject[] children;
    public GameObject parent;

    public GameObject() {
        super();
        this.name = "GameObject";
        this.className = "GameObject";
        this.children = new GameObject[0];
        this.parent = null;
    }

    public GameObject[] getDescendants() {
        GameObject[] descendants = new GameObject[0];
        for (GameObject child : this.children) {
            descendants = concat(descendants, child.getDescendants());
        }
        return concat(this.children, descendants);
    }

    public static GameObject[] concat(GameObject[] a, GameObject[] b) {
        int aLen = a.length;
        int bLen = b.length;
        GameObject[] c = new GameObject[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    public boolean isDescendantOf(GameObject gameObject) {
        if (this.parent == null) {
            return false;
        } else if (this.parent == gameObject) {
            return true;
        } else {
            return this.parent.isDescendantOf(gameObject);
        }
    }

}
