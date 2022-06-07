package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class SingleDefer<T> extends Single<T> {
    final Callable<? extends SingleSource<? extends T>> a;

    public SingleDefer(Callable<? extends SingleSource<? extends T>> callable) {
        this.a = callable;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        try {
            ((SingleSource) ObjectHelper.requireNonNull(this.a.call(), "The singleSupplier returned a null SingleSource")).subscribe(singleObserver);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }
}
