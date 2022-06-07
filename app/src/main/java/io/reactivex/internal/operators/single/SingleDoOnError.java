package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public final class SingleDoOnError<T> extends Single<T> {
    final SingleSource<T> a;
    final Consumer<? super Throwable> b;

    public SingleDoOnError(SingleSource<T> singleSource, Consumer<? super Throwable> consumer) {
        this.a = singleSource;
        this.b = consumer;
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
        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.b.onSuccess(t);
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            try {
                SingleDoOnError.this.b.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.b.onError(th);
        }
    }
}
