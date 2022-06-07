package org.seamless.util.math;

/* loaded from: classes4.dex */
public class Point {
    private int x;
    private int y;

    public Point(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Point multiply(double d) {
        int i = this.x;
        int i2 = 0;
        int i3 = i != 0 ? (int) (i * d) : 0;
        int i4 = this.y;
        if (i4 != 0) {
            i2 = (int) (i4 * d);
        }
        return new Point(i3, i2);
    }

    public Point divide(double d) {
        int i = this.x;
        int i2 = 0;
        int i3 = i != 0 ? (int) (i / d) : 0;
        int i4 = this.y;
        if (i4 != 0) {
            i2 = (int) (i4 / d);
        }
        return new Point(i3, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point point = (Point) obj;
        return this.x == point.x && this.y == point.y;
    }

    public int hashCode() {
        return (this.x * 31) + this.y;
    }

    public String toString() {
        return "Point(" + this.x + "/" + this.y + ")";
    }
}
