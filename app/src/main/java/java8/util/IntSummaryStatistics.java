package java8.util;

import java8.util.function.IntConsumer;

/* loaded from: classes5.dex */
public class IntSummaryStatistics implements IntConsumer {
    private long a;
    private long b;
    private int c;
    private int d;

    public IntSummaryStatistics() {
        this.c = Integer.MAX_VALUE;
        this.d = Integer.MIN_VALUE;
    }

    public IntSummaryStatistics(long j, int i, int i2, long j2) throws IllegalArgumentException {
        this.c = Integer.MAX_VALUE;
        this.d = Integer.MIN_VALUE;
        int i3 = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i3 < 0) {
            throw new IllegalArgumentException("Negative count value");
        } else if (i3 <= 0) {
        } else {
            if (i <= i2) {
                this.a = j;
                this.b = j2;
                this.c = i;
                this.d = i2;
                return;
            }
            throw new IllegalArgumentException("Minimum greater than maximum");
        }
    }

    @Override // java8.util.function.IntConsumer
    public void accept(int i) {
        this.a++;
        this.b += i;
        this.c = Math.min(this.c, i);
        this.d = Math.max(this.d, i);
    }

    public void combine(IntSummaryStatistics intSummaryStatistics) {
        this.a += intSummaryStatistics.a;
        this.b += intSummaryStatistics.b;
        this.c = Math.min(this.c, intSummaryStatistics.c);
        this.d = Math.max(this.d, intSummaryStatistics.d);
    }

    public final long getCount() {
        return this.a;
    }

    public final long getSum() {
        return this.b;
    }

    public final int getMin() {
        return this.c;
    }

    public final int getMax() {
        return this.d;
    }

    public final double getAverage() {
        if (getCount() > 0) {
            return getSum() / getCount();
        }
        return 0.0d;
    }

    public String toString() {
        return String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}", getClass().getSimpleName(), Long.valueOf(getCount()), Long.valueOf(getSum()), Integer.valueOf(getMin()), Double.valueOf(getAverage()), Integer.valueOf(getMax()));
    }
}
