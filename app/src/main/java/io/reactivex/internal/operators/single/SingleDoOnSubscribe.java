package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class SingleDoOnSubscribe<T> extends Single<T> {
    final SingleSource<T> a;
    final Consumer<? super Disposable> b;

    public SingleDoOnSubscribe(SingleSource<T> singleSource, Consumer<? super Disposable> consumer) {
        this.a = singleSource;
        this.b = consumer;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements SingleObserver<T> {
        final SingleObserver<? super T> a;
        final Consumer<? super Disposable> b;
        boolean c;

        a(SingleObserver<? super T> singleObserver, Consumer<? super Disposable> consumer) {
            this.a = singleObserver;
            this.b = consumer;
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            try {
                this.b.accept(disposable);
                this.a.onSubscribe(disposable);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.c = true;
                disposable.dispose();
                EmptyDisposable.error(th, this.a);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            if (!this.c) {
                this.a.onSuccess(t);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.onError(th);
            }
        }
    }
}
