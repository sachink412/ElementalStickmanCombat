package game;

// Joint that can freely move along a set of x/y range of motion, angle of motion
public class FreeformJoint extends Joint {
    public double[] positionRange;
    public double[] angleRange;

    public FreeformJoint(double[] posRange, double[] angleRange) {
        super();
        this.positionRange = posRange;
        this.angleRange = angleRange;
    }

    public FreeformJoint() {
        this(new double[] { 0, 0 }, new double[] { 0, 0 });
    }

}
