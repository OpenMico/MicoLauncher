package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes5.dex */
public class ThresholdCircuitBreaker extends AbstractCircuitBreaker<Long> {
    private final long a;
    private final AtomicLong b = new AtomicLong(0);

    public ThresholdCircuitBreaker(long j) {
        this.a = j;
    }

    public long getThreshold() {
        return this.a;
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public boolean checkState() throws CircuitBreakingException {
        return isOpen();
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public void close() {
        super.close();
        this.b.set(0L);
    }

    public boolean incrementAndCheckState(Long l) throws CircuitBreakingException {
        if (this.a == 0) {
            open();
        }
        if (this.b.addAndGet(l.longValue()) > this.a) {
            open();
        }
        return checkState();
    }
}
