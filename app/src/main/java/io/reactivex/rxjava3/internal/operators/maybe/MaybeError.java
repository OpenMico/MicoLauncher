package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes5.dex */
public final class MaybeError<T> extends Maybe<T> {
    final Throwable a;

    public MaybeError(Throwable th) {
        this.a = th;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        maybeObserver.onSubscribe(Disposable.disposed());
        maybeObserver.onError(this.a);
    }
}
