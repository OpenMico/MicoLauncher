package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class CompletableErrorSupplier extends Completable {
    final Callable<? extends Throwable> a;

    public CompletableErrorSupplier(Callable<? extends Throwable> callable) {
        this.a = callable;
    }

    @Override // io.reactivex.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        Throwable th;
        try {
            th = (Throwable) ObjectHelper.requireNonNull(this.a.call(), "The error returned is null");
        } catch (Throwable th2) {
            th = th2;
            Exceptions.throwIfFatal(th);
        }
        EmptyDisposable.error(th, completableObserver);
    }
}
