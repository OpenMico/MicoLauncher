package com.xiaomi.micolauncher.common.event;

import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public class ShowHideBackgroundEvent {
    private final Drawable a;
    private final boolean b;
    private final String c;

    public ShowHideBackgroundEvent(String str, boolean z, Drawable drawable) {
        this.c = str;
        this.b = z;
        this.a = drawable;
    }

    public Drawable getDrawable() {
        return this.a;
    }

    public boolean isShow() {
        return this.b;
    }

    public String getTag() {
        return this.c;
    }
}
