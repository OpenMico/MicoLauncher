package io.netty.util.internal;

import android.support.v4.media.session.PlaybackStateCompat;
import io.netty.util.internal.MessagePassingQueue;

/* loaded from: classes4.dex */
public class SpscLinkedQueue<E> extends b<E> {
    @Override // io.netty.util.internal.b, io.netty.util.internal.MessagePassingQueue
    public /* bridge */ /* synthetic */ int capacity() {
        return super.capacity();
    }

    public SpscLinkedQueue() {
        spProducerNode(new j<>());
        spConsumerNode(this.producerNode);
        this.consumerNode.a((j) null);
    }

    @Override // java.util.Queue, io.netty.util.internal.MessagePassingQueue
    public boolean offer(E e) {
        if (e != null) {
            j<E> jVar = new j<>(e);
            this.producerNode.a((j) jVar);
            this.producerNode = jVar;
            return true;
        }
        throw new IllegalArgumentException("null elements not allowed");
    }

    @Override // java.util.Queue, io.netty.util.internal.MessagePassingQueue
    public E poll() {
        j<E> c = this.consumerNode.c();
        if (c == null) {
            return null;
        }
        E a = c.a();
        this.consumerNode = c;
        return a;
    }

    @Override // java.util.Queue, io.netty.util.internal.MessagePassingQueue
    public E peek() {
        j<E> c = this.consumerNode.c();
        if (c != null) {
            return c.b();
        }
        return null;
    }

    @Override // io.netty.util.internal.MessagePassingQueue
    public boolean relaxedOffer(E e) {
        return offer(e);
    }

    @Override // io.netty.util.internal.MessagePassingQueue
    public E relaxedPoll() {
        return poll();
    }

    @Override // io.netty.util.internal.MessagePassingQueue
    public E relaxedPeek() {
        return peek();
    }

    @Override // io.netty.util.internal.MessagePassingQueue
    public int drain(MessagePassingQueue.Consumer<E> consumer) {
        long j = 0;
        do {
            int drain = drain(consumer, 4096);
            j += drain;
            if (drain != 4096) {
                break;
            }
        } while (j <= 2147479551);
        return (int) j;
    }

    @Override // io.netty.util.internal.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier) {
        long j = 0;
        do {
            fill(supplier, 4096);
            j += PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        } while (j <= 2147479551);
        return (int) j;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.util.internal.MessagePassingQueue
    public int drain(MessagePassingQueue.Consumer<E> consumer, int i) {
        j jVar = this.consumerNode;
        for (int i2 = 0; i2 < i; i2++) {
            jVar = jVar.c();
            if (jVar == null) {
                return i2;
            }
            Object a = jVar.a();
            this.consumerNode = jVar;
            consumer.accept(a);
        }
        return i;
    }

    @Override // io.netty.util.internal.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier, int i) {
        j jVar = this.producerNode;
        int i2 = 0;
        while (i2 < i) {
            j jVar2 = new j(supplier.get());
            jVar.a(jVar2);
            this.producerNode = jVar2;
            i2++;
            jVar = jVar2;
        }
        return i;
    }

    @Override // io.netty.util.internal.MessagePassingQueue
    public void drain(MessagePassingQueue.Consumer<E> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        j jVar = this.consumerNode;
        int i = 0;
        while (exitCondition.keepRunning()) {
            int i2 = 0;
            j<E> jVar2 = jVar;
            while (i2 < 4096) {
                j<E> c = jVar2.c();
                if (c == null) {
                    i = waitStrategy.idle(i);
                } else {
                    E a = c.a();
                    this.consumerNode = c;
                    consumer.accept(a);
                    i = 0;
                    jVar2 = c;
                }
                i2++;
                jVar2 = jVar2;
            }
            jVar = jVar2;
            i = i;
        }
    }

    @Override // io.netty.util.internal.MessagePassingQueue
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        j<E> jVar = this.producerNode;
        while (exitCondition.keepRunning()) {
            int i = 0;
            while (i < 4096) {
                j<E> jVar2 = new j<>(supplier.get());
                jVar.a((j) jVar2);
                this.producerNode = jVar2;
                i++;
                jVar = jVar2;
            }
        }
    }
}
