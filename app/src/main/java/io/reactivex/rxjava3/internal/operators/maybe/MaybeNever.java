package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;

/* loaded from: classes5.dex */
public final class MaybeNever extends Maybe<Object> {
    public static final MaybeNever INSTANCE = new MaybeNever();

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super Object> maybeObserver) {
        maybeObserver.onSubscribe(EmptyDisposable.NEVER);
    }
}
