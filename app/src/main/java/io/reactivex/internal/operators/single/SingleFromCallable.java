package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class SingleFromCallable<T> extends Single<T> {
    final Callable<? extends T> a;

    public SingleFromCallable(Callable<? extends T> callable) {
        this.a = callable;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        Disposable empty = Disposables.empty();
        singleObserver.onSubscribe(empty);
        if (!empty.isDisposed()) {
            try {
                Object obj = (Object) ObjectHelper.requireNonNull(this.a.call(), "The callable returned a null value");
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
