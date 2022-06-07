package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOperator;
import io.reactivex.SingleSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;

/* loaded from: classes4.dex */
public final class SingleLift<T, R> extends Single<R> {
    final SingleSource<T> a;
    final SingleOperator<? extends R, ? super T> b;

    public SingleLift(SingleSource<T> singleSource, SingleOperator<? extends R, ? super T> singleOperator) {
        this.a = singleSource;
        this.b = singleOperator;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super R> singleObserver) {
        try {
            this.a.subscribe((SingleObserver) ObjectHelper.requireNonNull(this.b.apply(singleObserver), "The onLift returned a null SingleObserver"));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }
}
