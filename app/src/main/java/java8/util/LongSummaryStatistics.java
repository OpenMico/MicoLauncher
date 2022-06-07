package java8.util;

import java8.util.function.IntConsumer;
import java8.util.function.LongConsumer;

/* loaded from: classes5.dex */
public class LongSummaryStatistics implements IntConsumer, LongConsumer {
    private long a;
    private long b;
    private long c;
    private long d;

    public LongSummaryStatistics() {
        this.c = Long.MAX_VALUE;
        this.d = Long.MIN_VALUE;
    }

    public LongSummaryStatistics(long j, long j2, long j3, long j4) throws IllegalArgumentException {
        this.c = Long.MAX_VALUE;
        this.d = Long.MIN_VALUE;
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i < 0) {
            throw new IllegalArgumentException("Negative count value");
        } else if (i <= 0) {
        } else {
            if (j2 <= j3) {
                this.a = j;
                this.b = j4;
                this.c = j2;
                this.d = j3;
                return;
            }
            throw new IllegalArgumentException("Minimum greater than maximum");
        }
    }

    @Override // java8.util.function.IntConsumer
    public void accept(int i) {
        accept(i);
    }

    @Override // java8.util.function.LongConsumer
    public void accept(long j) {
        this.a++;
        this.b += j;
        this.c = Math.min(this.c, j);
        this.d = Math.max(this.d, j);
    }

    public void combine(LongSummaryStatistics longSummaryStatistics) {
        this.a += longSummaryStatistics.a;
        this.b += longSummaryStatistics.b;
        this.c = Math.min(this.c, longSummaryStatistics.c);
        this.d = Math.max(this.d, longSummaryStatistics.d);
    }

    public final long getCount() {
        return this.a;
    }

    public final long getSum() {
        return this.b;
    }

    public final long getMin() {
        return this.c;
    }

    public final long getMax() {
        return this.d;
    }

    public final double getAverage() {
        if (getCount() > 0) {
            return getSum() / getCount();
        }
        return 0.0d;
    }

    public String toString() {
        return String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}", getClass().getSimpleName(), Long.valueOf(getCount()), Long.valueOf(getSum()), Long.valueOf(getMin()), Double.valueOf(getAverage()), Long.valueOf(getMax()));
    }
}
