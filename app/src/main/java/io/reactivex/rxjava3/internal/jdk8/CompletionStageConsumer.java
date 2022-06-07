package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class CompletionStageConsumer<T> extends CompletableFuture<T> implements CompletableObserver, MaybeObserver<T>, SingleObserver<T> {
    final AtomicReference<Disposable> a = new AtomicReference<>();
    final boolean b;
    final T c;

    public CompletionStageConsumer(boolean z, T t) {
        this.b = z;
        this.c = t;
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onSubscribe(@NonNull Disposable disposable) {
        DisposableHelper.setOnce(this.a, disposable);
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onSuccess(@NonNull T t) {
        b();
        complete(t);
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onError(Throwable th) {
        b();
        if (!completeExceptionally(th)) {
            RxJavaPlugins.onError(th);
        }
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onComplete() {
        if (this.b) {
            complete(this.c);
        } else {
            completeExceptionally(new NoSuchElementException("The source was empty"));
        }
    }

    void a() {
        DisposableHelper.dispose(this.a);
    }

    void b() {
        this.a.lazySet(DisposableHelper.DISPOSED);
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        a();
        return super.cancel(z);
    }

    @Override // java.util.concurrent.CompletableFuture
    public boolean complete(T t) {
        a();
        return super.complete(t);
    }

    @Override // java.util.concurrent.CompletableFuture
    public boolean completeExceptionally(Throwable th) {
        a();
        return super.completeExceptionally(th);
    }
}
