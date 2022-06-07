package com.xiaomi.micolauncher.skills.common.view;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

/* loaded from: classes3.dex */
public class AutoOverScroller extends OverScroller {
    private int a;

    public AutoOverScroller(Context context) {
        this(context, null);
    }

    public AutoOverScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
        this.a = -1;
    }

    @Override // android.widget.OverScroller
    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        int i6 = this.a;
        if (i6 > 0) {
            super.startScroll(i, i2, i3, i4, i6);
        } else {
            super.startScroll(i, i2, i3, i4, i5);
        }
    }

    @Override // android.widget.OverScroller
    public void startScroll(int i, int i2, int i3, int i4) {
        int i5 = this.a;
        if (i5 > 0) {
            super.startScroll(i, i2, i3, i4, i5);
        } else {
            super.startScroll(i, i2, i3, i4);
        }
    }

    public void setScrollDuration(int i) {
        this.a = i;
    }
}
