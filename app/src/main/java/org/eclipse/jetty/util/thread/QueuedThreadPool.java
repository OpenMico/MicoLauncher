package org.eclipse.jetty.util.thread;

import com.xiaomi.mipush.sdk.Constants;
import io.netty.handler.codec.dns.DnsRecord;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.ThreadPool;

/* loaded from: classes5.dex */
public class QueuedThreadPool extends AbstractLifeCycle implements Executor, Dumpable, ThreadPool.SizedThreadPool {
    private static final Logger LOG = Log.getLogger(QueuedThreadPool.class);
    private boolean _daemon;
    private boolean _detailedDump;
    private BlockingQueue<Runnable> _jobs;
    private final Object _joinLock;
    private final AtomicLong _lastShrink;
    private int _maxIdleTimeMs;
    private int _maxQueued;
    private int _maxStopTime;
    private int _maxThreads;
    private int _minThreads;
    private String _name;
    private int _priority;
    private Runnable _runnable;
    private final ConcurrentLinkedQueue<Thread> _threads;
    private final AtomicInteger _threadsIdle;
    private final AtomicInteger _threadsStarted;

    public QueuedThreadPool() {
        this._threadsStarted = new AtomicInteger();
        this._threadsIdle = new AtomicInteger();
        this._lastShrink = new AtomicLong();
        this._threads = new ConcurrentLinkedQueue<>();
        this._joinLock = new Object();
        this._maxIdleTimeMs = 60000;
        this._maxThreads = DnsRecord.CLASS_NONE;
        this._minThreads = 8;
        this._maxQueued = -1;
        this._priority = 5;
        this._daemon = false;
        this._maxStopTime = 100;
        this._detailedDump = false;
        this._runnable = new Runnable() { // from class: org.eclipse.jetty.util.thread.QueuedThreadPool.3
            /* JADX WARN: Code restructure failed: missing block: B:41:0x00ed, code lost:
                if (r1 == false) goto L_0x0114;
             */
            /* JADX WARN: Code restructure failed: missing block: B:54:0x0112, code lost:
                if (r1 != false) goto L_0x011d;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    Method dump skipped, instructions count: 325
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.thread.QueuedThreadPool.AnonymousClass3.run():void");
            }
        };
        this._name = "qtp" + super.hashCode();
    }

    public QueuedThreadPool(int i) {
        this();
        setMaxThreads(i);
    }

    public QueuedThreadPool(BlockingQueue<Runnable> blockingQueue) {
        this();
        this._jobs = blockingQueue;
        this._jobs.clear();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        BlockingQueue<Runnable> blockingQueue;
        super.doStart();
        this._threadsStarted.set(0);
        if (this._jobs == null) {
            int i = this._maxQueued;
            if (i > 0) {
                blockingQueue = new ArrayBlockingQueue<>(i);
            } else {
                int i2 = this._minThreads;
                blockingQueue = new BlockingArrayQueue<>(i2, i2);
            }
            this._jobs = blockingQueue;
        }
        int i3 = this._threadsStarted.get();
        while (isRunning() && i3 < this._minThreads) {
            startThread(i3);
            i3 = this._threadsStarted.get();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
        long currentTimeMillis = System.currentTimeMillis();
        while (this._threadsStarted.get() > 0 && System.currentTimeMillis() - currentTimeMillis < this._maxStopTime / 2) {
            Thread.sleep(1L);
        }
        this._jobs.clear();
        Runnable runnable = new Runnable() { // from class: org.eclipse.jetty.util.thread.QueuedThreadPool.1
            @Override // java.lang.Runnable
            public void run() {
            }
        };
        int i = this._threadsIdle.get();
        while (true) {
            i--;
            if (i <= 0) {
                break;
            }
            this._jobs.offer(runnable);
        }
        Thread.yield();
        if (this._threadsStarted.get() > 0) {
            Iterator<Thread> it = this._threads.iterator();
            while (it.hasNext()) {
                it.next().interrupt();
            }
        }
        while (this._threadsStarted.get() > 0 && System.currentTimeMillis() - currentTimeMillis < this._maxStopTime) {
            Thread.sleep(1L);
        }
        Thread.yield();
        int size = this._threads.size();
        if (size > 0) {
            LOG.warn(size + " threads could not be stopped", new Object[0]);
            if (size == 1 || LOG.isDebugEnabled()) {
                Iterator<Thread> it2 = this._threads.iterator();
                while (it2.hasNext()) {
                    Thread next = it2.next();
                    LOG.info("Couldn't stop " + next, new Object[0]);
                    StackTraceElement[] stackTrace = next.getStackTrace();
                    int length = stackTrace.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        StackTraceElement stackTraceElement = stackTrace[i2];
                        LOG.info(" at " + stackTraceElement, new Object[0]);
                    }
                }
            }
        }
        synchronized (this._joinLock) {
            this._joinLock.notifyAll();
        }
    }

    public void setDaemon(boolean z) {
        this._daemon = z;
    }

    public void setMaxIdleTimeMs(int i) {
        this._maxIdleTimeMs = i;
    }

    public void setMaxStopTimeMs(int i) {
        this._maxStopTime = i;
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool.SizedThreadPool
    public void setMaxThreads(int i) {
        this._maxThreads = i;
        int i2 = this._minThreads;
        int i3 = this._maxThreads;
        if (i2 > i3) {
            this._minThreads = i3;
        }
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool.SizedThreadPool
    public void setMinThreads(int i) {
        this._minThreads = i;
        int i2 = this._minThreads;
        if (i2 > this._maxThreads) {
            this._maxThreads = i2;
        }
        int i3 = this._threadsStarted.get();
        while (isStarted() && i3 < this._minThreads) {
            startThread(i3);
            i3 = this._threadsStarted.get();
        }
    }

    public void setName(String str) {
        if (!isRunning()) {
            this._name = str;
            return;
        }
        throw new IllegalStateException("started");
    }

    public void setThreadsPriority(int i) {
        this._priority = i;
    }

    public int getMaxQueued() {
        return this._maxQueued;
    }

    public void setMaxQueued(int i) {
        if (!isRunning()) {
            this._maxQueued = i;
            return;
        }
        throw new IllegalStateException("started");
    }

    public int getMaxIdleTimeMs() {
        return this._maxIdleTimeMs;
    }

    public int getMaxStopTimeMs() {
        return this._maxStopTime;
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool.SizedThreadPool
    public int getMaxThreads() {
        return this._maxThreads;
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool.SizedThreadPool
    public int getMinThreads() {
        return this._minThreads;
    }

    public String getName() {
        return this._name;
    }

    public int getThreadsPriority() {
        return this._priority;
    }

    public boolean isDaemon() {
        return this._daemon;
    }

    public boolean isDetailedDump() {
        return this._detailedDump;
    }

    public void setDetailedDump(boolean z) {
        this._detailedDump = z;
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public boolean dispatch(Runnable runnable) {
        int i;
        if (isRunning()) {
            int size = this._jobs.size();
            int idleThreads = getIdleThreads();
            if (this._jobs.offer(runnable)) {
                if ((idleThreads == 0 || size > idleThreads) && (i = this._threadsStarted.get()) < this._maxThreads) {
                    startThread(i);
                }
                return true;
            }
        }
        LOG.debug("Dispatched {} to stopped {}", runnable, this);
        return false;
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (!dispatch(runnable)) {
            throw new RejectedExecutionException();
        }
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public void join() throws InterruptedException {
        synchronized (this._joinLock) {
            while (isRunning()) {
                this._joinLock.wait();
            }
        }
        while (isStopping()) {
            Thread.sleep(1L);
        }
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public int getThreads() {
        return this._threadsStarted.get();
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public int getIdleThreads() {
        return this._threadsIdle.get();
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public boolean isLowOnThreads() {
        return this._threadsStarted.get() == this._maxThreads && this._jobs.size() >= this._threadsIdle.get();
    }

    private boolean startThread(int i) {
        if (!this._threadsStarted.compareAndSet(i, i + 1)) {
            return false;
        }
        try {
            Thread newThread = newThread(this._runnable);
            newThread.setDaemon(this._daemon);
            newThread.setPriority(this._priority);
            newThread.setName(this._name + Constants.ACCEPT_TIME_SEPARATOR_SERVER + newThread.getId());
            this._threads.add(newThread);
            newThread.start();
            return true;
        } catch (Throwable th) {
            this._threadsStarted.decrementAndGet();
            throw th;
        }
    }

    protected Thread newThread(Runnable runnable) {
        return new Thread(runnable);
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public String dump() {
        return AggregateLifeCycle.dump(this);
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        ArrayList arrayList = new ArrayList(getMaxThreads());
        Iterator<Thread> it = this._threads.iterator();
        while (true) {
            final boolean z = true;
            if (it.hasNext()) {
                final Thread next = it.next();
                final StackTraceElement[] stackTrace = next.getStackTrace();
                if (stackTrace != null) {
                    for (StackTraceElement stackTraceElement : stackTrace) {
                        if ("idleJobPoll".equals(stackTraceElement.getMethodName())) {
                            break;
                        }
                    }
                }
                z = false;
                if (this._detailedDump) {
                    arrayList.add(new Dumpable() { // from class: org.eclipse.jetty.util.thread.QueuedThreadPool.2
                        @Override // org.eclipse.jetty.util.component.Dumpable
                        public String dump() {
                            return null;
                        }

                        @Override // org.eclipse.jetty.util.component.Dumpable
                        public void dump(Appendable appendable2, String str2) throws IOException {
                            appendable2.append(String.valueOf(next.getId())).append(' ').append(next.getName()).append(' ').append(next.getState().toString()).append(z ? " IDLE" : "").append('\n');
                            if (!z) {
                                AggregateLifeCycle.dump(appendable2, str2, Arrays.asList(stackTrace));
                            }
                        }
                    });
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(next.getId());
                    sb.append(StringUtils.SPACE);
                    sb.append(next.getName());
                    sb.append(StringUtils.SPACE);
                    sb.append(next.getState());
                    sb.append(" @ ");
                    sb.append(stackTrace.length > 0 ? stackTrace[0] : "???");
                    sb.append(z ? " IDLE" : "");
                    arrayList.add(sb.toString());
                }
            } else {
                AggregateLifeCycle.dumpObject(appendable, this);
                AggregateLifeCycle.dump(appendable, str, arrayList);
                return;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this._name);
        sb.append("{");
        sb.append(getMinThreads());
        sb.append("<=");
        sb.append(getIdleThreads());
        sb.append("<=");
        sb.append(getThreads());
        sb.append("/");
        sb.append(getMaxThreads());
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        BlockingQueue<Runnable> blockingQueue = this._jobs;
        sb.append(blockingQueue == null ? -1 : blockingQueue.size());
        sb.append("}");
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Runnable idleJobPoll() throws InterruptedException {
        return this._jobs.poll(this._maxIdleTimeMs, TimeUnit.MILLISECONDS);
    }

    protected void runJob(Runnable runnable) {
        runnable.run();
    }

    protected BlockingQueue<Runnable> getQueue() {
        return this._jobs;
    }

    @Deprecated
    public boolean stopThread(long j) {
        Iterator<Thread> it = this._threads.iterator();
        while (it.hasNext()) {
            Thread next = it.next();
            if (next.getId() == j) {
                next.stop();
                return true;
            }
        }
        return false;
    }

    public boolean interruptThread(long j) {
        Iterator<Thread> it = this._threads.iterator();
        while (it.hasNext()) {
            Thread next = it.next();
            if (next.getId() == j) {
                next.interrupt();
                return true;
            }
        }
        return false;
    }

    public String dumpThread(long j) {
        Iterator<Thread> it = this._threads.iterator();
        while (it.hasNext()) {
            Thread next = it.next();
            if (next.getId() == j) {
                StringBuilder sb = new StringBuilder();
                sb.append(next.getId());
                sb.append(StringUtils.SPACE);
                sb.append(next.getName());
                sb.append(StringUtils.SPACE);
                sb.append(next.getState());
                sb.append(":\n");
                StackTraceElement[] stackTrace = next.getStackTrace();
                for (StackTraceElement stackTraceElement : stackTrace) {
                    sb.append("  at ");
                    sb.append(stackTraceElement.toString());
                    sb.append('\n');
                }
                return sb.toString();
            }
        }
        return null;
    }
}
