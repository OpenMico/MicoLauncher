package com.umeng.analytics.pro;

import android.content.Context;
import android.os.Looper;

/* compiled from: OpenDeviceId.java */
/* loaded from: classes2.dex */
public class z {
    private static y a = null;
    private static boolean b = false;

    public static synchronized String a(Context context) {
        synchronized (z.class) {
            try {
                if (context == null) {
                    throw new RuntimeException("Context is null");
                } else if (Looper.myLooper() != Looper.getMainLooper()) {
                    b(context);
                    if (a != null) {
                        try {
                            return a.a(context);
                        } catch (Exception unused) {
                        }
                    }
                    return null;
                } else {
                    throw new IllegalStateException("Cannot be called from the main thread");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static void b(Context context) {
        if (a == null && !b) {
            synchronized (z.class) {
                if (a == null && !b) {
                    a = aa.a(context);
                    b = true;
                }
            }
        }
    }
}
