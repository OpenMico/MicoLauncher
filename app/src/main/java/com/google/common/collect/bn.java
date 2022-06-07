package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: ObjectCountHashMap.java */
@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public class bn<K> {
    transient Object[] a;
    transient int[] b;
    transient int c;
    transient int d;
    @VisibleForTesting
    transient long[] e;
    private transient int[] f;
    private transient float g;
    private transient int h;

    private static int a(long j) {
        return (int) (j >>> 32);
    }

    private static long a(long j, int i) {
        return (j & (-4294967296L)) | (i & 4294967295L);
    }

    private static int b(long j) {
        return (int) j;
    }

    public int a(int i, int i2) {
        return i - 1;
    }

    public static <K> bn<K> a() {
        return new bn<>();
    }

    public static <K> bn<K> a(int i) {
        return new bn<>(i);
    }

    public bn() {
        a(3, 1.0f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public bn(bn<? extends K> bnVar) {
        a(bnVar.c(), 1.0f);
        int b = bnVar.b();
        while (b != -1) {
            a((bn<K>) bnVar.c(b), bnVar.d(b));
            b = bnVar.b(b);
        }
    }

    public bn(int i) {
        this(i, 1.0f);
    }

    public bn(int i, float f) {
        a(i, f);
    }

    public void a(int i, float f) {
        boolean z = false;
        Preconditions.checkArgument(i >= 0, "Initial capacity must be non-negative");
        if (f > 0.0f) {
            z = true;
        }
        Preconditions.checkArgument(z, "Illegal load factor");
        int a2 = au.a(i, f);
        this.f = j(a2);
        this.g = f;
        this.a = new Object[i];
        this.b = new int[i];
        this.e = k(i);
        this.h = Math.max(1, (int) (a2 * f));
    }

    private static int[] j(int i) {
        int[] iArr = new int[i];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private static long[] k(int i) {
        long[] jArr = new long[i];
        Arrays.fill(jArr, -1L);
        return jArr;
    }

    private int e() {
        return this.f.length - 1;
    }

    public int b() {
        return this.c == 0 ? -1 : 0;
    }

    public int b(int i) {
        int i2 = i + 1;
        if (i2 < this.c) {
            return i2;
        }
        return -1;
    }

    public int c() {
        return this.c;
    }

    public K c(int i) {
        Preconditions.checkElementIndex(i, this.c);
        return (K) this.a[i];
    }

    public int d(int i) {
        Preconditions.checkElementIndex(i, this.c);
        return this.b[i];
    }

    public void b(int i, int i2) {
        Preconditions.checkElementIndex(i, this.c);
        this.b[i] = i2;
    }

    public Multiset.Entry<K> e(int i) {
        Preconditions.checkElementIndex(i, this.c);
        return new a(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ObjectCountHashMap.java */
    /* loaded from: classes2.dex */
    public class a extends Multisets.a<K> {
        @NullableDecl
        final K a;
        int b;

        a(int i) {
            bn.this = r1;
            this.a = (K) r1.a[i];
            this.b = i;
        }

        @Override // com.google.common.collect.Multiset.Entry
        public K getElement() {
            return this.a;
        }

        void a() {
            int i = this.b;
            if (i == -1 || i >= bn.this.c() || !Objects.equal(this.a, bn.this.a[this.b])) {
                this.b = bn.this.a(this.a);
            }
        }

        @Override // com.google.common.collect.Multiset.Entry
        public int getCount() {
            a();
            if (this.b == -1) {
                return 0;
            }
            return bn.this.b[this.b];
        }
    }

    public void f(int i) {
        if (i > this.e.length) {
            g(i);
        }
        if (i >= this.h) {
            m(Math.max(2, Integer.highestOneBit(i - 1) << 1));
        }
    }

    @CanIgnoreReturnValue
    public int a(@NullableDecl K k, int i) {
        t.b(i, "count");
        long[] jArr = this.e;
        Object[] objArr = this.a;
        int[] iArr = this.b;
        int a2 = au.a(k);
        int e = e() & a2;
        int i2 = this.c;
        int[] iArr2 = this.f;
        int i3 = iArr2[e];
        if (i3 == -1) {
            iArr2[e] = i2;
        } else {
            while (true) {
                long j = jArr[i3];
                if (a(j) != a2 || !Objects.equal(k, objArr[i3])) {
                    int b = b(j);
                    if (b == -1) {
                        jArr[i3] = a(j, i2);
                        break;
                    }
                    i3 = b;
                } else {
                    int i4 = iArr[i3];
                    iArr[i3] = i;
                    return i4;
                }
            }
        }
        if (i2 != Integer.MAX_VALUE) {
            int i5 = i2 + 1;
            l(i5);
            a(i2, k, i, a2);
            this.c = i5;
            if (i2 >= this.h) {
                m(this.f.length * 2);
            }
            this.d++;
            return 0;
        }
        throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
    }

    public void a(int i, @NullableDecl K k, int i2, int i3) {
        this.e[i] = (i3 << 32) | 4294967295L;
        this.a[i] = k;
        this.b[i] = i2;
    }

    private void l(int i) {
        int length = this.e.length;
        if (i > length) {
            int max = Math.max(1, length >>> 1) + length;
            if (max < 0) {
                max = Integer.MAX_VALUE;
            }
            if (max != length) {
                g(max);
            }
        }
    }

    public void g(int i) {
        this.a = Arrays.copyOf(this.a, i);
        this.b = Arrays.copyOf(this.b, i);
        long[] jArr = this.e;
        int length = jArr.length;
        long[] copyOf = Arrays.copyOf(jArr, i);
        if (i > length) {
            Arrays.fill(copyOf, length, i, -1L);
        }
        this.e = copyOf;
    }

    private void m(int i) {
        if (this.f.length >= 1073741824) {
            this.h = Integer.MAX_VALUE;
            return;
        }
        int i2 = ((int) (i * this.g)) + 1;
        int[] j = j(i);
        long[] jArr = this.e;
        int length = j.length - 1;
        for (int i3 = 0; i3 < this.c; i3++) {
            int a2 = a(jArr[i3]);
            int i4 = a2 & length;
            int i5 = j[i4];
            j[i4] = i3;
            jArr[i3] = (a2 << 32) | (i5 & 4294967295L);
        }
        this.h = i2;
        this.f = j;
    }

    public int a(@NullableDecl Object obj) {
        int a2 = au.a(obj);
        int i = this.f[e() & a2];
        while (i != -1) {
            long j = this.e[i];
            if (a(j) == a2 && Objects.equal(obj, this.a[i])) {
                return i;
            }
            i = b(j);
        }
        return -1;
    }

    public int b(@NullableDecl Object obj) {
        int a2 = a(obj);
        if (a2 == -1) {
            return 0;
        }
        return this.b[a2];
    }

    @CanIgnoreReturnValue
    public int c(@NullableDecl Object obj) {
        return b(obj, au.a(obj));
    }

    private int b(@NullableDecl Object obj, int i) {
        int e = e() & i;
        int i2 = this.f[e];
        if (i2 == -1) {
            return 0;
        }
        int i3 = -1;
        while (true) {
            if (a(this.e[i2]) != i || !Objects.equal(obj, this.a[i2])) {
                int b = b(this.e[i2]);
                if (b == -1) {
                    return 0;
                }
                i3 = i2;
                i2 = b;
            } else {
                int i4 = this.b[i2];
                if (i3 == -1) {
                    this.f[e] = b(this.e[i2]);
                } else {
                    long[] jArr = this.e;
                    jArr[i3] = a(jArr[i3], b(jArr[i2]));
                }
                i(i2);
                this.c--;
                this.d++;
                return i4;
            }
        }
    }

    @CanIgnoreReturnValue
    public int h(int i) {
        return b(this.a[i], a(this.e[i]));
    }

    public void i(int i) {
        int c = c() - 1;
        if (i < c) {
            Object[] objArr = this.a;
            objArr[i] = objArr[c];
            int[] iArr = this.b;
            iArr[i] = iArr[c];
            objArr[c] = null;
            iArr[c] = 0;
            long[] jArr = this.e;
            long j = jArr[c];
            jArr[i] = j;
            jArr[c] = -1;
            int a2 = a(j) & e();
            int[] iArr2 = this.f;
            int i2 = iArr2[a2];
            if (i2 == c) {
                iArr2[a2] = i;
                return;
            }
            while (true) {
                long j2 = this.e[i2];
                int b = b(j2);
                if (b == c) {
                    this.e[i2] = a(j2, i);
                    return;
                }
                i2 = b;
            }
        } else {
            this.a[i] = null;
            this.b[i] = 0;
            this.e[i] = -1;
        }
    }

    public void d() {
        this.d++;
        Arrays.fill(this.a, 0, this.c, (Object) null);
        Arrays.fill(this.b, 0, this.c, 0);
        Arrays.fill(this.f, -1);
        Arrays.fill(this.e, -1L);
        this.c = 0;
    }
}
