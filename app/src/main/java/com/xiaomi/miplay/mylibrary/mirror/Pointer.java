package com.xiaomi.miplay.mylibrary.mirror;

/* loaded from: classes4.dex */
public class Pointer {
    private final long a;
    private final int b;
    private Point c;
    private float d;
    private boolean e;

    public Pointer(long j, int i) {
        this.a = j;
        this.b = i;
    }

    public long getId() {
        return this.a;
    }

    public int getLocalId() {
        return this.b;
    }

    public Point getPoint() {
        return this.c;
    }

    public void setPoint(Point point) {
        this.c = point;
    }

    public float getPressure() {
        return this.d;
    }

    public void setPressure(float f) {
        this.d = f;
    }

    public boolean isUp() {
        return this.e;
    }

    public void setUp(boolean z) {
        this.e = z;
    }
}
