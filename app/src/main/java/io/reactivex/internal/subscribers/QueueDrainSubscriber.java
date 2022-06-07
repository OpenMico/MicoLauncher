package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.QueueDrain;
import io.reactivex.internal.util.QueueDrainHelper;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public abstract class QueueDrainSubscriber<T, U, V> extends d implements FlowableSubscriber<T>, QueueDrain<U, V> {
    protected volatile boolean cancelled;
    protected volatile boolean done;
    protected final Subscriber<? super V> downstream;
    protected Throwable error;
    protected final SimplePlainQueue<U> queue;

    public boolean accept(Subscriber<? super V> subscriber, U u) {
        return false;
    }

    public QueueDrainSubscriber(Subscriber<? super V> subscriber, SimplePlainQueue<U> simplePlainQueue) {
        this.downstream = subscriber;
        this.queue = simplePlainQueue;
    }

    @Override // io.reactivex.internal.util.QueueDrain
    public final boolean cancelled() {
        return this.cancelled;
    }

    @Override // io.reactivex.internal.util.QueueDrain
    public final boolean done() {
        return this.done;
    }

    @Override // io.reactivex.internal.util.QueueDrain
    public final boolean enter() {
        return this.o.getAndIncrement() == 0;
    }

    public final boolean fastEnter() {
        return this.o.get() == 0 && this.o.compareAndSet(0, 1);
    }

    protected final void fastPathEmitMax(U u, boolean z, Disposable disposable) {
        Subscriber<? super V> subscriber = this.downstream;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (fastEnter()) {
            long j = this.n.get();
            if (j != 0) {
                if (accept(subscriber, u) && j != Long.MAX_VALUE) {
                    produced(1L);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                disposable.dispose();
                subscriber.onError(new MissingBackpressureException("Could not emit buffer due to lack of requests"));
                return;
            }
        } else {
            simplePlainQueue.offer(u);
            if (!enter()) {
                return;
            }
        }
        QueueDrainHelper.drainMaxLoop(simplePlainQueue, subscriber, z, disposable, this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void fastPathOrderedEmitMax(U u, boolean z, Disposable disposable) {
        Subscriber<? super V> subscriber = this.downstream;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (fastEnter()) {
            long j = this.n.get();
            if (j == 0) {
                this.cancelled = true;
                disposable.dispose();
                subscriber.onError(new MissingBackpressureException("Could not emit buffer due to lack of requests"));
                return;
            } else if (simplePlainQueue.isEmpty()) {
                if (accept(subscriber, u) && j != Long.MAX_VALUE) {
                    produced(1L);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                simplePlainQueue.offer(u);
            }
        } else {
            simplePlainQueue.offer(u);
            if (!enter()) {
                return;
            }
        }
        QueueDrainHelper.drainMaxLoop(simplePlainQueue, subscriber, z, disposable, this);
    }

    @Override // io.reactivex.internal.util.QueueDrain
    public final Throwable error() {
        return this.error;
    }

    @Override // io.reactivex.internal.util.QueueDrain
    public final int leave(int i) {
        return this.o.addAndGet(i);
    }

    @Override // io.reactivex.internal.util.QueueDrain
    public final long requested() {
        return this.n.get();
    }

    @Override // io.reactivex.internal.util.QueueDrain
    public final long produced(long j) {
        return this.n.addAndGet(-j);
    }

    public final void requested(long j) {
        if (SubscriptionHelper.validate(j)) {
            BackpressureHelper.add(this.n, j);
        }
    }
}
