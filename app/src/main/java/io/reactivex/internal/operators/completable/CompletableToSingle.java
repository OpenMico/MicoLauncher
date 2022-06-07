package io.reactivex.internal.operators.completable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class CompletableToSingle<T> extends Single<T> {
    final CompletableSource a;
    final Callable<? extends T> b;
    final T c;

    public CompletableToSingle(CompletableSource completableSource, Callable<? extends T> callable, T t) {
        this.a = completableSource;
        this.c = t;
        this.b = callable;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes4.dex */
    final class a implements CompletableObserver {
        private final SingleObserver<? super T> b;

        a(SingleObserver<? super T> singleObserver) {
            this.b = singleObserver;
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
        public void onComplete() {
            T t;
            if (CompletableToSingle.this.b != null) {
                try {
                    t = (Object) CompletableToSingle.this.b.call();
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

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.b.onError(th);
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }
    }
}
