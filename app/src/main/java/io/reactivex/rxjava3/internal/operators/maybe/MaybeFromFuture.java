package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public final class MaybeFromFuture<T> extends Maybe<T> {
    final Future<? extends T> a;
    final long b;
    final TimeUnit c;

    public MaybeFromFuture(Future<? extends T> future, long j, TimeUnit timeUnit) {
        this.a = future;
        this.b = j;
        this.c = timeUnit;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        Object obj;
        Disposable empty = Disposable.empty();
        maybeObserver.onSubscribe(empty);
        if (!empty.isDisposed()) {
            try {
                if (this.b <= 0) {
                    obj = (Object) this.a.get();
                } else {
                    obj = (Object) this.a.get(this.b, this.c);
                }
                if (empty.isDisposed()) {
                    return;
                }
                if (obj == null) {
                    maybeObserver.onComplete();
                } else {
                    maybeObserver.onSuccess(obj);
                }
            } catch (Throwable th) {
                th = th;
                Exceptions.throwIfFatal(th);
                if (th instanceof ExecutionException) {
                    th = th.getCause();
                }
                Exceptions.throwIfFatal(th);
                if (!empty.isDisposed()) {
                    maybeObserver.onError(th);
                }
            }
        }
    }
}
