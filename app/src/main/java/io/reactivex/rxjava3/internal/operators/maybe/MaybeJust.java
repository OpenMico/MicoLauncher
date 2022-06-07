package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.fuseable.ScalarSupplier;

/* loaded from: classes5.dex */
public final class MaybeJust<T> extends Maybe<T> implements ScalarSupplier<T> {
    final T a;

    public MaybeJust(T t) {
        this.a = t;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        maybeObserver.onSubscribe(Disposable.disposed());
        maybeObserver.onSuccess((T) this.a);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.ScalarSupplier, io.reactivex.rxjava3.functions.Supplier
    public T get() {
        return this.a;
    }
}
