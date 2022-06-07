package io.reactivex.internal.operators.single;

import io.reactivex.Notification;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.Experimental;
import io.reactivex.internal.operators.mixed.MaterializeSingleObserver;

@Experimental
/* loaded from: classes4.dex */
public final class SingleMaterialize<T> extends Single<Notification<T>> {
    final Single<T> a;

    public SingleMaterialize(Single<T> single) {
        this.a = single;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Notification<T>> singleObserver) {
        this.a.subscribe(new MaterializeSingleObserver(singleObserver));
    }
}
