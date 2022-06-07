package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import java.util.Iterator;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class StatsAccumulator {
    private long a = 0;
    private double b = 0.0d;
    private double c = 0.0d;
    private double d = Double.NaN;
    private double e = Double.NaN;

    public void add(double d) {
        long j = this.a;
        if (j == 0) {
            this.a = 1L;
            this.b = d;
            this.d = d;
            this.e = d;
            if (!Doubles.isFinite(d)) {
                this.c = Double.NaN;
                return;
            }
            return;
        }
        this.a = j + 1;
        if (!Doubles.isFinite(d) || !Doubles.isFinite(this.b)) {
            this.b = a(this.b, d);
            this.c = Double.NaN;
        } else {
            double d2 = this.b;
            double d3 = d - d2;
            this.b = d2 + (d3 / this.a);
            this.c += d3 * (d - this.b);
        }
        this.d = Math.min(this.d, d);
        this.e = Math.max(this.e, d);
    }

    public void addAll(Iterable<? extends Number> iterable) {
        for (Number number : iterable) {
            add(number.doubleValue());
        }
    }

    public void addAll(Iterator<? extends Number> it) {
        while (it.hasNext()) {
            add(((Number) it.next()).doubleValue());
        }
    }

    public void addAll(double... dArr) {
        for (double d : dArr) {
            add(d);
        }
    }

    public void addAll(int... iArr) {
        for (int i : iArr) {
            add(i);
        }
    }

    public void addAll(long... jArr) {
        for (long j : jArr) {
            add(j);
        }
    }

    public void addAll(Stats stats) {
        if (stats.count() != 0) {
            long j = this.a;
            if (j == 0) {
                this.a = stats.count();
                this.b = stats.mean();
                this.c = stats.a();
                this.d = stats.min();
                this.e = stats.max();
                return;
            }
            this.a = j + stats.count();
            if (!Doubles.isFinite(this.b) || !Doubles.isFinite(stats.mean())) {
                this.b = a(this.b, stats.mean());
                this.c = Double.NaN;
            } else {
                double mean = stats.mean();
                double d = this.b;
                double d2 = mean - d;
                this.b = d + ((stats.count() * d2) / this.a);
                this.c += stats.a() + (d2 * (stats.mean() - this.b) * stats.count());
            }
            this.d = Math.min(this.d, stats.min());
            this.e = Math.max(this.e, stats.max());
        }
    }

    public Stats snapshot() {
        return new Stats(this.a, this.b, this.c, this.d, this.e);
    }

    public long count() {
        return this.a;
    }

    public double mean() {
        Preconditions.checkState(this.a != 0);
        return this.b;
    }

    public final double sum() {
        return this.b * this.a;
    }

    public final double populationVariance() {
        Preconditions.checkState(this.a != 0);
        if (Double.isNaN(this.c)) {
            return Double.NaN;
        }
        if (this.a == 1) {
            return 0.0d;
        }
        return a.e(this.c) / this.a;
    }

    public final double populationStandardDeviation() {
        return Math.sqrt(populationVariance());
    }

    public final double sampleVariance() {
        Preconditions.checkState(this.a > 1);
        if (Double.isNaN(this.c)) {
            return Double.NaN;
        }
        return a.e(this.c) / (this.a - 1);
    }

    public final double sampleStandardDeviation() {
        return Math.sqrt(sampleVariance());
    }

    public double min() {
        Preconditions.checkState(this.a != 0);
        return this.d;
    }

    public double max() {
        Preconditions.checkState(this.a != 0);
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public double a() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static double a(double d, double d2) {
        if (Doubles.isFinite(d)) {
            return d2;
        }
        if (Doubles.isFinite(d2) || d == d2) {
            return d;
        }
        return Double.NaN;
    }
}
