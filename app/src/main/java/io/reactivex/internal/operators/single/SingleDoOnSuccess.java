package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public final class SingleDoOnSuccess<T> extends Single<T> {
    final SingleSource<T> a;
    final Consumer<? super T> b;

    public SingleDoOnSuccess(SingleSource<T> singleSource, Consumer<? super T> consumer) {
        this.a = singleSource;
        this.b = consumer;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes4.dex */
    final class a implements SingleObserver<T> {
        final SingleObserver<? super T> a;

        a(SingleObserver<? super T> singleObserver) {
            this.a = singleObserver;
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.a.onSubscribe(disposable);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            try {
                SingleDoOnSuccess.this.b.accept(t);
                this.a.onSuccess(t);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.a.onError(th);
        }
    }
}
