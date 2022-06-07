package org.eclipse.jetty.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes5.dex */
public class Atomics {
    private Atomics() {
    }

    public static void updateMin(AtomicLong atomicLong, long j) {
        long j2 = atomicLong.get();
        while (j < j2 && !atomicLong.compareAndSet(j2, j)) {
            j2 = atomicLong.get();
        }
    }

    public static void updateMax(AtomicLong atomicLong, long j) {
        long j2 = atomicLong.get();
        while (j > j2 && !atomicLong.compareAndSet(j2, j)) {
            j2 = atomicLong.get();
        }
    }

    public static void updateMin(AtomicInteger atomicInteger, int i) {
        int i2 = atomicInteger.get();
        while (i < i2 && !atomicInteger.compareAndSet(i2, i)) {
            i2 = atomicInteger.get();
        }
    }

    public static void updateMax(AtomicInteger atomicInteger, int i) {
        int i2 = atomicInteger.get();
        while (i > i2 && !atomicInteger.compareAndSet(i2, i)) {
            i2 = atomicInteger.get();
        }
    }
}
