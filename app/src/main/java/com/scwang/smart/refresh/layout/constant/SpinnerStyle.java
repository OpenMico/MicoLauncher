package com.scwang.smart.refresh.layout.constant;

/* loaded from: classes2.dex */
public class SpinnerStyle {
    public final boolean front;
    public final int ordinal;
    public final boolean scale;
    public static final SpinnerStyle Translate = new SpinnerStyle(0, true, false);
    @Deprecated
    public static final SpinnerStyle Scale = new SpinnerStyle(1, true, true);
    public static final SpinnerStyle FixedBehind = new SpinnerStyle(2, false, false);
    public static final SpinnerStyle FixedFront = new SpinnerStyle(3, true, false);
    public static final SpinnerStyle MatchLayout = new SpinnerStyle(4, true, false);
    public static final SpinnerStyle[] values = {Translate, Scale, FixedBehind, FixedFront, MatchLayout};

    protected SpinnerStyle(int i, boolean z, boolean z2) {
        this.ordinal = i;
        this.front = z;
        this.scale = z2;
    }
}
