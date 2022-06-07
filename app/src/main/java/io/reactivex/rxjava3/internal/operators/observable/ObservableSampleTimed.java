package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableSampleTimed<T> extends a<T, T> {
    final long a;
    final TimeUnit b;
    final Scheduler c;
    final boolean d;

    public ObservableSampleTimed(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = z;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        if (this.d) {
            this.source.subscribe(new a(serializedObserver, this.a, this.b, this.c));
        } else {
            this.source.subscribe(new b(serializedObserver, this.a, this.b, this.c));
        }
    }

    /* loaded from: classes5.dex */
    static abstract class c<T> extends AtomicReference<T> implements Observer<T>, Disposable, Runnable {
        private static final long serialVersionUID = -3517602651313910099L;
        final Observer<? super T> downstream;
        final long period;
        final Scheduler scheduler;
        final AtomicReference<Disposable> timer = new AtomicReference<>();
        final TimeUnit unit;
        Disposable upstream;

        abstract void a();

        c(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.downstream = observer;
            this.period = j;
            this.unit = timeUnit;
            this.scheduler = scheduler;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
                Scheduler scheduler = this.scheduler;
                long j = this.period;
                DisposableHelper.replace(this.timer, scheduler.schedulePeriodicallyDirect(this, j, j, this.unit));
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            lazySet(t);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            b();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            b();
            a();
        }

        void b() {
            DisposableHelper.dispose(this.timer);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            b();
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        void c() {
            T andSet = getAndSet(null);
            if (andSet != null) {
                this.downstream.onNext(andSet);
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T> extends c<T> {
        private static final long serialVersionUID = -7139995637533111443L;

        b(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(observer, j, timeUnit, scheduler);
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableSampleTimed.c
        void a() {
            this.downstream.onComplete();
        }

        @Override // java.lang.Runnable
        public void run() {
            c();
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends c<T> {
        private static final long serialVersionUID = -7139995637533111443L;
        final AtomicInteger wip = new AtomicInteger(1);

        a(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(observer, j, timeUnit, scheduler);
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableSampleTimed.c
        void a() {
            c();
            if (this.wip.decrementAndGet() == 0) {
                this.downstream.onComplete();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.wip.incrementAndGet() == 2) {
                c();
                if (this.wip.decrementAndGet() == 0) {
                    this.downstream.onComplete();
                }
            }
        }
    }
}
