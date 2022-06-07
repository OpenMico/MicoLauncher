package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.Notification;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.Experimental;
import io.reactivex.internal.operators.mixed.MaterializeSingleObserver;

@Experimental
/* loaded from: classes4.dex */
public final class MaybeMaterialize<T> extends Single<Notification<T>> {
    final Maybe<T> a;

    public MaybeMaterialize(Maybe<T> maybe) {
        this.a = maybe;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Notification<T>> singleObserver) {
        this.a.subscribe(new MaterializeSingleObserver(singleObserver));
    }
}
