// joint that can freely move along a set x/y range of motion, angle of motion
package game;

public class FreeformJoint extends Joint {
    public double[] posRange;
    public double[] angleRange;

    public FreeformJoint(double[] posRange, double[] angleRange) {
        this.posRange = posRange;
        this.angleRange = angleRange;
    }

    public FreeformJoint() {
        this(new double[] { 0, 0 }, new double[] { 0, 0 });
    }

}
