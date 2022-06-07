package com.xiaomi.miplay.mylibrary.mirror;

import java.util.Objects;

/* loaded from: classes4.dex */
public class Position {
    private Point a;
    private Size b;

    public Position(Point point, Size size) {
        this.a = point;
        this.b = size;
    }

    public Position(int i, int i2, int i3, int i4) {
        this(new Point(i, i2), new Size(i3, i4));
    }

    public Point getPoint() {
        return this.a;
    }

    public Size getScreenSize() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position position = (Position) obj;
        return Objects.equals(this.a, position.a) && Objects.equals(this.b, position.b);
    }

    public int hashCode() {
        return Objects.hash(this.a, this.b);
    }

    public String toString() {
        return "Position{point=" + this.a + ", screenSize=" + this.b + '}';
    }
}
