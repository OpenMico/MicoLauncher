package com.rd.animation.data.type;

import com.rd.animation.data.Value;

/* loaded from: classes2.dex */
public class ScaleAnimationValue extends ColorAnimationValue implements Value {
    private int a;
    private int b;

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
}
