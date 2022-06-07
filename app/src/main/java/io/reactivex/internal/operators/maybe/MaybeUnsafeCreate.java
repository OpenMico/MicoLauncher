package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;

/* loaded from: classes4.dex */
public final class MaybeUnsafeCreate<T> extends a<T, T> {
    public MaybeUnsafeCreate(MaybeSource<T> maybeSource) {
        super(maybeSource);
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(maybeObserver);
    }
}
