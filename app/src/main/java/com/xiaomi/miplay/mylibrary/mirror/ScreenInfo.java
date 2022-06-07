package com.xiaomi.miplay.mylibrary.mirror;

import android.graphics.Rect;

/* loaded from: classes4.dex */
public final class ScreenInfo {
    private final Rect a;
    private final Size b;
    private final boolean c;

    public ScreenInfo(Rect rect, Size size, boolean z) {
        this.a = rect;
        this.b = size;
        this.c = z;
    }

    public Rect getContentRect() {
        return this.a;
    }

    public Size getVideoSize() {
        return this.b;
    }

    public ScreenInfo withRotation(int i) {
        boolean z = true;
        if ((i & 1) == 0) {
            z = false;
        }
        return this.c == z ? this : new ScreenInfo(Device.a(this.a), this.b.rotate(), z);
    }
}
