package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;

/* loaded from: classes5.dex */
public final class SingleDoOnError<T> extends Single<T> {
    final SingleSource<T> a;
    final Consumer<? super Throwable> b;

    public SingleDoOnError(SingleSource<T> singleSource, Consumer<? super Throwable> consumer) {
        this.a = singleSource;
        this.b = consumer;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes5.dex */
    final class a implements SingleObserver<T> {
        private final SingleObserver<? super T> b;

        a(SingleObserver<? super T> singleObserver) {
            this.b = singleObserver;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t) {
            this.b.onSuccess(t);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
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
