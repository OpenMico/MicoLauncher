package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public final class CacheStats {
    private final long a;
    private final long b;
    private final long c;
    private final long d;
    private final long e;
    private final long f;

    public CacheStats(long j, long j2, long j3, long j4, long j5, long j6) {
        boolean z = true;
        Preconditions.checkArgument(j >= 0);
        Preconditions.checkArgument(j2 >= 0);
        Preconditions.checkArgument(j3 >= 0);
        Preconditions.checkArgument(j4 >= 0);
        Preconditions.checkArgument(j5 >= 0);
        Preconditions.checkArgument(j6 < 0 ? false : z);
        this.a = j;
        this.b = j2;
        this.c = j3;
        this.d = j4;
        this.e = j5;
        this.f = j6;
    }

    public long requestCount() {
        return this.a + this.b;
    }

    public long hitCount() {
        return this.a;
    }

    public double hitRate() {
        long requestCount = requestCount();
        if (requestCount == 0) {
            return 1.0d;
        }
        return this.a / requestCount;
    }

    public long missCount() {
        return this.b;
    }

    public double missRate() {
        long requestCount = requestCount();
        if (requestCount == 0) {
            return 0.0d;
        }
        return this.b / requestCount;
    }

    public long loadCount() {
        return this.c + this.d;
    }

    public long loadSuccessCount() {
        return this.c;
    }

    public long loadExceptionCount() {
        return this.d;
    }

    public double loadExceptionRate() {
        long j = this.c;
        long j2 = this.d;
        long j3 = j + j2;
        if (j3 == 0) {
            return 0.0d;
        }
        return j2 / j3;
    }

    public long totalLoadTime() {
        return this.e;
    }

    public double averageLoadPenalty() {
        long j = this.c + this.d;
        if (j == 0) {
            return 0.0d;
        }
        return this.e / j;
    }

    public long evictionCount() {
        return this.f;
    }

    public CacheStats minus(CacheStats cacheStats) {
        return new CacheStats(Math.max(0L, this.a - cacheStats.a), Math.max(0L, this.b - cacheStats.b), Math.max(0L, this.c - cacheStats.c), Math.max(0L, this.d - cacheStats.d), Math.max(0L, this.e - cacheStats.e), Math.max(0L, this.f - cacheStats.f));
    }

    public CacheStats plus(CacheStats cacheStats) {
        return new CacheStats(this.a + cacheStats.a, this.b + cacheStats.b, this.c + cacheStats.c, this.d + cacheStats.d, this.e + cacheStats.e, this.f + cacheStats.f);
    }

    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.a), Long.valueOf(this.b), Long.valueOf(this.c), Long.valueOf(this.d), Long.valueOf(this.e), Long.valueOf(this.f));
    }

    public boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof CacheStats)) {
            return false;
        }
        CacheStats cacheStats = (CacheStats) obj;
        return this.a == cacheStats.a && this.b == cacheStats.b && this.c == cacheStats.c && this.d == cacheStats.d && this.e == cacheStats.e && this.f == cacheStats.f;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("hitCount", this.a).add("missCount", this.b).add("loadSuccessCount", this.c).add("loadExceptionCount", this.d).add("totalLoadTime", this.e).add("evictionCount", this.f).toString();
    }
}
