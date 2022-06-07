package io.netty.util.internal.chmv8;

import io.netty.util.internal.chmv8.ForkJoinPool;

/* loaded from: classes4.dex */
public class ForkJoinWorkerThread extends Thread {
    final ForkJoinPool a;
    final ForkJoinPool.c b;

    protected void onStart() {
    }

    protected void onTermination(Throwable th) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ForkJoinWorkerThread(ForkJoinPool forkJoinPool) {
        super("aForkJoinWorkerThread");
        this.a = forkJoinPool;
        this.b = forkJoinPool.a(this);
    }

    public ForkJoinPool getPool() {
        return this.a;
    }

    public int getPoolIndex() {
        return this.b.e >>> 1;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Throwable th = null;
        try {
            onStart();
            this.a.a(this.b);
            try {
                onTermination(null);
            } catch (Throwable th2) {
                this.a.a(this, (Throwable) null);
                throw th2;
            }
        } catch (Throwable th3) {
            try {
                onTermination(null);
            } catch (Throwable th4) {
                this.a.a(this, (Throwable) null);
                throw th4;
            }
            this.a.a(this, th);
            throw th3;
        }
        this.a.a(this, th);
    }
}
