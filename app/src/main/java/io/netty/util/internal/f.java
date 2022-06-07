package io.netty.util.internal;

/* compiled from: BaseLinkedQueue.java */
/* loaded from: classes4.dex */
abstract class f<E> extends d<E> {
    protected static final long P_NODE_OFFSET;
    protected j<E> producerNode;

    static {
        try {
            P_NODE_OFFSET = w.a(f.class.getDeclaredField("producerNode"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    protected final void spProducerNode(j<E> jVar) {
        this.producerNode = jVar;
    }

    protected final j<E> lvProducerNode() {
        return (j) w.b(this, P_NODE_OFFSET);
    }

    protected final j<E> lpProducerNode() {
        return this.producerNode;
    }
}
