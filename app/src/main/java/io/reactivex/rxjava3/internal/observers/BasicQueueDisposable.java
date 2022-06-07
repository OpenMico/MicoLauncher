package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.internal.fuseable.QueueDisposable;

/* loaded from: classes4.dex */
public abstract class BasicQueueDisposable<T> implements QueueDisposable<T> {
    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public final boolean offer(T t) {
        throw new UnsupportedOperationException("Should not be called");
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public final boolean offer(T t, T t2) {
        throw new UnsupportedOperationException("Should not be called");
    }
}
