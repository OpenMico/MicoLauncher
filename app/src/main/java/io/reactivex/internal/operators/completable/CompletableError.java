package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.internal.disposables.EmptyDisposable;

/* loaded from: classes4.dex */
public final class CompletableError extends Completable {
    final Throwable a;

    public CompletableError(Throwable th) {
        this.a = th;
    }

    @Override // io.reactivex.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        EmptyDisposable.error(this.a, completableObserver);
    }
}
