package com.rd.animation.data;

import androidx.annotation.NonNull;
import com.rd.animation.data.type.ColorAnimationValue;
import com.rd.animation.data.type.DropAnimationValue;
import com.rd.animation.data.type.FillAnimationValue;
import com.rd.animation.data.type.ScaleAnimationValue;
import com.rd.animation.data.type.SwapAnimationValue;
import com.rd.animation.data.type.ThinWormAnimationValue;
import com.rd.animation.data.type.WormAnimationValue;

/* loaded from: classes2.dex */
public class AnimationValue {
    private ColorAnimationValue a;
    private ScaleAnimationValue b;
    private WormAnimationValue c;
    private FillAnimationValue d;
    private ThinWormAnimationValue e;
    private DropAnimationValue f;
    private SwapAnimationValue g;

    @NonNull
    public ColorAnimationValue getColorAnimationValue() {
        if (this.a == null) {
            this.a = new ColorAnimationValue();
        }
        return this.a;
    }

    @NonNull
    public ScaleAnimationValue getScaleAnimationValue() {
        if (this.b == null) {
            this.b = new ScaleAnimationValue();
        }
        return this.b;
    }

    @NonNull
    public WormAnimationValue getWormAnimationValue() {
        if (this.c == null) {
            this.c = new WormAnimationValue();
        }
        return this.c;
    }

    @NonNull
    public FillAnimationValue getFillAnimationValue() {
        if (this.d == null) {
            this.d = new FillAnimationValue();
        }
        return this.d;
    }

    @NonNull
    public ThinWormAnimationValue getThinWormAnimationValue() {
        if (this.e == null) {
            this.e = new ThinWormAnimationValue();
        }
        return this.e;
    }

    @NonNull
    public DropAnimationValue getDropAnimationValue() {
        if (this.f == null) {
            this.f = new DropAnimationValue();
        }
        return this.f;
    }

    @NonNull
    public SwapAnimationValue getSwapAnimationValue() {
        if (this.g == null) {
            this.g = new SwapAnimationValue();
        }
        return this.g;
    }
}
