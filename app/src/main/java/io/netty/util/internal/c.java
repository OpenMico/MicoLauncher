package io.netty.util.internal;

/* compiled from: BaseLinkedQueue.java */
/* loaded from: classes4.dex */
abstract class c<E> extends e<E> {
    protected static final long C_NODE_OFFSET;
    protected j<E> consumerNode;

    static {
        try {
            C_NODE_OFFSET = w.a(c.class.getDeclaredField("consumerNode"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    protected final void spConsumerNode(j<E> jVar) {
        this.consumerNode = jVar;
    }

    protected final j<E> lvConsumerNode() {
        return (j) w.b(this, C_NODE_OFFSET);
    }

    protected final j<E> lpConsumerNode() {
        return this.consumerNode;
    }
}
