package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;

/* loaded from: classes4.dex */
public final class SingleOnErrorReturn<T> extends Single<T> {
    final SingleSource<? extends T> a;
    final Function<? super Throwable, ? extends T> b;
    final T c;

    public SingleOnErrorReturn(SingleSource<? extends T> singleSource, Function<? super Throwable, ? extends T> function, T t) {
        this.a = singleSource;
        this.b = function;
        this.c = t;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes4.dex */
    final class a implements SingleObserver<T> {
        private final SingleObserver<? super T> b;

        a(SingleObserver<? super T> singleObserver) {
            this.b = singleObserver;
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            T t;
            if (SingleOnErrorReturn.this.b != null) {
                try {
                    t = (Object) SingleOnErrorReturn.this.b.apply(th);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.b.onError(new CompositeException(th, th2));
                    return;
                }
            } else {
                t = SingleOnErrorReturn.this.c;
            }
            if (t == null) {
                NullPointerException nullPointerException = new NullPointerException("Value supplied was null");
                nullPointerException.initCause(th);
                this.b.onError(nullPointerException);
                return;
            }
            this.b.onSuccess(t);
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.b.onSuccess(t);
        }
    }
}
