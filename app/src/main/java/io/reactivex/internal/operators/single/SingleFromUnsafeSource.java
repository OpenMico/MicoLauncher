package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;

/* loaded from: classes4.dex */
public final class SingleFromUnsafeSource<T> extends Single<T> {
    final SingleSource<T> a;

    public SingleFromUnsafeSource(SingleSource<T> singleSource) {
        this.a = singleSource;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(singleObserver);
    }
}
