package com.google.android.exoplayer2.util;

import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
public final class IntArrayQueue {
    private int a = 0;
    private int b = -1;
    private int c = 0;
    private int[] d = new int[16];
    private int e = this.d.length - 1;

    public void add(int i) {
        if (this.c == this.d.length) {
            a();
        }
        this.b = (this.b + 1) & this.e;
        this.d[this.b] = i;
        this.c++;
    }

    public int remove() {
        int i = this.c;
        if (i != 0) {
            int[] iArr = this.d;
            int i2 = this.a;
            int i3 = iArr[i2];
            this.a = (i2 + 1) & this.e;
            this.c = i - 1;
            return i3;
        }
        throw new NoSuchElementException();
    }

    public int size() {
        return this.c;
    }

    public boolean isEmpty() {
        return this.c == 0;
    }

    public void clear() {
        this.a = 0;
        this.b = -1;
        this.c = 0;
    }

    public int capacity() {
        return this.d.length;
    }

    private void a() {
        int[] iArr = this.d;
        int length = iArr.length << 1;
        if (length >= 0) {
            int[] iArr2 = new int[length];
            int length2 = iArr.length;
            int i = this.a;
            int i2 = length2 - i;
            System.arraycopy(iArr, i, iArr2, 0, i2);
            System.arraycopy(this.d, 0, iArr2, i2, i);
            this.a = 0;
            this.b = this.c - 1;
            this.d = iArr2;
            this.e = this.d.length - 1;
            return;
        }
        throw new IllegalStateException();
    }
}
