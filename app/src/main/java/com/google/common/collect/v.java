package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: CompactHashSet.java */
@GwtIncompatible
/* loaded from: classes2.dex */
public class v<E> extends AbstractSet<E> implements Serializable {
    @MonotonicNonNullDecl
    transient Object[] a;
    transient float b;
    transient int c;
    @MonotonicNonNullDecl
    private transient int[] d;
    @MonotonicNonNullDecl
    private transient long[] e;
    private transient int f;
    private transient int g;

    private static long a(long j, int i) {
        return (j & (-4294967296L)) | (i & 4294967295L);
    }

    public static int b(long j) {
        return (int) (j >>> 32);
    }

    private static int c(long j) {
        return (int) j;
    }

    int a(int i, int i2) {
        return i - 1;
    }

    public static <E> v<E> a() {
        return new v<>();
    }

    public static <E> v<E> a(int i) {
        return new v<>(i);
    }

    public v() {
        a(3, 1.0f);
    }

    public v(int i) {
        a(i, 1.0f);
    }

    public void a(int i, float f) {
        boolean z = false;
        Preconditions.checkArgument(i >= 0, "Initial capacity must be non-negative");
        if (f > 0.0f) {
            z = true;
        }
        Preconditions.checkArgument(z, "Illegal load factor");
        int a = au.a(i, f);
        this.d = e(a);
        this.b = f;
        this.a = new Object[i];
        this.e = f(i);
        this.f = Math.max(1, (int) (a * f));
    }

    private static int[] e(int i) {
        int[] iArr = new int[i];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private static long[] f(int i) {
        long[] jArr = new long[i];
        Arrays.fill(jArr, -1L);
        return jArr;
    }

    private int c() {
        return this.d.length - 1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    @CanIgnoreReturnValue
    public boolean add(@NullableDecl E e) {
        long[] jArr = this.e;
        Object[] objArr = this.a;
        int a = au.a(e);
        int c = c() & a;
        int i = this.g;
        int[] iArr = this.d;
        int i2 = iArr[c];
        if (i2 == -1) {
            iArr[c] = i;
        } else {
            while (true) {
                long j = jArr[i2];
                if (b(j) == a && Objects.equal(e, objArr[i2])) {
                    return false;
                }
                int c2 = c(j);
                if (c2 == -1) {
                    jArr[i2] = a(j, i);
                    break;
                }
                i2 = c2;
            }
        }
        if (i != Integer.MAX_VALUE) {
            int i3 = i + 1;
            g(i3);
            a(i, (int) e, a);
            this.g = i3;
            if (i >= this.f) {
                h(this.d.length * 2);
            }
            this.c++;
            return true;
        }
        throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
    }

    public void a(int i, E e, int i2) {
        this.e[i] = (i2 << 32) | 4294967295L;
        this.a[i] = e;
    }

    private void g(int i) {
        int length = this.e.length;
        if (i > length) {
            int max = Math.max(1, length >>> 1) + length;
            if (max < 0) {
                max = Integer.MAX_VALUE;
            }
            if (max != length) {
                b(max);
            }
        }
    }

    public void b(int i) {
        this.a = Arrays.copyOf(this.a, i);
        long[] jArr = this.e;
        int length = jArr.length;
        long[] copyOf = Arrays.copyOf(jArr, i);
        if (i > length) {
            Arrays.fill(copyOf, length, i, -1L);
        }
        this.e = copyOf;
    }

    private void h(int i) {
        if (this.d.length >= 1073741824) {
            this.f = Integer.MAX_VALUE;
            return;
        }
        int i2 = ((int) (i * this.b)) + 1;
        int[] e = e(i);
        long[] jArr = this.e;
        int length = e.length - 1;
        for (int i3 = 0; i3 < this.g; i3++) {
            int b = b(jArr[i3]);
            int i4 = b & length;
            int i5 = e[i4];
            e[i4] = i3;
            jArr[i3] = (b << 32) | (i5 & 4294967295L);
        }
        this.f = i2;
        this.d = e;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(@NullableDecl Object obj) {
        int a = au.a(obj);
        int i = this.d[c() & a];
        while (i != -1) {
            long j = this.e[i];
            if (b(j) == a && Objects.equal(obj, this.a[i])) {
                return true;
            }
            i = c(j);
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    @CanIgnoreReturnValue
    public boolean remove(@NullableDecl Object obj) {
        return a(obj, au.a(obj));
    }

    @CanIgnoreReturnValue
    public boolean a(Object obj, int i) {
        int c = c() & i;
        int i2 = this.d[c];
        if (i2 == -1) {
            return false;
        }
        int i3 = -1;
        while (true) {
            if (b(this.e[i2]) != i || !Objects.equal(obj, this.a[i2])) {
                int c2 = c(this.e[i2]);
                if (c2 == -1) {
                    return false;
                }
                i3 = i2;
                i2 = c2;
            } else {
                if (i3 == -1) {
                    this.d[c] = c(this.e[i2]);
                } else {
                    long[] jArr = this.e;
                    jArr[i3] = a(jArr[i3], c(jArr[i2]));
                }
                c(i2);
                this.g--;
                this.c++;
                return true;
            }
        }
    }

    public void c(int i) {
        int size = size() - 1;
        if (i < size) {
            Object[] objArr = this.a;
            objArr[i] = objArr[size];
            objArr[size] = null;
            long[] jArr = this.e;
            long j = jArr[size];
            jArr[i] = j;
            jArr[size] = -1;
            int b = b(j) & c();
            int[] iArr = this.d;
            int i2 = iArr[b];
            if (i2 == size) {
                iArr[b] = i;
                return;
            }
            while (true) {
                long j2 = this.e[i2];
                int c = c(j2);
                if (c == size) {
                    this.e[i2] = a(j2, i);
                    return;
                }
                i2 = c;
            }
        } else {
            this.a[i] = null;
            this.e[i] = -1;
        }
    }

    int b() {
        return isEmpty() ? -1 : 0;
    }

    int d(int i) {
        int i2 = i + 1;
        if (i2 < this.g) {
            return i2;
        }
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<E> iterator() {
        return new Iterator<E>() { // from class: com.google.common.collect.v.1
            int a;
            int b;
            int c = -1;

            {
                v.this = this;
                this.a = v.this.c;
                this.b = v.this.b();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.b >= 0;
            }

            @Override // java.util.Iterator
            public E next() {
                a();
                if (hasNext()) {
                    this.c = this.b;
                    Object[] objArr = v.this.a;
                    int i = this.b;
                    E e = (E) objArr[i];
                    this.b = v.this.d(i);
                    return e;
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                a();
                t.a(this.c >= 0);
                this.a++;
                v vVar = v.this;
                vVar.a(vVar.a[this.c], v.b(v.this.e[this.c]));
                this.b = v.this.a(this.b, this.c);
                this.c = -1;
            }

            private void a() {
                if (v.this.c != this.a) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.g;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.g == 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public Object[] toArray() {
        return Arrays.copyOf(this.a, this.g);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    @CanIgnoreReturnValue
    public <T> T[] toArray(T[] tArr) {
        return (T[]) ObjectArrays.a(this.a, 0, this.g, tArr);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.c++;
        Arrays.fill(this.a, 0, this.g, (Object) null);
        Arrays.fill(this.d, -1);
        Arrays.fill(this.e, -1L);
        this.g = 0;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.g);
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            objectOutputStream.writeObject(it.next());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        a(3, 1.0f);
        int readInt = objectInputStream.readInt();
        while (true) {
            readInt--;
            if (readInt >= 0) {
                add(objectInputStream.readObject());
            } else {
                return;
            }
        }
    }
}
