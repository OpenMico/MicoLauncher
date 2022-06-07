package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;

/* loaded from: classes4.dex */
public final class SingleContains<T> extends Single<Boolean> {
    final SingleSource<T> a;
    final Object b;
    final BiPredicate<Object, Object> c;

    public SingleContains(SingleSource<T> singleSource, Object obj, BiPredicate<Object, Object> biPredicate) {
        this.a = singleSource;
        this.b = obj;
        this.c = biPredicate;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes4.dex */
    final class a implements SingleObserver<T> {
        private final SingleObserver<? super Boolean> b;

        a(SingleObserver<? super Boolean> singleObserver) {
            this.b = singleObserver;
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            try {
                this.b.onSuccess(Boolean.valueOf(SingleContains.this.c.test(t, SingleContains.this.b)));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.b.onError(th);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.b.onError(th);
        }
    }
}
