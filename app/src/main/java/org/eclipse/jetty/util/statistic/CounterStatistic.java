package org.eclipse.jetty.util.statistic;

import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.util.Atomics;

/* loaded from: classes5.dex */
public class CounterStatistic {
    protected final AtomicLong _max = new AtomicLong();
    protected final AtomicLong _curr = new AtomicLong();
    protected final AtomicLong _total = new AtomicLong();

    public void reset() {
        reset(0L);
    }

    public void reset(long j) {
        this._max.set(j);
        this._curr.set(j);
        this._total.set(0L);
    }

    public void add(long j) {
        long addAndGet = this._curr.addAndGet(j);
        if (j > 0) {
            this._total.addAndGet(j);
        }
        Atomics.updateMax(this._max, addAndGet);
    }

    public void subtract(long j) {
        add(-j);
    }

    public void increment() {
        add(1L);
    }

    public void decrement() {
        add(-1L);
    }

    public long getMax() {
        return this._max.get();
    }

    public long getCurrent() {
        return this._curr.get();
    }

    public long getTotal() {
        return this._total.get();
    }
}
