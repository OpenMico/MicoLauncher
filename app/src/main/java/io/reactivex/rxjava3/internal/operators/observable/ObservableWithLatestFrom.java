package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.observers.SerializedObserver;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableWithLatestFrom<T, U, R> extends a<T, R> {
    final BiFunction<? super T, ? super U, ? extends R> a;
    final ObservableSource<? extends U> b;

    public ObservableWithLatestFrom(ObservableSource<T> observableSource, BiFunction<? super T, ? super U, ? extends R> biFunction, ObservableSource<? extends U> observableSource2) {
        super(observableSource);
        this.a = biFunction;
        this.b = observableSource2;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super R> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        a aVar = new a(serializedObserver, this.a);
        serializedObserver.onSubscribe(aVar);
        this.b.subscribe(new b(aVar));
        this.source.subscribe(aVar);
    }

    /* loaded from: classes5.dex */
    static final class a<T, U, R> extends AtomicReference<U> implements Observer<T>, Disposable {
        private static final long serialVersionUID = -312246233408980075L;
        final BiFunction<? super T, ? super U, ? extends R> combiner;
        final Observer<? super R> downstream;
        final AtomicReference<Disposable> upstream = new AtomicReference<>();
        final AtomicReference<Disposable> other = new AtomicReference<>();

        a(Observer<? super R> observer, BiFunction<? super T, ? super U, ? extends R> biFunction) {
            this.downstream = observer;
            this.combiner = biFunction;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.upstream, disposable);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            U u = get();
            if (u != null) {
                try {
                    this.downstream.onNext(Objects.requireNonNull(this.combiner.apply(t, u), "The combiner returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    dispose();
                    this.downstream.onError(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.other);
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            DisposableHelper.dispose(this.other);
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.upstream);
            DisposableHelper.dispose(this.other);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.upstream.get());
        }

        public boolean a(Disposable disposable) {
            return DisposableHelper.setOnce(this.other, disposable);
        }

        public void a(Throwable th) {
            DisposableHelper.dispose(this.upstream);
            this.downstream.onError(th);
        }
    }

    /* loaded from: classes5.dex */
    final class b implements Observer<U> {
        private final a<T, U, R> b;

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
        }

        b(a<T, U, R> aVar) {
            this.b = aVar;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            this.b.a(disposable);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(U u) {
            this.b.lazySet(u);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.b.a(th);
        }
    }
}
