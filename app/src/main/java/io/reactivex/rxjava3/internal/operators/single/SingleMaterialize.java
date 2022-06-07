package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.internal.operators.mixed.MaterializeSingleObserver;

/* loaded from: classes5.dex */
public final class SingleMaterialize<T> extends Single<Notification<T>> {
    final Single<T> a;

    public SingleMaterialize(Single<T> single) {
        this.a = single;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super Notification<T>> singleObserver) {
        this.a.subscribe(new MaterializeSingleObserver(singleObserver));
    }
}
