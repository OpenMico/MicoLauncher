package com.xiaomi.onetrack.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class i {
    private static ThreadPoolExecutor a;
    private static int b = Runtime.getRuntime().availableProcessors() + 1;

    static {
        int i = b;
        a = new ThreadPoolExecutor(i, i, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        a.allowCoreThreadTimeOut(true);
    }

    public static void a(Runnable runnable) {
        try {
            a.execute(runnable);
        } catch (Throwable unused) {
        }
    }
}
