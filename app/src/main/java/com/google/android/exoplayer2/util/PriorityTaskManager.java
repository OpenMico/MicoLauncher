package com.google.android.exoplayer2.util;

import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;

/* loaded from: classes2.dex */
public final class PriorityTaskManager {
    private final Object a = new Object();
    private final PriorityQueue<Integer> b = new PriorityQueue<>(10, Collections.reverseOrder());
    private int c = Integer.MIN_VALUE;

    /* loaded from: classes2.dex */
    public static class PriorityTooLowException extends IOException {
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public PriorityTooLowException(int r3, int r4) {
            /*
                r2 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r1 = 60
                r0.<init>(r1)
                java.lang.String r1 = "Priority too low [priority="
                r0.append(r1)
                r0.append(r3)
                java.lang.String r3 = ", highest="
                r0.append(r3)
                r0.append(r4)
                java.lang.String r3 = "]"
                r0.append(r3)
                java.lang.String r3 = r0.toString()
                r2.<init>(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.PriorityTaskManager.PriorityTooLowException.<init>(int, int):void");
        }
    }

    public void add(int i) {
        synchronized (this.a) {
            this.b.add(Integer.valueOf(i));
            this.c = Math.max(this.c, i);
        }
    }

    public void proceed(int i) throws InterruptedException {
        synchronized (this.a) {
            while (this.c != i) {
                this.a.wait();
            }
        }
    }

    public boolean proceedNonBlocking(int i) {
        boolean z;
        synchronized (this.a) {
            z = this.c == i;
        }
        return z;
    }

    public void proceedOrThrow(int i) throws PriorityTooLowException {
        synchronized (this.a) {
            if (this.c != i) {
                throw new PriorityTooLowException(i, this.c);
            }
        }
    }

    public void remove(int i) {
        synchronized (this.a) {
            this.b.remove(Integer.valueOf(i));
            this.c = this.b.isEmpty() ? Integer.MIN_VALUE : ((Integer) Util.castNonNull(this.b.peek())).intValue();
            this.a.notifyAll();
        }
    }
}
