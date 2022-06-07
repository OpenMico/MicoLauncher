package com.google.android.exoplayer2.util;

import androidx.annotation.GuardedBy;
import com.google.android.exoplayer2.C;

/* loaded from: classes2.dex */
public final class TimestampAdjuster {
    public static final long MODE_NO_OFFSET = Long.MAX_VALUE;
    public static final long MODE_SHARED = 9223372036854775806L;
    @GuardedBy("this")
    private long a;
    @GuardedBy("this")
    private long b;
    @GuardedBy("this")
    private long c;
    private final ThreadLocal<Long> d = new ThreadLocal<>();

    public TimestampAdjuster(long j) {
        reset(j);
    }

    public synchronized void sharedInitializeOrWait(boolean z, long j) throws InterruptedException {
        Assertions.checkState(this.a == MODE_SHARED);
        if (this.b == C.TIME_UNSET) {
            if (z) {
                this.d.set(Long.valueOf(j));
            } else {
                while (this.b == C.TIME_UNSET) {
                    wait();
                }
            }
        }
    }

    public synchronized long getFirstSampleTimestampUs() {
        long j;
        if (!(this.a == Long.MAX_VALUE || this.a == MODE_SHARED)) {
            j = this.a;
        }
        j = C.TIME_UNSET;
        return j;
    }

    public synchronized long getLastAdjustedTimestampUs() {
        long j;
        if (this.c != C.TIME_UNSET) {
            j = this.c + this.b;
        } else {
            j = getFirstSampleTimestampUs();
        }
        return j;
    }

    public synchronized long getTimestampOffsetUs() {
        return this.b;
    }

    public synchronized void reset(long j) {
        this.a = j;
        this.b = j == Long.MAX_VALUE ? 0L : -9223372036854775807L;
        this.c = C.TIME_UNSET;
    }

    public synchronized long adjustTsTimestamp(long j) {
        if (j == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        if (this.c != C.TIME_UNSET) {
            long usToNonWrappedPts = usToNonWrappedPts(this.c);
            long j2 = (4294967296L + usToNonWrappedPts) / 8589934592L;
            long j3 = ((j2 - 1) * 8589934592L) + j;
            j += j2 * 8589934592L;
            if (Math.abs(j3 - usToNonWrappedPts) < Math.abs(j - usToNonWrappedPts)) {
                j = j3;
            }
        }
        return adjustSampleTimestamp(ptsToUs(j));
    }

    public synchronized long adjustSampleTimestamp(long j) {
        long j2;
        if (j == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        if (this.b == C.TIME_UNSET) {
            if (this.a == MODE_SHARED) {
                j2 = ((Long) Assertions.checkNotNull(this.d.get())).longValue();
            } else {
                j2 = this.a;
            }
            this.b = j2 - j;
            notifyAll();
        }
        this.c = j;
        return j + this.b;
    }

    public static long ptsToUs(long j) {
        return (j * 1000000) / 90000;
    }

    public static long usToWrappedPts(long j) {
        return usToNonWrappedPts(j) % 8589934592L;
    }

    public static long usToNonWrappedPts(long j) {
        return (j * 90000) / 1000000;
    }
}
