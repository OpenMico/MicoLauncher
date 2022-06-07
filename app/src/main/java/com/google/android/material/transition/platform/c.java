package com.google.android.material.transition.platform;

import androidx.annotation.RequiresApi;

/* compiled from: FadeModeResult.java */
@RequiresApi(21)
/* loaded from: classes2.dex */
class c {
    final int a;
    final int b;
    final boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static c a(int i, int i2) {
        return new c(i, i2, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static c b(int i, int i2) {
        return new c(i, i2, true);
    }

    private c(int i, int i2, boolean z) {
        this.a = i;
        this.b = i2;
        this.c = z;
    }
}
