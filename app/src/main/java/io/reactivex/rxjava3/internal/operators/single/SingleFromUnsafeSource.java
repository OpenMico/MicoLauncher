package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;

/* loaded from: classes5.dex */
public final class SingleFromUnsafeSource<T> extends Single<T> {
    final SingleSource<T> a;

    public SingleFromUnsafeSource(SingleSource<T> singleSource) {
        this.a = singleSource;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(singleObserver);
    }
}
