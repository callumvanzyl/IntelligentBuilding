package math;

import java.util.ArrayList;

/**
 * generates a line from deflated grid points
 * used to draw walls
 * algorithm inspired by a snippet from https://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm
 */
public class LineGenerator {
    /**
     * generate a line between a pair of points
     * @param v1 the points to start at
     * @param v2 the end point
     * @return an array containing vector2 values denoting each step of the line
     */
    public ArrayList<Vector2> generate(Vector2 v1, Vector2 v2) {
        ArrayList<Vector2> points = new ArrayList<>();

        int d = 0;

        double dx = Math.abs(v2.getX() - v1.getX());
        double dy = Math.abs(v2.getY() - v1.getY());

        double dx2 = 2 * dx;
        double dy2 = 2 * dy;

        int ix = v1.getX() < v2.getX() ? 1 : -1;
        int iy = v1.getY() < v2.getY() ? 1 : -1;

        double x = v1.getX();
        double y = v1.getY();

        if (dx >= dy) {
            while (true) {
                points.add(new Vector2(x, y));
                if (x == v2.getX())
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                points.add(new Vector2(x, y));
                if (y == v2.getY())
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }

        return points;
    }
}
