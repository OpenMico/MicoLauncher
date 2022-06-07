package com.rd.animation.data.type;

import com.rd.animation.data.Value;

/* loaded from: classes2.dex */
public class ColorAnimationValue implements Value {
    private int a;
    private int b;

    public int getColor() {
        return this.a;
    }

    public void setColor(int i) {
        this.a = i;
    }

    public int getColorReverse() {
        return this.b;
    }

    public void setColorReverse(int i) {
        this.b = i;
    }
}
