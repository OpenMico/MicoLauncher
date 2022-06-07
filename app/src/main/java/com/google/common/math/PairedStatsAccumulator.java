package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class PairedStatsAccumulator {
    private final StatsAccumulator a = new StatsAccumulator();
    private final StatsAccumulator b = new StatsAccumulator();
    private double c = 0.0d;

    private double a(double d) {
        if (d > 0.0d) {
            return d;
        }
        return Double.MIN_VALUE;
    }

    private static double b(double d) {
        if (d >= 1.0d) {
            return 1.0d;
        }
        if (d <= -1.0d) {
            return -1.0d;
        }
        return d;
    }

    public void add(double d, double d2) {
        this.a.add(d);
        if (!Doubles.isFinite(d) || !Doubles.isFinite(d2)) {
            this.c = Double.NaN;
        } else if (this.a.count() > 1) {
            this.c += (d - this.a.mean()) * (d2 - this.b.mean());
        }
        this.b.add(d2);
    }

    public void addAll(PairedStats pairedStats) {
        if (pairedStats.count() != 0) {
            this.a.addAll(pairedStats.xStats());
            if (this.b.count() == 0) {
                this.c = pairedStats.a();
            } else {
                this.c += pairedStats.a() + ((pairedStats.xStats().mean() - this.a.mean()) * (pairedStats.yStats().mean() - this.b.mean()) * pairedStats.count());
            }
            this.b.addAll(pairedStats.yStats());
        }
    }

    public PairedStats snapshot() {
        return new PairedStats(this.a.snapshot(), this.b.snapshot(), this.c);
    }

    public long count() {
        return this.a.count();
    }

    public Stats xStats() {
        return this.a.snapshot();
    }

    public Stats yStats() {
        return this.b.snapshot();
    }

    public double populationCovariance() {
        Preconditions.checkState(count() != 0);
        return this.c / count();
    }

    public final double sampleCovariance() {
        Preconditions.checkState(count() > 1);
        return this.c / (count() - 1);
    }

    public final double pearsonsCorrelationCoefficient() {
        boolean z = true;
        Preconditions.checkState(count() > 1);
        if (Double.isNaN(this.c)) {
            return Double.NaN;
        }
        double a = this.a.a();
        double a2 = this.b.a();
        Preconditions.checkState(a > 0.0d);
        if (a2 <= 0.0d) {
            z = false;
        }
        Preconditions.checkState(z);
        return b(this.c / Math.sqrt(a(a * a2)));
    }

    public final LinearTransformation leastSquaresFit() {
        boolean z = true;
        Preconditions.checkState(count() > 1);
        if (Double.isNaN(this.c)) {
            return LinearTransformation.forNaN();
        }
        double a = this.a.a();
        if (a <= 0.0d) {
            if (this.b.a() <= 0.0d) {
                z = false;
            }
            Preconditions.checkState(z);
            return LinearTransformation.vertical(this.a.mean());
        } else if (this.b.a() > 0.0d) {
            return LinearTransformation.mapping(this.a.mean(), this.b.mean()).withSlope(this.c / a);
        } else {
            return LinearTransformation.horizontal(this.b.mean());
        }
    }
}
