package com.xiaomi.push;

import android.os.Looper;

/* loaded from: classes4.dex */
public class ap {
    public static void a() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            throw new RuntimeException("can't do this on ui thread");
        }
    }
}
