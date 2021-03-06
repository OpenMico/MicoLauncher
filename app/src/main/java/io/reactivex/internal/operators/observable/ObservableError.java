package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class ObservableError<T> extends Observable<T> {
    final Callable<? extends Throwable> a;

    public ObservableError(Callable<? extends Throwable> callable) {
        this.a = callable;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        Throwable th;
        try {
            th = (Throwable) ObjectHelper.requireNonNull(this.a.call(), "Callable returned null throwable. Null values are generally not allowed in 2.x operators and sources.");
        } catch (Throwable th2) {
            th = th2;
            Exceptions.throwIfFatal(th);
        }
        EmptyDisposable.error(th, observer);
    }
}
