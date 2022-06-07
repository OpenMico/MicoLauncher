package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class ObservableMapNotification<T, R> extends a<T, ObservableSource<? extends R>> {
    final Function<? super T, ? extends ObservableSource<? extends R>> a;
    final Function<? super Throwable, ? extends ObservableSource<? extends R>> b;
    final Callable<? extends ObservableSource<? extends R>> c;

    public ObservableMapNotification(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends R>> function, Function<? super Throwable, ? extends ObservableSource<? extends R>> function2, Callable<? extends ObservableSource<? extends R>> callable) {
        super(observableSource);
        this.a = function;
        this.b = function2;
        this.c = callable;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super ObservableSource<? extends R>> observer) {
        this.source.subscribe(new a(observer, this.a, this.b, this.c));
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> implements Observer<T>, Disposable {
        final Observer<? super ObservableSource<? extends R>> a;
        final Function<? super T, ? extends ObservableSource<? extends R>> b;
        final Function<? super Throwable, ? extends ObservableSource<? extends R>> c;
        final Callable<? extends ObservableSource<? extends R>> d;
        Disposable e;

        a(Observer<? super ObservableSource<? extends R>> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, Function<? super Throwable, ? extends ObservableSource<? extends R>> function2, Callable<? extends ObservableSource<? extends R>> callable) {
            this.a = observer;
            this.b = function;
            this.c = function2;
            this.d = callable;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.e, disposable)) {
                this.e = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.e.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.e.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            try {
                this.a.onNext((ObservableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The onNext ObservableSource returned is null"));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            try {
                this.a.onNext((ObservableSource) ObjectHelper.requireNonNull(this.c.apply(th), "The onError ObservableSource returned is null"));
                this.a.onComplete();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.a.onError(new CompositeException(th, th2));
            }
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            try {
                this.a.onNext((ObservableSource) ObjectHelper.requireNonNull(this.d.call(), "The onComplete ObservableSource returned is null"));
                this.a.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.a.onError(th);
            }
        }
    }
}
