package io.reactivex.internal.schedulers;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Function;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public class SchedulerWhen extends Scheduler implements Disposable {
    static final Disposable c = new g();
    static final Disposable d = Disposables.disposed();
    private final Scheduler e;
    private final FlowableProcessor<Flowable<Completable>> f = UnicastProcessor.create().toSerialized();
    private Disposable g;

    public SchedulerWhen(Function<Flowable<Flowable<Completable>>, Completable> function, Scheduler scheduler) {
        this.e = scheduler;
        try {
            this.g = function.apply(this.f).subscribe();
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        this.g.dispose();
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        return this.g.isDisposed();
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        Scheduler.Worker createWorker = this.e.createWorker();
        FlowableProcessor<T> serialized = UnicastProcessor.create().toSerialized();
        Flowable<Completable> map = serialized.map(new a(createWorker));
        e eVar = new e(serialized, createWorker);
        this.f.onNext(map);
        return eVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static abstract class f extends AtomicReference<Disposable> implements Disposable {
        protected abstract Disposable a(Scheduler.Worker worker, CompletableObserver completableObserver);

        f() {
            super(SchedulerWhen.c);
        }

        void b(Scheduler.Worker worker, CompletableObserver completableObserver) {
            Disposable disposable = get();
            if (disposable != SchedulerWhen.d && disposable == SchedulerWhen.c) {
                Disposable a = a(worker, completableObserver);
                if (!compareAndSet(SchedulerWhen.c, a)) {
                    a.dispose();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get().isDisposed();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            Disposable disposable;
            Disposable disposable2 = SchedulerWhen.d;
            do {
                disposable = get();
                if (disposable == SchedulerWhen.d) {
                    return;
                }
            } while (!compareAndSet(disposable, disposable2));
            if (disposable != SchedulerWhen.c) {
                disposable.dispose();
            }
        }
    }

    /* loaded from: classes4.dex */
    static class c extends f {
        private final Runnable action;

        c(Runnable runnable) {
            this.action = runnable;
        }

        @Override // io.reactivex.internal.schedulers.SchedulerWhen.f
        protected Disposable a(Scheduler.Worker worker, CompletableObserver completableObserver) {
            return worker.schedule(new d(this.action, completableObserver));
        }
    }

    /* loaded from: classes4.dex */
    static class b extends f {
        private final Runnable action;
        private final long delayTime;
        private final TimeUnit unit;

        b(Runnable runnable, long j, TimeUnit timeUnit) {
            this.action = runnable;
            this.delayTime = j;
            this.unit = timeUnit;
        }

        @Override // io.reactivex.internal.schedulers.SchedulerWhen.f
        protected Disposable a(Scheduler.Worker worker, CompletableObserver completableObserver) {
            return worker.schedule(new d(this.action, completableObserver), this.delayTime, this.unit);
        }
    }

    /* loaded from: classes4.dex */
    static class d implements Runnable {
        final CompletableObserver a;
        final Runnable b;

        d(Runnable runnable, CompletableObserver completableObserver) {
            this.b = runnable;
            this.a = completableObserver;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.b.run();
            } finally {
                this.a.onComplete();
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class a implements Function<f, Completable> {
        final Scheduler.Worker a;

        a(Scheduler.Worker worker) {
            this.a = worker;
        }

        /* renamed from: a */
        public Completable apply(f fVar) {
            return new C0275a(fVar);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.internal.schedulers.SchedulerWhen$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public final class C0275a extends Completable {
            final f a;

            C0275a(f fVar) {
                this.a = fVar;
            }

            @Override // io.reactivex.Completable
            protected void subscribeActual(CompletableObserver completableObserver) {
                completableObserver.onSubscribe(this.a);
                this.a.b(a.this.a, completableObserver);
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class e extends Scheduler.Worker {
        private final AtomicBoolean a = new AtomicBoolean();
        private final FlowableProcessor<f> b;
        private final Scheduler.Worker c;

        e(FlowableProcessor<f> flowableProcessor, Scheduler.Worker worker) {
            this.b = flowableProcessor;
            this.c = worker;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.a.compareAndSet(false, true)) {
                this.b.onComplete();
                this.c.dispose();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.a.get();
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            b bVar = new b(runnable, j, timeUnit);
            this.b.onNext(bVar);
            return bVar;
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            c cVar = new c(runnable);
            this.b.onNext(cVar);
            return cVar;
        }
    }

    /* loaded from: classes4.dex */
    static final class g implements Disposable {
        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return false;
        }

        g() {
        }
    }
}
