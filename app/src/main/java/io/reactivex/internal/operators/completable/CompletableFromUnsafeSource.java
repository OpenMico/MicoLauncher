package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;

/* loaded from: classes4.dex */
public final class CompletableFromUnsafeSource extends Completable {
    final CompletableSource a;

    public CompletableFromUnsafeSource(CompletableSource completableSource) {
        this.a = completableSource;
    }

    @Override // io.reactivex.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(completableObserver);
    }
}
