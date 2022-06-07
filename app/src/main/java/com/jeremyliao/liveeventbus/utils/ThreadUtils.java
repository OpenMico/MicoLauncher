package com.jeremyliao.liveeventbus.utils;

import android.os.Looper;

/* loaded from: classes2.dex */
public final class ThreadUtils {
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
