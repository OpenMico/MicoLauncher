package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.util.concurrent.ForwardingListenableFuture;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.Exception;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
@GwtIncompatible
@Deprecated
/* loaded from: classes2.dex */
public abstract class AbstractCheckedFuture<V, X extends Exception> extends ForwardingListenableFuture.SimpleForwardingListenableFuture<V> implements CheckedFuture<V, X> {
    protected abstract X mapException(Exception exc);

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractCheckedFuture(ListenableFuture<V> listenableFuture) {
        super(listenableFuture);
    }

    @Override // com.google.common.util.concurrent.CheckedFuture
    @CanIgnoreReturnValue
    public V checkedGet() throws Exception {
        Exception e;
        try {
            return get();
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw mapException(e2);
        } catch (CancellationException e3) {
            e = e3;
            throw mapException(e);
        } catch (ExecutionException e4) {
            e = e4;
            throw mapException(e);
        }
    }

    @Override // com.google.common.util.concurrent.CheckedFuture
    @CanIgnoreReturnValue
    public V checkedGet(long j, TimeUnit timeUnit) throws TimeoutException, Exception {
        Exception e;
        try {
            return get(j, timeUnit);
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw mapException(e2);
        } catch (CancellationException e3) {
            e = e3;
            throw mapException(e);
        } catch (ExecutionException e4) {
            e = e4;
            throw mapException(e);
        }
    }
}
