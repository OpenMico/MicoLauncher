package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class LinearTransformation {
    public abstract LinearTransformation inverse();

    public abstract boolean isHorizontal();

    public abstract boolean isVertical();

    public abstract double slope();

    public abstract double transform(double d);

    public static LinearTransformationBuilder mapping(double d, double d2) {
        Preconditions.checkArgument(a.b(d) && a.b(d2));
        return new LinearTransformationBuilder(d, d2);
    }

    /* loaded from: classes2.dex */
    public static final class LinearTransformationBuilder {
        private final double a;
        private final double b;

        private LinearTransformationBuilder(double d, double d2) {
            this.a = d;
            this.b = d2;
        }

        public LinearTransformation and(double d, double d2) {
            boolean z = true;
            Preconditions.checkArgument(a.b(d) && a.b(d2));
            double d3 = this.a;
            if (d != d3) {
                return withSlope((d2 - this.b) / (d - d3));
            }
            if (d2 == this.b) {
                z = false;
            }
            Preconditions.checkArgument(z);
            return new c(this.a);
        }

        public LinearTransformation withSlope(double d) {
            Preconditions.checkArgument(!Double.isNaN(d));
            if (a.b(d)) {
                return new b(d, this.b - (this.a * d));
            }
            return new c(this.a);
        }
    }

    public static LinearTransformation vertical(double d) {
        Preconditions.checkArgument(a.b(d));
        return new c(d);
    }

    public static LinearTransformation horizontal(double d) {
        Preconditions.checkArgument(a.b(d));
        return new b(0.0d, d);
    }

    public static LinearTransformation forNaN() {
        return a.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b extends LinearTransformation {
        final double a;
        final double b;
        @LazyInit
        LinearTransformation c;

        @Override // com.google.common.math.LinearTransformation
        public boolean isVertical() {
            return false;
        }

        b(double d, double d2) {
            this.a = d;
            this.b = d2;
            this.c = null;
        }

        b(double d, double d2, LinearTransformation linearTransformation) {
            this.a = d;
            this.b = d2;
            this.c = linearTransformation;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isHorizontal() {
            return this.a == 0.0d;
        }

        @Override // com.google.common.math.LinearTransformation
        public double slope() {
            return this.a;
        }

        @Override // com.google.common.math.LinearTransformation
        public double transform(double d) {
            return (d * this.a) + this.b;
        }

        @Override // com.google.common.math.LinearTransformation
        public LinearTransformation inverse() {
            LinearTransformation linearTransformation = this.c;
            if (linearTransformation != null) {
                return linearTransformation;
            }
            LinearTransformation a = a();
            this.c = a;
            return a;
        }

        public String toString() {
            return String.format("y = %g * x + %g", Double.valueOf(this.a), Double.valueOf(this.b));
        }

        private LinearTransformation a() {
            double d = this.a;
            if (d != 0.0d) {
                return new b(1.0d / d, (this.b * (-1.0d)) / d, this);
            }
            return new c(this.b, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c extends LinearTransformation {
        final double a;
        @LazyInit
        LinearTransformation b;

        @Override // com.google.common.math.LinearTransformation
        public boolean isHorizontal() {
            return false;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isVertical() {
            return true;
        }

        c(double d) {
            this.a = d;
            this.b = null;
        }

        c(double d, LinearTransformation linearTransformation) {
            this.a = d;
            this.b = linearTransformation;
        }

        @Override // com.google.common.math.LinearTransformation
        public double slope() {
            throw new IllegalStateException();
        }

        @Override // com.google.common.math.LinearTransformation
        public double transform(double d) {
            throw new IllegalStateException();
        }

        @Override // com.google.common.math.LinearTransformation
        public LinearTransformation inverse() {
            LinearTransformation linearTransformation = this.b;
            if (linearTransformation != null) {
                return linearTransformation;
            }
            LinearTransformation a = a();
            this.b = a;
            return a;
        }

        public String toString() {
            return String.format("x = %g", Double.valueOf(this.a));
        }

        private LinearTransformation a() {
            return new b(0.0d, this.a, this);
        }
    }

    /* loaded from: classes2.dex */
    private static final class a extends LinearTransformation {
        static final a a = new a();

        @Override // com.google.common.math.LinearTransformation
        public LinearTransformation inverse() {
            return this;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isHorizontal() {
            return false;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isVertical() {
            return false;
        }

        @Override // com.google.common.math.LinearTransformation
        public double slope() {
            return Double.NaN;
        }

        public String toString() {
            return "NaN";
        }

        @Override // com.google.common.math.LinearTransformation
        public double transform(double d) {
            return Double.NaN;
        }

        private a() {
        }
    }
}
