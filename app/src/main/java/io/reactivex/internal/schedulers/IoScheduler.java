package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
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

/* loaded from: classes4.dex */
public final class IoScheduler extends Scheduler {
    public static final long KEEP_ALIVE_TIME_DEFAULT = 60;
    static final RxThreadFactory c;
    static final RxThreadFactory d;
    static final a i;
    final ThreadFactory f;
    final AtomicReference<a> g;
    private static final TimeUnit k = TimeUnit.SECONDS;
    private static final long j = Long.getLong("rx2.io-keep-alive-time", 60).longValue();
    static final c e = new c(new RxThreadFactory("RxCachedThreadSchedulerShutdown"));
    static boolean h = Boolean.getBoolean("rx2.io-scheduled-release");

    static {
        e.dispose();
        int max = Math.max(1, Math.min(10, Integer.getInteger("rx2.io-priority", 5).intValue()));
        c = new RxThreadFactory("RxCachedThreadScheduler", max);
        d = new RxThreadFactory("RxCachedWorkerPoolEvictor", max);
        i = new a(0L, null, c);
        i.d();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
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
                scheduledExecutorService = Executors.newScheduledThreadPool(1, IoScheduler.d);
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
            b();
        }

        c a() {
            if (this.a.isDisposed()) {
                return IoScheduler.e;
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
            cVar.a(c() + this.b);
            this.c.offer(cVar);
        }

        void b() {
            if (!this.c.isEmpty()) {
                long c = c();
                Iterator<c> it = this.c.iterator();
                while (it.hasNext()) {
                    c next = it.next();
                    if (next.a() > c) {
                        return;
                    }
                    if (this.c.remove(next)) {
                        this.a.remove(next);
                    }
                }
            }
        }

        long c() {
            return System.nanoTime();
        }

        void d() {
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
        this(c);
    }

    public IoScheduler(ThreadFactory threadFactory) {
        this.f = threadFactory;
        this.g = new AtomicReference<>(i);
        start();
    }

    @Override // io.reactivex.Scheduler
    public void start() {
        a aVar = new a(j, k, this.f);
        if (!this.g.compareAndSet(i, aVar)) {
            aVar.d();
        }
    }

    @Override // io.reactivex.Scheduler
    public void shutdown() {
        a aVar;
        a aVar2;
        do {
            aVar = this.g.get();
            aVar2 = i;
            if (aVar == aVar2) {
                return;
            }
        } while (!this.g.compareAndSet(aVar, aVar2));
        aVar.d();
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new b(this.g.get());
    }

    public int size() {
        return this.g.get().a.size();
    }

    /* loaded from: classes4.dex */
    static final class b extends Scheduler.Worker implements Runnable {
        final AtomicBoolean a = new AtomicBoolean();
        private final CompositeDisposable b = new CompositeDisposable();
        private final a c;
        private final c d;

        b(a aVar) {
            this.c = aVar;
            this.d = aVar.a();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.a.compareAndSet(false, true)) {
                this.b.dispose();
                if (IoScheduler.h) {
                    this.d.scheduleActual(this, 0L, TimeUnit.NANOSECONDS, null);
                } else {
                    this.c.a(this.d);
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.c.a(this.d);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.a.get();
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (this.b.isDisposed()) {
                return EmptyDisposable.INSTANCE;
            }
            return this.d.scheduleActual(runnable, j, timeUnit, this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class c extends NewThreadWorker {
        private long b = 0;

        c(ThreadFactory threadFactory) {
            super(threadFactory);
        }

        public long a() {
            return this.b;
        }

        public void a(long j) {
            this.b = j;
        }
    }
}
