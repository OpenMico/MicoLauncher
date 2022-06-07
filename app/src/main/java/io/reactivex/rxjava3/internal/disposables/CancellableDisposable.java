package io.reactivex.rxjava3.internal.disposables;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Cancellable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class CancellableDisposable extends AtomicReference<Cancellable> implements Disposable {
    private static final long serialVersionUID = 5718521705281392066L;

    public CancellableDisposable(Cancellable cancellable) {
        super(cancellable);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return get() == null;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        Cancellable andSet;
        if (get() != null && (andSet = getAndSet(null)) != null) {
            try {
                andSet.cancel();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }
}
