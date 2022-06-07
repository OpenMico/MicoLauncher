package com.google.common.hash;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

/* compiled from: FarmHashFingerprint64.java */
/* loaded from: classes2.dex */
final class j extends e {
    static final HashFunction a = new j();

    private static long a(long j) {
        return j ^ (j >>> 47);
    }

    private static long a(long j, long j2, long j3) {
        long j4 = (j ^ j2) * j3;
        long j5 = ((j4 ^ (j4 >>> 47)) ^ j2) * j3;
        return (j5 ^ (j5 >>> 47)) * j3;
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    public String toString() {
        return "Hashing.farmHashFingerprint64()";
    }

    j() {
    }

    @Override // com.google.common.hash.e, com.google.common.hash.c, com.google.common.hash.HashFunction
    public HashCode hashBytes(byte[] bArr, int i, int i2) {
        Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
        return HashCode.fromLong(a(bArr, i, i2));
    }

    @VisibleForTesting
    static long a(byte[] bArr, int i, int i2) {
        if (i2 <= 32) {
            if (i2 <= 16) {
                return b(bArr, i, i2);
            }
            return c(bArr, i, i2);
        } else if (i2 <= 64) {
            return d(bArr, i, i2);
        } else {
            return e(bArr, i, i2);
        }
    }

    private static void a(byte[] bArr, int i, long j, long j2, long[] jArr) {
        long a2 = l.a(bArr, i);
        long a3 = l.a(bArr, i + 8);
        long a4 = l.a(bArr, i + 16);
        long a5 = l.a(bArr, i + 24);
        long j3 = j + a2;
        long j4 = a3 + j3 + a4;
        jArr[0] = j4 + a5;
        jArr[1] = Long.rotateRight(j2 + j3 + a5, 21) + Long.rotateRight(j4, 44) + j3;
    }

    private static long b(byte[] bArr, int i, int i2) {
        if (i2 >= 8) {
            long j = (i2 * 2) - 7286425919675154353L;
            long a2 = l.a(bArr, i) - 7286425919675154353L;
            long a3 = l.a(bArr, (i + i2) - 8);
            return a((Long.rotateRight(a3, 37) * j) + a2, (Long.rotateRight(a2, 25) + a3) * j, j);
        } else if (i2 >= 4) {
            return a(i2 + ((l.b(bArr, i) & 4294967295L) << 3), l.b(bArr, (i + i2) - 4) & 4294967295L, (i2 * 2) - 7286425919675154353L);
        } else if (i2 <= 0) {
            return -7286425919675154353L;
        } else {
            return a((((bArr[i] & 255) + ((bArr[(i2 >> 1) + i] & 255) << 8)) * (-7286425919675154353L)) ^ ((i2 + ((bArr[i + (i2 - 1)] & 255) << 2)) * (-4348849565147123417L))) * (-7286425919675154353L);
        }
    }

    private static long c(byte[] bArr, int i, int i2) {
        long j = (i2 * 2) - 7286425919675154353L;
        long a2 = l.a(bArr, i) * (-5435081209227447693L);
        long a3 = l.a(bArr, i + 8);
        int i3 = i + i2;
        long a4 = l.a(bArr, i3 - 8) * j;
        return a((l.a(bArr, i3 - 16) * (-7286425919675154353L)) + Long.rotateRight(a2 + a3, 43) + Long.rotateRight(a4, 30), a2 + Long.rotateRight(a3 - 7286425919675154353L, 18) + a4, j);
    }

    private static long d(byte[] bArr, int i, int i2) {
        long j = (i2 * 2) - 7286425919675154353L;
        long a2 = l.a(bArr, i) * (-7286425919675154353L);
        long a3 = l.a(bArr, i + 8);
        int i3 = i + i2;
        long a4 = l.a(bArr, i3 - 8) * j;
        long rotateRight = Long.rotateRight(a2 + a3, 43) + Long.rotateRight(a4, 30) + (l.a(bArr, i3 - 16) * (-7286425919675154353L));
        long a5 = a(rotateRight, a4 + Long.rotateRight(a3 - 7286425919675154353L, 18) + a2, j);
        long a6 = l.a(bArr, i + 16) * j;
        long a7 = l.a(bArr, i + 24);
        long a8 = (rotateRight + l.a(bArr, i3 - 32)) * j;
        return a(((a5 + l.a(bArr, i3 - 24)) * j) + Long.rotateRight(a6 + a7, 43) + Long.rotateRight(a8, 30), a6 + Long.rotateRight(a7 + a2, 18) + a8, j);
    }

    private static long e(byte[] bArr, int i, int i2) {
        long a2 = a(-7956866745689871395L) * (-7286425919675154353L);
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        long a3 = 95310865018149119L + l.a(bArr, i);
        int i3 = i2 - 1;
        int i4 = i + ((i3 / 64) * 64);
        int i5 = i3 & 63;
        int i6 = (i4 + i5) - 63;
        long j = 2480279821605975764L;
        int i7 = i;
        while (true) {
            long rotateRight = (Long.rotateRight(((a3 + j) + jArr[0]) + l.a(bArr, i7 + 8), 37) * (-5435081209227447693L)) ^ jArr2[1];
            long rotateRight2 = (Long.rotateRight(j + jArr[1] + l.a(bArr, i7 + 48), 42) * (-5435081209227447693L)) + jArr[0] + l.a(bArr, i7 + 40);
            long rotateRight3 = Long.rotateRight(a2 + jArr2[0], 33) * (-5435081209227447693L);
            a(bArr, i7, jArr[1] * (-5435081209227447693L), rotateRight + jArr2[0], jArr);
            a(bArr, i7 + 32, rotateRight3 + jArr2[1], rotateRight2 + l.a(bArr, i7 + 16), jArr2);
            i7 += 64;
            if (i7 == i4) {
                long j2 = ((rotateRight & 255) << 1) - 5435081209227447693L;
                jArr2[0] = jArr2[0] + i5;
                jArr[0] = jArr[0] + jArr2[0];
                jArr2[0] = jArr2[0] + jArr[0];
                long rotateRight4 = (Long.rotateRight(((rotateRight3 + rotateRight2) + jArr[0]) + l.a(bArr, i6 + 8), 37) * j2) ^ (jArr2[1] * 9);
                long rotateRight5 = (Long.rotateRight(rotateRight2 + jArr[1] + l.a(bArr, i6 + 48), 42) * j2) + (jArr[0] * 9) + l.a(bArr, i6 + 40);
                long rotateRight6 = Long.rotateRight(rotateRight + jArr2[0], 33) * j2;
                a(bArr, i6, jArr[1] * j2, rotateRight4 + jArr2[0], jArr);
                a(bArr, i6 + 32, rotateRight6 + jArr2[1], l.a(bArr, i6 + 16) + rotateRight5, jArr2);
                return a(a(jArr[0], jArr2[0], j2) + (a(rotateRight5) * (-4348849565147123417L)) + rotateRight4, a(jArr[1], jArr2[1], j2) + rotateRight6, j2);
            }
            a2 = rotateRight;
            j = rotateRight2;
            a3 = rotateRight3;
        }
    }
}
