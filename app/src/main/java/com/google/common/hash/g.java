package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.BloomFilter;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLongArray;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: BloomFilterStrategies.java */
/* loaded from: classes2.dex */
enum g implements BloomFilter.b {
    MURMUR128_MITZ_32 {
        @Override // com.google.common.hash.BloomFilter.b
        public <T> boolean a(T t, Funnel<? super T> funnel, int i, a aVar) {
            long a = aVar.a();
            long asLong = Hashing.murmur3_128().hashObject(t, funnel).asLong();
            int i2 = (int) asLong;
            int i3 = (int) (asLong >>> 32);
            boolean z = false;
            for (int i4 = 1; i4 <= i; i4++) {
                int i5 = (i4 * i3) + i2;
                if (i5 < 0) {
                    i5 = ~i5;
                }
                z |= aVar.a(i5 % a);
            }
            return z;
        }

        @Override // com.google.common.hash.BloomFilter.b
        public <T> boolean b(T t, Funnel<? super T> funnel, int i, a aVar) {
            long a = aVar.a();
            long asLong = Hashing.murmur3_128().hashObject(t, funnel).asLong();
            int i2 = (int) asLong;
            int i3 = (int) (asLong >>> 32);
            for (int i4 = 1; i4 <= i; i4++) {
                int i5 = (i4 * i3) + i2;
                if (i5 < 0) {
                    i5 = ~i5;
                }
                if (!aVar.b(i5 % a)) {
                    return false;
                }
            }
            return true;
        }
    },
    MURMUR128_MITZ_64 {
        @Override // com.google.common.hash.BloomFilter.b
        public <T> boolean a(T t, Funnel<? super T> funnel, int i, a aVar) {
            long a = aVar.a();
            byte[] a2 = Hashing.murmur3_128().hashObject(t, funnel).a();
            long a3 = a(a2);
            long b = b(a2);
            long j = a3;
            boolean z = false;
            for (int i2 = 0; i2 < i; i2++) {
                z |= aVar.a((Long.MAX_VALUE & j) % a);
                j += b;
            }
            return z;
        }

        @Override // com.google.common.hash.BloomFilter.b
        public <T> boolean b(T t, Funnel<? super T> funnel, int i, a aVar) {
            long a = aVar.a();
            byte[] a2 = Hashing.murmur3_128().hashObject(t, funnel).a();
            long a3 = a(a2);
            long b = b(a2);
            long j = a3;
            for (int i2 = 0; i2 < i; i2++) {
                if (!aVar.b((Long.MAX_VALUE & j) % a)) {
                    return false;
                }
                j += b;
            }
            return true;
        }

        private long a(byte[] bArr) {
            return Longs.fromBytes(bArr[7], bArr[6], bArr[5], bArr[4], bArr[3], bArr[2], bArr[1], bArr[0]);
        }

        private long b(byte[] bArr) {
            return Longs.fromBytes(bArr[15], bArr[14], bArr[13], bArr[12], bArr[11], bArr[10], bArr[9], bArr[8]);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: BloomFilterStrategies.java */
    /* loaded from: classes2.dex */
    public static final class a {
        final AtomicLongArray a;
        private final m b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(long j) {
            this(new long[Ints.checkedCast(LongMath.divide(j, 64L, RoundingMode.CEILING))]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(long[] jArr) {
            Preconditions.checkArgument(jArr.length > 0, "data length is zero!");
            this.a = new AtomicLongArray(jArr);
            this.b = n.a();
            long j = 0;
            for (long j2 : jArr) {
                j += Long.bitCount(j2);
            }
            this.b.a(j);
        }

        boolean a(long j) {
            long j2;
            long j3;
            if (b(j)) {
                return false;
            }
            int i = (int) (j >>> 6);
            long j4 = 1 << ((int) j);
            do {
                j2 = this.a.get(i);
                j3 = j2 | j4;
                if (j2 == j3) {
                    return false;
                }
            } while (!this.a.compareAndSet(i, j2, j3));
            this.b.a();
            return true;
        }

        boolean b(long j) {
            return ((1 << ((int) j)) & this.a.get((int) (j >>> 6))) != 0;
        }

        public static long[] a(AtomicLongArray atomicLongArray) {
            long[] jArr = new long[atomicLongArray.length()];
            for (int i = 0; i < jArr.length; i++) {
                jArr[i] = atomicLongArray.get(i);
            }
            return jArr;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public long a() {
            return this.a.length() * 64;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public long b() {
            return this.b.b();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public a c() {
            return new a(a(this.a));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void a(a aVar) {
            long j;
            long j2;
            boolean z;
            Preconditions.checkArgument(this.a.length() == aVar.a.length(), "BitArrays must be of equal length (%s != %s)", this.a.length(), aVar.a.length());
            for (int i = 0; i < this.a.length(); i++) {
                long j3 = aVar.a.get(i);
                while (true) {
                    j = this.a.get(i);
                    j2 = j | j3;
                    if (j != j2) {
                        if (this.a.compareAndSet(i, j, j2)) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    this.b.a(Long.bitCount(j2) - Long.bitCount(j));
                }
            }
        }

        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof a) {
                return Arrays.equals(a(this.a), a(((a) obj).a));
            }
            return false;
        }

        public int hashCode() {
            return Arrays.hashCode(a(this.a));
        }
    }
}
