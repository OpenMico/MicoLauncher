package com.rd.animation.data.type;

import com.rd.animation.data.Value;

/* loaded from: classes2.dex */
public class SwapAnimationValue implements Value {
    private int a;
    private int b;

    public int getCoordinate() {
        return this.a;
    }

    public void setCoordinate(int i) {
        this.a = i;
    }

    public int getCoordinateReverse() {
        return this.b;
    }

    public void setCoordinateReverse(int i) {
        this.b = i;
    }
}
