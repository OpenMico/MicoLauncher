package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiPredicate;

/* loaded from: classes5.dex */
public final class SingleContains<T> extends Single<Boolean> {
    final SingleSource<T> a;
    final Object b;
    final BiPredicate<Object, Object> c;

    public SingleContains(SingleSource<T> singleSource, Object obj, BiPredicate<Object, Object> biPredicate) {
        this.a = singleSource;
        this.b = obj;
        this.c = biPredicate;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes5.dex */
    final class a implements SingleObserver<T> {
        private final SingleObserver<? super Boolean> b;

        a(SingleObserver<? super Boolean> singleObserver) {
            this.b = singleObserver;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t) {
            try {
                this.b.onSuccess(Boolean.valueOf(SingleContains.this.c.test(t, SingleContains.this.b)));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.b.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.b.onError(th);
        }
    }
}
