package io.netty.util.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MpscLinkedQueueTailRef.java */
/* loaded from: classes4.dex */
public abstract class v<E> extends u<E> {
    private static final AtomicReferenceFieldUpdater<v, MpscLinkedQueueNode> a;
    private static final long serialVersionUID = 8717072462993327429L;
    private volatile transient MpscLinkedQueueNode<E> b;

    static {
        AtomicReferenceFieldUpdater<v, MpscLinkedQueueNode> newAtomicReferenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(v.class, "tailRef");
        if (newAtomicReferenceFieldUpdater == null) {
            newAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(v.class, MpscLinkedQueueNode.class, "b");
        }
        a = newAtomicReferenceFieldUpdater;
    }

    protected final MpscLinkedQueueNode<E> b() {
        return this.b;
    }

    protected final void c(MpscLinkedQueueNode<E> mpscLinkedQueueNode) {
        this.b = mpscLinkedQueueNode;
    }

    protected final MpscLinkedQueueNode<E> d(MpscLinkedQueueNode<E> mpscLinkedQueueNode) {
        return a.getAndSet(this, mpscLinkedQueueNode);
    }
}
