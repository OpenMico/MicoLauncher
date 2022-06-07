package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;

/* loaded from: classes5.dex */
public final class SingleError<T> extends Single<T> {
    final Supplier<? extends Throwable> a;

    public SingleError(Supplier<? extends Throwable> supplier) {
        this.a = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        Throwable th;
        try {
            th = (Throwable) ExceptionHelper.nullCheck(this.a.get(), "Supplier returned a null Throwable.");
        } catch (Throwable th2) {
            th = th2;
            Exceptions.throwIfFatal(th);
        }
        EmptyDisposable.error(th, singleObserver);
    }
}
