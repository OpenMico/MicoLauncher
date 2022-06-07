package com.xiaomi.ai.b;

import com.xiaomi.ai.log.Logger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class c {
    public static final ExecutorService a;
    public static final ThreadPoolExecutor b;
    private static final int d = Runtime.getRuntime().availableProcessors();
    private static final BlockingQueue<Runnable> e = new LinkedBlockingQueue(128);
    private static int f = Integer.MAX_VALUE;
    private static boolean g = false;
    private static ScheduledThreadPoolExecutor c = new ScheduledThreadPoolExecutor(2, new e("GlobalThread-delay", 5));

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 32, 10L, TimeUnit.SECONDS, e, new e("GlobalThread-core", 5));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        a = threadPoolExecutor;
        c.setKeepAliveTime(10L, TimeUnit.SECONDS);
        c.allowCoreThreadTimeOut(true);
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(1, 1, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue(128), new e("GlobalThread-log_upload", 1), new ThreadPoolExecutor.DiscardOldestPolicy());
        threadPoolExecutor2.allowCoreThreadTimeOut(true);
        b = threadPoolExecutor2;
    }

    public static ScheduledFuture a(Runnable runnable, long j) {
        String str;
        String str2;
        if (!g) {
            if (f >= 21) {
                c.setRemoveOnCancelPolicy(true);
                g = true;
            } else {
                str = "GlobalThread";
                str2 = "non-supported android api:" + f;
                Logger.c(str, str2, false);
                return null;
            }
        }
        if (c.getQueue().size() > 128) {
            Logger.c("GlobalThread", "queue full .error", false);
            throw new RejectedExecutionException("GlobalThread,queue overflow .error");
        } else if (runnable != null) {
            return c.schedule(runnable, j, TimeUnit.MILLISECONDS);
        } else {
            str = "GlobalThread";
            str2 = "runnable null .error";
            Logger.c(str, str2, false);
            return null;
        }
    }

    public static void a(int i) {
        if (f == Integer.MAX_VALUE) {
            f = i;
        }
        if (f >= 21) {
            c.setRemoveOnCancelPolicy(true);
        }
    }

    public static void a(ScheduledFuture scheduledFuture) {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        } else {
            Logger.b("GlobalThread", "removeCallBacks error,empty future", false);
        }
    }
}
