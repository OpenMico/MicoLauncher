package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class CompletableDefer extends Completable {
    final Callable<? extends CompletableSource> a;

    public CompletableDefer(Callable<? extends CompletableSource> callable) {
        this.a = callable;
    }

    @Override // io.reactivex.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        try {
            ((CompletableSource) ObjectHelper.requireNonNull(this.a.call(), "The completableSupplier returned a null CompletableSource")).subscribe(completableObserver);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, completableObserver);
        }
    }
}
