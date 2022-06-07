package kotlinx.coroutines;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.c;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CommonPool.kt */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0011\u001a\u0004\u0018\u0001H\u0012\"\u0004\b\u0000\u0010\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\u0082\b¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0019H\u0002J\u001c\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001d2\n\u0010\u0013\u001a\u00060\u001ej\u0002`\u001fH\u0016J\b\u0010 \u001a\u00020\u0006H\u0002J!\u0010!\u001a\u00020\u00102\n\u0010\"\u001a\u0006\u0012\u0002\b\u00030#2\u0006\u0010\u0005\u001a\u00020\u0019H\u0000¢\u0006\u0002\b$J\r\u0010%\u001a\u00020\u0017H\u0000¢\u0006\u0002\b&J\u0015\u0010'\u001a\u00020\u00172\u0006\u0010(\u001a\u00020)H\u0000¢\u0006\u0002\b*J\b\u0010+\u001a\u00020\u0004H\u0016J\r\u0010\u000f\u001a\u00020\u0017H\u0000¢\u0006\u0002\b,R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\r\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lkotlinx/coroutines/CommonPool;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "()V", "DEFAULT_PARALLELISM_PROPERTY_NAME", "", "executor", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "parallelism", "", "getParallelism", "()I", "pool", "requestedParallelism", "usePrivatePool", "", "Try", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "close", "", "createPlainPool", "Ljava/util/concurrent/ExecutorService;", "createPool", "dispatch", c.R, "Lkotlin/coroutines/CoroutineContext;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "getOrCreatePoolSync", "isGoodCommonPool", "fjpClass", "Ljava/lang/Class;", "isGoodCommonPool$kotlinx_coroutines_core", "restore", "restore$kotlinx_coroutines_core", "shutdown", RtspHeaders.Values.TIMEOUT, "", "shutdown$kotlinx_coroutines_core", "toString", "usePrivatePool$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class CommonPool extends ExecutorCoroutineDispatcher {
    @NotNull
    public static final CommonPool INSTANCE = new CommonPool();
    private static final int a;
    private static boolean b;
    @Nullable
    private static volatile Executor pool;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void e() {
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    @NotNull
    public String toString() {
        return "CommonPool";
    }

    private CommonPool() {
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcher
    @NotNull
    public Executor getExecutor() {
        Executor executor = pool;
        return executor == null ? d() : executor;
    }

    static {
        String str;
        int i;
        CommonPool commonPool = INSTANCE;
        try {
            str = System.getProperty("kotlinx.coroutines.default.parallelism");
        } catch (Throwable unused) {
            str = null;
        }
        if (str == null) {
            i = -1;
        } else {
            Integer intOrNull = StringsKt.toIntOrNull(str);
            if (intOrNull == null || intOrNull.intValue() < 1) {
                throw new IllegalStateException(Intrinsics.stringPlus("Expected positive number in kotlinx.coroutines.default.parallelism, but has ", str).toString());
            }
            i = intOrNull.intValue();
        }
        a = i;
    }

    private final int a() {
        Integer valueOf = Integer.valueOf(a);
        if (!(valueOf.intValue() > 0)) {
            valueOf = null;
        }
        if (valueOf == null) {
            return RangesKt.coerceAtLeast(Runtime.getRuntime().availableProcessors() - 1, 1);
        }
        return valueOf.intValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final java.util.concurrent.ExecutorService b() {
        /*
            r6 = this;
            java.lang.SecurityManager r0 = java.lang.System.getSecurityManager()
            if (r0 == 0) goto L_0x000b
            java.util.concurrent.ExecutorService r0 = r6.c()
            return r0
        L_0x000b:
            r0 = 0
            java.lang.String r1 = "java.util.concurrent.ForkJoinPool"
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch: Throwable -> 0x0013
            goto L_0x0014
        L_0x0013:
            r1 = r0
        L_0x0014:
            if (r1 != 0) goto L_0x001b
            java.util.concurrent.ExecutorService r0 = r6.c()
            return r0
        L_0x001b:
            boolean r2 = kotlinx.coroutines.CommonPool.b
            r3 = 0
            if (r2 != 0) goto L_0x004b
            int r2 = kotlinx.coroutines.CommonPool.a
            if (r2 >= 0) goto L_0x004b
            java.lang.String r2 = "commonPool"
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch: Throwable -> 0x0039
            java.lang.reflect.Method r2 = r1.getMethod(r2, r4)     // Catch: Throwable -> 0x0039
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch: Throwable -> 0x0039
            java.lang.Object r2 = r2.invoke(r0, r4)     // Catch: Throwable -> 0x0039
            boolean r4 = r2 instanceof java.util.concurrent.ExecutorService     // Catch: Throwable -> 0x0039
            if (r4 == 0) goto L_0x0039
            java.util.concurrent.ExecutorService r2 = (java.util.concurrent.ExecutorService) r2     // Catch: Throwable -> 0x0039
            goto L_0x003a
        L_0x0039:
            r2 = r0
        L_0x003a:
            if (r2 != 0) goto L_0x003d
            goto L_0x004b
        L_0x003d:
            kotlinx.coroutines.CommonPool r4 = kotlinx.coroutines.CommonPool.INSTANCE
            boolean r4 = r4.isGoodCommonPool$kotlinx_coroutines_core(r1, r2)
            if (r4 == 0) goto L_0x0046
            goto L_0x0047
        L_0x0046:
            r2 = r0
        L_0x0047:
            if (r2 != 0) goto L_0x004a
            goto L_0x004b
        L_0x004a:
            return r2
        L_0x004b:
            r2 = 1
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch: Throwable -> 0x006f
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch: Throwable -> 0x006f
            r4[r3] = r5     // Catch: Throwable -> 0x006f
            java.lang.reflect.Constructor r1 = r1.getConstructor(r4)     // Catch: Throwable -> 0x006f
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch: Throwable -> 0x006f
            kotlinx.coroutines.CommonPool r4 = kotlinx.coroutines.CommonPool.INSTANCE     // Catch: Throwable -> 0x006f
            int r4 = r4.a()     // Catch: Throwable -> 0x006f
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: Throwable -> 0x006f
            r2[r3] = r4     // Catch: Throwable -> 0x006f
            java.lang.Object r1 = r1.newInstance(r2)     // Catch: Throwable -> 0x006f
            boolean r2 = r1 instanceof java.util.concurrent.ExecutorService     // Catch: Throwable -> 0x006f
            if (r2 == 0) goto L_0x006f
            java.util.concurrent.ExecutorService r1 = (java.util.concurrent.ExecutorService) r1     // Catch: Throwable -> 0x006f
            r0 = r1
        L_0x006f:
            if (r0 != 0) goto L_0x0076
            java.util.concurrent.ExecutorService r0 = r6.c()
            return r0
        L_0x0076:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.CommonPool.b():java.util.concurrent.ExecutorService");
    }

    public final boolean isGoodCommonPool$kotlinx_coroutines_core(@NotNull Class<?> cls, @NotNull ExecutorService executorService) {
        executorService.submit($$Lambda$CommonPool$eghxBgz3ViznHUG4r_syaASJ_o.INSTANCE);
        Integer num = null;
        try {
            Object invoke = cls.getMethod("getPoolSize", new Class[0]).invoke(executorService, new Object[0]);
            if (invoke instanceof Integer) {
                num = (Integer) invoke;
            }
        } catch (Throwable unused) {
        }
        return num != null && num.intValue() >= 1;
    }

    private final ExecutorService c() {
        final AtomicInteger atomicInteger = new AtomicInteger();
        return Executors.newFixedThreadPool(a(), new ThreadFactory() { // from class: kotlinx.coroutines.-$$Lambda$CommonPool$PDKI0o-Duc9e5CdSIbdhgq6TQwk
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread a2;
                a2 = CommonPool.a(atomicInteger, runnable);
                return a2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Thread a(AtomicInteger atomicInteger, Runnable runnable) {
        Thread thread = new Thread(runnable, Intrinsics.stringPlus("CommonPool-worker-", Integer.valueOf(atomicInteger.incrementAndGet())));
        thread.setDaemon(true);
        return thread;
    }

    private final synchronized Executor d() {
        ExecutorService executorService;
        executorService = pool;
        if (executorService == null) {
            ExecutorService b2 = b();
            CommonPool commonPool = INSTANCE;
            pool = b2;
            executorService = b2;
        }
        return executorService;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public void dispatch(@NotNull CoroutineContext coroutineContext, @NotNull Runnable runnable) {
        try {
            Executor executor = pool;
            if (executor == null) {
                executor = d();
            }
            AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
            executor.execute(timeSource == null ? runnable : timeSource.wrapTask(runnable));
        } catch (RejectedExecutionException unused) {
            AbstractTimeSource timeSource2 = AbstractTimeSourceKt.getTimeSource();
            if (timeSource2 != null) {
                timeSource2.unTrackTask();
            }
            DefaultExecutor.INSTANCE.enqueue(runnable);
        }
    }

    public final synchronized void usePrivatePool$kotlinx_coroutines_core() {
        shutdown$kotlinx_coroutines_core(0L);
        b = true;
        pool = null;
    }

    public final synchronized void shutdown$kotlinx_coroutines_core(long j) {
        Executor executor = pool;
        ExecutorService executorService = executor instanceof ExecutorService ? (ExecutorService) executor : null;
        if (executorService != null) {
            executorService.shutdown();
            if (j > 0) {
                executorService.awaitTermination(j, TimeUnit.MILLISECONDS);
            }
            for (Runnable runnable : executorService.shutdownNow()) {
                DefaultExecutor.INSTANCE.enqueue(runnable);
            }
        }
        pool = $$Lambda$CommonPool$ei5nkNc1NVjjsln7D6SYfxLZBbA.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void a(Runnable runnable) {
        throw new RejectedExecutionException("CommonPool was shutdown");
    }

    public final synchronized void restore$kotlinx_coroutines_core() {
        shutdown$kotlinx_coroutines_core(0L);
        b = false;
        pool = null;
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcher, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        throw new IllegalStateException("Close cannot be invoked on CommonPool".toString());
    }
}
