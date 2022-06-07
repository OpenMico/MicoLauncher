package org.apache.commons.lang.math;

import java.util.Random;

/* loaded from: classes5.dex */
public final class JVMRandom extends Random {
    private static final Random a = new Random();
    private static final long serialVersionUID = 1;
    private boolean constructed;

    private static int a(long j) {
        int i = 0;
        long j2 = j;
        while (j >= 0) {
            if (j2 == 0) {
                return i;
            }
            i++;
            j <<= 1;
            j2 >>= 1;
        }
        return 64 - i;
    }

    public JVMRandom() {
        this.constructed = false;
        this.constructed = true;
    }

    @Override // java.util.Random
    public synchronized void setSeed(long j) {
        if (this.constructed) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.Random
    public synchronized double nextGaussian() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Random
    public void nextBytes(byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Random
    public int nextInt() {
        return nextInt(Integer.MAX_VALUE);
    }

    @Override // java.util.Random
    public int nextInt(int i) {
        return a.nextInt(i);
    }

    @Override // java.util.Random
    public long nextLong() {
        return nextLong(Long.MAX_VALUE);
    }

    public static long nextLong(long j) {
        long a2;
        long j2;
        if (j <= 0) {
            throw new IllegalArgumentException("Upper bound for nextInt must be positive");
        } else if (((-j) & j) == j) {
            return a() >> (63 - a(j - 1));
        } else {
            do {
                a2 = a();
                j2 = a2 % j;
            } while ((a2 - j2) + (j - 1) < 0);
            return j2;
        }
    }

    @Override // java.util.Random
    public boolean nextBoolean() {
        return a.nextBoolean();
    }

    @Override // java.util.Random
    public float nextFloat() {
        return a.nextFloat();
    }

    @Override // java.util.Random
    public double nextDouble() {
        return a.nextDouble();
    }

    private static long a() {
        return a.nextLong() & Long.MAX_VALUE;
    }
}
