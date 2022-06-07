package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: CompactHashMap.java */
@GwtIncompatible
/* loaded from: classes2.dex */
public class u<K, V> extends AbstractMap<K, V> implements Serializable {
    @VisibleForTesting
    @MonotonicNonNullDecl
    transient long[] a;
    @VisibleForTesting
    @MonotonicNonNullDecl
    transient Object[] b;
    @VisibleForTesting
    @MonotonicNonNullDecl
    transient Object[] c;
    transient float d;
    transient int e;
    @MonotonicNonNullDecl
    private transient int[] f;
    private transient int g;
    private transient int h;
    @MonotonicNonNullDecl
    private transient Set<K> i;
    @MonotonicNonNullDecl
    private transient Set<Map.Entry<K, V>> j;
    @MonotonicNonNullDecl
    private transient Collection<V> k;

    private static int a(long j) {
        return (int) (j >>> 32);
    }

    private static long a(long j, int i) {
        return (j & (-4294967296L)) | (i & 4294967295L);
    }

    private static int b(long j) {
        return (int) j;
    }

    int a(int i, int i2) {
        return i - 1;
    }

    void b(int i) {
    }

    public static <K, V> u<K, V> a() {
        return new u<>();
    }

    public static <K, V> u<K, V> a(int i) {
        return new u<>(i);
    }

    u() {
        a(3, 1.0f);
    }

    u(int i) {
        this(i, 1.0f);
    }

    public u(int i, float f) {
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
        this.f = f(a2);
        this.d = f;
        this.b = new Object[i];
        this.c = new Object[i];
        this.a = g(i);
        this.g = Math.max(1, (int) (a2 * f));
    }

    private static int[] f(int i) {
        int[] iArr = new int[i];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private static long[] g(int i) {
        long[] jArr = new long[i];
        Arrays.fill(jArr, -1L);
        return jArr;
    }

    private int i() {
        return this.f.length - 1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @NullableDecl
    public V put(@NullableDecl K k, @NullableDecl V v) {
        long[] jArr = this.a;
        Object[] objArr = this.b;
        Object[] objArr2 = this.c;
        int a2 = au.a(k);
        int i = i() & a2;
        int i2 = this.h;
        int[] iArr = this.f;
        int i3 = iArr[i];
        if (i3 == -1) {
            iArr[i] = i2;
        } else {
            while (true) {
                long j = jArr[i3];
                if (a(j) != a2 || !Objects.equal(k, objArr[i3])) {
                    int b2 = b(j);
                    if (b2 == -1) {
                        jArr[i3] = a(j, i2);
                        break;
                    }
                    i3 = b2;
                } else {
                    V v2 = (V) objArr2[i3];
                    objArr2[i3] = v;
                    b(i3);
                    return v2;
                }
            }
        }
        if (i2 != Integer.MAX_VALUE) {
            int i4 = i2 + 1;
            h(i4);
            a(i2, k, v, a2);
            this.h = i4;
            if (i2 >= this.g) {
                i(this.f.length * 2);
            }
            this.e++;
            return null;
        }
        throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
    }

    public void a(int i, @NullableDecl K k, @NullableDecl V v, int i2) {
        this.a[i] = (i2 << 32) | 4294967295L;
        this.b[i] = k;
        this.c[i] = v;
    }

    private void h(int i) {
        int length = this.a.length;
        if (i > length) {
            int max = Math.max(1, length >>> 1) + length;
            if (max < 0) {
                max = Integer.MAX_VALUE;
            }
            if (max != length) {
                c(max);
            }
        }
    }

    public void c(int i) {
        this.b = Arrays.copyOf(this.b, i);
        this.c = Arrays.copyOf(this.c, i);
        long[] jArr = this.a;
        int length = jArr.length;
        long[] copyOf = Arrays.copyOf(jArr, i);
        if (i > length) {
            Arrays.fill(copyOf, length, i, -1L);
        }
        this.a = copyOf;
    }

    private void i(int i) {
        if (this.f.length >= 1073741824) {
            this.g = Integer.MAX_VALUE;
            return;
        }
        int i2 = ((int) (i * this.d)) + 1;
        int[] f = f(i);
        long[] jArr = this.a;
        int length = f.length - 1;
        for (int i3 = 0; i3 < this.h; i3++) {
            int a2 = a(jArr[i3]);
            int i4 = a2 & length;
            int i5 = f[i4];
            f[i4] = i3;
            jArr[i3] = (a2 << 32) | (i5 & 4294967295L);
        }
        this.g = i2;
        this.f = f;
    }

    public int a(@NullableDecl Object obj) {
        int a2 = au.a(obj);
        int i = this.f[i() & a2];
        while (i != -1) {
            long j = this.a[i];
            if (a(j) == a2 && Objects.equal(obj, this.b[i])) {
                return i;
            }
            i = b(j);
        }
        return -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@NullableDecl Object obj) {
        return a(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(@NullableDecl Object obj) {
        int a2 = a(obj);
        b(a2);
        if (a2 == -1) {
            return null;
        }
        return (V) this.c[a2];
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @NullableDecl
    public V remove(@NullableDecl Object obj) {
        return a(obj, au.a(obj));
    }

    @NullableDecl
    private V a(@NullableDecl Object obj, int i) {
        int i2 = i() & i;
        int i3 = this.f[i2];
        if (i3 == -1) {
            return null;
        }
        int i4 = -1;
        while (true) {
            if (a(this.a[i3]) != i || !Objects.equal(obj, this.b[i3])) {
                int b2 = b(this.a[i3]);
                if (b2 == -1) {
                    return null;
                }
                i4 = i3;
                i3 = b2;
            } else {
                V v = (V) this.c[i3];
                if (i4 == -1) {
                    this.f[i2] = b(this.a[i3]);
                } else {
                    long[] jArr = this.a;
                    jArr[i4] = a(jArr[i4], b(jArr[i3]));
                }
                d(i3);
                this.h--;
                this.e++;
                return v;
            }
        }
    }

    @CanIgnoreReturnValue
    public V j(int i) {
        return a(this.b[i], a(this.a[i]));
    }

    public void d(int i) {
        int size = size() - 1;
        if (i < size) {
            Object[] objArr = this.b;
            objArr[i] = objArr[size];
            Object[] objArr2 = this.c;
            objArr2[i] = objArr2[size];
            objArr[size] = null;
            objArr2[size] = null;
            long[] jArr = this.a;
            long j = jArr[size];
            jArr[i] = j;
            jArr[size] = -1;
            int a2 = a(j) & i();
            int[] iArr = this.f;
            int i2 = iArr[a2];
            if (i2 == size) {
                iArr[a2] = i;
                return;
            }
            while (true) {
                long j2 = this.a[i2];
                int b2 = b(j2);
                if (b2 == size) {
                    this.a[i2] = a(j2, i);
                    return;
                }
                i2 = b2;
            }
        } else {
            this.b[i] = null;
            this.c[i] = null;
            this.a[i] = -1;
        }
    }

    int b() {
        return isEmpty() ? -1 : 0;
    }

    int e(int i) {
        int i2 = i + 1;
        if (i2 < this.h) {
            return i2;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: CompactHashMap.java */
    /* loaded from: classes2.dex */
    public abstract class b<T> implements Iterator<T> {
        int b;
        int c;
        int d;

        abstract T a(int i);

        private b() {
            u.this = r1;
            this.b = u.this.e;
            this.c = u.this.b();
            this.d = -1;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.c >= 0;
        }

        @Override // java.util.Iterator
        public T next() {
            a();
            if (hasNext()) {
                int i = this.c;
                this.d = i;
                T a = a(i);
                this.c = u.this.e(this.c);
                return a;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            a();
            t.a(this.d >= 0);
            this.b++;
            u.this.j(this.d);
            this.c = u.this.a(this.c, this.d);
            this.d = -1;
        }

        private void a() {
            if (u.this.e != this.b) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.i;
        if (set != null) {
            return set;
        }
        Set<K> c2 = c();
        this.i = c2;
        return c2;
    }

    Set<K> c() {
        return new c();
    }

    /* compiled from: CompactHashMap.java */
    /* loaded from: classes2.dex */
    public class c extends AbstractSet<K> {
        c() {
            u.this = r1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return u.this.h;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return u.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@NullableDecl Object obj) {
            int a = u.this.a(obj);
            if (a == -1) {
                return false;
            }
            u.this.j(a);
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return u.this.d();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            u.this.clear();
        }
    }

    Iterator<K> d() {
        return new u<K, V>.b() { // from class: com.google.common.collect.u.1
            @Override // com.google.common.collect.u.b
            K a(int i) {
                return (K) u.this.b[i];
            }
        };
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.j;
        if (set != null) {
            return set;
        }
        Set<Map.Entry<K, V>> e2 = e();
        this.j = e2;
        return e2;
    }

    Set<Map.Entry<K, V>> e() {
        return new a();
    }

    /* compiled from: CompactHashMap.java */
    /* loaded from: classes2.dex */
    public class a extends AbstractSet<Map.Entry<K, V>> {
        a() {
            u.this = r1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return u.this.h;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            u.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return u.this.f();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int a = u.this.a(entry.getKey());
            return a != -1 && Objects.equal(u.this.c[a], entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int a = u.this.a(entry.getKey());
            if (a == -1 || !Objects.equal(u.this.c[a], entry.getValue())) {
                return false;
            }
            u.this.j(a);
            return true;
        }
    }

    Iterator<Map.Entry<K, V>> f() {
        return new u<K, V>.b() { // from class: com.google.common.collect.u.2
            /* renamed from: b */
            public Map.Entry<K, V> a(int i) {
                return new d(i);
            }
        };
    }

    /* compiled from: CompactHashMap.java */
    /* loaded from: classes2.dex */
    public final class d extends f<K, V> {
        @NullableDecl
        private final K b;
        private int c;

        d(int i) {
            u.this = r1;
            this.b = (K) r1.b[i];
            this.c = i;
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public K getKey() {
            return this.b;
        }

        private void a() {
            int i = this.c;
            if (i == -1 || i >= u.this.size() || !Objects.equal(this.b, u.this.b[this.c])) {
                this.c = u.this.a(this.b);
            }
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public V getValue() {
            a();
            if (this.c == -1) {
                return null;
            }
            return (V) u.this.c[this.c];
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public V setValue(V v) {
            a();
            if (this.c == -1) {
                u.this.put(this.b, v);
                return null;
            }
            V v2 = (V) u.this.c[this.c];
            u.this.c[this.c] = v;
            return v2;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.h;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return this.h == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@NullableDecl Object obj) {
        for (int i = 0; i < this.h; i++) {
            if (Objects.equal(obj, this.c[i])) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        Collection<V> collection = this.k;
        if (collection != null) {
            return collection;
        }
        Collection<V> g = g();
        this.k = g;
        return g;
    }

    Collection<V> g() {
        return new e();
    }

    /* compiled from: CompactHashMap.java */
    /* loaded from: classes2.dex */
    public class e extends AbstractCollection<V> {
        e() {
            u.this = r1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return u.this.h;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            u.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return u.this.h();
        }
    }

    Iterator<V> h() {
        return new u<K, V>.b() { // from class: com.google.common.collect.u.3
            @Override // com.google.common.collect.u.b
            V a(int i) {
                return (V) u.this.c[i];
            }
        };
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.e++;
        Arrays.fill(this.b, 0, this.h, (Object) null);
        Arrays.fill(this.c, 0, this.h, (Object) null);
        Arrays.fill(this.f, -1);
        Arrays.fill(this.a, -1L);
        this.h = 0;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.h);
        for (int i = 0; i < this.h; i++) {
            objectOutputStream.writeObject(this.b[i]);
            objectOutputStream.writeObject(this.c[i]);
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
                put(objectInputStream.readObject(), objectInputStream.readObject());
            } else {
                return;
            }
        }
    }
}
