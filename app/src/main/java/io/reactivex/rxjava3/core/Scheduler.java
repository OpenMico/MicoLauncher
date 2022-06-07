package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.schedulers.NewThreadWorker;
import io.reactivex.rxjava3.internal.schedulers.SchedulerWhen;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class Scheduler {
    static final long a = a(Long.getLong("rx3.scheduler.drift-tolerance", 15).longValue(), System.getProperty("rx3.scheduler.drift-tolerance-unit", "minutes"));

    @NonNull
    public abstract Worker createWorker();

    public void shutdown() {
    }

    public void start() {
    }

    static long a(long j, String str) {
        if ("seconds".equalsIgnoreCase(str)) {
            return TimeUnit.SECONDS.toNanos(j);
        }
        if ("milliseconds".equalsIgnoreCase(str)) {
            return TimeUnit.MILLISECONDS.toNanos(j);
        }
        return TimeUnit.MINUTES.toNanos(j);
    }

    public static long clockDriftTolerance() {
        return a;
    }

    public long now(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable) {
        return scheduleDirect(runnable, 0L, TimeUnit.NANOSECONDS);
    }

    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
        Worker createWorker = createWorker();
        a aVar = new a(RxJavaPlugins.onSchedule(runnable), createWorker);
        createWorker.schedule(aVar, j, timeUnit);
        return aVar;
    }

    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable runnable, long j, long j2, @NonNull TimeUnit timeUnit) {
        Worker createWorker = createWorker();
        b bVar = new b(RxJavaPlugins.onSchedule(runnable), createWorker);
        Disposable schedulePeriodically = createWorker.schedulePeriodically(bVar, j, j2, timeUnit);
        return schedulePeriodically == EmptyDisposable.INSTANCE ? schedulePeriodically : bVar;
    }

    @NonNull
    public <S extends Scheduler & Disposable> S when(@NonNull Function<Flowable<Flowable<Completable>>, Completable> function) {
        Objects.requireNonNull(function, "combine is null");
        return new SchedulerWhen(function, this);
    }

    /* loaded from: classes4.dex */
    public static abstract class Worker implements Disposable {
        @NonNull
        public abstract Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit);

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            return schedule(runnable, 0L, TimeUnit.NANOSECONDS);
        }

        @NonNull
        public Disposable schedulePeriodically(@NonNull Runnable runnable, long j, long j2, @NonNull TimeUnit timeUnit) {
            SequentialDisposable sequentialDisposable = new SequentialDisposable();
            SequentialDisposable sequentialDisposable2 = new SequentialDisposable(sequentialDisposable);
            Runnable onSchedule = RxJavaPlugins.onSchedule(runnable);
            long nanos = timeUnit.toNanos(j2);
            long now = now(TimeUnit.NANOSECONDS);
            Disposable schedule = schedule(new a(now + timeUnit.toNanos(j), onSchedule, now, sequentialDisposable2, nanos), j, timeUnit);
            if (schedule == EmptyDisposable.INSTANCE) {
                return schedule;
            }
            sequentialDisposable.replace(schedule);
            return sequentialDisposable2;
        }

        public long now(@NonNull TimeUnit timeUnit) {
            return timeUnit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        /* loaded from: classes4.dex */
        public final class a implements SchedulerRunnableIntrospection, Runnable {
            @NonNull
            final Runnable a;
            @NonNull
            final SequentialDisposable b;
            final long c;
            long d;
            long e;
            long f;

            a(long j, Runnable runnable, @NonNull long j2, SequentialDisposable sequentialDisposable, @NonNull long j3) {
                Worker.this = r1;
                this.a = runnable;
                this.b = sequentialDisposable;
                this.c = j3;
                this.e = j2;
                this.f = j;
            }

            @Override // java.lang.Runnable
            public void run() {
                long j;
                this.a.run();
                if (!this.b.isDisposed()) {
                    long now = Worker.this.now(TimeUnit.NANOSECONDS);
                    long j2 = this.e;
                    if (Scheduler.a + now < j2 || now >= j2 + this.c + Scheduler.a) {
                        long j3 = this.c;
                        long j4 = now + j3;
                        long j5 = this.d + 1;
                        this.d = j5;
                        this.f = j4 - (j3 * j5);
                        j = j4;
                    } else {
                        long j6 = this.f;
                        long j7 = this.d + 1;
                        this.d = j7;
                        j = j6 + (j7 * this.c);
                    }
                    this.e = now;
                    this.b.replace(Worker.this.schedule(this, j - now, TimeUnit.NANOSECONDS));
                }
            }

            @Override // io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
            public Runnable getWrappedRunnable() {
                return this.a;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b implements Disposable, SchedulerRunnableIntrospection, Runnable {
        @NonNull
        final Runnable a;
        @NonNull
        final Worker b;
        volatile boolean c;

        b(@NonNull Runnable runnable, @NonNull Worker worker) {
            this.a = runnable;
            this.b = worker;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!this.c) {
                try {
                    this.a.run();
                } catch (Throwable th) {
                    dispose();
                    RxJavaPlugins.onError(th);
                    throw th;
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.c = true;
            this.b.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.c;
        }

        @Override // io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
        public Runnable getWrappedRunnable() {
            return this.a;
        }
    }

    /* loaded from: classes4.dex */
    public static final class a implements Disposable, SchedulerRunnableIntrospection, Runnable {
        @NonNull
        final Runnable a;
        @NonNull
        final Worker b;
        @Nullable
        Thread c;

        a(@NonNull Runnable runnable, @NonNull Worker worker) {
            this.a = runnable;
            this.b = worker;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.c = Thread.currentThread();
            try {
                this.a.run();
            } finally {
                dispose();
                this.c = null;
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.c == Thread.currentThread()) {
                Worker worker = this.b;
                if (worker instanceof NewThreadWorker) {
                    ((NewThreadWorker) worker).shutdown();
                    return;
                }
            }
            this.b.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }

        @Override // io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
        public Runnable getWrappedRunnable() {
            return this.a;
        }
    }
}
