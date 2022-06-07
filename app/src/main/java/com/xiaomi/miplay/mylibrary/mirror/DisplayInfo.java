package com.xiaomi.miplay.mylibrary.mirror;

/* loaded from: classes4.dex */
public final class DisplayInfo {
    private final Size a;
    private final int b;

    public DisplayInfo(Size size, int i) {
        this.a = size;
        this.b = i;
    }

    public Size getSize() {
        return this.a;
    }

    public int getRotation() {
        return this.b;
    }
}
