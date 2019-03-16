package math;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Polygon implements Serializable {
    private ArrayList<Vector2> vertices = new ArrayList<>();

    public void appendVertex(Vector2 point) {
        vertices.add(point);
    }

    public boolean containsPoint(Vector2 point) {
        int i, j;
        boolean c = false;
        int nvrts = vertices.size() - 1;
        for (i = 0, j = nvrts-1; i < nvrts; j = i++) {
            Vector2 vi = vertices.get(i);
            Vector2 vj = vertices.get(j);
            if (((vi.getY() > point.getY()) != (vj.getY() > point.getY()))
                    && (point.getX() < (vj.getX() - vi.getX()) * (point.getY() - vi.getY()) / (vj.getY() - vi.getY()) + vi.getX()))
                c = !c;
        }
        return c;
    }

    public Vector2 getLeastExtremePoint() {
        ArrayList<Double> allX = new ArrayList<>();
        ArrayList<Double> allY = new ArrayList<>();
        for (Vector2 p: vertices) {
            allX.add(p.getX());
            allY.add(p.getY());
        }
        return new Vector2(Collections.min(allX), Collections.min(allY));
    }

    /**
     * get the bottom
     * @return
     */
    public Vector2 getMostExtremePoint() {
        ArrayList<Double> allX = new ArrayList<>();
        ArrayList<Double> allY = new ArrayList<>();
        for (Vector2 p: vertices) {
            allX.add(p.getX());
            allY.add(p.getY());
        }
        return new Vector2(Collections.max(allX), Collections.max(allY));
    }

    public Vector2[] getVertices() {
        return vertices.toArray(new Vector2[0]);
    }

    public void setVertices(Vector2[] vertices) {
        this.vertices = new ArrayList(Arrays.asList(vertices));
    }
}
