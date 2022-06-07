package org.eclipse.jetty.util.statistic;

import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.util.Atomics;

/* loaded from: classes5.dex */
public class SampleStatistic {
    protected final AtomicLong _max = new AtomicLong();
    protected final AtomicLong _total = new AtomicLong();
    protected final AtomicLong _count = new AtomicLong();
    protected final AtomicLong _totalVariance100 = new AtomicLong();

    public void reset() {
        this._max.set(0L);
        this._total.set(0L);
        this._count.set(0L);
        this._totalVariance100.set(0L);
    }

    public void set(long j) {
        long addAndGet = this._total.addAndGet(j);
        long incrementAndGet = this._count.incrementAndGet();
        if (incrementAndGet > 1) {
            long j2 = (10 * j) - ((addAndGet * 10) / incrementAndGet);
            this._totalVariance100.addAndGet(j2 * j2);
        }
        Atomics.updateMax(this._max, j);
    }

    public long getMax() {
        return this._max.get();
    }

    public long getTotal() {
        return this._total.get();
    }

    public long getCount() {
        return this._count.get();
    }

    public double getMean() {
        return this._total.get() / this._count.get();
    }

    public double getVariance() {
        long j = this._totalVariance100.get();
        long j2 = this._count.get();
        if (j2 > 1) {
            return (j / 100.0d) / (j2 - 1);
        }
        return 0.0d;
    }

    public double getStdDev() {
        return Math.sqrt(getVariance());
    }
}
