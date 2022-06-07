package com.xiaomi.analytics.internal.util;

import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class TaskRunner {
    private static ThreadPoolExecutor a;
    private static int b = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService SINGLE_EXECUTOR = Executors.newSingleThreadExecutor();

    static {
        int i = b;
        a = new ThreadPoolExecutor(i, i, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        a.allowCoreThreadTimeOut(true);
    }

    public static void execute(Runnable runnable) {
        try {
            a.execute(runnable);
        } catch (Exception e) {
            Log.e(ALog.addPrefix("TaskRunner"), "execute e", e);
        }
    }
}
