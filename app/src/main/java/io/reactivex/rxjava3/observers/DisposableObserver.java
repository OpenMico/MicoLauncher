package io.reactivex.rxjava3.observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.EndConsumerHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public abstract class DisposableObserver<T> implements Observer<T>, Disposable {
    final AtomicReference<Disposable> f = new AtomicReference<>();

    protected void onStart() {
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onSubscribe(@NonNull Disposable disposable) {
        if (EndConsumerHelper.setOnce(this.f, disposable, getClass())) {
            onStart();
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return this.f.get() == DisposableHelper.DISPOSED;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final void dispose() {
        DisposableHelper.dispose(this.f);
    }
}
