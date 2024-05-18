package game;

public class TFrame {
    // position + orientation vector
    public Vector2D posVector = new Vector2D();
    public Vector2D orientVector = new Vector2D();

    // constructor
    public TFrame() {
        posVector = new Vector2D();
        orientVector = new Vector2D();
    }

    public TFrame(Vector2D posVector, Vector2D orientVector) {
        this.posVector = posVector;
        this.orientVector = orientVector;
    }

    public TFrame(double xPos, double yPos, double orientX, double orientY) {
        this.posVector = new Vector2D(xPos, yPos);
        this.orientVector = new Vector2D(orientX, orientY);
    }

    // methods
    public void combine(TFrame frame) {
        posVector.add(frame.posVector);
        orientVector.add(frame.orientVector);
    }

    public String toString() {
        return "[" + posVector.toString() + ", " + orientVector.toString() + "]";
    }
}