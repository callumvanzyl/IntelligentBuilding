package math;

import java.io.Serializable;

public class Vector2 implements Serializable {
    private double x;
    private double y;

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean almostEquals(Vector2 v2) {
        return(x >= v2.getX()-5 && x <= v2.getX()+5 && y >= v2.getY()-5 && y <= v2.getY()+5);
    }

    public boolean equals(Vector2 v2) {
        return (x == v2.getX() && y == v2.getY());
    }

    public Vector2 lerp(Vector2 v2, double t) {
        double x = getX() * (1-t) + (v2.getX()*t);
        double y = getY() * (1-t) + (v2.getY()*t);
        return new Vector2(x, y);
    }

    public String toString() {
        return this.x + ", " + this.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
