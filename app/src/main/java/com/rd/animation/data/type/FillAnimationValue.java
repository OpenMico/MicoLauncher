package com.rd.animation.data.type;

import com.rd.animation.data.Value;

/* loaded from: classes2.dex */
public class FillAnimationValue extends ColorAnimationValue implements Value {
    private int a;
    private int b;
    private int c;
    private int d;

    public int getRadius() {
        return this.a;
    }

    public void setRadius(int i) {
        this.a = i;
    }

    public int getRadiusReverse() {
        return this.b;
    }

    public void setRadiusReverse(int i) {
        this.b = i;
    }

    public int getStroke() {
        return this.c;
    }

    public void setStroke(int i) {
        this.c = i;
    }

    public int getStrokeReverse() {
        return this.d;
    }

    public void setStrokeReverse(int i) {
        this.d = i;
    }
}
