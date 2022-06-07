package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;

/* loaded from: classes4.dex */
public final class ObservableOnErrorReturn<T> extends a<T, T> {
    final Function<? super Throwable, ? extends T> a;

    public ObservableOnErrorReturn(ObservableSource<T> observableSource, Function<? super Throwable, ? extends T> function) {
        super(observableSource);
        this.a = function;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer, this.a));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        final Function<? super Throwable, ? extends T> b;
        Disposable c;

        a(Observer<? super T> observer, Function<? super Throwable, ? extends T> function) {
            this.a = observer;
            this.b = function;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            try {
                Object apply = this.b.apply(th);
                if (apply == null) {
                    NullPointerException nullPointerException = new NullPointerException("The supplied value is null");
                    nullPointerException.initCause(th);
                    this.a.onError(nullPointerException);
                    return;
                }
                this.a.onNext(apply);
                this.a.onComplete();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.a.onError(new CompositeException(th, th2));
            }
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.a.onComplete();
        }
    }
}
