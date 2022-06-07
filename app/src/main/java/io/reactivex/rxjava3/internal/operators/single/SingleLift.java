package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOperator;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class SingleLift<T, R> extends Single<R> {
    final SingleSource<T> a;
    final SingleOperator<? extends R, ? super T> b;

    public SingleLift(SingleSource<T> singleSource, SingleOperator<? extends R, ? super T> singleOperator) {
        this.a = singleSource;
        this.b = singleOperator;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super R> singleObserver) {
        try {
            this.a.subscribe((SingleObserver) Objects.requireNonNull(this.b.apply(singleObserver), "The onLift returned a null SingleObserver"));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }
}
