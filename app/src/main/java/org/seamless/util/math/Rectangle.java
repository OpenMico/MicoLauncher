package org.seamless.util.math;

/* loaded from: classes4.dex */
public class Rectangle {
    private int height;
    private Point position;
    private int width;

    public Rectangle() {
    }

    public Rectangle(Point point, int i, int i2) {
        this.position = point;
        this.width = i;
        this.height = i2;
    }

    public void reset() {
        this.position = new Point(0, 0);
        this.width = 0;
        this.height = 0;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point point) {
        this.position = point;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public Rectangle intersection(Rectangle rectangle) {
        int x = this.position.getX();
        int y = this.position.getY();
        int x2 = rectangle.position.getX();
        int y2 = rectangle.position.getY();
        long j = x + this.width;
        long j2 = y + this.height;
        long j3 = x2 + rectangle.width;
        long j4 = y2 + rectangle.height;
        if (x < x2) {
            x = x2;
        }
        if (y < y2) {
            y = y2;
        }
        if (j > j3) {
            j = j3;
        }
        if (j2 > j4) {
            j2 = j4;
        }
        long j5 = j - x;
        long j6 = j2 - y;
        long j7 = -2147483648L;
        if (j5 < -2147483648L) {
            j5 = -2147483648L;
        }
        if (j6 >= -2147483648L) {
            j7 = j6;
        }
        return new Rectangle(new Point(x, y), (int) j5, (int) j7);
    }

    public boolean isOverlapping(Rectangle rectangle) {
        Rectangle intersection = intersection(rectangle);
        return intersection.getWidth() > 0 && intersection.getHeight() > 0;
    }

    public String toString() {
        return "Rectangle(" + this.position + " - " + this.width + "x" + this.height + ")";
    }
}
