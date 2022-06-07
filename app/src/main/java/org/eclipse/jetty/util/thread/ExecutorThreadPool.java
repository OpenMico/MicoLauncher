package org.eclipse.jetty.util.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class ExecutorThreadPool extends AbstractLifeCycle implements LifeCycle, ThreadPool {
    private static final Logger LOG = Log.getLogger(ExecutorThreadPool.class);
    private final ExecutorService _executor;

    public ExecutorThreadPool(ExecutorService executorService) {
        this._executor = executorService;
    }

    public ExecutorThreadPool() {
        this(new ThreadPoolExecutor(256, 256, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue()));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ExecutorThreadPool(int r15) {
        /*
            r14 = this;
            if (r15 >= 0) goto L_0x0016
            java.util.concurrent.ThreadPoolExecutor r15 = new java.util.concurrent.ThreadPoolExecutor
            r1 = 256(0x100, float:3.59E-43)
            r2 = 256(0x100, float:3.59E-43)
            r3 = 60
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.SECONDS
            java.util.concurrent.LinkedBlockingQueue r6 = new java.util.concurrent.LinkedBlockingQueue
            r6.<init>()
            r0 = r15
            r0.<init>(r1, r2, r3, r5, r6)
            goto L_0x0040
        L_0x0016:
            if (r15 != 0) goto L_0x002c
            java.util.concurrent.ThreadPoolExecutor r15 = new java.util.concurrent.ThreadPoolExecutor
            r8 = 32
            r9 = 256(0x100, float:3.59E-43)
            r10 = 60
            java.util.concurrent.TimeUnit r12 = java.util.concurrent.TimeUnit.SECONDS
            java.util.concurrent.SynchronousQueue r13 = new java.util.concurrent.SynchronousQueue
            r13.<init>()
            r7 = r15
            r7.<init>(r8, r9, r10, r12, r13)
            goto L_0x0040
        L_0x002c:
            java.util.concurrent.ThreadPoolExecutor r7 = new java.util.concurrent.ThreadPoolExecutor
            r1 = 32
            r2 = 256(0x100, float:3.59E-43)
            r3 = 60
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.SECONDS
            java.util.concurrent.ArrayBlockingQueue r6 = new java.util.concurrent.ArrayBlockingQueue
            r6.<init>(r15)
            r0 = r7
            r0.<init>(r1, r2, r3, r5, r6)
            r15 = r7
        L_0x0040:
            r14.<init>(r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.thread.ExecutorThreadPool.<init>(int):void");
    }

    public ExecutorThreadPool(int i, int i2, long j) {
        this(i, i2, j, TimeUnit.MILLISECONDS);
    }

    public ExecutorThreadPool(int i, int i2, long j, TimeUnit timeUnit) {
        this(i, i2, j, timeUnit, new LinkedBlockingQueue());
    }

    public ExecutorThreadPool(int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        this(new ThreadPoolExecutor(i, i2, j, timeUnit, blockingQueue));
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public boolean dispatch(Runnable runnable) {
        try {
            this._executor.execute(runnable);
            return true;
        } catch (RejectedExecutionException e) {
            LOG.warn(e);
            return false;
        }
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public int getIdleThreads() {
        ExecutorService executorService = this._executor;
        if (!(executorService instanceof ThreadPoolExecutor)) {
            return -1;
        }
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        return threadPoolExecutor.getPoolSize() - threadPoolExecutor.getActiveCount();
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public int getThreads() {
        ExecutorService executorService = this._executor;
        if (executorService instanceof ThreadPoolExecutor) {
            return ((ThreadPoolExecutor) executorService).getPoolSize();
        }
        return -1;
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public boolean isLowOnThreads() {
        ExecutorService executorService = this._executor;
        if (!(executorService instanceof ThreadPoolExecutor)) {
            return false;
        }
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        return threadPoolExecutor.getPoolSize() == threadPoolExecutor.getMaximumPoolSize() && threadPoolExecutor.getQueue().size() >= threadPoolExecutor.getPoolSize() - threadPoolExecutor.getActiveCount();
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public void join() throws InterruptedException {
        this._executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
        this._executor.shutdownNow();
    }
}
