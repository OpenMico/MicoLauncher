package com.rd.utils;

import android.os.Build;
import android.view.View;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class IdUtils {
    private static final AtomicInteger a = new AtomicInteger(1);

    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < 17) {
            return a();
        }
        return View.generateViewId();
    }

    private static int a() {
        int i;
        int i2;
        do {
            i = a.get();
            i2 = i + 1;
            if (i2 > 16777215) {
                i2 = 1;
            }
        } while (!a.compareAndSet(i, i2));
        return i;
    }
}
