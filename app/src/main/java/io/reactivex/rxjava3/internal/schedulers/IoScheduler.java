package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class IoScheduler extends Scheduler {
    public static final long KEEP_ALIVE_TIME_DEFAULT = 60;
    static final RxThreadFactory b;
    static final RxThreadFactory c;
    static final a g;
    final ThreadFactory e;
    final AtomicReference<a> f;
    private static final TimeUnit i = TimeUnit.SECONDS;
    private static final long h = Long.getLong("rx3.io-keep-alive-time", 60).longValue();
    static final c d = new c(new RxThreadFactory("RxCachedThreadSchedulerShutdown"));

    static {
        d.dispose();
        int max = Math.max(1, Math.min(10, Integer.getInteger("rx3.io-priority", 5).intValue()));
        b = new RxThreadFactory("RxCachedThreadScheduler", max);
        c = new RxThreadFactory("RxCachedWorkerPoolEvictor", max);
        g = new a(0L, null, b);
        g.c();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a implements Runnable {
        final CompositeDisposable a;
        private final long b;
        private final ConcurrentLinkedQueue<c> c;
        private final ScheduledExecutorService d;
        private final Future<?> e;
        private final ThreadFactory f;

        a(long j, TimeUnit timeUnit, ThreadFactory threadFactory) {
            ScheduledFuture<?> scheduledFuture;
            this.b = timeUnit != null ? timeUnit.toNanos(j) : 0L;
            this.c = new ConcurrentLinkedQueue<>();
            this.a = new CompositeDisposable();
            this.f = threadFactory;
            ScheduledExecutorService scheduledExecutorService = null;
            if (timeUnit != null) {
                scheduledExecutorService = Executors.newScheduledThreadPool(1, IoScheduler.c);
                long j2 = this.b;
                scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(this, j2, j2, TimeUnit.NANOSECONDS);
            } else {
                scheduledFuture = null;
            }
            this.d = scheduledExecutorService;
            this.e = scheduledFuture;
        }

        @Override // java.lang.Runnable
        public void run() {
            a(this.c, this.a);
        }

        c a() {
            if (this.a.isDisposed()) {
                return IoScheduler.d;
            }
            while (!this.c.isEmpty()) {
                c poll = this.c.poll();
                if (poll != null) {
                    return poll;
                }
            }
            c cVar = new c(this.f);
            this.a.add(cVar);
            return cVar;
        }

        void a(c cVar) {
            cVar.a(b() + this.b);
            this.c.offer(cVar);
        }

        static void a(ConcurrentLinkedQueue<c> concurrentLinkedQueue, CompositeDisposable compositeDisposable) {
            if (!concurrentLinkedQueue.isEmpty()) {
                long b = b();
                Iterator<c> it = concurrentLinkedQueue.iterator();
                while (it.hasNext()) {
                    c next = it.next();
                    if (next.a() > b) {
                        return;
                    }
                    if (concurrentLinkedQueue.remove(next)) {
                        compositeDisposable.remove(next);
                    }
                }
            }
        }

        static long b() {
            return System.nanoTime();
        }

        void c() {
            this.a.dispose();
            Future<?> future = this.e;
            if (future != null) {
                future.cancel(true);
            }
            ScheduledExecutorService scheduledExecutorService = this.d;
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdownNow();
            }
        }
    }

    public IoScheduler() {
        this(b);
    }

    public IoScheduler(ThreadFactory threadFactory) {
        this.e = threadFactory;
        this.f = new AtomicReference<>(g);
        start();
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void start() {
        a aVar = new a(h, i, this.e);
        if (!this.f.compareAndSet(g, aVar)) {
            aVar.c();
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void shutdown() {
        a andSet = this.f.getAndSet(g);
        if (andSet != g) {
            andSet.c();
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new b(this.f.get());
    }

    public int size() {
        return this.f.get().a.size();
    }

    /* loaded from: classes5.dex */
    static final class b extends Scheduler.Worker {
        final AtomicBoolean a = new AtomicBoolean();
        private final CompositeDisposable b = new CompositeDisposable();
        private final a c;
        private final c d;

        b(a aVar) {
            this.c = aVar;
            this.d = aVar.a();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.a.compareAndSet(false, true)) {
                this.b.dispose();
                this.c.a(this.d);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.a.get();
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (this.b.isDisposed()) {
                return EmptyDisposable.INSTANCE;
            }
            return this.d.scheduleActual(runnable, j, timeUnit, this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class c extends NewThreadWorker {
        long a = 0;

        c(ThreadFactory threadFactory) {
            super(threadFactory);
        }

        public long a() {
            return this.a;
        }

        public void a(long j) {
            this.a = j;
        }
    }
}
