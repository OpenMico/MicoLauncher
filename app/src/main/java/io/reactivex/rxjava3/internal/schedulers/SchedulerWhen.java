package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public class SchedulerWhen extends Scheduler implements Disposable {
    static final Disposable b = new g();
    static final Disposable c = Disposable.disposed();
    private final Scheduler d;
    private final FlowableProcessor<Flowable<Completable>> e = UnicastProcessor.create().toSerialized();
    private Disposable f;

    public SchedulerWhen(Function<Flowable<Flowable<Completable>>, Completable> function, Scheduler scheduler) {
        this.d = scheduler;
        try {
            this.f = function.apply(this.e).subscribe();
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        this.f.dispose();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.f.isDisposed();
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        Scheduler.Worker createWorker = this.d.createWorker();
        FlowableProcessor<T> serialized = UnicastProcessor.create().toSerialized();
        Flowable<Completable> map = serialized.map(new a(createWorker));
        e eVar = new e(serialized, createWorker);
        this.e.onNext(map);
        return eVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static abstract class f extends AtomicReference<Disposable> implements Disposable {
        protected abstract Disposable a(Scheduler.Worker worker, CompletableObserver completableObserver);

        f() {
            super(SchedulerWhen.b);
        }

        void b(Scheduler.Worker worker, CompletableObserver completableObserver) {
            Disposable disposable = get();
            if (disposable != SchedulerWhen.c && disposable == SchedulerWhen.b) {
                Disposable a = a(worker, completableObserver);
                if (!compareAndSet(SchedulerWhen.b, a)) {
                    a.dispose();
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get().isDisposed();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            getAndSet(SchedulerWhen.c).dispose();
        }
    }

    /* loaded from: classes5.dex */
    static class c extends f {
        private final Runnable action;

        c(Runnable runnable) {
            this.action = runnable;
        }

        @Override // io.reactivex.rxjava3.internal.schedulers.SchedulerWhen.f
        protected Disposable a(Scheduler.Worker worker, CompletableObserver completableObserver) {
            return worker.schedule(new d(this.action, completableObserver));
        }
    }

    /* loaded from: classes5.dex */
    static class b extends f {
        private final Runnable action;
        private final long delayTime;
        private final TimeUnit unit;

        b(Runnable runnable, long j, TimeUnit timeUnit) {
            this.action = runnable;
            this.delayTime = j;
            this.unit = timeUnit;
        }

        @Override // io.reactivex.rxjava3.internal.schedulers.SchedulerWhen.f
        protected Disposable a(Scheduler.Worker worker, CompletableObserver completableObserver) {
            return worker.schedule(new d(this.action, completableObserver), this.delayTime, this.unit);
        }
    }

    /* loaded from: classes5.dex */
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

    /* loaded from: classes5.dex */
    static final class a implements Function<f, Completable> {
        final Scheduler.Worker a;

        a(Scheduler.Worker worker) {
            this.a = worker;
        }

        /* renamed from: a */
        public Completable apply(f fVar) {
            return new C0343a(fVar);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.rxjava3.internal.schedulers.SchedulerWhen$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        public final class C0343a extends Completable {
            final f a;

            C0343a(f fVar) {
                this.a = fVar;
            }

            @Override // io.reactivex.rxjava3.core.Completable
            protected void subscribeActual(CompletableObserver completableObserver) {
                completableObserver.onSubscribe(this.a);
                this.a.b(a.this.a, completableObserver);
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class e extends Scheduler.Worker {
        private final AtomicBoolean a = new AtomicBoolean();
        private final FlowableProcessor<f> b;
        private final Scheduler.Worker c;

        e(FlowableProcessor<f> flowableProcessor, Scheduler.Worker worker) {
            this.b = flowableProcessor;
            this.c = worker;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.a.compareAndSet(false, true)) {
                this.b.onComplete();
                this.c.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.a.get();
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            b bVar = new b(runnable, j, timeUnit);
            this.b.onNext(bVar);
            return bVar;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            c cVar = new c(runnable);
            this.b.onNext(cVar);
            return cVar;
        }
    }

    /* loaded from: classes5.dex */
    static final class g implements Disposable {
        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return false;
        }

        g() {
        }
    }
}
