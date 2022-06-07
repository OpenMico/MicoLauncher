package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

/* compiled from: CompactLinkedHashMap.java */
@GwtIncompatible
/* loaded from: classes2.dex */
class w<K, V> extends u<K, V> {
    private final boolean accessOrder;
    @VisibleForTesting
    @MonotonicNonNullDecl
    transient long[] f;
    private transient int g;
    private transient int h;

    public static <K, V> w<K, V> i() {
        return new w<>();
    }

    public static <K, V> w<K, V> f(int i) {
        return new w<>(i);
    }

    w() {
        this(3);
    }

    w(int i) {
        this(i, 1.0f, false);
    }

    w(int i, float f, boolean z) {
        super(i, f);
        this.accessOrder = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.u
    public void a(int i, float f) {
        super.a(i, f);
        this.g = -2;
        this.h = -2;
        this.f = new long[i];
        Arrays.fill(this.f, -1L);
    }

    private int g(int i) {
        return (int) (this.f[i] >>> 32);
    }

    @Override // com.google.common.collect.u
    int e(int i) {
        return (int) this.f[i];
    }

    private void b(int i, int i2) {
        long[] jArr = this.f;
        jArr[i] = (jArr[i] & (-4294967296L)) | (i2 & 4294967295L);
    }

    private void c(int i, int i2) {
        long[] jArr = this.f;
        jArr[i] = (jArr[i] & 4294967295L) | (i2 << 32);
    }

    private void d(int i, int i2) {
        if (i == -2) {
            this.g = i2;
        } else {
            b(i, i2);
        }
        if (i2 == -2) {
            this.h = i;
        } else {
            c(i2, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.u
    public void a(int i, K k, V v, int i2) {
        super.a(i, k, v, i2);
        d(this.h, i);
        d(i, -2);
    }

    @Override // com.google.common.collect.u
    void b(int i) {
        if (this.accessOrder) {
            d(g(i), e(i));
            d(this.h, i);
            d(i, -2);
            this.e++;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.u
    public void d(int i) {
        int size = size() - 1;
        d(g(i), e(i));
        if (i < size) {
            d(g(size), i);
            d(i, e(size));
        }
        super.d(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.u
    public void c(int i) {
        super.c(i);
        this.f = Arrays.copyOf(this.f, i);
    }

    @Override // com.google.common.collect.u
    int b() {
        return this.g;
    }

    @Override // com.google.common.collect.u
    int a(int i, int i2) {
        return i >= size() ? i2 : i;
    }

    @Override // com.google.common.collect.u, java.util.AbstractMap, java.util.Map
    public void clear() {
        super.clear();
        this.g = -2;
        this.h = -2;
    }
}
