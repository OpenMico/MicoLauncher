package com.xiaomi.mico.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
public class ThreadsImplementation {
    private final a a = new a(0);
    private final a b = new a(1);
    private final RejectedExecutionHandler c = new RejectedExecutionHandler() { // from class: com.xiaomi.mico.utils.ThreadsImplementation.1
        @Override // java.util.concurrent.RejectedExecutionHandler
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            ThreadsImplementation.this.f().post(runnable);
        }
    };
    private final c d = new c(UtilsConfig.getIoCorePoolSize(), UtilsConfig.getIoThreadsCount(), TimeUnit.SECONDS.toMillis(10), this.a, this.c);
    private final c e = new c(UtilsConfig.getComputationThreadsCount(), TimeUnit.SECONDS.toMillis(2), this.b, this.c);
    private final ScheduledExecutorService f = new ScheduledThreadPoolExecutor(1, new a(), this.c);
    private final Object g = new Object();
    @GuardedBy("workHandlerLock")
    private final AtomicReference<Handler> h = new AtomicReference<>(null);
    @GuardedBy("workHandlerLock")
    private final AtomicReference<Handler> i = new AtomicReference<>(null);
    private final Handler j = new Handler(Looper.getMainLooper());
    private Map<Object, b> k = Collections.synchronizedMap(new IdentityHashMap());

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class b {
        long a;
        long b;

        b() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ThreadsImplementation() {
        if (UtilsConfig.isEnableTracing()) {
            e().scheduleWithFixedDelay(new Runnable() { // from class: com.xiaomi.mico.utils.ThreadsImplementation.2
                @Override // java.lang.Runnable
                public void run() {
                    if (UtilsConfig.isEnableTracing()) {
                        ThreadsImplementation.this.n();
                    }
                }
            }, 0L, (Math.min(UtilsConfig.getHeavyWorkMaxDuration(), UtilsConfig.getLightWorkMaxDuration()) / 2) + 1, TimeUnit.MILLISECONDS);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ExecutorService a() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return UtilsConfig.getIoThreadsCount();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ExecutorService c() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int d() {
        return UtilsConfig.getComputationThreadsCount();
    }

    ScheduledExecutorService e() {
        return this.f;
    }

    private Handler i() {
        return this.h.get();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Handler f() {
        return a(this.h, UtilsConfig.getLightWorkMaxDuration());
    }

    private Handler j() {
        return this.i.get();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Handler g() {
        return a(this.i, UtilsConfig.getHeavyWorkMaxDuration());
    }

    private Handler a(@NonNull AtomicReference<Handler> atomicReference, final long j) {
        Handler handler = atomicReference.get();
        if (handler != null) {
            return handler;
        }
        synchronized (this.g) {
            Handler handler2 = atomicReference.get();
            if (handler2 != null) {
                return handler2;
            }
            HandlerThread handlerThread = new HandlerThread("mico_work_handler_thread", 10);
            handlerThread.start();
            Handler handler3 = new Handler(handlerThread.getLooper()) { // from class: com.xiaomi.mico.utils.ThreadsImplementation.3
                @Override // android.os.Handler
                public void dispatchMessage(Message message) {
                    Runnable callback = message.getCallback();
                    if (callback != null) {
                        ThreadsImplementation.this.a((Object) callback, j);
                    }
                    super.dispatchMessage(message);
                    if (callback != null) {
                        ThreadsImplementation.this.a((Object) callback);
                    }
                }
            };
            atomicReference.set(handler3);
            return handler3;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Runnable runnable) {
        Handler i = i();
        if (i != null) {
            i.removeCallbacks(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(Runnable runnable) {
        Handler j = j();
        if (j != null) {
            j.removeCallbacks(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(Runnable runnable) {
        this.j.post(runnable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Runnable runnable, long j) {
        this.j.postDelayed(runnable, j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d(Runnable runnable) {
        this.j.removeCallbacks(runnable);
    }

    public void verifyThread() {
        if (!k()) {
            if (!UtilsConfig.isCrashOnBugDetected()) {
                LogCallback m = m();
                if (m != null) {
                    m.e("Unexpectedly in main thread", new Object[0]);
                }
                UtilsConfig.getLogCallback().printStackTrace("BlockMainThreadError");
                return;
            }
            throw new IllegalStateException("Unexpectedly in main thread");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void h() {
        if (!l()) {
            if (!UtilsConfig.isCrashOnBugDetected()) {
                Thread currentThread = Thread.currentThread();
                LogCallback m = m();
                if (m != null) {
                    m.e("Not main thread %s %s", currentThread.getName(), Long.valueOf(currentThread.getId()));
                }
                UtilsConfig.getLogCallback().printStackTrace("NotMainThreadError");
                return;
            }
            throw new IllegalStateException("Non main thread");
        }
    }

    private boolean k() {
        return Looper.getMainLooper() != Looper.myLooper();
    }

    private boolean l() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LogCallback m() {
        return UtilsConfig.getLogCallback();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Object obj, long j) {
        b bVar = new b();
        bVar.a = o();
        bVar.b = j;
        this.k.put(obj, bVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void n() {
        LogCallback m = m();
        Set<Map.Entry<Object, b>> entrySet = this.k.entrySet();
        this.k.size();
        for (Map.Entry<Object, b> entry : entrySet) {
            if (o() - entry.getValue().a >= entry.getValue().b) {
                m.w("Task %s running duration exceeds threshold %s", entry.getKey(), Long.valueOf(entry.getValue().b));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Object obj) {
        this.k.remove(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long o() {
        return SystemClock.uptimeMillis();
    }

    /* loaded from: classes3.dex */
    public static class SameThreadChecker {
        private final int a = Integer.MIN_VALUE;
        private final String b;
        private volatile long c;

        public static SameThreadChecker get(String str) {
            return new SameThreadChecker(str);
        }

        private SameThreadChecker(String str) {
            this.b = str;
        }

        public void setupThread() {
            Thread currentThread = Thread.currentThread();
            this.c = currentThread.getId();
            UtilsConfig.getLogCallback().i("%s Initial thread %s %s", this.b, currentThread.getName(), Long.valueOf(currentThread.getId()));
        }

        public void check() {
            if (notSameThread()) {
                if (!UtilsConfig.isCrashOnBugDetected()) {
                    Thread currentThread = Thread.currentThread();
                    UtilsConfig.getLogCallback().e("Not same thread %s %s", currentThread.getName(), Long.valueOf(currentThread.getId()));
                    return;
                }
                throw new IllegalStateException("Not same thread");
            }
        }

        public boolean notSameThread() {
            long id = Thread.currentThread().getId();
            return (id == -2147483648L || id == this.c) ? false : true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class c extends ThreadPoolExecutor {
        private long b;
        private AtomicInteger c = new AtomicInteger(0);
        private Set<Object> d = new CopyOnWriteArraySet();

        c(int i, long j, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
            super(1, i, 1L, TimeUnit.MINUTES, new SynchronousQueue(), threadFactory, rejectedExecutionHandler);
            this.b = j;
        }

        c(int i, int i2, long j, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
            super(i, i2, 1L, TimeUnit.MINUTES, new LinkedBlockingDeque(), threadFactory, rejectedExecutionHandler);
            this.b = j;
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        @NonNull
        public Future<?> submit(Runnable runnable) {
            return super.submit(a(runnable));
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        @NonNull
        public <T> Future<T> submit(Runnable runnable, T t) {
            ThreadsImplementation.this.m().i("submit task %s", runnable);
            return super.submit(a(runnable), t);
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        @NonNull
        public <T> Future<T> submit(Callable<T> callable) {
            return super.submit(a((Callable) callable));
        }

        @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            super.execute(a(runnable));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Runnable a(final Runnable runnable) {
            return !UtilsConfig.isEnableTracing() ? runnable : new Runnable() { // from class: com.xiaomi.mico.utils.ThreadsImplementation.c.1
                @Override // java.lang.Runnable
                public void run() {
                    Process.setThreadPriority(UtilsConfig.getThreadPriority());
                    long a = c.this.a((Object) runnable);
                    runnable.run();
                    c.this.a(a, runnable);
                }

                public String toString() {
                    return runnable.toString();
                }
            };
        }

        private <T> Callable<T> a(final Callable<T> callable) {
            return !UtilsConfig.isEnableTracing() ? callable : new Callable<T>() { // from class: com.xiaomi.mico.utils.ThreadsImplementation.c.2
                /* JADX WARN: Type inference failed for: r2v4, types: [T, java.lang.Object] */
                @Override // java.util.concurrent.Callable
                public T call() throws Exception {
                    long j;
                    try {
                        Process.setThreadPriority(UtilsConfig.getThreadPriority());
                        j = 0;
                        try {
                            j = c.this.a((Object) callable);
                            return callable.call();
                        } catch (Exception e) {
                            ThreadsImplementation.this.m().e("PossibleFC %s", e);
                            throw e;
                        }
                    } finally {
                        c.this.a(j, callable);
                    }
                }
            };
        }

        private void a() {
            for (Object obj : this.d) {
                ThreadsImplementation.this.m().i("task %s", obj);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long a(Object obj) {
            this.d.add(obj);
            this.c.incrementAndGet();
            ThreadsImplementation.this.a(obj, this.b);
            return ThreadsImplementation.this.o();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(long j, Object obj) {
            this.d.remove(obj);
            ThreadsImplementation.this.a(obj);
            int decrementAndGet = this.c.decrementAndGet();
            int maximumPoolSize = getMaximumPoolSize();
            if (decrementAndGet >= maximumPoolSize - 1) {
                ThreadsImplementation.this.m().e("heavy thread pool load, max thread pool size %s, pending tasks %s", Integer.valueOf(maximumPoolSize), Integer.valueOf(decrementAndGet));
                a();
            }
            long o = ThreadsImplementation.this.o() - j;
            if (o > this.b) {
                ThreadsImplementation.this.m().i("task %s run time costs %s millis", obj, Long.valueOf(o));
            }
        }
    }

    /* loaded from: classes3.dex */
    private class a implements ThreadFactory {
        private AtomicInteger b;
        private final int c;

        a(int i) {
            this.b = new AtomicInteger(0);
            this.c = i;
        }

        a() {
            this.b = new AtomicInteger(0);
            this.c = 2;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(@NonNull Runnable runnable) {
            int i = this.c;
            if (i == 0 || i == 1) {
                runnable = (this.c == 0 ? ThreadsImplementation.this.d : ThreadsImplementation.this.e).a(runnable);
            }
            Thread thread = new Thread(runnable, "mico-thread-pool" + this.b.incrementAndGet());
            thread.setPriority(5);
            return thread;
        }
    }
}
