package com.rd.animation.data.type;

import com.rd.animation.data.Value;

/* loaded from: classes2.dex */
public class DropAnimationValue implements Value {
    private int a;
    private int b;
    private int c;

    public int getWidth() {
        return this.a;
    }

    public void setWidth(int i) {
        this.a = i;
    }

    public int getHeight() {
        return this.b;
    }

    public void setHeight(int i) {
        this.b = i;
    }

    public int getRadius() {
        return this.c;
    }

    public void setRadius(int i) {
        this.c = i;
    }
}
