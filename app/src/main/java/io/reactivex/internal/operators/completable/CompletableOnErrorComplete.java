package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;

/* loaded from: classes4.dex */
public final class CompletableOnErrorComplete extends Completable {
    final CompletableSource a;
    final Predicate<? super Throwable> b;

    public CompletableOnErrorComplete(CompletableSource completableSource, Predicate<? super Throwable> predicate) {
        this.a = completableSource;
        this.b = predicate;
    }

    @Override // io.reactivex.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new a(completableObserver));
    }

    /* loaded from: classes4.dex */
    final class a implements CompletableObserver {
        private final CompletableObserver b;

        a(CompletableObserver completableObserver) {
            this.b = completableObserver;
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
        public void onComplete() {
            this.b.onComplete();
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            try {
                if (CompletableOnErrorComplete.this.b.test(th)) {
                    this.b.onComplete();
                } else {
                    this.b.onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.b.onError(new CompositeException(th, th2));
            }
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }
    }
}
