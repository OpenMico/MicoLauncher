package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;

/* loaded from: classes4.dex */
public final class CompletableToSingle<T> extends Single<T> {
    final CompletableSource a;
    final Supplier<? extends T> b;
    final T c;

    public CompletableToSingle(CompletableSource completableSource, Supplier<? extends T> supplier, T t) {
        this.a = completableSource;
        this.c = t;
        this.b = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes4.dex */
    final class a implements CompletableObserver {
        private final SingleObserver<? super T> b;

        a(SingleObserver<? super T> singleObserver) {
            this.b = singleObserver;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            T t;
            if (CompletableToSingle.this.b != null) {
                try {
                    t = (Object) CompletableToSingle.this.b.get();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.b.onError(th);
                    return;
                }
            } else {
                t = CompletableToSingle.this.c;
            }
            if (t == null) {
                this.b.onError(new NullPointerException("The value supplied is null"));
            } else {
                this.b.onSuccess(t);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            this.b.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }
    }
}
