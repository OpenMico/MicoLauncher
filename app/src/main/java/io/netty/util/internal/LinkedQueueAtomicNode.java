package io.netty.util.internal;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class LinkedQueueAtomicNode<E> extends AtomicReference<LinkedQueueAtomicNode<E>> {
    private static final long serialVersionUID = 2404266111789071508L;
    private E value;

    /* JADX INFO: Access modifiers changed from: package-private */
    public LinkedQueueAtomicNode() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LinkedQueueAtomicNode(E e) {
        spValue(e);
    }

    public E getAndNullValue() {
        E lpValue = lpValue();
        spValue(null);
        return lpValue;
    }

    public E lpValue() {
        return this.value;
    }

    public void spValue(E e) {
        this.value = e;
    }

    public void soNext(LinkedQueueAtomicNode<E> linkedQueueAtomicNode) {
        lazySet(linkedQueueAtomicNode);
    }

    public LinkedQueueAtomicNode<E> lvNext() {
        return get();
    }
}
