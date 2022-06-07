package io.netty.util.internal;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: MpscLinkedQueueHeadRef.java */
/* loaded from: classes4.dex */
abstract class s<E> extends t<E> implements Serializable {
    private static final AtomicReferenceFieldUpdater<s, MpscLinkedQueueNode> a;
    private static final long serialVersionUID = 8467054865577874285L;
    private volatile transient MpscLinkedQueueNode<E> b;

    static {
        AtomicReferenceFieldUpdater<s, MpscLinkedQueueNode> newAtomicReferenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(s.class, "headRef");
        if (newAtomicReferenceFieldUpdater == null) {
            newAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(s.class, MpscLinkedQueueNode.class, "b");
        }
        a = newAtomicReferenceFieldUpdater;
    }

    protected final MpscLinkedQueueNode<E> a() {
        return this.b;
    }

    protected final void a(MpscLinkedQueueNode<E> mpscLinkedQueueNode) {
        this.b = mpscLinkedQueueNode;
    }

    protected final void b(MpscLinkedQueueNode<E> mpscLinkedQueueNode) {
        a.lazySet(this, mpscLinkedQueueNode);
    }
}
