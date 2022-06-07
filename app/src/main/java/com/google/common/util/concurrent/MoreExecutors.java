package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ForwardingListenableFuture;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.milink.base.contract.LockContract;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class MoreExecutors {
    private MoreExecutors() {
    }

    @Beta
    @GwtIncompatible
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor, long j, TimeUnit timeUnit) {
        return new a().a(threadPoolExecutor, j, timeUnit);
    }

    @Beta
    @GwtIncompatible
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor) {
        return new a().a(threadPoolExecutor);
    }

    @Beta
    @GwtIncompatible
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, long j, TimeUnit timeUnit) {
        return new a().a(scheduledThreadPoolExecutor, j, timeUnit);
    }

    @Beta
    @GwtIncompatible
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        return new a().a(scheduledThreadPoolExecutor);
    }

    @Beta
    @GwtIncompatible
    public static void addDelayedShutdownHook(ExecutorService executorService, long j, TimeUnit timeUnit) {
        new a().a(executorService, j, timeUnit);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static class a {
        a() {
        }

        final ExecutorService a(ThreadPoolExecutor threadPoolExecutor, long j, TimeUnit timeUnit) {
            MoreExecutors.b(threadPoolExecutor);
            ExecutorService unconfigurableExecutorService = Executors.unconfigurableExecutorService(threadPoolExecutor);
            a((ExecutorService) threadPoolExecutor, j, timeUnit);
            return unconfigurableExecutorService;
        }

        final ExecutorService a(ThreadPoolExecutor threadPoolExecutor) {
            return a(threadPoolExecutor, 120L, TimeUnit.SECONDS);
        }

        final ScheduledExecutorService a(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, long j, TimeUnit timeUnit) {
            MoreExecutors.b(scheduledThreadPoolExecutor);
            ScheduledExecutorService unconfigurableScheduledExecutorService = Executors.unconfigurableScheduledExecutorService(scheduledThreadPoolExecutor);
            a((ExecutorService) scheduledThreadPoolExecutor, j, timeUnit);
            return unconfigurableScheduledExecutorService;
        }

        final ScheduledExecutorService a(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
            return a(scheduledThreadPoolExecutor, 120L, TimeUnit.SECONDS);
        }

        final void a(final ExecutorService executorService, final long j, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(executorService);
            Preconditions.checkNotNull(timeUnit);
            a(MoreExecutors.a("DelayedShutdownHook-for-" + executorService, new Runnable() { // from class: com.google.common.util.concurrent.MoreExecutors.a.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        executorService.shutdown();
                        executorService.awaitTermination(j, timeUnit);
                    } catch (InterruptedException unused) {
                    }
                }
            }));
        }

        @VisibleForTesting
        void a(Thread thread) {
            Runtime.getRuntime().addShutdownHook(thread);
        }
    }

    @GwtIncompatible
    public static void b(ThreadPoolExecutor threadPoolExecutor) {
        threadPoolExecutor.setThreadFactory(new ThreadFactoryBuilder().setDaemon(true).setThreadFactory(threadPoolExecutor.getThreadFactory()).build());
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    private static final class c extends AbstractListeningExecutorService {
        private final Object a;
        @GuardedBy(LockContract.Matcher.LOCK)
        private int b;
        @GuardedBy(LockContract.Matcher.LOCK)
        private boolean c;

        private c() {
            this.a = new Object();
            this.b = 0;
            this.c = false;
        }

        /* synthetic */ c(AnonymousClass1 r1) {
            this();
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            a();
            try {
                runnable.run();
            } finally {
                b();
            }
        }

        @Override // java.util.concurrent.ExecutorService
        public boolean isShutdown() {
            boolean z;
            synchronized (this.a) {
                z = this.c;
            }
            return z;
        }

        @Override // java.util.concurrent.ExecutorService
        public void shutdown() {
            synchronized (this.a) {
                this.c = true;
                if (this.b == 0) {
                    this.a.notifyAll();
                }
            }
        }

        @Override // java.util.concurrent.ExecutorService
        public List<Runnable> shutdownNow() {
            shutdown();
            return Collections.emptyList();
        }

        @Override // java.util.concurrent.ExecutorService
        public boolean isTerminated() {
            boolean z;
            synchronized (this.a) {
                z = this.c && this.b == 0;
            }
            return z;
        }

        @Override // java.util.concurrent.ExecutorService
        public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
            long nanos = timeUnit.toNanos(j);
            synchronized (this.a) {
                while (true) {
                    if (this.c && this.b == 0) {
                        return true;
                    }
                    if (nanos <= 0) {
                        return false;
                    }
                    long nanoTime = System.nanoTime();
                    TimeUnit.NANOSECONDS.timedWait(this.a, nanos);
                    nanos -= System.nanoTime() - nanoTime;
                }
            }
        }

        private void a() {
            synchronized (this.a) {
                if (!this.c) {
                    this.b++;
                } else {
                    throw new RejectedExecutionException("Executor already shutdown");
                }
            }
        }

        private void b() {
            synchronized (this.a) {
                int i = this.b - 1;
                this.b = i;
                if (i == 0) {
                    this.a.notifyAll();
                }
            }
        }
    }

    @GwtIncompatible
    public static ListeningExecutorService newDirectExecutorService() {
        return new c(null);
    }

    public static Executor directExecutor() {
        return b.INSTANCE;
    }

    /* loaded from: classes2.dex */
    public enum b implements Executor {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "MoreExecutors.directExecutor()";
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            runnable.run();
        }
    }

    @Beta
    @GwtIncompatible
    public static Executor newSequentialExecutor(Executor executor) {
        return new q(executor);
    }

    @GwtIncompatible
    public static ListeningExecutorService listeningDecorator(ExecutorService executorService) {
        return executorService instanceof ListeningExecutorService ? (ListeningExecutorService) executorService : executorService instanceof ScheduledExecutorService ? new e((ScheduledExecutorService) executorService) : new d(executorService);
    }

    @GwtIncompatible
    public static ListeningScheduledExecutorService listeningDecorator(ScheduledExecutorService scheduledExecutorService) {
        return scheduledExecutorService instanceof ListeningScheduledExecutorService ? (ListeningScheduledExecutorService) scheduledExecutorService : new e(scheduledExecutorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static class d extends AbstractListeningExecutorService {
        private final ExecutorService a;

        d(ExecutorService executorService) {
            this.a = (ExecutorService) Preconditions.checkNotNull(executorService);
        }

        @Override // java.util.concurrent.ExecutorService
        public final boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
            return this.a.awaitTermination(j, timeUnit);
        }

        @Override // java.util.concurrent.ExecutorService
        public final boolean isShutdown() {
            return this.a.isShutdown();
        }

        @Override // java.util.concurrent.ExecutorService
        public final boolean isTerminated() {
            return this.a.isTerminated();
        }

        @Override // java.util.concurrent.ExecutorService
        public final void shutdown() {
            this.a.shutdown();
        }

        @Override // java.util.concurrent.ExecutorService
        public final List<Runnable> shutdownNow() {
            return this.a.shutdownNow();
        }

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            this.a.execute(runnable);
        }
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    private static final class e extends d implements ListeningScheduledExecutorService {
        final ScheduledExecutorService a;

        e(ScheduledExecutorService scheduledExecutorService) {
            super(scheduledExecutorService);
            this.a = (ScheduledExecutorService) Preconditions.checkNotNull(scheduledExecutorService);
        }

        @Override // com.google.common.util.concurrent.ListeningScheduledExecutorService, java.util.concurrent.ScheduledExecutorService
        public ListenableScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            t a2 = t.a(runnable, (Object) null);
            return new a(a2, this.a.schedule(a2, j, timeUnit));
        }

        @Override // com.google.common.util.concurrent.ListeningScheduledExecutorService, java.util.concurrent.ScheduledExecutorService
        public <V> ListenableScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit) {
            t a2 = t.a((Callable) callable);
            return new a(a2, this.a.schedule(a2, j, timeUnit));
        }

        @Override // com.google.common.util.concurrent.ListeningScheduledExecutorService, java.util.concurrent.ScheduledExecutorService
        public ListenableScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            b bVar = new b(runnable);
            return new a(bVar, this.a.scheduleAtFixedRate(bVar, j, j2, timeUnit));
        }

        @Override // com.google.common.util.concurrent.ListeningScheduledExecutorService, java.util.concurrent.ScheduledExecutorService
        public ListenableScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            b bVar = new b(runnable);
            return new a(bVar, this.a.scheduleWithFixedDelay(bVar, j, j2, timeUnit));
        }

        /* loaded from: classes2.dex */
        public static final class a<V> extends ForwardingListenableFuture.SimpleForwardingListenableFuture<V> implements ListenableScheduledFuture<V> {
            private final ScheduledFuture<?> a;

            public a(ListenableFuture<V> listenableFuture, ScheduledFuture<?> scheduledFuture) {
                super(listenableFuture);
                this.a = scheduledFuture;
            }

            @Override // com.google.common.util.concurrent.ForwardingFuture, java.util.concurrent.Future
            public boolean cancel(boolean z) {
                boolean cancel = super.cancel(z);
                if (cancel) {
                    this.a.cancel(z);
                }
                return cancel;
            }

            @Override // java.util.concurrent.Delayed
            public long getDelay(TimeUnit timeUnit) {
                return this.a.getDelay(timeUnit);
            }

            /* renamed from: a */
            public int compareTo(Delayed delayed) {
                return this.a.compareTo(delayed);
            }
        }

        @GwtIncompatible
        /* loaded from: classes2.dex */
        public static final class b extends AbstractFuture<Void> implements Runnable {
            private final Runnable a;

            public b(Runnable runnable) {
                this.a = (Runnable) Preconditions.checkNotNull(runnable);
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    this.a.run();
                } catch (Throwable th) {
                    setException(th);
                    throw Throwables.propagate(th);
                }
            }
        }
    }

    /* renamed from: com.google.common.util.concurrent.MoreExecutors$1 */
    /* loaded from: classes2.dex */
    static class AnonymousClass1 implements Runnable {
        final /* synthetic */ BlockingQueue a;
        final /* synthetic */ ListenableFuture b;

        @Override // java.lang.Runnable
        public void run() {
            this.a.add(this.b);
        }
    }

    @Beta
    @GwtIncompatible
    public static ThreadFactory platformThreadFactory() {
        Throwable e2;
        if (!a()) {
            return Executors.defaultThreadFactory();
        }
        try {
            return (ThreadFactory) Class.forName("com.google.appengine.api.ThreadManager").getMethod("currentRequestThreadFactory", new Class[0]).invoke(null, new Object[0]);
        } catch (ClassNotFoundException e3) {
            e2 = e3;
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e2);
        } catch (IllegalAccessException e4) {
            e2 = e4;
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e2);
        } catch (NoSuchMethodException e5) {
            e2 = e5;
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e2);
        } catch (InvocationTargetException e6) {
            throw Throwables.propagate(e6.getCause());
        }
    }

    @GwtIncompatible
    private static boolean a() {
        if (System.getProperty("com.google.appengine.runtime.environment") == null) {
            return false;
        }
        try {
            return Class.forName("com.google.apphosting.api.ApiProxy").getMethod("getCurrentEnvironment", new Class[0]).invoke(null, new Object[0]) != null;
        } catch (ClassNotFoundException unused) {
            return false;
        } catch (IllegalAccessException unused2) {
            return false;
        } catch (NoSuchMethodException unused3) {
            return false;
        } catch (InvocationTargetException unused4) {
            return false;
        }
    }

    @GwtIncompatible
    public static Thread a(String str, Runnable runnable) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(runnable);
        Thread newThread = platformThreadFactory().newThread(runnable);
        try {
            newThread.setName(str);
        } catch (SecurityException unused) {
        }
        return newThread;
    }

    @GwtIncompatible
    public static Executor a(final Executor executor, final Supplier<String> supplier) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(supplier);
        return a() ? executor : new Executor() { // from class: com.google.common.util.concurrent.MoreExecutors.2
            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                executor.execute(Callables.a(runnable, supplier));
            }
        };
    }

    @GwtIncompatible
    public static ScheduledExecutorService a(ScheduledExecutorService scheduledExecutorService, final Supplier<String> supplier) {
        Preconditions.checkNotNull(scheduledExecutorService);
        Preconditions.checkNotNull(supplier);
        return a() ? scheduledExecutorService : new v(scheduledExecutorService) { // from class: com.google.common.util.concurrent.MoreExecutors.3
            @Override // com.google.common.util.concurrent.u
            protected <T> Callable<T> a(Callable<T> callable) {
                return Callables.a(callable, supplier);
            }

            @Override // com.google.common.util.concurrent.u
            protected Runnable a(Runnable runnable) {
                return Callables.a(runnable, supplier);
            }
        };
    }

    @CanIgnoreReturnValue
    @Beta
    @GwtIncompatible
    public static boolean shutdownAndAwaitTermination(ExecutorService executorService, long j, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(j) / 2;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(nanos, TimeUnit.NANOSECONDS)) {
                executorService.shutdownNow();
                executorService.awaitTermination(nanos, TimeUnit.NANOSECONDS);
            }
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
        }
        return executorService.isTerminated();
    }

    public static Executor a(Executor executor, AbstractFuture<?> abstractFuture) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(abstractFuture);
        return executor == directExecutor() ? executor : new AnonymousClass4(executor, abstractFuture);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.google.common.util.concurrent.MoreExecutors$4 */
    /* loaded from: classes2.dex */
    public static class AnonymousClass4 implements Executor {
        boolean a = true;
        final /* synthetic */ Executor b;
        final /* synthetic */ AbstractFuture c;

        AnonymousClass4(Executor executor, AbstractFuture abstractFuture) {
            this.b = executor;
            this.c = abstractFuture;
        }

        @Override // java.util.concurrent.Executor
        public void execute(final Runnable runnable) {
            try {
                this.b.execute(new Runnable() { // from class: com.google.common.util.concurrent.MoreExecutors.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AnonymousClass4.this.a = false;
                        runnable.run();
                    }
                });
            } catch (RejectedExecutionException e) {
                if (this.a) {
                    this.c.setException(e);
                }
            }
        }
    }
}
