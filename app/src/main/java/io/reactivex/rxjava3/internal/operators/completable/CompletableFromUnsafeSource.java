package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;

/* loaded from: classes4.dex */
public final class CompletableFromUnsafeSource extends Completable {
    final CompletableSource a;

    public CompletableFromUnsafeSource(CompletableSource completableSource) {
        this.a = completableSource;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(completableObserver);
    }
}
