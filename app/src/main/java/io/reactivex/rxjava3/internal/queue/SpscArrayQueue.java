package io.reactivex.rxjava3.internal.queue;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes5.dex */
public final class SpscArrayQueue<E> extends AtomicReferenceArray<E> implements SimplePlainQueue<E> {
    private static final Integer a = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096);
    private static final long serialVersionUID = -1296597691183856449L;
    final int lookAheadStep;
    long producerLookAhead;
    final int mask = length() - 1;
    final AtomicLong producerIndex = new AtomicLong();
    final AtomicLong consumerIndex = new AtomicLong();

    int a(long j, int i) {
        return ((int) j) & i;
    }

    public SpscArrayQueue(int i) {
        super(Pow2.roundToPowerOfTwo(i));
        this.lookAheadStep = Math.min(i / 4, a.intValue());
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean offer(E e) {
        if (e != null) {
            int i = this.mask;
            long j = this.producerIndex.get();
            int a2 = a(j, i);
            if (j >= this.producerLookAhead) {
                long j2 = this.lookAheadStep + j;
                if (a(a(j2, i)) == null) {
                    this.producerLookAhead = j2;
                } else if (a(a2) != null) {
                    return false;
                }
            }
            a(a2, (int) e);
            a(j + 1);
            return true;
        }
        throw new NullPointerException("Null is not a valid element");
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean offer(E e, E e2) {
        return offer(e) && offer(e2);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue, io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    @Nullable
    public E poll() {
        long j = this.consumerIndex.get();
        int c = c(j);
        E a2 = a(c);
        if (a2 == null) {
            return null;
        }
        b(j + 1);
        a(c, (int) null);
        return a2;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return this.producerIndex.get() == this.consumerIndex.get();
    }

    void a(long j) {
        this.producerIndex.lazySet(j);
    }

    void b(long j) {
        this.consumerIndex.lazySet(j);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    int c(long j) {
        return ((int) j) & this.mask;
    }

    void a(int i, E e) {
        lazySet(i, e);
    }

    E a(int i) {
        return get(i);
    }
}
