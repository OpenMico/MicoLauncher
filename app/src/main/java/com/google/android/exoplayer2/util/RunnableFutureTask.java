package com.google.android.exoplayer2.util;

import androidx.annotation.Nullable;
import java.lang.Exception;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
public abstract class RunnableFutureTask<R, E extends Exception> implements RunnableFuture<R> {
    private final ConditionVariable a = new ConditionVariable();
    private final ConditionVariable b = new ConditionVariable();
    private final Object c = new Object();
    @Nullable
    private Exception d;
    @Nullable
    private R e;
    @Nullable
    private Thread f;
    private boolean g;

    protected void cancelWork() {
    }

    @UnknownNull
    protected abstract R doWork() throws Exception;

    public final void blockUntilStarted() {
        this.a.blockUninterruptible();
    }

    public final void blockUntilFinished() {
        this.b.blockUninterruptible();
    }

    @Override // java.util.concurrent.Future
    @UnknownNull
    public final R get() throws ExecutionException, InterruptedException {
        this.b.block();
        return a();
    }

    @Override // java.util.concurrent.Future
    @UnknownNull
    public final R get(long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        if (this.b.block(TimeUnit.MILLISECONDS.convert(j, timeUnit))) {
            return a();
        }
        throw new TimeoutException();
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        synchronized (this.c) {
            if (!this.g && !this.b.isOpen()) {
                this.g = true;
                cancelWork();
                Thread thread = this.f;
                if (thread == null) {
                    this.a.open();
                    this.b.open();
                } else if (z) {
                    thread.interrupt();
                }
                return true;
            }
            return false;
        }
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return this.b.isOpen();
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.g;
    }

    @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
    public final void run() {
        synchronized (this.c) {
            try {
                if (!this.g) {
                    this.f = Thread.currentThread();
                    this.a.open();
                    try {
                        try {
                            this.e = doWork();
                            synchronized (this.c) {
                                try {
                                    this.b.open();
                                    this.f = null;
                                    Thread.interrupted();
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        } catch (Exception e) {
                            this.d = e;
                            synchronized (this.c) {
                                try {
                                    this.b.open();
                                    this.f = null;
                                    Thread.interrupted();
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                        }
                    } catch (Throwable th3) {
                        synchronized (this.c) {
                            try {
                                this.b.open();
                                this.f = null;
                                Thread.interrupted();
                                throw th3;
                            } catch (Throwable th4) {
                                throw th4;
                            }
                        }
                    }
                }
            } catch (Throwable th5) {
                throw th5;
            }
        }
    }

    @UnknownNull
    private R a() throws ExecutionException {
        if (!this.g) {
            Exception exc = this.d;
            if (exc == null) {
                return this.e;
            }
            throw new ExecutionException(exc);
        }
        throw new CancellationException();
    }
}
