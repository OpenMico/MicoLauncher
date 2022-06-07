package io.reactivex.observers;

import io.reactivex.MaybeObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public abstract class DisposableMaybeObserver<T> implements MaybeObserver<T>, Disposable {
    final AtomicReference<Disposable> a = new AtomicReference<>();

    protected void onStart() {
    }

    @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
    public final void onSubscribe(@NonNull Disposable disposable) {
        if (EndConsumerHelper.setOnce(this.a, disposable, getClass())) {
            onStart();
        }
    }

    @Override // io.reactivex.disposables.Disposable
    public final boolean isDisposed() {
        return this.a.get() == DisposableHelper.DISPOSED;
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        DisposableHelper.dispose(this.a);
    }
}
