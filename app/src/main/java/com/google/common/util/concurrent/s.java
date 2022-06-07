package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TimeoutFuture.java */
@GwtIncompatible
/* loaded from: classes2.dex */
public final class s<V> extends AbstractFuture.h<V> {
    @NullableDecl
    private ListenableFuture<V> a;
    @NullableDecl
    private Future<?> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <V> ListenableFuture<V> a(ListenableFuture<V> listenableFuture, long j, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        s sVar = new s(listenableFuture);
        a aVar = new a(sVar);
        sVar.b = scheduledExecutorService.schedule(aVar, j, timeUnit);
        listenableFuture.addListener(aVar, MoreExecutors.directExecutor());
        return sVar;
    }

    private s(ListenableFuture<V> listenableFuture) {
        this.a = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TimeoutFuture.java */
    /* loaded from: classes2.dex */
    public static final class a<V> implements Runnable {
        @NullableDecl
        s<V> a;

        a(s<V> sVar) {
            this.a = sVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            ListenableFuture<? extends V> listenableFuture;
            s<V> sVar = this.a;
            if (sVar != null && (listenableFuture = ((s) sVar).a) != null) {
                this.a = null;
                if (listenableFuture.isDone()) {
                    sVar.setFuture(listenableFuture);
                    return;
                }
                try {
                    sVar.setException(new TimeoutException("Future timed out: " + listenableFuture));
                } finally {
                    listenableFuture.cancel(true);
                }
            }
        }
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected String pendingToString() {
        ListenableFuture<V> listenableFuture = this.a;
        if (listenableFuture == null) {
            return null;
        }
        return "inputFuture=[" + listenableFuture + "]";
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected void afterDone() {
        a((Future<?>) this.a);
        Future<?> future = this.b;
        if (future != null) {
            future.cancel(false);
        }
        this.a = null;
        this.b = null;
    }
}
