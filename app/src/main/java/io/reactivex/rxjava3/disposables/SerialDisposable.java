package io.reactivex.rxjava3.disposables;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class SerialDisposable implements Disposable {
    final AtomicReference<Disposable> a;

    public SerialDisposable() {
        this.a = new AtomicReference<>();
    }

    public SerialDisposable(@Nullable Disposable disposable) {
        this.a = new AtomicReference<>(disposable);
    }

    public boolean set(@Nullable Disposable disposable) {
        return DisposableHelper.set(this.a, disposable);
    }

    public boolean replace(@Nullable Disposable disposable) {
        return DisposableHelper.replace(this.a, disposable);
    }

    @Nullable
    public Disposable get() {
        Disposable disposable = this.a.get();
        return disposable == DisposableHelper.DISPOSED ? Disposable.disposed() : disposable;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        DisposableHelper.dispose(this.a);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return DisposableHelper.isDisposed(this.a.get());
    }
}
