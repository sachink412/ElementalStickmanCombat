package game;

public class TFrame {
    public Vector2D positionVector = new Vector2D();
    public double orientation = 0;

    public TFrame() {
        positionVector = new Vector2D();
        orientation = 0;
    }

    public TFrame(Vector2D positionVector, double orientation) {
        this.positionVector = positionVector;
        this.orientation = orientation;
    }

    public TFrame(double xPosition, double yPosition, double orientation) {
        this.positionVector = new Vector2D(xPosition, yPosition);
        this.orientation = orientation;
    }

    public void combine(TFrame frame) {
        positionVector.add(frame.positionVector);
        orientation += frame.orientation;
    }

    public String toString() {
        return "[" + positionVector.toString() + ", " + orientation + "]";
    }
}