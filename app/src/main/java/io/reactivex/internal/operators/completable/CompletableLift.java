package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOperator;
import io.reactivex.CompletableSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class CompletableLift extends Completable {
    final CompletableSource a;
    final CompletableOperator b;

    public CompletableLift(CompletableSource completableSource, CompletableOperator completableOperator) {
        this.a = completableSource;
        this.b = completableOperator;
    }

    @Override // io.reactivex.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        try {
            this.a.subscribe(this.b.apply(completableObserver));
        } catch (NullPointerException e) {
            throw e;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
    }
}
