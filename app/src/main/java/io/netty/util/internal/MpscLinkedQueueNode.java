package io.netty.util.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* loaded from: classes4.dex */
public abstract class MpscLinkedQueueNode<T> {
    private static final AtomicReferenceFieldUpdater<MpscLinkedQueueNode, MpscLinkedQueueNode> a;
    private volatile MpscLinkedQueueNode<T> b;

    public abstract T value();

    static {
        AtomicReferenceFieldUpdater<MpscLinkedQueueNode, MpscLinkedQueueNode> newAtomicReferenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(MpscLinkedQueueNode.class, "next");
        if (newAtomicReferenceFieldUpdater == null) {
            newAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(MpscLinkedQueueNode.class, MpscLinkedQueueNode.class, "b");
        }
        a = newAtomicReferenceFieldUpdater;
    }

    public final MpscLinkedQueueNode<T> d() {
        return this.b;
    }

    public final void a(MpscLinkedQueueNode<T> mpscLinkedQueueNode) {
        a.lazySet(this, mpscLinkedQueueNode);
    }

    public T clearMaybe() {
        return value();
    }

    public void e() {
        a(null);
    }
}
