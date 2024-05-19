package game;

public class TFrame {
    public Vector2D positionVector = new Vector2D();
    public Vector2D orientationVector = new Vector2D();

    public TFrame() {
        positionVector = new Vector2D();
        orientationVector = new Vector2D();
    }

    public TFrame(Vector2D positionVector, Vector2D orientationVector) {
        this.positionVector = positionVector;
        this.orientationVector = orientationVector;
    }

    public TFrame(double xPosition, double yPosition, double xOrientation, double yOrientation) {
        this.positionVector = new Vector2D(xPosition, yPosition);
        this.orientationVector = new Vector2D(xOrientation, yOrientation);
    }

    public void combine(TFrame frame) {
        positionVector.add(frame.positionVector);
        orientationVector.add(frame.orientationVector);
    }

    public String toString() {
        return "[" + positionVector.toString() + ", " + orientationVector.toString() + "]";
    }
}