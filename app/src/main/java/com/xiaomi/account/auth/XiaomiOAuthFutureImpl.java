package com.xiaomi.account.auth;

import android.accounts.OperationCanceledException;
import android.os.Looper;
import com.xiaomi.account.openauth.XMAuthericationException;
import com.xiaomi.account.openauth.XiaomiOAuthFuture;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
public class XiaomiOAuthFutureImpl<V> extends FutureTask<V> implements XiaomiOAuthFuture<V> {
    private static final long DEFAULT_TIMEOUT_MINUTE = 10;

    private void ensureNotOnMainThread() {
        Looper myLooper = Looper.myLooper();
        if (myLooper != null && myLooper == Looper.getMainLooper()) {
            throw new IllegalStateException("calling this from your main thread can lead to deadlock");
        }
    }

    public XiaomiOAuthFutureImpl(Callable<V> callable) {
        super(callable);
    }

    @Override // com.xiaomi.account.openauth.XiaomiOAuthFuture
    public V getResult() throws IOException, OperationCanceledException, XMAuthericationException {
        return internalGetResult(Long.valueOf((long) DEFAULT_TIMEOUT_MINUTE), TimeUnit.MINUTES);
    }

    @Override // com.xiaomi.account.openauth.XiaomiOAuthFuture
    public V getResult(long j, TimeUnit timeUnit) throws IOException, OperationCanceledException, XMAuthericationException {
        return internalGetResult(Long.valueOf(j), timeUnit);
    }

    private V internalGetResult(Long l, TimeUnit timeUnit) throws OperationCanceledException, IOException, XMAuthericationException {
        if (!isDone()) {
            ensureNotOnMainThread();
        }
        try {
            try {
                try {
                    try {
                        if (l == null) {
                            return get();
                        }
                        return get(l.longValue(), timeUnit);
                    } finally {
                        cancel(true);
                    }
                } catch (InterruptedException | TimeoutException unused) {
                    cancel(true);
                    throw new OperationCanceledException();
                }
            } catch (CancellationException unused2) {
                throw new OperationCanceledException();
            }
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                throw ((IOException) cause);
            } else if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else if (cause instanceof XMAuthericationException) {
                throw ((XMAuthericationException) cause);
            } else if (cause instanceof OperationCanceledException) {
                throw ((OperationCanceledException) cause);
            } else {
                throw new XMAuthericationException(cause);
            }
        }
    }
}
