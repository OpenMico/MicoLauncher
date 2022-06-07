package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;

/* loaded from: classes4.dex */
public final class CompletableError extends Completable {
    final Throwable a;

    public CompletableError(Throwable th) {
        this.a = th;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        EmptyDisposable.error(this.a, completableObserver);
    }
}
