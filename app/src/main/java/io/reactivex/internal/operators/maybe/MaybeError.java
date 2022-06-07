package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposables;

/* loaded from: classes4.dex */
public final class MaybeError<T> extends Maybe<T> {
    final Throwable a;

    public MaybeError(Throwable th) {
        this.a = th;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        maybeObserver.onSubscribe(Disposables.disposed());
        maybeObserver.onError(this.a);
    }
}
