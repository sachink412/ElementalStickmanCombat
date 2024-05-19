package game;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D() {
        x = 0;
        y = 0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D v) {
        x = v.x;
        y = v.y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2D v) {
        x = v.x;
        y = v.y;

    }

    public Vector2D add(Vector2D v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Vector2D sub(Vector2D v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    public Vector2D mul(double scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    public Vector2D div(double scalar) {
        x /= scalar;
        y /= scalar;
        return this;
    }

    public double mag() {
        return (double) Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        double m = mag();
        if (m != 0) {
            x /= m;
            y /= m;
        }
    }

    public double dot(Vector2D v) {
        return x * v.x + y * v.y;
    }

    public double angle(Vector2D v) {
        return (double) Math.acos(dot(v) / (mag() * v.mag()));
    }

    public void rotate(double angle) {
        double x1 = x;
        double y1 = y;
        x = (double) (x1 * Math.cos(angle) - y1 * Math.sin(angle));
        y = (double) (x1 * Math.sin(angle) + y1 * Math.cos(angle));
    }

    public void limit(double max) {
        if (mag() > max) {
            normalize();
            mul(max);
        }
    }

    public void setMag(double mag) {
        normalize();
        mul(mag);
    }

    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}