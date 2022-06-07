package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class SingleFromSupplier<T> extends Single<T> {
    final Supplier<? extends T> a;

    public SingleFromSupplier(Supplier<? extends T> supplier) {
        this.a = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        Disposable empty = Disposable.empty();
        singleObserver.onSubscribe(empty);
        if (!empty.isDisposed()) {
            try {
                Object obj = (Object) Objects.requireNonNull(this.a.get(), "The supplier returned a null value");
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
