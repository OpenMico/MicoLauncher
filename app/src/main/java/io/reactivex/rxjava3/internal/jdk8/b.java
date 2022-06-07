package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableStageObserver.java */
/* loaded from: classes4.dex */
public abstract class b<T> extends CompletableFuture<T> implements Observer<T> {
    final AtomicReference<Disposable> c = new AtomicReference<>();
    T d;

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onSubscribe(@NonNull Disposable disposable) {
        DisposableHelper.setOnce(this.c, disposable);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onError(Throwable th) {
        clear();
        if (!completeExceptionally(th)) {
            RxJavaPlugins.onError(th);
        }
    }

    protected final void disposeUpstream() {
        DisposableHelper.dispose(this.c);
    }

    protected final void clear() {
        this.d = null;
        this.c.lazySet(DisposableHelper.DISPOSED);
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        disposeUpstream();
        return super.cancel(z);
    }

    @Override // java.util.concurrent.CompletableFuture
    public final boolean complete(T t) {
        disposeUpstream();
        return super.complete(t);
    }

    @Override // java.util.concurrent.CompletableFuture
    public final boolean completeExceptionally(Throwable th) {
        disposeUpstream();
        return super.completeExceptionally(th);
    }
}
