package com.xiaomi.micolauncher.module.homepage.event;

/* loaded from: classes3.dex */
public class MainPageMaskAnimationEvent {
    public float bottom;
    public float left;
    public float radius;
    public float right;
    public boolean show;
    public float top;

    public MainPageMaskAnimationEvent(boolean z) {
        this.show = z;
    }

    public MainPageMaskAnimationEvent(boolean z, float f, float f2, float f3, float f4, float f5) {
        this.show = z;
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
        this.radius = f5;
    }
}
