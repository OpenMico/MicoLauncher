package com.xiaomi.miplay.mylibrary.mirror;

import java.util.Objects;

/* loaded from: classes4.dex */
public class Point {
    private final int a;
    private final int b;

    public Point(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public int getX() {
        return this.a;
    }

    public int getY() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point point = (Point) obj;
        return this.a == point.a && this.b == point.b;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.a), Integer.valueOf(this.b));
    }

    public String toString() {
        return "Point{x=" + this.a + ", y=" + this.b + '}';
    }
}
