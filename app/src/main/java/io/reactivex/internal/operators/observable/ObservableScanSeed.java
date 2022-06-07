package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class ObservableScanSeed<T, R> extends a<T, R> {
    final BiFunction<R, ? super T, R> a;
    final Callable<R> b;

    public ObservableScanSeed(ObservableSource<T> observableSource, Callable<R> callable, BiFunction<R, ? super T, R> biFunction) {
        super(observableSource);
        this.a = biFunction;
        this.b = callable;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        try {
            this.source.subscribe(new a(observer, this.a, ObjectHelper.requireNonNull(this.b.call(), "The seed supplied is null")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> implements Observer<T>, Disposable {
        final Observer<? super R> a;
        final BiFunction<R, ? super T, R> b;
        R c;
        Disposable d;
        boolean e;

        a(Observer<? super R> observer, BiFunction<R, ? super T, R> biFunction, R r) {
            this.a = observer;
            this.b = biFunction;
            this.c = r;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.onSubscribe(this);
                this.a.onNext((R) this.c);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.d.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!this.e) {
                try {
                    R r = (R) ObjectHelper.requireNonNull(this.b.apply(this.c, t), "The accumulator returned a null value");
                    this.c = r;
                    this.a.onNext(r);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.d.dispose();
                    onError(th);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.a.onComplete();
            }
        }
    }
}
