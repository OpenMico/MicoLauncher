package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.disposables.ListCompositeDisposable;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.schedulers.SchedulerMultiWorkerSupport;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ComputationScheduler extends Scheduler implements SchedulerMultiWorkerSupport {
    final ThreadFactory f;
    final AtomicReference<b> g;
    static final int d = a(Runtime.getRuntime().availableProcessors(), Integer.getInteger("rx3.computation-threads", 0).intValue());
    static final c e = new c(new RxThreadFactory("RxComputationShutdown"));
    static final RxThreadFactory c = new RxThreadFactory("RxComputationThreadPool", Math.max(1, Math.min(10, Integer.getInteger("rx3.computation-priority", 5).intValue())), true);
    static final b b = new b(0, c);

    static int a(int i, int i2) {
        return (i2 <= 0 || i2 > i) ? i : i2;
    }

    static {
        e.dispose();
        b.b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b implements SchedulerMultiWorkerSupport {
        final int a;
        final c[] b;
        long c;

        b(int i, ThreadFactory threadFactory) {
            this.a = i;
            this.b = new c[i];
            for (int i2 = 0; i2 < i; i2++) {
                this.b[i2] = new c(threadFactory);
            }
        }

        public c a() {
            int i = this.a;
            if (i == 0) {
                return ComputationScheduler.e;
            }
            c[] cVarArr = this.b;
            long j = this.c;
            this.c = 1 + j;
            return cVarArr[(int) (j % i)];
        }

        public void b() {
            for (c cVar : this.b) {
                cVar.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.internal.schedulers.SchedulerMultiWorkerSupport
        public void createWorkers(int i, SchedulerMultiWorkerSupport.WorkerCallback workerCallback) {
            int i2 = this.a;
            if (i2 == 0) {
                for (int i3 = 0; i3 < i; i3++) {
                    workerCallback.onWorker(i3, ComputationScheduler.e);
                }
                return;
            }
            int i4 = ((int) this.c) % i2;
            for (int i5 = 0; i5 < i; i5++) {
                workerCallback.onWorker(i5, new a(this.b[i4]));
                i4++;
                if (i4 == i2) {
                    i4 = 0;
                }
            }
            this.c = i4;
        }
    }

    public ComputationScheduler() {
        this(c);
    }

    public ComputationScheduler(ThreadFactory threadFactory) {
        this.f = threadFactory;
        this.g = new AtomicReference<>(b);
        start();
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new a(this.g.get().a());
    }

    @Override // io.reactivex.rxjava3.internal.schedulers.SchedulerMultiWorkerSupport
    public void createWorkers(int i, SchedulerMultiWorkerSupport.WorkerCallback workerCallback) {
        ObjectHelper.verifyPositive(i, "number > 0 required");
        this.g.get().createWorkers(i, workerCallback);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, TimeUnit timeUnit) {
        return this.g.get().a().scheduleDirect(runnable, j, timeUnit);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return this.g.get().a().schedulePeriodicallyDirect(runnable, j, j2, timeUnit);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void start() {
        b bVar = new b(d, this.f);
        if (!this.g.compareAndSet(b, bVar)) {
            bVar.b();
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void shutdown() {
        b andSet = this.g.getAndSet(b);
        if (andSet != b) {
            andSet.b();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a extends Scheduler.Worker {
        volatile boolean a;
        private final ListCompositeDisposable b = new ListCompositeDisposable();
        private final CompositeDisposable c = new CompositeDisposable();
        private final ListCompositeDisposable d = new ListCompositeDisposable();
        private final c e;

        a(c cVar) {
            this.e = cVar;
            this.d.add(this.b);
            this.d.add(this.c);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (!this.a) {
                this.a = true;
                this.d.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.a;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            if (this.a) {
                return EmptyDisposable.INSTANCE;
            }
            return this.e.scheduleActual(runnable, 0L, TimeUnit.MILLISECONDS, this.b);
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (this.a) {
                return EmptyDisposable.INSTANCE;
            }
            return this.e.scheduleActual(runnable, j, timeUnit, this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class c extends NewThreadWorker {
        c(ThreadFactory threadFactory) {
            super(threadFactory);
        }
    }
}
