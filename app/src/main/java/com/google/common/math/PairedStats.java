package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class PairedStats implements Serializable {
    private static final long serialVersionUID = 0;
    private final double sumOfProductsOfDeltas;
    private final Stats xStats;
    private final Stats yStats;

    private static double a(double d) {
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

    /* JADX INFO: Access modifiers changed from: package-private */
    public PairedStats(Stats stats, Stats stats2, double d) {
        this.xStats = stats;
        this.yStats = stats2;
        this.sumOfProductsOfDeltas = d;
    }

    public long count() {
        return this.xStats.count();
    }

    public Stats xStats() {
        return this.xStats;
    }

    public Stats yStats() {
        return this.yStats;
    }

    public double populationCovariance() {
        Preconditions.checkState(count() != 0);
        return this.sumOfProductsOfDeltas / count();
    }

    public double sampleCovariance() {
        Preconditions.checkState(count() > 1);
        return this.sumOfProductsOfDeltas / (count() - 1);
    }

    public double pearsonsCorrelationCoefficient() {
        boolean z = true;
        Preconditions.checkState(count() > 1);
        if (Double.isNaN(this.sumOfProductsOfDeltas)) {
            return Double.NaN;
        }
        double a = xStats().a();
        double a2 = yStats().a();
        Preconditions.checkState(a > 0.0d);
        if (a2 <= 0.0d) {
            z = false;
        }
        Preconditions.checkState(z);
        return b(this.sumOfProductsOfDeltas / Math.sqrt(a(a * a2)));
    }

    public LinearTransformation leastSquaresFit() {
        boolean z = true;
        Preconditions.checkState(count() > 1);
        if (Double.isNaN(this.sumOfProductsOfDeltas)) {
            return LinearTransformation.forNaN();
        }
        double a = this.xStats.a();
        if (a <= 0.0d) {
            if (this.yStats.a() <= 0.0d) {
                z = false;
            }
            Preconditions.checkState(z);
            return LinearTransformation.vertical(this.xStats.mean());
        } else if (this.yStats.a() > 0.0d) {
            return LinearTransformation.mapping(this.xStats.mean(), this.yStats.mean()).withSlope(this.sumOfProductsOfDeltas / a);
        } else {
            return LinearTransformation.horizontal(this.yStats.mean());
        }
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PairedStats pairedStats = (PairedStats) obj;
        return this.xStats.equals(pairedStats.xStats) && this.yStats.equals(pairedStats.yStats) && Double.doubleToLongBits(this.sumOfProductsOfDeltas) == Double.doubleToLongBits(pairedStats.sumOfProductsOfDeltas);
    }

    public int hashCode() {
        return Objects.hashCode(this.xStats, this.yStats, Double.valueOf(this.sumOfProductsOfDeltas));
    }

    public String toString() {
        if (count() > 0) {
            return MoreObjects.toStringHelper(this).add("xStats", this.xStats).add("yStats", this.yStats).add("populationCovariance", populationCovariance()).toString();
        }
        return MoreObjects.toStringHelper(this).add("xStats", this.xStats).add("yStats", this.yStats).toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public double a() {
        return this.sumOfProductsOfDeltas;
    }

    public byte[] toByteArray() {
        ByteBuffer order = ByteBuffer.allocate(88).order(ByteOrder.LITTLE_ENDIAN);
        this.xStats.a(order);
        this.yStats.a(order);
        order.putDouble(this.sumOfProductsOfDeltas);
        return order.array();
    }

    public static PairedStats fromByteArray(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        Preconditions.checkArgument(bArr.length == 88, "Expected PairedStats.BYTES = %s, got %s", 88, bArr.length);
        ByteBuffer order = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
        return new PairedStats(Stats.b(order), Stats.b(order), order.getDouble());
    }
}
