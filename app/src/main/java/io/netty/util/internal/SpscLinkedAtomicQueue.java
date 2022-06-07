package io.netty.util.internal;

/* loaded from: classes4.dex */
public final class SpscLinkedAtomicQueue<E> extends a<E> {
    public SpscLinkedAtomicQueue() {
        LinkedQueueAtomicNode<E> linkedQueueAtomicNode = new LinkedQueueAtomicNode<>();
        spProducerNode(linkedQueueAtomicNode);
        spConsumerNode(linkedQueueAtomicNode);
        linkedQueueAtomicNode.soNext(null);
    }

    @Override // java.util.Queue
    public boolean offer(E e) {
        if (e != null) {
            LinkedQueueAtomicNode<E> linkedQueueAtomicNode = new LinkedQueueAtomicNode<>(e);
            lpProducerNode().soNext(linkedQueueAtomicNode);
            spProducerNode(linkedQueueAtomicNode);
            return true;
        }
        throw new IllegalArgumentException("null elements not allowed");
    }

    @Override // java.util.Queue
    public E poll() {
        LinkedQueueAtomicNode<E> lvNext = lpConsumerNode().lvNext();
        if (lvNext == null) {
            return null;
        }
        E andNullValue = lvNext.getAndNullValue();
        spConsumerNode(lvNext);
        return andNullValue;
    }

    @Override // java.util.Queue
    public E peek() {
        LinkedQueueAtomicNode<E> lvNext = lpConsumerNode().lvNext();
        if (lvNext != null) {
            return lvNext.lpValue();
        }
        return null;
    }
}
