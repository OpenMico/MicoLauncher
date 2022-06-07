package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public final class CompletableDoOnEvent extends Completable {
    final CompletableSource a;
    final Consumer<? super Throwable> b;

    public CompletableDoOnEvent(CompletableSource completableSource, Consumer<? super Throwable> consumer) {
        this.a = completableSource;
        this.b = consumer;
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
            try {
                CompletableDoOnEvent.this.b.accept(null);
                this.b.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.b.onError(th);
            }
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            try {
                CompletableDoOnEvent.this.b.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.b.onError(th);
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }
    }
}
