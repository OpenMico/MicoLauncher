package io.netty.util.internal;

/* loaded from: classes4.dex */
public abstract class OneTimeTask extends MpscLinkedQueueNode<Runnable> implements Runnable {
    @Override // io.netty.util.internal.MpscLinkedQueueNode
    public Runnable value() {
        return this;
    }
}
