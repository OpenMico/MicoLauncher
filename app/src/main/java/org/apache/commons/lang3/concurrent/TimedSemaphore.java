package org.apache.commons.lang3.concurrent;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.Validate;

/* loaded from: classes5.dex */
public class TimedSemaphore {
    public static final int NO_LIMIT = 0;
    private final ScheduledExecutorService a;
    private final long b;
    private final TimeUnit c;
    private final boolean d;
    private ScheduledFuture<?> e;
    private long f;
    private long g;
    private int h;
    private int i;
    private int j;
    private boolean k;

    public TimedSemaphore(long j, TimeUnit timeUnit, int i) {
        this(null, j, timeUnit, i);
    }

    public TimedSemaphore(ScheduledExecutorService scheduledExecutorService, long j, TimeUnit timeUnit, int i) {
        Validate.inclusiveBetween(1L, Long.MAX_VALUE, j, "Time period must be greater than 0!");
        this.b = j;
        this.c = timeUnit;
        if (scheduledExecutorService != null) {
            this.a = scheduledExecutorService;
            this.d = false;
        } else {
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
            scheduledThreadPoolExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
            scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            this.a = scheduledThreadPoolExecutor;
            this.d = true;
        }
        setLimit(i);
    }

    public final synchronized int getLimit() {
        return this.h;
    }

    public final synchronized void setLimit(int i) {
        this.h = i;
    }

    public synchronized void shutdown() {
        if (!this.k) {
            if (this.d) {
                getExecutorService().shutdownNow();
            }
            if (this.e != null) {
                this.e.cancel(false);
            }
            this.k = true;
        }
    }

    public synchronized boolean isShutdown() {
        return this.k;
    }

    public synchronized void acquire() throws InterruptedException {
        boolean c;
        b();
        do {
            c = c();
            if (!c) {
                wait();
                continue;
            }
        } while (!c);
    }

    public synchronized boolean tryAcquire() {
        b();
        return c();
    }

    public synchronized int getLastAcquiresPerPeriod() {
        return this.j;
    }

    public synchronized int getAcquireCount() {
        return this.i;
    }

    public synchronized int getAvailablePermits() {
        return getLimit() - getAcquireCount();
    }

    public synchronized double getAverageCallsPerPeriod() {
        double d;
        if (this.g == 0) {
            d = 0.0d;
        } else {
            d = this.f / this.g;
        }
        return d;
    }

    public long getPeriod() {
        return this.b;
    }

    public TimeUnit getUnit() {
        return this.c;
    }

    protected ScheduledExecutorService getExecutorService() {
        return this.a;
    }

    protected ScheduledFuture<?> startTimer() {
        return getExecutorService().scheduleAtFixedRate(new Runnable() { // from class: org.apache.commons.lang3.concurrent.TimedSemaphore.1
            @Override // java.lang.Runnable
            public void run() {
                TimedSemaphore.this.a();
            }
        }, getPeriod(), getPeriod(), getUnit());
    }

    synchronized void a() {
        this.j = this.i;
        this.f += this.i;
        this.g++;
        this.i = 0;
        notifyAll();
    }

    private void b() {
        if (isShutdown()) {
            throw new IllegalStateException("TimedSemaphore is shut down!");
        } else if (this.e == null) {
            this.e = startTimer();
        }
    }

    private boolean c() {
        if (getLimit() > 0 && this.i >= getLimit()) {
            return false;
        }
        this.i++;
        return true;
    }
}
