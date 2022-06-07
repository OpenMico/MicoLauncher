package io.reactivex.rxjava3.internal.subscriptions;

import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes5.dex */
public abstract class BasicIntQueueSubscription<T> extends AtomicInteger implements QueueSubscription<T> {
    private static final long serialVersionUID = -6671519529404341862L;

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public final boolean offer(T t) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public final boolean offer(T t, T t2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
