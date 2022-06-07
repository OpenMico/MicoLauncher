package com.xiaomi.onetrack.util;

import android.os.Looper;

/* loaded from: classes4.dex */
public class w {
    private static final String a = "ProcessUtil";

    public static boolean a() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
