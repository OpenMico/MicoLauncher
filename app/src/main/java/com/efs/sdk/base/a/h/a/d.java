package com.efs.sdk.base.a.h.a;

import androidx.annotation.NonNull;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class d {
    private static ThreadPoolExecutor a = new ThreadPoolExecutor(2, 2, 10, TimeUnit.MINUTES, new LinkedBlockingQueue(Integer.MAX_VALUE), new ThreadPoolExecutor.DiscardOldestPolicy());

    public static Future<?> a(@NonNull Runnable runnable) {
        try {
            return a.submit(runnable);
        } catch (Throwable th) {
            com.efs.sdk.base.a.h.d.b("efs.util.concurrent", "submit task error!", th);
            return null;
        }
    }
}
