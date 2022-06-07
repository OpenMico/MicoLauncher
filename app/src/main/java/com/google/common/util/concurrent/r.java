package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.math.LongMath;
import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.TimeUnit;

/* compiled from: SmoothRateLimiter.java */
@GwtIncompatible
/* loaded from: classes2.dex */
abstract class r extends RateLimiter {
    double a;
    double b;
    double c;
    private long d;

    abstract void a(double d, double d2);

    abstract double b();

    abstract long b(double d, double d2);

    /* compiled from: SmoothRateLimiter.java */
    /* loaded from: classes2.dex */
    static final class b extends r {
        private final long d;
        private double e;
        private double f;
        private double g;

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(RateLimiter.a aVar, long j, TimeUnit timeUnit, double d) {
            super(aVar);
            this.d = timeUnit.toMicros(j);
            this.g = d;
        }

        @Override // com.google.common.util.concurrent.r
        void a(double d, double d2) {
            double d3 = this.b;
            double d4 = this.g * d2;
            long j = this.d;
            this.f = (j * 0.5d) / d2;
            this.b = this.f + ((j * 2.0d) / (d2 + d4));
            this.e = (d4 - d2) / (this.b - this.f);
            if (d3 == Double.POSITIVE_INFINITY) {
                this.a = 0.0d;
            } else {
                this.a = d3 == 0.0d ? this.b : (this.a * this.b) / d3;
            }
        }

        @Override // com.google.common.util.concurrent.r
        long b(double d, double d2) {
            long j;
            double d3 = d - this.f;
            if (d3 > 0.0d) {
                double min = Math.min(d3, d2);
                j = (long) (((a(d3) + a(d3 - min)) * min) / 2.0d);
                d2 -= min;
            } else {
                j = 0;
            }
            return j + ((long) (this.c * d2));
        }

        private double a(double d) {
            return this.c + (d * this.e);
        }

        @Override // com.google.common.util.concurrent.r
        double b() {
            return this.d / this.b;
        }
    }

    /* compiled from: SmoothRateLimiter.java */
    /* loaded from: classes2.dex */
    static final class a extends r {
        final double d;

        @Override // com.google.common.util.concurrent.r
        long b(double d, double d2) {
            return 0L;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(RateLimiter.a aVar, double d) {
            super(aVar);
            this.d = d;
        }

        @Override // com.google.common.util.concurrent.r
        void a(double d, double d2) {
            double d3 = this.b;
            this.b = this.d * d;
            if (d3 == Double.POSITIVE_INFINITY) {
                this.a = this.b;
                return;
            }
            double d4 = 0.0d;
            if (d3 != 0.0d) {
                d4 = (this.a * this.b) / d3;
            }
            this.a = d4;
        }

        @Override // com.google.common.util.concurrent.r
        double b() {
            return this.c;
        }
    }

    private r(RateLimiter.a aVar) {
        super(aVar);
        this.d = 0L;
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    final void a(double d, long j) {
        b(j);
        double micros = TimeUnit.SECONDS.toMicros(1L) / d;
        this.c = micros;
        a(d, micros);
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    final double a() {
        return TimeUnit.SECONDS.toMicros(1L) / this.c;
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    final long a(long j) {
        return this.d;
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    final long b(int i, long j) {
        b(j);
        long j2 = this.d;
        double d = i;
        double min = Math.min(d, this.a);
        this.d = LongMath.saturatedAdd(this.d, b(this.a, min) + ((long) ((d - min) * this.c)));
        this.a -= min;
        return j2;
    }

    void b(long j) {
        long j2 = this.d;
        if (j > j2) {
            this.a = Math.min(this.b, this.a + ((j - j2) / b()));
            this.d = j;
        }
    }
}
