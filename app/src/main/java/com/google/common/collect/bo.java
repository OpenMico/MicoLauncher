package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import java.util.Arrays;

/* compiled from: ObjectCountLinkedHashMap.java */
@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
class bo<K> extends bn<K> {
    @VisibleForTesting
    transient long[] f;
    private transient int g;
    private transient int h;

    bo() {
        this(3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public bo(int i) {
        this(i, 1.0f);
    }

    bo(int i, float f) {
        super(i, f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public bo(bn<K> bnVar) {
        a(bnVar.c(), 1.0f);
        int b = bnVar.b();
        while (b != -1) {
            a((bo<K>) bnVar.c(b), bnVar.d(b));
            b = bnVar.b(b);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.bn
    public void a(int i, float f) {
        super.a(i, f);
        this.g = -2;
        this.h = -2;
        this.f = new long[i];
        Arrays.fill(this.f, -1L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.bn
    public int b() {
        int i = this.g;
        if (i == -2) {
            return -1;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.bn
    public int b(int i) {
        int k = k(i);
        if (k == -2) {
            return -1;
        }
        return k;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.bn
    public int a(int i, int i2) {
        return i == c() ? i2 : i;
    }

    private int j(int i) {
        return (int) (this.f[i] >>> 32);
    }

    private int k(int i) {
        return (int) this.f[i];
    }

    private void c(int i, int i2) {
        long[] jArr = this.f;
        jArr[i] = (jArr[i] & (-4294967296L)) | (i2 & 4294967295L);
    }

    private void d(int i, int i2) {
        long[] jArr = this.f;
        jArr[i] = (jArr[i] & 4294967295L) | (i2 << 32);
    }

    private void e(int i, int i2) {
        if (i == -2) {
            this.g = i2;
        } else {
            c(i, i2);
        }
        if (i2 == -2) {
            this.h = i;
        } else {
            d(i2, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.bn
    public void a(int i, K k, int i2, int i3) {
        super.a(i, k, i2, i3);
        e(this.h, i);
        e(i, -2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.bn
    public void i(int i) {
        int c = c() - 1;
        e(j(i), k(i));
        if (i < c) {
            e(j(c), i);
            e(i, k(c));
        }
        super.i(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.bn
    public void g(int i) {
        super.g(i);
        long[] jArr = this.f;
        int length = jArr.length;
        this.f = Arrays.copyOf(jArr, i);
        Arrays.fill(this.f, length, i, -1L);
    }

    @Override // com.google.common.collect.bn
    public void d() {
        super.d();
        this.g = -2;
        this.h = -2;
    }
}
