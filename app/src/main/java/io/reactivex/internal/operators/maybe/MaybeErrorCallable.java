package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class MaybeErrorCallable<T> extends Maybe<T> {
    final Callable<? extends Throwable> a;

    public MaybeErrorCallable(Callable<? extends Throwable> callable) {
        this.a = callable;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        Throwable th;
        maybeObserver.onSubscribe(Disposables.disposed());
        try {
            th = (Throwable) ObjectHelper.requireNonNull(this.a.call(), "Callable returned null throwable. Null values are generally not allowed in 2.x operators and sources.");
        } catch (Throwable th2) {
            th = th2;
            Exceptions.throwIfFatal(th);
        }
        maybeObserver.onError(th);
    }
}
