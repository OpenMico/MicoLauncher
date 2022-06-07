package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableReduceSeedSingle;
import java.util.Objects;
import org.reactivestreams.Publisher;

/* loaded from: classes5.dex */
public final class FlowableReduceWithSingle<T, R> extends Single<R> {
    final Publisher<T> a;
    final Supplier<R> b;
    final BiFunction<R, ? super T, R> c;

    public FlowableReduceWithSingle(Publisher<T> publisher, Supplier<R> supplier, BiFunction<R, ? super T, R> biFunction) {
        this.a = publisher;
        this.b = supplier;
        this.c = biFunction;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super R> singleObserver) {
        try {
            this.a.subscribe(new FlowableReduceSeedSingle.a(singleObserver, this.c, Objects.requireNonNull(this.b.get(), "The seedSupplier returned a null value")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }
}
