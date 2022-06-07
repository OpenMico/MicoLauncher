package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.hash.g;
import com.google.common.math.DoubleMath;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.RoundingMode;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public final class BloomFilter<T> implements Predicate<T>, Serializable {
    private final g.a bits;
    private final Funnel<? super T> funnel;
    private final int numHashFunctions;
    private final b strategy;

    /* loaded from: classes2.dex */
    public interface b extends Serializable {
        <T> boolean a(T t, Funnel<? super T> funnel, int i, g.a aVar);

        <T> boolean b(T t, Funnel<? super T> funnel, int i, g.a aVar);

        int ordinal();
    }

    private BloomFilter(g.a aVar, int i, Funnel<? super T> funnel, b bVar) {
        boolean z = true;
        Preconditions.checkArgument(i > 0, "numHashFunctions (%s) must be > 0", i);
        Preconditions.checkArgument(i > 255 ? false : z, "numHashFunctions (%s) must be <= 255", i);
        this.bits = (g.a) Preconditions.checkNotNull(aVar);
        this.numHashFunctions = i;
        this.funnel = (Funnel) Preconditions.checkNotNull(funnel);
        this.strategy = (b) Preconditions.checkNotNull(bVar);
    }

    public BloomFilter<T> copy() {
        return new BloomFilter<>(this.bits.c(), this.numHashFunctions, this.funnel, this.strategy);
    }

    public boolean mightContain(T t) {
        return this.strategy.b(t, this.funnel, this.numHashFunctions, this.bits);
    }

    @Override // com.google.common.base.Predicate
    @Deprecated
    public boolean apply(T t) {
        return mightContain(t);
    }

    @CanIgnoreReturnValue
    public boolean put(T t) {
        return this.strategy.a(t, this.funnel, this.numHashFunctions, this.bits);
    }

    public double expectedFpp() {
        return Math.pow(this.bits.b() / a(), this.numHashFunctions);
    }

    public long approximateElementCount() {
        double a2 = this.bits.a();
        return DoubleMath.roundToLong(((-Math.log1p(-(this.bits.b() / a2))) * a2) / this.numHashFunctions, RoundingMode.HALF_UP);
    }

    @VisibleForTesting
    long a() {
        return this.bits.a();
    }

    public boolean isCompatible(BloomFilter<T> bloomFilter) {
        Preconditions.checkNotNull(bloomFilter);
        return this != bloomFilter && this.numHashFunctions == bloomFilter.numHashFunctions && a() == bloomFilter.a() && this.strategy.equals(bloomFilter.strategy) && this.funnel.equals(bloomFilter.funnel);
    }

    public void putAll(BloomFilter<T> bloomFilter) {
        Preconditions.checkNotNull(bloomFilter);
        Preconditions.checkArgument(this != bloomFilter, "Cannot combine a BloomFilter with itself.");
        Preconditions.checkArgument(this.numHashFunctions == bloomFilter.numHashFunctions, "BloomFilters must have the same number of hash functions (%s != %s)", this.numHashFunctions, bloomFilter.numHashFunctions);
        Preconditions.checkArgument(a() == bloomFilter.a(), "BloomFilters must have the same size underlying bit arrays (%s != %s)", a(), bloomFilter.a());
        Preconditions.checkArgument(this.strategy.equals(bloomFilter.strategy), "BloomFilters must have equal strategies (%s != %s)", this.strategy, bloomFilter.strategy);
        Preconditions.checkArgument(this.funnel.equals(bloomFilter.funnel), "BloomFilters must have equal funnels (%s != %s)", this.funnel, bloomFilter.funnel);
        this.bits.a(bloomFilter.bits);
    }

    @Override // com.google.common.base.Predicate
    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BloomFilter)) {
            return false;
        }
        BloomFilter bloomFilter = (BloomFilter) obj;
        return this.numHashFunctions == bloomFilter.numHashFunctions && this.funnel.equals(bloomFilter.funnel) && this.bits.equals(bloomFilter.bits) && this.strategy.equals(bloomFilter.strategy);
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.numHashFunctions), this.funnel, this.strategy, this.bits);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int i, double d) {
        return create(funnel, i, d);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long j, double d) {
        return a(funnel, j, d, g.MURMUR128_MITZ_64);
    }

    @VisibleForTesting
    static <T> BloomFilter<T> a(Funnel<? super T> funnel, long j, double d, b bVar) {
        Preconditions.checkNotNull(funnel);
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        boolean z = true;
        Preconditions.checkArgument(i >= 0, "Expected insertions (%s) must be >= 0", j);
        Preconditions.checkArgument(d > 0.0d, "False positive probability (%s) must be > 0.0", Double.valueOf(d));
        if (d >= 1.0d) {
            z = false;
        }
        Preconditions.checkArgument(z, "False positive probability (%s) must be < 1.0", Double.valueOf(d));
        Preconditions.checkNotNull(bVar);
        if (i == 0) {
            j = 1;
        }
        long a2 = a(j, d);
        try {
            return new BloomFilter<>(new g.a(a2), a(j, a2), funnel, bVar);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Could not create BloomFilter of " + a2 + " bits", e);
        }
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int i) {
        return create(funnel, i);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long j) {
        return create(funnel, j, 0.03d);
    }

    @VisibleForTesting
    static int a(long j, long j2) {
        return Math.max(1, (int) Math.round((j2 / j) * Math.log(2.0d)));
    }

    @VisibleForTesting
    static long a(long j, double d) {
        if (d == 0.0d) {
            d = Double.MIN_VALUE;
        }
        return (long) (((-j) * Math.log(d)) / (Math.log(2.0d) * Math.log(2.0d)));
    }

    private Object writeReplace() {
        return new a(this);
    }

    /* loaded from: classes2.dex */
    private static class a<T> implements Serializable {
        private static final long serialVersionUID = 1;
        final long[] data;
        final Funnel<? super T> funnel;
        final int numHashFunctions;
        final b strategy;

        a(BloomFilter<T> bloomFilter) {
            this.data = g.a.a(((BloomFilter) bloomFilter).bits.a);
            this.numHashFunctions = ((BloomFilter) bloomFilter).numHashFunctions;
            this.funnel = ((BloomFilter) bloomFilter).funnel;
            this.strategy = ((BloomFilter) bloomFilter).strategy;
        }

        Object readResolve() {
            return new BloomFilter(new g.a(this.data), this.numHashFunctions, this.funnel, this.strategy);
        }
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeByte(SignedBytes.checkedCast(this.strategy.ordinal()));
        dataOutputStream.writeByte(UnsignedBytes.checkedCast(this.numHashFunctions));
        dataOutputStream.writeInt(this.bits.a.length());
        for (int i = 0; i < this.bits.a.length(); i++) {
            dataOutputStream.writeLong(this.bits.a.get(i));
        }
    }

    public static <T> BloomFilter<T> readFrom(InputStream inputStream, Funnel<? super T> funnel) throws IOException {
        RuntimeException e;
        byte b2;
        int i;
        DataInputStream dataInputStream;
        Preconditions.checkNotNull(inputStream, "InputStream");
        Preconditions.checkNotNull(funnel, "Funnel");
        int i2 = -1;
        try {
            dataInputStream = new DataInputStream(inputStream);
            b2 = dataInputStream.readByte();
            try {
                i = UnsignedBytes.toInt(dataInputStream.readByte());
            } catch (RuntimeException e2) {
                e = e2;
                i = -1;
            }
        } catch (RuntimeException e3) {
            e = e3;
            b2 = -1;
            i = -1;
        }
        try {
            i2 = dataInputStream.readInt();
            g gVar = g.values()[b2];
            long[] jArr = new long[i2];
            for (int i3 = 0; i3 < jArr.length; i3++) {
                jArr[i3] = dataInputStream.readLong();
            }
            return new BloomFilter<>(new g.a(jArr), i, funnel, gVar);
        } catch (RuntimeException e4) {
            e = e4;
            throw new IOException("Unable to deserialize BloomFilter from InputStream. strategyOrdinal: " + ((int) b2) + " numHashFunctions: " + i + " dataLength: " + i2, e);
        }
    }
}
