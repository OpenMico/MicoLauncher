package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.observers.SerializedObserver;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableSampleWithObservable<T> extends a<T, T> {
    final ObservableSource<?> a;
    final boolean b;

    public ObservableSampleWithObservable(ObservableSource<T> observableSource, ObservableSource<?> observableSource2, boolean z) {
        super(observableSource);
        this.a = observableSource2;
        this.b = z;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        if (this.b) {
            this.source.subscribe(new a(serializedObserver, this.a));
        } else {
            this.source.subscribe(new b(serializedObserver, this.a));
        }
    }

    /* loaded from: classes5.dex */
    static abstract class c<T> extends AtomicReference<T> implements Observer<T>, Disposable {
        private static final long serialVersionUID = -3517602651313910099L;
        final Observer<? super T> downstream;
        final AtomicReference<Disposable> other = new AtomicReference<>();
        final ObservableSource<?> sampler;
        Disposable upstream;

        abstract void a();

        abstract void b();

        c(Observer<? super T> observer, ObservableSource<?> observableSource) {
            this.downstream = observer;
            this.sampler = observableSource;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
                if (this.other.get() == null) {
                    this.sampler.subscribe(new d(this));
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            lazySet(t);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.other);
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            DisposableHelper.dispose(this.other);
            a();
        }

        boolean a(Disposable disposable) {
            return DisposableHelper.setOnce(this.other, disposable);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.other);
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.other.get() == DisposableHelper.DISPOSED;
        }

        public void a(Throwable th) {
            this.upstream.dispose();
            this.downstream.onError(th);
        }

        public void c() {
            this.upstream.dispose();
            a();
        }

        void d() {
            T andSet = getAndSet(null);
            if (andSet != null) {
                this.downstream.onNext(andSet);
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class d<T> implements Observer<Object> {
        final c<T> a;

        d(c<T> cVar) {
            this.a = cVar;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            this.a.a(disposable);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            this.a.b();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.a.a(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.a.c();
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T> extends c<T> {
        private static final long serialVersionUID = -3029755663834015785L;

        b(Observer<? super T> observer, ObservableSource<?> observableSource) {
            super(observer, observableSource);
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableSampleWithObservable.c
        void a() {
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableSampleWithObservable.c
        void b() {
            d();
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends c<T> {
        private static final long serialVersionUID = -3029755663834015785L;
        volatile boolean done;
        final AtomicInteger wip = new AtomicInteger();

        a(Observer<? super T> observer, ObservableSource<?> observableSource) {
            super(observer, observableSource);
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableSampleWithObservable.c
        void a() {
            this.done = true;
            if (this.wip.getAndIncrement() == 0) {
                d();
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableSampleWithObservable.c
        void b() {
            if (this.wip.getAndIncrement() == 0) {
                do {
                    boolean z = this.done;
                    d();
                    if (z) {
                        this.downstream.onComplete();
                        return;
                    }
                } while (this.wip.decrementAndGet() != 0);
            }
        }
    }
}
