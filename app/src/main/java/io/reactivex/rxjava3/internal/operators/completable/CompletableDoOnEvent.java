package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;

/* loaded from: classes4.dex */
public final class CompletableDoOnEvent extends Completable {
    final CompletableSource a;
    final Consumer<? super Throwable> b;

    public CompletableDoOnEvent(CompletableSource completableSource, Consumer<? super Throwable> consumer) {
        this.a = completableSource;
        this.b = consumer;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new a(completableObserver));
    }

    /* loaded from: classes4.dex */
    final class a implements CompletableObserver {
        private final CompletableObserver b;

        a(CompletableObserver completableObserver) {
            this.b = completableObserver;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            try {
                CompletableDoOnEvent.this.b.accept(null);
                this.b.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.b.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            try {
                CompletableDoOnEvent.this.b.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.b.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }
    }
}
