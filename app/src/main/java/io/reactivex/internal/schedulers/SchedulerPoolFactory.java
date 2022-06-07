package io.reactivex.internal.schedulers;

import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class SchedulerPoolFactory {
    public static final boolean PURGE_ENABLED;
    public static final int PURGE_PERIOD_SECONDS;
    static final AtomicReference<ScheduledExecutorService> a = new AtomicReference<>();
    static final Map<ScheduledThreadPoolExecutor, Object> b = new ConcurrentHashMap();

    private SchedulerPoolFactory() {
        throw new IllegalStateException("No instances!");
    }

    static {
        b bVar = new b();
        PURGE_ENABLED = a(true, "rx2.purge-enabled", true, true, (Function<String, String>) bVar);
        PURGE_PERIOD_SECONDS = a(PURGE_ENABLED, "rx2.purge-period-seconds", 1, 1, bVar);
        start();
    }

    public static void start() {
        a(PURGE_ENABLED);
    }

    static void a(boolean z) {
        if (z) {
            while (true) {
                ScheduledExecutorService scheduledExecutorService = a.get();
                if (scheduledExecutorService == null) {
                    ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1, new RxThreadFactory("RxSchedulerPurge"));
                    if (a.compareAndSet(scheduledExecutorService, newScheduledThreadPool)) {
                        a aVar = new a();
                        int i = PURGE_PERIOD_SECONDS;
                        newScheduledThreadPool.scheduleAtFixedRate(aVar, i, i, TimeUnit.SECONDS);
                        return;
                    }
                    newScheduledThreadPool.shutdownNow();
                } else {
                    return;
                }
            }
        }
    }

    public static void shutdown() {
        ScheduledExecutorService andSet = a.getAndSet(null);
        if (andSet != null) {
            andSet.shutdownNow();
        }
        b.clear();
    }

    static int a(boolean z, String str, int i, int i2, Function<String, String> function) {
        if (!z) {
            return i2;
        }
        try {
            String apply = function.apply(str);
            return apply == null ? i : Integer.parseInt(apply);
        } catch (Throwable unused) {
            return i;
        }
    }

    static boolean a(boolean z, String str, boolean z2, boolean z3, Function<String, String> function) {
        if (!z) {
            return z3;
        }
        try {
            String apply = function.apply(str);
            return apply == null ? z2 : "true".equals(apply);
        } catch (Throwable unused) {
            return z2;
        }
    }

    /* loaded from: classes4.dex */
    static final class b implements Function<String, String> {
        b() {
        }

        /* renamed from: a */
        public String apply(String str) throws Exception {
            return System.getProperty(str);
        }
    }

    public static ScheduledExecutorService create(ThreadFactory threadFactory) {
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1, threadFactory);
        a(PURGE_ENABLED, newScheduledThreadPool);
        return newScheduledThreadPool;
    }

    static void a(boolean z, ScheduledExecutorService scheduledExecutorService) {
        if (z && (scheduledExecutorService instanceof ScheduledThreadPoolExecutor)) {
            b.put((ScheduledThreadPoolExecutor) scheduledExecutorService, scheduledExecutorService);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Iterator it = new ArrayList(SchedulerPoolFactory.b.keySet()).iterator();
            while (it.hasNext()) {
                ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) it.next();
                if (scheduledThreadPoolExecutor.isShutdown()) {
                    SchedulerPoolFactory.b.remove(scheduledThreadPoolExecutor);
                } else {
                    scheduledThreadPoolExecutor.purge();
                }
            }
        }
    }
}
