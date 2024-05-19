package game;
import java.awt.geom.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.awt.*;
import java.util.Set;
import javax.sound.sampled.Line;

public class Intersector {
 public static void main(String[] args) throws Exception {

        final Rectangle rect = new Rectangle(50, 50, 100, 100);
        final Rectangle rect2 = new Rectangle(69, 50, 100, 100);
        final Set<ArrayList<?>> intersections = getShapeIntersections(rect, rect2);
        final Iterator<ArrayList<?>> it = intersections.iterator();
        while(it.hasNext()) {
            final ArrayList<?> getList = it.next();
            final Point2D intersection = (Point2D) getList.get(0);
            System.out.println("Intersection at (" + intersection.getX() + ", " + intersection.getY() + ")");
        }

    }

    public static Set<Point2D> getIntersections(final Polygon poly, final Line2D.Double line) throws Exception {

        final PathIterator polyIt = poly.getPathIterator(null); 
        final double[] coords = new double[100]; 
        final double[] firstCoords = new double[2]; 
        final double[] lastCoords = new double[2]; 
        final Set<Point2D> intersections = new HashSet<Point2D>(); 
        polyIt.currentSegment(firstCoords); 
        lastCoords[0] = firstCoords[0];
        lastCoords[1] = firstCoords[1];
        polyIt.next();
        while(!polyIt.isDone()) {
            final int type = polyIt.currentSegment(coords);
            switch(type) {
                case PathIterator.SEG_LINETO : {
                    final Line2D.Double currentLine = new Line2D.Double(lastCoords[0], lastCoords[1], coords[0], coords[1]);
                    if(currentLine.intersectsLine(line))
                        intersections.add(getIntersection(currentLine, line));
                    lastCoords[0] = coords[0];
                    lastCoords[1] = coords[1];
                    break;
                }
                case PathIterator.SEG_CLOSE : {
                    final Line2D.Double currentLine = new Line2D.Double(coords[0], coords[1], firstCoords[0], firstCoords[1]);
                    if(currentLine.intersectsLine(line))
                        intersections.add(getIntersection(currentLine, line));
                    break;
                }
                default : {
                    throw new Exception("Unsupported PathIterator segment type.");
                }
            }
            polyIt.next();
        }
        return intersections;

    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Set<ArrayList<?>> getShapeIntersections(final Shape poly1, final Shape poly2) throws Exception {
        PathIterator polyIt = poly1.getPathIterator(null); 
        if (poly1 instanceof Ellipse2D) {
            polyIt = poly1.getBounds2D().getPathIterator(null);
        }
        final double[] coords = new double[100]; 
        final double[] firstCoords = new double[2]; 
        final double[] lastCoords = new double[2]; 
        final Set<ArrayList<?>> intersections = new HashSet<ArrayList<?>>(); 
        ArrayList<Line2D.Double> lines = new ArrayList<Line2D.Double>();
        ArrayList<CubicCurve2D> curves = new ArrayList<CubicCurve2D>();
        polyIt.currentSegment(firstCoords); 
        lastCoords[0] = firstCoords[0];
        lastCoords[1] = firstCoords[1];
        polyIt.next();

        while(!polyIt.isDone()) {
            final int type = polyIt.currentSegment(coords);
            switch(type) {
                case PathIterator.SEG_LINETO : {
                    final Line2D.Double currentLine = new Line2D.Double(lastCoords[0], lastCoords[1], coords[0], coords[1]);
                    lines.add(currentLine);
                    lastCoords[0] = coords[0];
                    lastCoords[1] = coords[1];
                    break;
                }
                case PathIterator.SEG_CLOSE : {
                    final Line2D.Double currentLine = new Line2D.Double(coords[0], coords[1], firstCoords[0], firstCoords[1]);
                    lines.add(currentLine);
                    break;
                }

                case PathIterator.SEG_CUBICTO : {
                    CubicCurve2D curve = new CubicCurve2D.Double(lastCoords[0], lastCoords[1], coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
                    lastCoords[0] = coords[4];
                    lastCoords[1] = coords[5];
                    curves.add(curve);
                    break;
                }

                default : {
                    System.out.println("COOKED");
                    System.out.println(type);
                    throw new Exception("Unsupported PathIterator segment type.");
                }
            }

            polyIt.next();
        }
        PathIterator polyIt2 = poly2.getPathIterator(null);
        if (poly2 instanceof Ellipse2D) {
            polyIt2 = poly2.getBounds2D().getPathIterator(null);
        }
        final double[] coords2 = new double[100];
        ArrayList<Line2D.Double> lines2 = new ArrayList<Line2D.Double>();
        ArrayList<CubicCurve2D> curves2 = new ArrayList<CubicCurve2D>();
        final double[] firstCoords2 = new double[2];
        final double[] lastCoords2 = new double[2];
        polyIt2.currentSegment(firstCoords2);
        lastCoords2[0] = firstCoords2[0];
        lastCoords2[1] = firstCoords2[1];
        polyIt2.next();

        while(!polyIt2.isDone()) {
            final int type = polyIt2.currentSegment(coords2);
            switch(type) {
                case PathIterator.SEG_LINETO : {
                    final Line2D.Double currentLine = new Line2D.Double(lastCoords2[0], lastCoords2[1], coords2[0], coords2[1]);
                    lines2.add(currentLine);
                    lastCoords2[0] = coords2[0];
                    lastCoords2[1] = coords2[1];
                    break;
                }
                case PathIterator.SEG_CLOSE : {
                    if (!(poly2 instanceof Ellipse2D)) {
                    final Line2D.Double currentLine = new Line2D.Double(coords2[0], coords2[1], firstCoords2[0], firstCoords2[1]);
                    lines2.add(currentLine);
                    }
                    break;
                }
                case PathIterator.SEG_CUBICTO : {
                    CubicCurve2D curve = new CubicCurve2D.Double(lastCoords2[0], lastCoords2[1], coords2[0], coords2[1], coords2[2], coords2[3], coords2[4], coords2[5]);
                    lastCoords2[0] = coords2[4];
                    lastCoords2[1] = coords2[5];
                    curves2.add(curve);
                    break;
                }
                default : {
                    throw new Exception("Unsupported PathIterator segment type.");
                }
            }
            polyIt2.next();
        }
        
        for (Line2D.Double line : lines) {
            for (Line2D.Double line2 : lines2) {
                if (line.intersectsLine(line2)) {
                    intersections.add(new ArrayList(Arrays.asList(new Object[] {getIntersection(line, line2), line, line2})));
                }
            }
        }
        
            return intersections;

    }
    public static Point2D getIntersection(final Line2D.Double line1, final Line2D.Double line2) {

        final double x1,y1, x2,y2, x3,y3, x4,y4;
        x1 = line1.x1; y1 = line1.y1; x2 = line1.x2; y2 = line1.y2;
        x3 = line2.x1; y3 = line2.y1; x4 = line2.x2; y4 = line2.y2;
        final double x = (
                (x2 - x1)*(x3*y4 - x4*y3) - (x4 - x3)*(x1*y2 - x2*y1)
                ) /
                (
                (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4)
                );
        final double y = (
                (y3 - y4)*(x1*y2 - x2*y1) - (y1 - y2)*(x3*y4 - x4*y3)
                ) /
                (
                (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4)
                );

        return new Point2D.Double(x, y);

    }
}
