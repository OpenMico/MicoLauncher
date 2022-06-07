package io.reactivex.rxjava3.internal.fuseable;

import io.reactivex.rxjava3.annotations.NonNull;

/* loaded from: classes4.dex */
public abstract class AbstractEmptyQueueFuseable<T> implements QueueDisposable<T>, QueueSubscription<T> {
    @Override // org.reactivestreams.Subscription
    public void cancel() {
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public final void clear() {
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return false;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public final boolean isEmpty() {
        return true;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public final T poll() throws Throwable {
        return null;
    }

    @Override // org.reactivestreams.Subscription
    public final void request(long j) {
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
    public final int requestFusion(int i) {
        return i & 2;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public final boolean offer(@NonNull T t) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public final boolean offer(@NonNull T t, @NonNull T t2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
