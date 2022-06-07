package io.netty.util.concurrent;

import io.netty.util.internal.InternalThreadLocalMap;

/* loaded from: classes4.dex */
public class FastThreadLocalThread extends Thread {
    private InternalThreadLocalMap a;

    public FastThreadLocalThread() {
    }

    public FastThreadLocalThread(Runnable runnable) {
        super(runnable);
    }

    public FastThreadLocalThread(ThreadGroup threadGroup, Runnable runnable) {
        super(threadGroup, runnable);
    }

    public FastThreadLocalThread(String str) {
        super(str);
    }

    public FastThreadLocalThread(ThreadGroup threadGroup, String str) {
        super(threadGroup, str);
    }

    public FastThreadLocalThread(Runnable runnable, String str) {
        super(runnable, str);
    }

    public FastThreadLocalThread(ThreadGroup threadGroup, Runnable runnable, String str) {
        super(threadGroup, runnable, str);
    }

    public FastThreadLocalThread(ThreadGroup threadGroup, Runnable runnable, String str, long j) {
        super(threadGroup, runnable, str, j);
    }

    public final InternalThreadLocalMap threadLocalMap() {
        return this.a;
    }

    public final void setThreadLocalMap(InternalThreadLocalMap internalThreadLocalMap) {
        this.a = internalThreadLocalMap;
    }
}
