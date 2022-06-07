package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.Callable;

/* loaded from: classes5.dex */
public final class SingleFromCallable<T> extends Single<T> {
    final Callable<? extends T> a;

    public SingleFromCallable(Callable<? extends T> callable) {
        this.a = callable;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        Disposable empty = Disposable.empty();
        singleObserver.onSubscribe(empty);
        if (!empty.isDisposed()) {
            try {
                Object obj = (Object) Objects.requireNonNull(this.a.call(), "The callable returned a null value");
                if (!empty.isDisposed()) {
                    singleObserver.onSuccess(obj);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                if (!empty.isDisposed()) {
                    singleObserver.onError(th);
                } else {
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }
}
