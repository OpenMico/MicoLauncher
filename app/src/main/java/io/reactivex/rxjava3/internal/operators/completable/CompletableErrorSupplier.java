package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CompletableErrorSupplier extends Completable {
    final Supplier<? extends Throwable> a;

    public CompletableErrorSupplier(Supplier<? extends Throwable> supplier) {
        this.a = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        Throwable th;
        try {
            th = (Throwable) Objects.requireNonNull(this.a.get(), "The error returned is null");
        } catch (Throwable th2) {
            th = th2;
            Exceptions.throwIfFatal(th);
        }
        EmptyDisposable.error(th, completableObserver);
    }
}
