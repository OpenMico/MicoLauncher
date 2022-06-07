package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class SingleDefer<T> extends Single<T> {
    final Supplier<? extends SingleSource<? extends T>> a;

    public SingleDefer(Supplier<? extends SingleSource<? extends T>> supplier) {
        this.a = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        try {
            ((SingleSource) Objects.requireNonNull(this.a.get(), "The singleSupplier returned a null SingleSource")).subscribe(singleObserver);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }
}
