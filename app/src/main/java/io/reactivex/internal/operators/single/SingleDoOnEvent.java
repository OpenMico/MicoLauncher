package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiConsumer;

/* loaded from: classes4.dex */
public final class SingleDoOnEvent<T> extends Single<T> {
    final SingleSource<T> a;
    final BiConsumer<? super T, ? super Throwable> b;

    public SingleDoOnEvent(SingleSource<T> singleSource, BiConsumer<? super T, ? super Throwable> biConsumer) {
        this.a = singleSource;
        this.b = biConsumer;
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
            try {
                SingleDoOnEvent.this.b.accept(t, null);
                this.b.onSuccess(t);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.b.onError(th);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            try {
                SingleDoOnEvent.this.b.accept(null, th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.b.onError(th);
        }
    }
}
