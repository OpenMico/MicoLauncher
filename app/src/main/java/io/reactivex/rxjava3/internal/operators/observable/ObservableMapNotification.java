package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class ObservableMapNotification<T, R> extends a<T, ObservableSource<? extends R>> {
    final Function<? super T, ? extends ObservableSource<? extends R>> a;
    final Function<? super Throwable, ? extends ObservableSource<? extends R>> b;
    final Supplier<? extends ObservableSource<? extends R>> c;

    public ObservableMapNotification(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends R>> function, Function<? super Throwable, ? extends ObservableSource<? extends R>> function2, Supplier<? extends ObservableSource<? extends R>> supplier) {
        super(observableSource);
        this.a = function;
        this.b = function2;
        this.c = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super ObservableSource<? extends R>> observer) {
        this.source.subscribe(new a(observer, this.a, this.b, this.c));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> implements Observer<T>, Disposable {
        final Observer<? super ObservableSource<? extends R>> a;
        final Function<? super T, ? extends ObservableSource<? extends R>> b;
        final Function<? super Throwable, ? extends ObservableSource<? extends R>> c;
        final Supplier<? extends ObservableSource<? extends R>> d;
        Disposable e;

        a(Observer<? super ObservableSource<? extends R>> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, Function<? super Throwable, ? extends ObservableSource<? extends R>> function2, Supplier<? extends ObservableSource<? extends R>> supplier) {
            this.a = observer;
            this.b = function;
            this.c = function2;
            this.d = supplier;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.e, disposable)) {
                this.e = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.e.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.e.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            try {
                this.a.onNext((ObservableSource) Objects.requireNonNull(this.b.apply(t), "The onNext ObservableSource returned is null"));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            try {
                this.a.onNext((ObservableSource) Objects.requireNonNull(this.c.apply(th), "The onError ObservableSource returned is null"));
                this.a.onComplete();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.a.onError(new CompositeException(th, th2));
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            try {
                this.a.onNext((ObservableSource) Objects.requireNonNull(this.d.get(), "The onComplete ObservableSource returned is null"));
                this.a.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.a.onError(th);
            }
        }
    }
}
