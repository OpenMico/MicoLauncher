package com.xiaomi.miplay.mylibrary.mirror;

import android.graphics.Rect;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class Size {
    private final int a;
    private final int b;

    public Size(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public int getWidth() {
        return this.a;
    }

    public int getHeight() {
        return this.b;
    }

    public Size rotate() {
        return new Size(this.b, this.a);
    }

    public Rect toRect() {
        return new Rect(0, 0, this.a, this.b);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Size size = (Size) obj;
        return this.a == size.a && this.b == size.b;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.a), Integer.valueOf(this.b));
    }

    public String toString() {
        return "Size{width=" + this.a + ", height=" + this.b + '}';
    }
}
