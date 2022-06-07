package com.rd.draw.data;

import androidx.annotation.NonNull;
import com.rd.animation.type.AnimationType;

/* loaded from: classes2.dex */
public class Indicator {
    public static final int COUNT_NONE = -1;
    public static final int DEFAULT_COUNT = 3;
    public static final int DEFAULT_PADDING_DP = 8;
    public static final int DEFAULT_RADIUS_DP = 6;
    public static final int IDLE_ANIMATION_DURATION = 250;
    public static final int MIN_COUNT = 1;
    private RtlMode A;
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private float j;
    private int k;
    private int l;
    private boolean m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;
    private long r;
    private long s;
    private int u;
    private int v;
    private int w;
    private Orientation y;
    private AnimationType z;
    private int t = 3;
    private int x = -1;

    public int getHeight() {
        return this.a;
    }

    public void setHeight(int i) {
        this.a = i;
    }

    public int getWidth() {
        return this.b;
    }

    public void setWidth(int i) {
        this.b = i;
    }

    public int getRadius() {
        return this.c;
    }

    public void setRadius(int i) {
        this.c = i;
    }

    public int getPadding() {
        return this.d;
    }

    public void setPadding(int i) {
        this.d = i;
    }

    public int getPaddingLeft() {
        return this.e;
    }

    public void setPaddingLeft(int i) {
        this.e = i;
    }

    public int getPaddingTop() {
        return this.f;
    }

    public void setPaddingTop(int i) {
        this.f = i;
    }

    public int getPaddingRight() {
        return this.g;
    }

    public void setPaddingRight(int i) {
        this.g = i;
    }

    public int getPaddingBottom() {
        return this.h;
    }

    public void setPaddingBottom(int i) {
        this.h = i;
    }

    public int getStroke() {
        return this.i;
    }

    public void setStroke(int i) {
        this.i = i;
    }

    public float getScaleFactor() {
        return this.j;
    }

    public void setScaleFactor(float f) {
        this.j = f;
    }

    public int getUnselectedColor() {
        return this.k;
    }

    public void setUnselectedColor(int i) {
        this.k = i;
    }

    public int getSelectedColor() {
        return this.l;
    }

    public void setSelectedColor(int i) {
        this.l = i;
    }

    public boolean isInteractiveAnimation() {
        return this.m;
    }

    public void setInteractiveAnimation(boolean z) {
        this.m = z;
    }

    public boolean isAutoVisibility() {
        return this.n;
    }

    public void setAutoVisibility(boolean z) {
        this.n = z;
    }

    public boolean isDynamicCount() {
        return this.o;
    }

    public void setDynamicCount(boolean z) {
        this.o = z;
    }

    public boolean isFadeOnIdle() {
        return this.p;
    }

    public void setFadeOnIdle(boolean z) {
        this.p = z;
    }

    public boolean isIdle() {
        return this.q;
    }

    public void setIdle(boolean z) {
        this.q = z;
    }

    public long getIdleDuration() {
        return this.r;
    }

    public void setIdleDuration(long j) {
        this.r = j;
    }

    public long getAnimationDuration() {
        return this.s;
    }

    public void setAnimationDuration(long j) {
        this.s = j;
    }

    public int getCount() {
        return this.t;
    }

    public void setCount(int i) {
        this.t = i;
    }

    public int getSelectedPosition() {
        return this.u;
    }

    public void setSelectedPosition(int i) {
        this.u = i;
    }

    public int getSelectingPosition() {
        return this.v;
    }

    public void setSelectingPosition(int i) {
        this.v = i;
    }

    public int getLastSelectedPosition() {
        return this.w;
    }

    public void setLastSelectedPosition(int i) {
        this.w = i;
    }

    public int getViewPagerId() {
        return this.x;
    }

    public void setViewPagerId(int i) {
        this.x = i;
    }

    @NonNull
    public Orientation getOrientation() {
        if (this.y == null) {
            this.y = Orientation.HORIZONTAL;
        }
        return this.y;
    }

    public void setOrientation(Orientation orientation) {
        this.y = orientation;
    }

    @NonNull
    public AnimationType getAnimationType() {
        if (this.z == null) {
            this.z = AnimationType.NONE;
        }
        return this.z;
    }

    public void setAnimationType(AnimationType animationType) {
        this.z = animationType;
    }

    @NonNull
    public RtlMode getRtlMode() {
        if (this.A == null) {
            this.A = RtlMode.Off;
        }
        return this.A;
    }

    public void setRtlMode(RtlMode rtlMode) {
        this.A = rtlMode;
    }
}
