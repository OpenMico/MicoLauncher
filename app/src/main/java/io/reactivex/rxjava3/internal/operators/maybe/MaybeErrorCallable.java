package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;

/* loaded from: classes5.dex */
public final class MaybeErrorCallable<T> extends Maybe<T> {
    final Supplier<? extends Throwable> a;

    public MaybeErrorCallable(Supplier<? extends Throwable> supplier) {
        this.a = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        Throwable th;
        maybeObserver.onSubscribe(Disposable.disposed());
        try {
            th = (Throwable) ExceptionHelper.nullCheck(this.a.get(), "Supplier returned a null Throwable.");
        } catch (Throwable th2) {
            th = th2;
            Exceptions.throwIfFatal(th);
        }
        maybeObserver.onError(th);
    }
}
