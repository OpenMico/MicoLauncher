package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes5.dex */
public final class MaybeFromRunnable<T> extends Maybe<T> implements Supplier<T> {
    final Runnable a;

    public MaybeFromRunnable(Runnable runnable) {
        this.a = runnable;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        Disposable empty = Disposable.empty();
        maybeObserver.onSubscribe(empty);
        if (!empty.isDisposed()) {
            try {
                this.a.run();
                if (!empty.isDisposed()) {
                    maybeObserver.onComplete();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                if (!empty.isDisposed()) {
                    maybeObserver.onError(th);
                } else {
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    @Override // io.reactivex.rxjava3.functions.Supplier
    public T get() {
        this.a.run();
        return null;
    }
}
