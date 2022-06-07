package io.netty.util.internal;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BaseLinkedAtomicQueue.java */
/* loaded from: classes4.dex */
public abstract class a<E> extends AbstractQueue<E> {
    private final AtomicReference<LinkedQueueAtomicNode<E>> a = new AtomicReference<>();
    private final AtomicReference<LinkedQueueAtomicNode<E>> b = new AtomicReference<>();

    protected final LinkedQueueAtomicNode<E> lvProducerNode() {
        return this.a.get();
    }

    protected final LinkedQueueAtomicNode<E> lpProducerNode() {
        return this.a.get();
    }

    protected final void spProducerNode(LinkedQueueAtomicNode<E> linkedQueueAtomicNode) {
        this.a.lazySet(linkedQueueAtomicNode);
    }

    protected final LinkedQueueAtomicNode<E> xchgProducerNode(LinkedQueueAtomicNode<E> linkedQueueAtomicNode) {
        return this.a.getAndSet(linkedQueueAtomicNode);
    }

    protected final LinkedQueueAtomicNode<E> lvConsumerNode() {
        return this.b.get();
    }

    protected final LinkedQueueAtomicNode<E> lpConsumerNode() {
        return this.b.get();
    }

    protected final void spConsumerNode(LinkedQueueAtomicNode<E> linkedQueueAtomicNode) {
        this.b.lazySet(linkedQueueAtomicNode);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        LinkedQueueAtomicNode<E> lvNext;
        LinkedQueueAtomicNode<E> lvConsumerNode = lvConsumerNode();
        LinkedQueueAtomicNode<E> lvProducerNode = lvProducerNode();
        int i = 0;
        while (lvConsumerNode != lvProducerNode && i < Integer.MAX_VALUE) {
            do {
                lvNext = lvConsumerNode.lvNext();
            } while (lvNext == null);
            i++;
            lvConsumerNode = lvNext;
        }
        return i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean isEmpty() {
        return lvConsumerNode() == lvProducerNode();
    }
}
