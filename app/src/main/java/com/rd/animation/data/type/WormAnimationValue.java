package com.rd.animation.data.type;

import com.rd.animation.data.Value;

/* loaded from: classes2.dex */
public class WormAnimationValue implements Value {
    private int a;
    private int b;

    public int getRectStart() {
        return this.a;
    }

    public void setRectStart(int i) {
        this.a = i;
    }

    public int getRectEnd() {
        return this.b;
    }

    public void setRectEnd(int i) {
        this.b = i;
    }
}
