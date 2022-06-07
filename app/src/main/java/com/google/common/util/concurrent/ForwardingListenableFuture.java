package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;

@CanIgnoreReturnValue
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class ForwardingListenableFuture<V> extends ForwardingFuture<V> implements ListenableFuture<V> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.util.concurrent.ForwardingFuture, com.google.common.collect.ForwardingObject
    public abstract ListenableFuture<? extends V> delegate();

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(Runnable runnable, Executor executor) {
        delegate().addListener(runnable, executor);
    }

    /* loaded from: classes2.dex */
    public static abstract class SimpleForwardingListenableFuture<V> extends ForwardingListenableFuture<V> {
        private final ListenableFuture<V> a;

        /* JADX INFO: Access modifiers changed from: protected */
        public SimpleForwardingListenableFuture(ListenableFuture<V> listenableFuture) {
            this.a = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.util.concurrent.ForwardingListenableFuture, com.google.common.util.concurrent.ForwardingFuture, com.google.common.collect.ForwardingObject
        public final ListenableFuture<V> delegate() {
            return this.a;
        }
    }
}
