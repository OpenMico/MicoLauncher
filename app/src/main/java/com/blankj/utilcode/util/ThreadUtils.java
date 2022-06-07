package com.blankj.utilcode.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import java.lang.Thread;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public final class ThreadUtils {
    private static final Handler a = new Handler(Looper.getMainLooper());
    private static final Map<Integer, Map<Integer, ExecutorService>> b = new HashMap();
    private static final Map<Task, ExecutorService> c = new ConcurrentHashMap();
    private static final int d = Runtime.getRuntime().availableProcessors();
    private static final Timer e = new Timer();
    private static Executor f;

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static Handler getMainHandler() {
        return a;
    }

    public static void runOnUiThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            a.post(runnable);
        }
    }

    public static void runOnUiThreadDelayed(Runnable runnable, long j) {
        a.postDelayed(runnable, j);
    }

    public static ExecutorService getFixedPool(@IntRange(from = 1) int i) {
        return a(i);
    }

    public static ExecutorService getFixedPool(@IntRange(from = 1) int i, @IntRange(from = 1, to = 10) int i2) {
        return a(i, i2);
    }

    public static ExecutorService getSinglePool() {
        return a(-1);
    }

    public static ExecutorService getSinglePool(@IntRange(from = 1, to = 10) int i) {
        return a(-1, i);
    }

    public static ExecutorService getCachedPool() {
        return a(-2);
    }

    public static ExecutorService getCachedPool(@IntRange(from = 1, to = 10) int i) {
        return a(-2, i);
    }

    public static ExecutorService getIoPool() {
        return a(-4);
    }

    public static ExecutorService getIoPool(@IntRange(from = 1, to = 10) int i) {
        return a(-4, i);
    }

    public static ExecutorService getCpuPool() {
        return a(-8);
    }

    public static ExecutorService getCpuPool(@IntRange(from = 1, to = 10) int i) {
        return a(-8, i);
    }

    public static <T> void executeByFixed(@IntRange(from = 1) int i, Task<T> task) {
        a(a(i), task);
    }

    public static <T> void executeByFixed(@IntRange(from = 1) int i, Task<T> task, @IntRange(from = 1, to = 10) int i2) {
        a(a(i, i2), task);
    }

    public static <T> void executeByFixedWithDelay(@IntRange(from = 1) int i, Task<T> task, long j, TimeUnit timeUnit) {
        a(a(i), task, j, timeUnit);
    }

    public static <T> void executeByFixedWithDelay(@IntRange(from = 1) int i, Task<T> task, long j, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i2) {
        a(a(i, i2), task, j, timeUnit);
    }

    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) int i, Task<T> task, long j, TimeUnit timeUnit) {
        a(a(i), task, 0L, j, timeUnit);
    }

    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) int i, Task<T> task, long j, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i2) {
        a(a(i, i2), task, 0L, j, timeUnit);
    }

    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) int i, Task<T> task, long j, long j2, TimeUnit timeUnit) {
        a(a(i), task, j, j2, timeUnit);
    }

    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) int i, Task<T> task, long j, long j2, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i2) {
        a(a(i, i2), task, j, j2, timeUnit);
    }

    public static <T> void executeBySingle(Task<T> task) {
        a(a(-1), task);
    }

    public static <T> void executeBySingle(Task<T> task, @IntRange(from = 1, to = 10) int i) {
        a(a(-1, i), task);
    }

    public static <T> void executeBySingleWithDelay(Task<T> task, long j, TimeUnit timeUnit) {
        a(a(-1), task, j, timeUnit);
    }

    public static <T> void executeBySingleWithDelay(Task<T> task, long j, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i) {
        a(a(-1, i), task, j, timeUnit);
    }

    public static <T> void executeBySingleAtFixRate(Task<T> task, long j, TimeUnit timeUnit) {
        a(a(-1), task, 0L, j, timeUnit);
    }

    public static <T> void executeBySingleAtFixRate(Task<T> task, long j, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i) {
        a(a(-1, i), task, 0L, j, timeUnit);
    }

    public static <T> void executeBySingleAtFixRate(Task<T> task, long j, long j2, TimeUnit timeUnit) {
        a(a(-1), task, j, j2, timeUnit);
    }

    public static <T> void executeBySingleAtFixRate(Task<T> task, long j, long j2, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i) {
        a(a(-1, i), task, j, j2, timeUnit);
    }

    public static <T> void executeByCached(Task<T> task) {
        a(a(-2), task);
    }

    public static <T> void executeByCached(Task<T> task, @IntRange(from = 1, to = 10) int i) {
        a(a(-2, i), task);
    }

    public static <T> void executeByCachedWithDelay(Task<T> task, long j, TimeUnit timeUnit) {
        a(a(-2), task, j, timeUnit);
    }

    public static <T> void executeByCachedWithDelay(Task<T> task, long j, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i) {
        a(a(-2, i), task, j, timeUnit);
    }

    public static <T> void executeByCachedAtFixRate(Task<T> task, long j, TimeUnit timeUnit) {
        a(a(-2), task, 0L, j, timeUnit);
    }

    public static <T> void executeByCachedAtFixRate(Task<T> task, long j, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i) {
        a(a(-2, i), task, 0L, j, timeUnit);
    }

    public static <T> void executeByCachedAtFixRate(Task<T> task, long j, long j2, TimeUnit timeUnit) {
        a(a(-2), task, j, j2, timeUnit);
    }

    public static <T> void executeByCachedAtFixRate(Task<T> task, long j, long j2, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i) {
        a(a(-2, i), task, j, j2, timeUnit);
    }

    public static <T> void executeByIo(Task<T> task) {
        a(a(-4), task);
    }

    public static <T> void executeByIo(Task<T> task, @IntRange(from = 1, to = 10) int i) {
        a(a(-4, i), task);
    }

    public static <T> void executeByIoWithDelay(Task<T> task, long j, TimeUnit timeUnit) {
        a(a(-4), task, j, timeUnit);
    }

    public static <T> void executeByIoWithDelay(Task<T> task, long j, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i) {
        a(a(-4, i), task, j, timeUnit);
    }

    public static <T> void executeByIoAtFixRate(Task<T> task, long j, TimeUnit timeUnit) {
        a(a(-4), task, 0L, j, timeUnit);
    }

    public static <T> void executeByIoAtFixRate(Task<T> task, long j, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i) {
        a(a(-4, i), task, 0L, j, timeUnit);
    }

    public static <T> void executeByIoAtFixRate(Task<T> task, long j, long j2, TimeUnit timeUnit) {
        a(a(-4), task, j, j2, timeUnit);
    }

    public static <T> void executeByIoAtFixRate(Task<T> task, long j, long j2, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i) {
        a(a(-4, i), task, j, j2, timeUnit);
    }

    public static <T> void executeByCpu(Task<T> task) {
        a(a(-8), task);
    }

    public static <T> void executeByCpu(Task<T> task, @IntRange(from = 1, to = 10) int i) {
        a(a(-8, i), task);
    }

    public static <T> void executeByCpuWithDelay(Task<T> task, long j, TimeUnit timeUnit) {
        a(a(-8), task, j, timeUnit);
    }

    public static <T> void executeByCpuWithDelay(Task<T> task, long j, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i) {
        a(a(-8, i), task, j, timeUnit);
    }

    public static <T> void executeByCpuAtFixRate(Task<T> task, long j, TimeUnit timeUnit) {
        a(a(-8), task, 0L, j, timeUnit);
    }

    public static <T> void executeByCpuAtFixRate(Task<T> task, long j, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i) {
        a(a(-8, i), task, 0L, j, timeUnit);
    }

    public static <T> void executeByCpuAtFixRate(Task<T> task, long j, long j2, TimeUnit timeUnit) {
        a(a(-8), task, j, j2, timeUnit);
    }

    public static <T> void executeByCpuAtFixRate(Task<T> task, long j, long j2, TimeUnit timeUnit, @IntRange(from = 1, to = 10) int i) {
        a(a(-8, i), task, j, j2, timeUnit);
    }

    public static <T> void executeByCustom(ExecutorService executorService, Task<T> task) {
        a(executorService, task);
    }

    public static <T> void executeByCustomWithDelay(ExecutorService executorService, Task<T> task, long j, TimeUnit timeUnit) {
        a(executorService, task, j, timeUnit);
    }

    public static <T> void executeByCustomAtFixRate(ExecutorService executorService, Task<T> task, long j, TimeUnit timeUnit) {
        a(executorService, task, 0L, j, timeUnit);
    }

    public static <T> void executeByCustomAtFixRate(ExecutorService executorService, Task<T> task, long j, long j2, TimeUnit timeUnit) {
        a(executorService, task, j, j2, timeUnit);
    }

    public static void cancel(Task task) {
        if (task != null) {
            task.cancel();
        }
    }

    public static void cancel(Task... taskArr) {
        if (!(taskArr == null || taskArr.length == 0)) {
            for (Task task : taskArr) {
                if (task != null) {
                    task.cancel();
                }
            }
        }
    }

    public static void cancel(List<Task> list) {
        if (!(list == null || list.size() == 0)) {
            for (Task task : list) {
                if (task != null) {
                    task.cancel();
                }
            }
        }
    }

    public static void cancel(ExecutorService executorService) {
        if (executorService instanceof b) {
            for (Map.Entry<Task, ExecutorService> entry : c.entrySet()) {
                if (entry.getValue() == executorService) {
                    cancel(entry.getKey());
                }
            }
            return;
        }
        Log.e("ThreadUtils", "The executorService is not ThreadUtils's pool.");
    }

    public static void setDeliver(Executor executor) {
        f = executor;
    }

    private static <T> void a(ExecutorService executorService, Task<T> task) {
        b(executorService, task, 0L, 0L, null);
    }

    private static <T> void a(ExecutorService executorService, Task<T> task, long j, TimeUnit timeUnit) {
        b(executorService, task, j, 0L, timeUnit);
    }

    private static <T> void a(ExecutorService executorService, Task<T> task, long j, long j2, TimeUnit timeUnit) {
        b(executorService, task, j, j2, timeUnit);
    }

    private static <T> void b(final ExecutorService executorService, final Task<T> task, long j, long j2, TimeUnit timeUnit) {
        synchronized (c) {
            if (c.get(task) != null) {
                Log.e("ThreadUtils", "Task can only be executed once.");
                return;
            }
            c.put(task, executorService);
            if (j2 != 0) {
                task.a(true);
                e.scheduleAtFixedRate(new TimerTask() { // from class: com.blankj.utilcode.util.ThreadUtils.2
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        executorService.execute(task);
                    }
                }, timeUnit.toMillis(j), timeUnit.toMillis(j2));
            } else if (j == 0) {
                executorService.execute(task);
            } else {
                e.schedule(new TimerTask() { // from class: com.blankj.utilcode.util.ThreadUtils.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        executorService.execute(task);
                    }
                }, timeUnit.toMillis(j));
            }
        }
    }

    private static ExecutorService a(int i) {
        return a(i, 5);
    }

    private static ExecutorService a(int i, int i2) {
        ExecutorService executorService;
        synchronized (b) {
            Map<Integer, ExecutorService> map = b.get(Integer.valueOf(i));
            if (map == null) {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                executorService = b.b(i, i2);
                concurrentHashMap.put(Integer.valueOf(i2), executorService);
                b.put(Integer.valueOf(i), concurrentHashMap);
            } else {
                executorService = map.get(Integer.valueOf(i2));
                if (executorService == null) {
                    executorService = b.b(i, i2);
                    map.put(Integer.valueOf(i2), executorService);
                }
            }
        }
        return executorService;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class b extends ThreadPoolExecutor {
        private final AtomicInteger a = new AtomicInteger();
        private a b;

        /* JADX INFO: Access modifiers changed from: private */
        public static ExecutorService b(int i, int i2) {
            if (i == -8) {
                return new b(ThreadUtils.d + 1, (ThreadUtils.d * 2) + 1, 30L, TimeUnit.SECONDS, new a(true), new c(ai.w, i2));
            }
            if (i == -4) {
                return new b((ThreadUtils.d * 2) + 1, (ThreadUtils.d * 2) + 1, 30L, TimeUnit.SECONDS, new a(), new c("io", i2));
            }
            switch (i) {
                case -2:
                    return new b(0, 128, 60L, TimeUnit.SECONDS, new a(true), new c("cached", i2));
                case -1:
                    return new b(1, 1, 0L, TimeUnit.MILLISECONDS, new a(), new c(LoopType.SINGLE, i2));
                default:
                    TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                    a aVar = new a();
                    return new b(i, i, 0L, timeUnit, aVar, new c("fixed(" + i + ")", i2));
            }
        }

        b(int i, int i2, long j, TimeUnit timeUnit, a aVar, ThreadFactory threadFactory) {
            super(i, i2, j, timeUnit, aVar, threadFactory);
            aVar.mPool = this;
            this.b = aVar;
        }

        @Override // java.util.concurrent.ThreadPoolExecutor
        protected void afterExecute(Runnable runnable, Throwable th) {
            this.a.decrementAndGet();
            super.afterExecute(runnable, th);
        }

        @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException("Argument 'command' of type Runnable (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            } else if (!isShutdown()) {
                this.a.incrementAndGet();
                try {
                    super.execute(runnable);
                } catch (RejectedExecutionException unused) {
                    Log.e("ThreadUtils", "This will not happen!");
                    this.b.offer(runnable);
                } catch (Throwable unused2) {
                    this.a.decrementAndGet();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a extends LinkedBlockingQueue<Runnable> {
        private int mCapacity;
        private volatile b mPool;

        a() {
            this.mCapacity = Integer.MAX_VALUE;
        }

        a(boolean z) {
            this.mCapacity = Integer.MAX_VALUE;
            if (z) {
                this.mCapacity = 0;
            }
        }

        /* renamed from: a */
        public boolean offer(@NonNull Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException("Argument 'runnable' of type Runnable (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            } else if (this.mCapacity > size() || this.mPool == null || this.mPool.getPoolSize() >= this.mPool.getMaximumPoolSize()) {
                return super.offer(runnable);
            } else {
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class c extends AtomicLong implements ThreadFactory {
        private static final AtomicInteger a = new AtomicInteger(1);
        private static final long serialVersionUID = -9209200509960368598L;
        private final boolean isDaemon;
        private final String namePrefix;
        private final int priority;

        c(String str, int i) {
            this(str, i, false);
        }

        c(String str, int i, boolean z) {
            this.namePrefix = str + "-pool-" + a.getAndIncrement() + "-thread-";
            this.priority = i;
            this.isDaemon = z;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(@NonNull Runnable runnable) {
            if (runnable != null) {
                Thread thread = new Thread(runnable, this.namePrefix + getAndIncrement()) { // from class: com.blankj.utilcode.util.ThreadUtils.c.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        try {
                            super.run();
                        } catch (Throwable th) {
                            Log.e("ThreadUtils", "Request threw uncaught throwable", th);
                        }
                    }
                };
                thread.setDaemon(this.isDaemon);
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.blankj.utilcode.util.ThreadUtils.c.2
                    @Override // java.lang.Thread.UncaughtExceptionHandler
                    public void uncaughtException(Thread thread2, Throwable th) {
                        System.out.println(th);
                    }
                });
                thread.setPriority(this.priority);
                return thread;
            }
            throw new NullPointerException("Argument 'r' of type Runnable (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    /* loaded from: classes.dex */
    public static abstract class SimpleTask<T> extends Task<T> {
        @Override // com.blankj.utilcode.util.ThreadUtils.Task
        public void onCancel() {
            Log.e("ThreadUtils", "onCancel: " + Thread.currentThread());
        }

        @Override // com.blankj.utilcode.util.ThreadUtils.Task
        public void onFail(Throwable th) {
            Log.e("ThreadUtils", "onFail: ", th);
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Task<T> implements Runnable {
        private final AtomicInteger a = new AtomicInteger(0);
        private volatile boolean b;
        private volatile Thread c;
        private Timer d;
        private long e;
        private OnTimeoutListener f;
        private Executor g;

        /* loaded from: classes.dex */
        public interface OnTimeoutListener {
            void onTimeout();
        }

        public abstract T doInBackground() throws Throwable;

        public abstract void onCancel();

        public abstract void onFail(Throwable th);

        public abstract void onSuccess(T t);

        @Override // java.lang.Runnable
        public void run() {
            if (this.b) {
                if (this.c == null) {
                    if (this.a.compareAndSet(0, 1)) {
                        this.c = Thread.currentThread();
                        if (this.f != null) {
                            Log.w("ThreadUtils", "Scheduled task doesn't support timeout.");
                        }
                    } else {
                        return;
                    }
                } else if (this.a.get() != 1) {
                    return;
                }
            } else if (this.a.compareAndSet(0, 1)) {
                this.c = Thread.currentThread();
                if (this.f != null) {
                    this.d = new Timer();
                    this.d.schedule(new TimerTask() { // from class: com.blankj.utilcode.util.ThreadUtils.Task.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            if (!Task.this.isDone() && Task.this.f != null) {
                                Task.this.a();
                                Task.this.f.onTimeout();
                                Task.this.onDone();
                            }
                        }
                    }, this.e);
                }
            } else {
                return;
            }
            try {
                final T doInBackground = doInBackground();
                if (this.b) {
                    if (this.a.get() == 1) {
                        b().execute(new Runnable() { // from class: com.blankj.utilcode.util.ThreadUtils.Task.2
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // java.lang.Runnable
                            public void run() {
                                Task.this.onSuccess(doInBackground);
                            }
                        });
                    }
                } else if (this.a.compareAndSet(1, 3)) {
                    b().execute(new Runnable() { // from class: com.blankj.utilcode.util.ThreadUtils.Task.3
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.lang.Runnable
                        public void run() {
                            Task.this.onSuccess(doInBackground);
                            Task.this.onDone();
                        }
                    });
                }
            } catch (InterruptedException unused) {
                this.a.compareAndSet(4, 5);
            } catch (Throwable th) {
                if (this.a.compareAndSet(1, 2)) {
                    b().execute(new Runnable() { // from class: com.blankj.utilcode.util.ThreadUtils.Task.4
                        @Override // java.lang.Runnable
                        public void run() {
                            Task.this.onFail(th);
                            Task.this.onDone();
                        }
                    });
                }
            }
        }

        public void cancel() {
            cancel(true);
        }

        public void cancel(boolean z) {
            synchronized (this.a) {
                if (this.a.get() <= 1) {
                    this.a.set(4);
                    if (z && this.c != null) {
                        this.c.interrupt();
                    }
                    b().execute(new Runnable() { // from class: com.blankj.utilcode.util.ThreadUtils.Task.5
                        @Override // java.lang.Runnable
                        public void run() {
                            Task.this.onCancel();
                            Task.this.onDone();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a() {
            synchronized (this.a) {
                if (this.a.get() <= 1) {
                    this.a.set(6);
                    if (this.c != null) {
                        this.c.interrupt();
                    }
                }
            }
        }

        public boolean isCanceled() {
            return this.a.get() >= 4;
        }

        public boolean isDone() {
            return this.a.get() > 1;
        }

        public Task<T> setDeliver(Executor executor) {
            this.g = executor;
            return this;
        }

        public Task<T> setTimeout(long j, OnTimeoutListener onTimeoutListener) {
            this.e = j;
            this.f = onTimeoutListener;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(boolean z) {
            this.b = z;
        }

        private Executor b() {
            Executor executor = this.g;
            return executor == null ? ThreadUtils.d() : executor;
        }

        @CallSuper
        protected void onDone() {
            ThreadUtils.c.remove(this);
            Timer timer = this.d;
            if (timer != null) {
                timer.cancel();
                this.d = null;
                this.f = null;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class SyncValue<T> {
        private CountDownLatch a = new CountDownLatch(1);
        private AtomicBoolean b = new AtomicBoolean();
        private T c;

        public void setValue(T t) {
            if (this.b.compareAndSet(false, true)) {
                this.c = t;
                this.a.countDown();
            }
        }

        public T getValue() {
            if (!this.b.get()) {
                try {
                    this.a.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return this.c;
        }

        public T getValue(long j, TimeUnit timeUnit, T t) {
            if (!this.b.get()) {
                try {
                    this.a.await(j, timeUnit);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return t;
                }
            }
            return this.c;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Executor d() {
        if (f == null) {
            f = new Executor() { // from class: com.blankj.utilcode.util.ThreadUtils.3
                @Override // java.util.concurrent.Executor
                public void execute(@NonNull Runnable runnable) {
                    if (runnable != null) {
                        ThreadUtils.runOnUiThread(runnable);
                        return;
                    }
                    throw new NullPointerException("Argument 'command' of type Runnable (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
                }
            };
        }
        return f;
    }
}
