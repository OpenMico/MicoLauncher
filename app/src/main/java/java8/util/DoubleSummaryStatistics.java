package java8.util;

import java8.util.function.DoubleConsumer;

/* loaded from: classes5.dex */
public class DoubleSummaryStatistics implements DoubleConsumer {
    private long a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;

    public DoubleSummaryStatistics() {
        this.e = Double.POSITIVE_INFINITY;
        this.f = Double.NEGATIVE_INFINITY;
    }

    public DoubleSummaryStatistics(long j, double d, double d2, double d3) throws IllegalArgumentException {
        this.e = Double.POSITIVE_INFINITY;
        this.f = Double.NEGATIVE_INFINITY;
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i < 0) {
            throw new IllegalArgumentException("Negative count value");
        } else if (i <= 0) {
        } else {
            if (d <= d2) {
                int i2 = Double.isNaN(d) ? 1 : 0;
                i2 = Double.isNaN(d2) ? i2 + 1 : i2;
                i2 = Double.isNaN(d3) ? i2 + 1 : i2;
                if (i2 <= 0 || i2 >= 3) {
                    this.a = j;
                    this.b = d3;
                    this.d = d3;
                    this.c = 0.0d;
                    this.e = d;
                    this.f = d2;
                    return;
                }
                throw new IllegalArgumentException("Some, not all, of the minimum, maximum, or sum is NaN");
            }
            throw new IllegalArgumentException("Minimum greater than maximum");
        }
    }

    @Override // java8.util.function.DoubleConsumer
    public void accept(double d) {
        this.a++;
        this.d += d;
        a(d);
        this.e = Math.min(this.e, d);
        this.f = Math.max(this.f, d);
    }

    public void combine(DoubleSummaryStatistics doubleSummaryStatistics) {
        this.a += doubleSummaryStatistics.a;
        this.d += doubleSummaryStatistics.d;
        a(doubleSummaryStatistics.b);
        a(-doubleSummaryStatistics.c);
        this.e = Math.min(this.e, doubleSummaryStatistics.e);
        this.f = Math.max(this.f, doubleSummaryStatistics.f);
    }

    private void a(double d) {
        double d2 = d - this.c;
        double d3 = this.b;
        double d4 = d3 + d2;
        this.c = (d4 - d3) - d2;
        this.b = d4;
    }

    public final long getCount() {
        return this.a;
    }

    public final double getSum() {
        double d = this.b - this.c;
        return (!Double.isNaN(d) || !Double.isInfinite(this.d)) ? d : this.d;
    }

    public final double getMin() {
        return this.e;
    }

    public final double getMax() {
        return this.f;
    }

    public final double getAverage() {
        if (getCount() > 0) {
            return getSum() / getCount();
        }
        return 0.0d;
    }

    public String toString() {
        return String.format("%s{count=%d, sum=%f, min=%f, average=%f, max=%f}", getClass().getSimpleName(), Long.valueOf(getCount()), Double.valueOf(getSum()), Double.valueOf(getMin()), Double.valueOf(getAverage()), Double.valueOf(getMax()));
    }
}
