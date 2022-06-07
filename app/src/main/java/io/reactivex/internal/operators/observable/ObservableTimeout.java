package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableTimeoutTimed;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableTimeout<T, U, V> extends a<T, T> {
    final ObservableSource<U> a;
    final Function<? super T, ? extends ObservableSource<V>> b;
    final ObservableSource<? extends T> c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public interface d extends ObservableTimeoutTimed.d {
        void a(long j, Throwable th);
    }

    public ObservableTimeout(Observable<T> observable, ObservableSource<U> observableSource, Function<? super T, ? extends ObservableSource<V>> function, ObservableSource<? extends T> observableSource2) {
        super(observable);
        this.a = observableSource;
        this.b = function;
        this.c = observableSource2;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        ObservableSource<? extends T> observableSource = this.c;
        if (observableSource == null) {
            c cVar = new c(observer, this.b);
            observer.onSubscribe(cVar);
            cVar.a((ObservableSource<?>) this.a);
            this.source.subscribe(cVar);
            return;
        }
        b bVar = new b(observer, this.b, observableSource);
        observer.onSubscribe(bVar);
        bVar.a((ObservableSource<?>) this.a);
        this.source.subscribe(bVar);
    }

    /* loaded from: classes4.dex */
    static final class c<T> extends AtomicLong implements Observer<T>, Disposable, d {
        private static final long serialVersionUID = 3764492702657003550L;
        final Observer<? super T> downstream;
        final Function<? super T, ? extends ObservableSource<?>> itemTimeoutIndicator;
        final SequentialDisposable task = new SequentialDisposable();
        final AtomicReference<Disposable> upstream = new AtomicReference<>();

        c(Observer<? super T> observer, Function<? super T, ? extends ObservableSource<?>> function) {
            this.downstream = observer;
            this.itemTimeoutIndicator = function;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.upstream, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            long j = get();
            if (j != Long.MAX_VALUE) {
                long j2 = 1 + j;
                if (compareAndSet(j, j2)) {
                    Disposable disposable = this.task.get();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    this.downstream.onNext(t);
                    try {
                        ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply(t), "The itemTimeoutIndicator returned a null ObservableSource.");
                        a aVar = new a(j2, this);
                        if (this.task.replace(aVar)) {
                            observableSource.subscribe(aVar);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.upstream.get().dispose();
                        getAndSet(Long.MAX_VALUE);
                        this.downstream.onError(th);
                    }
                }
            }
        }

        void a(ObservableSource<?> observableSource) {
            if (observableSource != null) {
                a aVar = new a(0L, this);
                if (this.task.replace(aVar)) {
                    observableSource.subscribe(aVar);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.task.dispose();
                this.downstream.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.task.dispose();
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.internal.operators.observable.ObservableTimeoutTimed.d
        public void a(long j) {
            if (compareAndSet(j, Long.MAX_VALUE)) {
                DisposableHelper.dispose(this.upstream);
                this.downstream.onError(new TimeoutException());
            }
        }

        @Override // io.reactivex.internal.operators.observable.ObservableTimeout.d
        public void a(long j, Throwable th) {
            if (compareAndSet(j, Long.MAX_VALUE)) {
                DisposableHelper.dispose(this.upstream);
                this.downstream.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.upstream);
            this.task.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.upstream.get());
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable, d {
        private static final long serialVersionUID = -7508389464265974549L;
        final Observer<? super T> downstream;
        ObservableSource<? extends T> fallback;
        final Function<? super T, ? extends ObservableSource<?>> itemTimeoutIndicator;
        final SequentialDisposable task = new SequentialDisposable();
        final AtomicLong index = new AtomicLong();
        final AtomicReference<Disposable> upstream = new AtomicReference<>();

        b(Observer<? super T> observer, Function<? super T, ? extends ObservableSource<?>> function, ObservableSource<? extends T> observableSource) {
            this.downstream = observer;
            this.itemTimeoutIndicator = function;
            this.fallback = observableSource;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.upstream, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            long j = this.index.get();
            if (j != Long.MAX_VALUE) {
                long j2 = 1 + j;
                if (this.index.compareAndSet(j, j2)) {
                    Disposable disposable = this.task.get();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    this.downstream.onNext(t);
                    try {
                        ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply(t), "The itemTimeoutIndicator returned a null ObservableSource.");
                        a aVar = new a(j2, this);
                        if (this.task.replace(aVar)) {
                            observableSource.subscribe(aVar);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.upstream.get().dispose();
                        this.index.getAndSet(Long.MAX_VALUE);
                        this.downstream.onError(th);
                    }
                }
            }
        }

        void a(ObservableSource<?> observableSource) {
            if (observableSource != null) {
                a aVar = new a(0L, this);
                if (this.task.replace(aVar)) {
                    observableSource.subscribe(aVar);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.task.dispose();
                this.downstream.onError(th);
                this.task.dispose();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.task.dispose();
                this.downstream.onComplete();
                this.task.dispose();
            }
        }

        @Override // io.reactivex.internal.operators.observable.ObservableTimeoutTimed.d
        public void a(long j) {
            if (this.index.compareAndSet(j, Long.MAX_VALUE)) {
                DisposableHelper.dispose(this.upstream);
                ObservableSource<? extends T> observableSource = this.fallback;
                this.fallback = null;
                observableSource.subscribe(new ObservableTimeoutTimed.a(this.downstream, this));
            }
        }

        @Override // io.reactivex.internal.operators.observable.ObservableTimeout.d
        public void a(long j, Throwable th) {
            if (this.index.compareAndSet(j, Long.MAX_VALUE)) {
                DisposableHelper.dispose(this);
                this.downstream.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.upstream);
            DisposableHelper.dispose(this);
            this.task.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a extends AtomicReference<Disposable> implements Observer<Object>, Disposable {
        private static final long serialVersionUID = 8708641127342403073L;
        final long idx;
        final d parent;

        a(long j, d dVar) {
            this.idx = j;
            this.parent = dVar;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(Object obj) {
            Disposable disposable = (Disposable) get();
            if (disposable != DisposableHelper.DISPOSED) {
                disposable.dispose();
                lazySet(DisposableHelper.DISPOSED);
                this.parent.a(this.idx);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (get() != DisposableHelper.DISPOSED) {
                lazySet(DisposableHelper.DISPOSED);
                this.parent.a(this.idx, th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (get() != DisposableHelper.DISPOSED) {
                lazySet(DisposableHelper.DISPOSED);
                this.parent.a(this.idx);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }
    }
}
