package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.Notification;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.Experimental;
import io.reactivex.internal.operators.mixed.MaterializeSingleObserver;

@Experimental
/* loaded from: classes4.dex */
public final class CompletableMaterialize<T> extends Single<Notification<T>> {
    final Completable a;

    public CompletableMaterialize(Completable completable) {
        this.a = completable;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Notification<T>> singleObserver) {
        this.a.subscribe(new MaterializeSingleObserver(singleObserver));
    }
}
