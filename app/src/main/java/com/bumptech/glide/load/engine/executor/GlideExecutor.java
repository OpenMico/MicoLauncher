package com.bumptech.glide.load.engine.executor;

import android.os.Process;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class GlideExecutor implements ExecutorService {
    private static final long a = TimeUnit.SECONDS.toMillis(10);
    private static volatile int b;
    private final ExecutorService c;

    /* loaded from: classes.dex */
    public interface UncaughtThrowableStrategy {
        public static final UncaughtThrowableStrategy IGNORE = new UncaughtThrowableStrategy() { // from class: com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.1
            @Override // com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy
            public void handle(Throwable th) {
            }
        };
        public static final UncaughtThrowableStrategy LOG = new UncaughtThrowableStrategy() { // from class: com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.2
            @Override // com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy
            public void handle(Throwable th) {
                if (th != null && Log.isLoggable("GlideExecutor", 6)) {
                    Log.e("GlideExecutor", "Request threw uncaught throwable", th);
                }
            }
        };
        public static final UncaughtThrowableStrategy THROW = new UncaughtThrowableStrategy() { // from class: com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.3
            @Override // com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy
            public void handle(Throwable th) {
                if (th != null) {
                    throw new RuntimeException("Request threw uncaught throwable", th);
                }
            }
        };
        public static final UncaughtThrowableStrategy DEFAULT = LOG;

        void handle(Throwable th);
    }

    public static Builder newDiskCacheBuilder() {
        return new Builder(true).setThreadCount(1).setName("disk-cache");
    }

    public static GlideExecutor newDiskCacheExecutor() {
        return newDiskCacheBuilder().build();
    }

    @Deprecated
    public static GlideExecutor newDiskCacheExecutor(UncaughtThrowableStrategy uncaughtThrowableStrategy) {
        return newDiskCacheBuilder().setUncaughtThrowableStrategy(uncaughtThrowableStrategy).build();
    }

    @Deprecated
    public static GlideExecutor newDiskCacheExecutor(int i, String str, UncaughtThrowableStrategy uncaughtThrowableStrategy) {
        return newDiskCacheBuilder().setThreadCount(i).setName(str).setUncaughtThrowableStrategy(uncaughtThrowableStrategy).build();
    }

    public static Builder newSourceBuilder() {
        return new Builder(false).setThreadCount(calculateBestThreadCount()).setName("source");
    }

    public static GlideExecutor newSourceExecutor() {
        return newSourceBuilder().build();
    }

    @Deprecated
    public static GlideExecutor newSourceExecutor(UncaughtThrowableStrategy uncaughtThrowableStrategy) {
        return newSourceBuilder().setUncaughtThrowableStrategy(uncaughtThrowableStrategy).build();
    }

    @Deprecated
    public static GlideExecutor newSourceExecutor(int i, String str, UncaughtThrowableStrategy uncaughtThrowableStrategy) {
        return newSourceBuilder().setThreadCount(i).setName(str).setUncaughtThrowableStrategy(uncaughtThrowableStrategy).build();
    }

    public static GlideExecutor newUnlimitedSourceExecutor() {
        return new GlideExecutor(new ThreadPoolExecutor(0, Integer.MAX_VALUE, a, TimeUnit.MILLISECONDS, new SynchronousQueue(), new b(new a(), "source-unlimited", UncaughtThrowableStrategy.DEFAULT, false)));
    }

    public static Builder newAnimationBuilder() {
        return new Builder(true).setThreadCount(calculateBestThreadCount() >= 4 ? 2 : 1).setName("animation");
    }

    public static GlideExecutor newAnimationExecutor() {
        return newAnimationBuilder().build();
    }

    @Deprecated
    public static GlideExecutor newAnimationExecutor(int i, UncaughtThrowableStrategy uncaughtThrowableStrategy) {
        return newAnimationBuilder().setThreadCount(i).setUncaughtThrowableStrategy(uncaughtThrowableStrategy).build();
    }

    @VisibleForTesting
    GlideExecutor(ExecutorService executorService) {
        this.c = executorService;
    }

    @Override // java.util.concurrent.Executor
    public void execute(@NonNull Runnable runnable) {
        this.c.execute(runnable);
    }

    @Override // java.util.concurrent.ExecutorService
    @NonNull
    public Future<?> submit(@NonNull Runnable runnable) {
        return this.c.submit(runnable);
    }

    @Override // java.util.concurrent.ExecutorService
    @NonNull
    public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> collection) throws InterruptedException {
        return this.c.invokeAll(collection);
    }

    @Override // java.util.concurrent.ExecutorService
    @NonNull
    public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> collection, long j, @NonNull TimeUnit timeUnit) throws InterruptedException {
        return this.c.invokeAll(collection, j, timeUnit);
    }

    @Override // java.util.concurrent.ExecutorService
    @NonNull
    public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        return (T) this.c.invokeAny(collection);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> collection, long j, @NonNull TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return (T) this.c.invokeAny(collection, j, timeUnit);
    }

    @Override // java.util.concurrent.ExecutorService
    @NonNull
    public <T> Future<T> submit(@NonNull Runnable runnable, T t) {
        return this.c.submit(runnable, t);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> Future<T> submit(@NonNull Callable<T> callable) {
        return this.c.submit(callable);
    }

    @Override // java.util.concurrent.ExecutorService
    public void shutdown() {
        this.c.shutdown();
    }

    @Override // java.util.concurrent.ExecutorService
    @NonNull
    public List<Runnable> shutdownNow() {
        return this.c.shutdownNow();
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        return this.c.isShutdown();
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        return this.c.isTerminated();
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j, @NonNull TimeUnit timeUnit) throws InterruptedException {
        return this.c.awaitTermination(j, timeUnit);
    }

    public String toString() {
        return this.c.toString();
    }

    public static int calculateBestThreadCount() {
        if (b == 0) {
            b = Math.min(4, a.a());
        }
        return b;
    }

    /* loaded from: classes.dex */
    private static final class a implements ThreadFactory {
        private a() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable) { // from class: com.bumptech.glide.load.engine.executor.GlideExecutor.a.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    Process.setThreadPriority(9);
                    super.run();
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b implements ThreadFactory {
        final UncaughtThrowableStrategy a;
        final boolean b;
        private final ThreadFactory c;
        private final String d;
        private final AtomicInteger e = new AtomicInteger();

        b(ThreadFactory threadFactory, String str, UncaughtThrowableStrategy uncaughtThrowableStrategy, boolean z) {
            this.c = threadFactory;
            this.d = str;
            this.a = uncaughtThrowableStrategy;
            this.b = z;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(@NonNull final Runnable runnable) {
            Thread newThread = this.c.newThread(new Runnable() { // from class: com.bumptech.glide.load.engine.executor.GlideExecutor.b.1
                @Override // java.lang.Runnable
                public void run() {
                    if (b.this.b) {
                        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().penaltyDeath().build());
                    }
                    try {
                        runnable.run();
                    } catch (Throwable th) {
                        b.this.a.handle(th);
                    }
                }
            });
            newThread.setName("glide-" + this.d + "-thread-" + this.e.getAndIncrement());
            return newThread;
        }
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        public static final long NO_THREAD_TIMEOUT = 0;
        private final boolean a;
        private int b;
        private int c;
        @NonNull
        private final ThreadFactory d = new a();
        @NonNull
        private UncaughtThrowableStrategy e = UncaughtThrowableStrategy.DEFAULT;
        private String f;
        private long g;

        Builder(boolean z) {
            this.a = z;
        }

        public Builder setThreadTimeoutMillis(long j) {
            this.g = j;
            return this;
        }

        public Builder setThreadCount(@IntRange(from = 1) int i) {
            this.b = i;
            this.c = i;
            return this;
        }

        public Builder setUncaughtThrowableStrategy(@NonNull UncaughtThrowableStrategy uncaughtThrowableStrategy) {
            this.e = uncaughtThrowableStrategy;
            return this;
        }

        public Builder setName(String str) {
            this.f = str;
            return this;
        }

        public GlideExecutor build() {
            if (!TextUtils.isEmpty(this.f)) {
                ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(this.b, this.c, this.g, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new b(this.d, this.f, this.e, this.a));
                if (this.g != 0) {
                    threadPoolExecutor.allowCoreThreadTimeOut(true);
                }
                return new GlideExecutor(threadPoolExecutor);
            }
            throw new IllegalArgumentException("Name must be non-null and non-empty, but given: " + this.f);
        }
    }
}
