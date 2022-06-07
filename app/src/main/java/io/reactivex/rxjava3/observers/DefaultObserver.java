package io.reactivex.rxjava3.observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.EndConsumerHelper;

/* loaded from: classes5.dex */
public abstract class DefaultObserver<T> implements Observer<T> {
    private Disposable a;

    protected void onStart() {
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onSubscribe(@NonNull Disposable disposable) {
        if (EndConsumerHelper.validate(this.a, disposable, getClass())) {
            this.a = disposable;
            onStart();
        }
    }

    protected final void cancel() {
        Disposable disposable = this.a;
        this.a = DisposableHelper.DISPOSED;
        disposable.dispose();
    }
}
