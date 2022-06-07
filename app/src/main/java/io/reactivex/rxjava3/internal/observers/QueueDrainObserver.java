package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.util.ObservableQueueDrain;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;

/* loaded from: classes4.dex */
public abstract class QueueDrainObserver<T, U, V> extends b implements Observer<T>, ObservableQueueDrain<U, V> {
    protected volatile boolean cancelled;
    protected volatile boolean done;
    protected final Observer<? super V> downstream;
    protected Throwable error;
    protected final SimplePlainQueue<U> queue;

    @Override // io.reactivex.rxjava3.internal.util.ObservableQueueDrain
    public void accept(Observer<? super V> observer, U u) {
    }

    public QueueDrainObserver(Observer<? super V> observer, SimplePlainQueue<U> simplePlainQueue) {
        this.downstream = observer;
        this.queue = simplePlainQueue;
    }

    @Override // io.reactivex.rxjava3.internal.util.ObservableQueueDrain
    public final boolean cancelled() {
        return this.cancelled;
    }

    @Override // io.reactivex.rxjava3.internal.util.ObservableQueueDrain
    public final boolean done() {
        return this.done;
    }

    @Override // io.reactivex.rxjava3.internal.util.ObservableQueueDrain
    public final boolean enter() {
        return this.a.getAndIncrement() == 0;
    }

    protected final void fastPathEmit(U u, boolean z, Disposable disposable) {
        Observer<? super V> observer = this.downstream;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (this.a.get() != 0 || !this.a.compareAndSet(0, 1)) {
            simplePlainQueue.offer(u);
            if (!enter()) {
                return;
            }
        } else {
            accept(observer, u);
            if (leave(-1) == 0) {
                return;
            }
        }
        QueueDrainHelper.drainLoop(simplePlainQueue, observer, z, disposable, this);
    }

    public final void fastPathOrderedEmit(U u, boolean z, Disposable disposable) {
        Observer<? super V> observer = this.downstream;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (this.a.get() != 0 || !this.a.compareAndSet(0, 1)) {
            simplePlainQueue.offer(u);
            if (!enter()) {
                return;
            }
        } else if (simplePlainQueue.isEmpty()) {
            accept(observer, u);
            if (leave(-1) == 0) {
                return;
            }
        } else {
            simplePlainQueue.offer(u);
        }
        QueueDrainHelper.drainLoop(simplePlainQueue, observer, z, disposable, this);
    }

    @Override // io.reactivex.rxjava3.internal.util.ObservableQueueDrain
    public final Throwable error() {
        return this.error;
    }

    @Override // io.reactivex.rxjava3.internal.util.ObservableQueueDrain
    public final int leave(int i) {
        return this.a.addAndGet(i);
    }
}
