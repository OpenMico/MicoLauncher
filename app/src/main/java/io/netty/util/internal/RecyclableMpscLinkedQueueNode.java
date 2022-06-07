package io.netty.util.internal;

import io.netty.util.Recycler;

/* loaded from: classes4.dex */
public abstract class RecyclableMpscLinkedQueueNode<T> extends MpscLinkedQueueNode<T> {
    private final Recycler.Handle a;

    /* JADX INFO: Access modifiers changed from: protected */
    public RecyclableMpscLinkedQueueNode(Recycler.Handle<? extends RecyclableMpscLinkedQueueNode<T>> handle) {
        if (handle != null) {
            this.a = handle;
            return;
        }
        throw new NullPointerException("handle");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // io.netty.util.internal.MpscLinkedQueueNode
    public final void e() {
        super.e();
        this.a.recycle(this);
    }
}
