package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes5.dex */
public final class TrampolineScheduler extends Scheduler {
    private static final TrampolineScheduler b = new TrampolineScheduler();

    public static TrampolineScheduler instance() {
        return b;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new c();
    }

    TrampolineScheduler() {
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable) {
        RxJavaPlugins.onSchedule(runnable).run();
        return EmptyDisposable.INSTANCE;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(j);
            RxJavaPlugins.onSchedule(runnable).run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            RxJavaPlugins.onError(e);
        }
        return EmptyDisposable.INSTANCE;
    }

    /* loaded from: classes5.dex */
    static final class c extends Scheduler.Worker implements Disposable {
        volatile boolean c;
        final PriorityBlockingQueue<b> a = new PriorityBlockingQueue<>();
        private final AtomicInteger d = new AtomicInteger();
        final AtomicInteger b = new AtomicInteger();

        c() {
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            return a(runnable, now(TimeUnit.MILLISECONDS));
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            long now = now(TimeUnit.MILLISECONDS) + timeUnit.toMillis(j);
            return a(new a(runnable, this, now), now);
        }

        Disposable a(Runnable runnable, long j) {
            if (this.c) {
                return EmptyDisposable.INSTANCE;
            }
            b bVar = new b(runnable, Long.valueOf(j), this.b.incrementAndGet());
            this.a.add(bVar);
            if (this.d.getAndIncrement() != 0) {
                return Disposable.fromRunnable(new a(bVar));
            }
            int i = 1;
            while (!this.c) {
                b poll = this.a.poll();
                if (poll == null) {
                    i = this.d.addAndGet(-i);
                    if (i == 0) {
                        return EmptyDisposable.INSTANCE;
                    }
                } else if (!poll.d) {
                    poll.a.run();
                }
            }
            this.a.clear();
            return EmptyDisposable.INSTANCE;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.c = true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.c;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes5.dex */
        public final class a implements Runnable {
            final b a;

            a(b bVar) {
                this.a = bVar;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.a.d = true;
                c.this.a.remove(this.a);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b implements Comparable<b> {
        final Runnable a;
        final long b;
        final int c;
        volatile boolean d;

        b(Runnable runnable, Long l, int i) {
            this.a = runnable;
            this.b = l.longValue();
            this.c = i;
        }

        /* renamed from: a */
        public int compareTo(b bVar) {
            int compare = Long.compare(this.b, bVar.b);
            return compare == 0 ? Integer.compare(this.c, bVar.c) : compare;
        }
    }

    /* loaded from: classes5.dex */
    static final class a implements Runnable {
        private final Runnable a;
        private final c b;
        private final long c;

        a(Runnable runnable, c cVar, long j) {
            this.a = runnable;
            this.b = cVar;
            this.c = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!this.b.c) {
                long now = this.b.now(TimeUnit.MILLISECONDS);
                long j = this.c;
                if (j > now) {
                    try {
                        Thread.sleep(j - now);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        RxJavaPlugins.onError(e);
                        return;
                    }
                }
                if (!this.b.c) {
                    this.a.run();
                }
            }
        }
    }
}
