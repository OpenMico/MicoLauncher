package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableReduceSeedSingle;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class ObservableReduceWithSingle<T, R> extends Single<R> {
    final ObservableSource<T> a;
    final Supplier<R> b;
    final BiFunction<R, ? super T, R> c;

    public ObservableReduceWithSingle(ObservableSource<T> observableSource, Supplier<R> supplier, BiFunction<R, ? super T, R> biFunction) {
        this.a = observableSource;
        this.b = supplier;
        this.c = biFunction;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super R> singleObserver) {
        try {
            this.a.subscribe(new ObservableReduceSeedSingle.a(singleObserver, this.c, Objects.requireNonNull(this.b.get(), "The seedSupplier returned a null value")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }
}
