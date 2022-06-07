package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class SafeMaybeObserver<T> implements MaybeObserver<T> {
    final MaybeObserver<? super T> a;
    boolean b;

    public SafeMaybeObserver(MaybeObserver<? super T> maybeObserver) {
        this.a = maybeObserver;
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onSubscribe(@NonNull Disposable disposable) {
        try {
            this.a.onSubscribe(disposable);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            this.b = true;
            disposable.dispose();
            RxJavaPlugins.onError(th);
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onSuccess(@NonNull T t) {
        if (!this.b) {
            try {
                this.a.onSuccess(t);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onError(@NonNull Throwable th) {
        if (this.b) {
            RxJavaPlugins.onError(th);
            return;
        }
        try {
            this.a.onError(th);
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            RxJavaPlugins.onError(new CompositeException(th, th2));
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onComplete() {
        if (!this.b) {
            try {
                this.a.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }
}
