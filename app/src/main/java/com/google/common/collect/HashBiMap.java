package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public final class HashBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V>, Serializable {
    transient K[] a;
    transient V[] b;
    transient int c;
    transient int d;
    private transient int[] e;
    private transient int[] f;
    private transient int[] g;
    private transient int[] h;
    @NullableDecl
    private transient int i;
    @NullableDecl
    private transient int j;
    private transient int[] k;
    private transient int[] l;
    private transient Set<K> m;
    private transient Set<V> n;
    private transient Set<Map.Entry<K, V>> o;
    @RetainedWith
    @MonotonicNonNullDecl
    private transient BiMap<V, K> p;

    public static <K, V> HashBiMap<K, V> create() {
        return create(16);
    }

    public static <K, V> HashBiMap<K, V> create(int i) {
        return new HashBiMap<>(i);
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> create = create(map.size());
        create.putAll(map);
        return create;
    }

    private HashBiMap(int i) {
        a(i);
    }

    void a(int i) {
        t.a(i, "expectedSize");
        int a2 = au.a(i, 1.0d);
        this.c = 0;
        this.a = (K[]) new Object[i];
        this.b = (V[]) new Object[i];
        this.e = c(a2);
        this.f = c(a2);
        this.g = c(i);
        this.h = c(i);
        this.i = -2;
        this.j = -2;
        this.k = c(i);
        this.l = c(i);
    }

    private static int[] c(int i) {
        int[] iArr = new int[i];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private static int[] a(int[] iArr, int i) {
        int length = iArr.length;
        int[] copyOf = Arrays.copyOf(iArr, i);
        Arrays.fill(copyOf, length, i, -1);
        return copyOf;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.c;
    }

    private void d(int i) {
        int[] iArr = this.g;
        if (iArr.length < i) {
            int a2 = ImmutableCollection.Builder.a(iArr.length, i);
            this.a = (K[]) Arrays.copyOf(this.a, a2);
            this.b = (V[]) Arrays.copyOf(this.b, a2);
            this.g = a(this.g, a2);
            this.h = a(this.h, a2);
            this.k = a(this.k, a2);
            this.l = a(this.l, a2);
        }
        if (this.e.length < i) {
            int a3 = au.a(i, 1.0d);
            this.e = c(a3);
            this.f = c(a3);
            for (int i2 = 0; i2 < this.c; i2++) {
                int e2 = e(au.a(this.a[i2]));
                int[] iArr2 = this.g;
                int[] iArr3 = this.e;
                iArr2[i2] = iArr3[e2];
                iArr3[e2] = i2;
                int e3 = e(au.a(this.b[i2]));
                int[] iArr4 = this.h;
                int[] iArr5 = this.f;
                iArr4[i2] = iArr5[e3];
                iArr5[e3] = i2;
            }
        }
    }

    private int e(int i) {
        return i & (this.e.length - 1);
    }

    int a(@NullableDecl Object obj) {
        return a(obj, au.a(obj));
    }

    int a(@NullableDecl Object obj, int i) {
        return a(obj, i, this.e, this.g, this.a);
    }

    int b(@NullableDecl Object obj) {
        return b(obj, au.a(obj));
    }

    int b(@NullableDecl Object obj, int i) {
        return a(obj, i, this.f, this.h, this.b);
    }

    int a(@NullableDecl Object obj, int i, int[] iArr, int[] iArr2, Object[] objArr) {
        int i2 = iArr[e(i)];
        while (i2 != -1) {
            if (Objects.equal(objArr[i2], obj)) {
                return i2;
            }
            i2 = iArr2[i2];
        }
        return -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@NullableDecl Object obj) {
        return a(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@NullableDecl Object obj) {
        return b(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @NullableDecl
    public V get(@NullableDecl Object obj) {
        int a2 = a(obj);
        if (a2 == -1) {
            return null;
        }
        return this.b[a2];
    }

    @NullableDecl
    K c(@NullableDecl Object obj) {
        int b2 = b(obj);
        if (b2 == -1) {
            return null;
        }
        return this.a[b2];
    }

    @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    public V put(@NullableDecl K k, @NullableDecl V v) {
        return a((HashBiMap<K, V>) k, (K) v, false);
    }

    @NullableDecl
    V a(@NullableDecl K k, @NullableDecl V v, boolean z) {
        int a2 = au.a(k);
        int a3 = a(k, a2);
        if (a3 != -1) {
            V v2 = this.b[a3];
            if (Objects.equal(v2, v)) {
                return v;
            }
            a(a3, (int) v, z);
            return v2;
        }
        int a4 = au.a(v);
        int b2 = b(v, a4);
        if (!z) {
            Preconditions.checkArgument(b2 == -1, "Value already present: %s", v);
        } else if (b2 != -1) {
            b(b2, a4);
        }
        d(this.c + 1);
        K[] kArr = this.a;
        int i = this.c;
        kArr[i] = k;
        this.b[i] = v;
        d(i, a2);
        e(this.c, a4);
        c(this.j, this.c);
        c(this.c, -2);
        this.c++;
        this.d++;
        return null;
    }

    @Override // com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    @NullableDecl
    public V forcePut(@NullableDecl K k, @NullableDecl V v) {
        return a((HashBiMap<K, V>) k, (K) v, true);
    }

    @NullableDecl
    K b(@NullableDecl V v, @NullableDecl K k, boolean z) {
        int a2 = au.a(v);
        int b2 = b(v, a2);
        if (b2 != -1) {
            K k2 = this.a[b2];
            if (Objects.equal(k2, k)) {
                return k;
            }
            b(b2, (int) k, z);
            return k2;
        }
        int i = this.j;
        int a3 = au.a(k);
        int a4 = a(k, a3);
        if (!z) {
            Preconditions.checkArgument(a4 == -1, "Key already present: %s", k);
        } else if (a4 != -1) {
            i = this.k[a4];
            a(a4, a3);
        }
        d(this.c + 1);
        K[] kArr = this.a;
        int i2 = this.c;
        kArr[i2] = k;
        this.b[i2] = v;
        d(i2, a3);
        e(this.c, a2);
        int i3 = i == -2 ? this.i : this.l[i];
        c(i, this.c);
        c(this.c, i3);
        this.c++;
        this.d++;
        return null;
    }

    private void c(int i, int i2) {
        if (i == -2) {
            this.i = i2;
        } else {
            this.l[i] = i2;
        }
        if (i2 == -2) {
            this.j = i;
        } else {
            this.k[i2] = i;
        }
    }

    private void d(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int e2 = e(i2);
        int[] iArr = this.g;
        int[] iArr2 = this.e;
        iArr[i] = iArr2[e2];
        iArr2[e2] = i;
    }

    private void e(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int e2 = e(i2);
        int[] iArr = this.h;
        int[] iArr2 = this.f;
        iArr[i] = iArr2[e2];
        iArr2[e2] = i;
    }

    private void f(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int e2 = e(i2);
        int[] iArr = this.e;
        if (iArr[e2] == i) {
            int[] iArr2 = this.g;
            iArr[e2] = iArr2[i];
            iArr2[i] = -1;
            return;
        }
        int i3 = iArr[e2];
        int i4 = this.g[i3];
        int i5 = i3;
        while (i4 != -1) {
            if (i4 == i) {
                int[] iArr3 = this.g;
                iArr3[i5] = iArr3[i];
                iArr3[i] = -1;
                return;
            }
            i4 = this.g[i4];
            i5 = i4;
        }
        throw new AssertionError("Expected to find entry with key " + this.a[i]);
    }

    private void g(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int e2 = e(i2);
        int[] iArr = this.f;
        if (iArr[e2] == i) {
            int[] iArr2 = this.h;
            iArr[e2] = iArr2[i];
            iArr2[i] = -1;
            return;
        }
        int i3 = iArr[e2];
        int i4 = this.h[i3];
        int i5 = i3;
        while (i4 != -1) {
            if (i4 == i) {
                int[] iArr3 = this.h;
                iArr3[i5] = iArr3[i];
                iArr3[i] = -1;
                return;
            }
            i4 = this.h[i4];
            i5 = i4;
        }
        throw new AssertionError("Expected to find entry with value " + this.b[i]);
    }

    public void a(int i, @NullableDecl V v, boolean z) {
        Preconditions.checkArgument(i != -1);
        int a2 = au.a(v);
        int b2 = b(v, a2);
        if (b2 != -1) {
            if (z) {
                b(b2, a2);
                if (i == this.c) {
                    i = b2;
                }
            } else {
                throw new IllegalArgumentException("Value already present in map: " + v);
            }
        }
        g(i, au.a(this.b[i]));
        this.b[i] = v;
        e(i, a2);
    }

    public void b(int i, @NullableDecl K k, boolean z) {
        int i2;
        Preconditions.checkArgument(i != -1);
        int a2 = au.a(k);
        int a3 = a(k, a2);
        int i3 = this.j;
        int i4 = -2;
        if (a3 == -1) {
            i2 = i3;
        } else if (z) {
            i2 = this.k[a3];
            i4 = this.l[a3];
            a(a3, a2);
            if (i == this.c) {
                i = a3;
            }
        } else {
            throw new IllegalArgumentException("Key already present in map: " + k);
        }
        if (i2 == i) {
            i2 = this.k[i];
        } else if (i2 == this.c) {
            i2 = a3;
        }
        if (i4 == i) {
            a3 = this.l[i];
        } else if (i4 != this.c) {
            a3 = i4;
        }
        c(this.k[i], this.l[i]);
        f(i, au.a(this.a[i]));
        this.a[i] = k;
        d(i, au.a(k));
        c(i2, i);
        c(i, a3);
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @NullableDecl
    public V remove(@NullableDecl Object obj) {
        int a2 = au.a(obj);
        int a3 = a(obj, a2);
        if (a3 == -1) {
            return null;
        }
        V v = this.b[a3];
        a(a3, a2);
        return v;
    }

    @NullableDecl
    K d(@NullableDecl Object obj) {
        int a2 = au.a(obj);
        int b2 = b(obj, a2);
        if (b2 == -1) {
            return null;
        }
        K k = this.a[b2];
        b(b2, a2);
        return k;
    }

    void b(int i) {
        a(i, au.a(this.a[i]));
    }

    private void a(int i, int i2, int i3) {
        Preconditions.checkArgument(i != -1);
        f(i, i2);
        g(i, i3);
        c(this.k[i], this.l[i]);
        h(this.c - 1, i);
        K[] kArr = this.a;
        int i4 = this.c;
        kArr[i4 - 1] = null;
        this.b[i4 - 1] = null;
        this.c = i4 - 1;
        this.d++;
    }

    void a(int i, int i2) {
        a(i, i2, au.a(this.b[i]));
    }

    void b(int i, int i2) {
        a(i, au.a(this.a[i]), i2);
    }

    private void h(int i, int i2) {
        if (i != i2) {
            int i3 = this.k[i];
            int i4 = this.l[i];
            c(i3, i2);
            c(i2, i4);
            K[] kArr = this.a;
            K k = kArr[i];
            V[] vArr = this.b;
            V v = vArr[i];
            kArr[i2] = k;
            vArr[i2] = v;
            int e2 = e(au.a(k));
            int[] iArr = this.e;
            if (iArr[e2] == i) {
                iArr[e2] = i2;
            } else {
                int i5 = iArr[e2];
                int i6 = this.g[i5];
                int i7 = i5;
                while (i6 != i) {
                    i6 = this.g[i6];
                    i7 = i6;
                }
                this.g[i7] = i2;
            }
            int[] iArr2 = this.g;
            iArr2[i2] = iArr2[i];
            iArr2[i] = -1;
            int e3 = e(au.a(v));
            int[] iArr3 = this.f;
            if (iArr3[e3] == i) {
                iArr3[e3] = i2;
            } else {
                int i8 = iArr3[e3];
                int i9 = this.h[i8];
                int i10 = i8;
                while (i9 != i) {
                    i9 = this.h[i9];
                    i10 = i9;
                }
                this.h[i10] = i2;
            }
            int[] iArr4 = this.h;
            iArr4[i2] = iArr4[i];
            iArr4[i] = -1;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Arrays.fill(this.a, 0, this.c, (Object) null);
        Arrays.fill(this.b, 0, this.c, (Object) null);
        Arrays.fill(this.e, -1);
        Arrays.fill(this.f, -1);
        Arrays.fill(this.g, 0, this.c, -1);
        Arrays.fill(this.h, 0, this.c, -1);
        Arrays.fill(this.k, 0, this.c, -1);
        Arrays.fill(this.l, 0, this.c, -1);
        this.c = 0;
        this.i = -2;
        this.j = -2;
        this.d++;
    }

    /* loaded from: classes2.dex */
    public static abstract class h<K, V, T> extends AbstractSet<T> {
        final HashBiMap<K, V> b;

        abstract T b(int i);

        h(HashBiMap<K, V> hashBiMap) {
            this.b = hashBiMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<T> iterator() {
            return new Iterator<T>() { // from class: com.google.common.collect.HashBiMap.h.1
                private int b;
                private int c = -1;
                private int d;
                private int e;

                {
                    h.this = this;
                    this.b = ((HashBiMap) h.this.b).i;
                    this.d = h.this.b.d;
                    this.e = h.this.b.c;
                }

                private void a() {
                    if (h.this.b.d != this.d) {
                        throw new ConcurrentModificationException();
                    }
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    a();
                    return this.b != -2 && this.e > 0;
                }

                @Override // java.util.Iterator
                public T next() {
                    if (hasNext()) {
                        T t = (T) h.this.b(this.b);
                        this.c = this.b;
                        this.b = ((HashBiMap) h.this.b).l[this.b];
                        this.e--;
                        return t;
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    a();
                    t.a(this.c != -1);
                    h.this.b.b(this.c);
                    if (this.b == h.this.b.c) {
                        this.b = this.c;
                    }
                    this.c = -1;
                    this.d = h.this.b.d;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.b.c;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            this.b.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.m;
        if (set != null) {
            return set;
        }
        f fVar = new f();
        this.m = fVar;
        return fVar;
    }

    /* loaded from: classes2.dex */
    public final class f extends h<K, V, K> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        f() {
            super(r1);
            HashBiMap.this = r1;
        }

        @Override // com.google.common.collect.HashBiMap.h
        K b(int i) {
            return HashBiMap.this.a[i];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            return HashBiMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@NullableDecl Object obj) {
            int a = au.a(obj);
            int a2 = HashBiMap.this.a(obj, a);
            if (a2 == -1) {
                return false;
            }
            HashBiMap.this.a(a2, a);
            return true;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
    public Set<V> values() {
        Set<V> set = this.n;
        if (set != null) {
            return set;
        }
        g gVar = new g();
        this.n = gVar;
        return gVar;
    }

    /* loaded from: classes2.dex */
    public final class g extends h<K, V, V> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        g() {
            super(r1);
            HashBiMap.this = r1;
        }

        @Override // com.google.common.collect.HashBiMap.h
        V b(int i) {
            return HashBiMap.this.b[i];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            return HashBiMap.this.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@NullableDecl Object obj) {
            int a = au.a(obj);
            int b = HashBiMap.this.b(obj, a);
            if (b == -1) {
                return false;
            }
            HashBiMap.this.b(b, a);
            return true;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.o;
        if (set != null) {
            return set;
        }
        c cVar = new c();
        this.o = cVar;
        return cVar;
    }

    /* loaded from: classes2.dex */
    final class c extends h<K, V, Map.Entry<K, V>> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        c() {
            super(r1);
            HashBiMap.this = r1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int a = HashBiMap.this.a(key);
            return a != -1 && Objects.equal(value, HashBiMap.this.b[a]);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        @CanIgnoreReturnValue
        public boolean remove(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int a = au.a(key);
            int a2 = HashBiMap.this.a(key, a);
            if (a2 == -1 || !Objects.equal(value, HashBiMap.this.b[a2])) {
                return false;
            }
            HashBiMap.this.a(a2, a);
            return true;
        }

        /* renamed from: a */
        public Map.Entry<K, V> b(int i) {
            return new a(i);
        }
    }

    /* loaded from: classes2.dex */
    public final class a extends f<K, V> {
        @NullableDecl
        final K a;
        int b;

        a(int i) {
            HashBiMap.this = r1;
            this.a = r1.a[i];
            this.b = i;
        }

        void a() {
            int i = this.b;
            if (i == -1 || i > HashBiMap.this.c || !Objects.equal(HashBiMap.this.a[this.b], this.a)) {
                this.b = HashBiMap.this.a(this.a);
            }
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public K getKey() {
            return this.a;
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        @NullableDecl
        public V getValue() {
            a();
            if (this.b == -1) {
                return null;
            }
            return HashBiMap.this.b[this.b];
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public V setValue(V v) {
            a();
            if (this.b == -1) {
                return (V) HashBiMap.this.put(this.a, v);
            }
            V v2 = HashBiMap.this.b[this.b];
            if (Objects.equal(v2, v)) {
                return v;
            }
            HashBiMap.this.a(this.b, (int) v, false);
            return v2;
        }
    }

    @Override // com.google.common.collect.BiMap
    public BiMap<V, K> inverse() {
        BiMap<V, K> biMap = this.p;
        if (biMap != null) {
            return biMap;
        }
        d dVar = new d(this);
        this.p = dVar;
        return dVar;
    }

    /* loaded from: classes2.dex */
    static class d<K, V> extends AbstractMap<V, K> implements BiMap<V, K>, Serializable {
        private transient Set<Map.Entry<V, K>> a;
        private final HashBiMap<K, V> forward;

        d(HashBiMap<K, V> hashBiMap) {
            this.forward = hashBiMap;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.forward.c;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return this.forward.containsValue(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @NullableDecl
        public K get(@NullableDecl Object obj) {
            return this.forward.c(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsValue(@NullableDecl Object obj) {
            return this.forward.containsKey(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
        @CanIgnoreReturnValue
        @NullableDecl
        public K put(@NullableDecl V v, @NullableDecl K k) {
            return this.forward.b((HashBiMap<K, V>) v, (V) k, false);
        }

        @Override // com.google.common.collect.BiMap
        @CanIgnoreReturnValue
        @NullableDecl
        public K forcePut(@NullableDecl V v, @NullableDecl K k) {
            return this.forward.b((HashBiMap<K, V>) v, (V) k, true);
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<K, V> inverse() {
            return this.forward;
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CanIgnoreReturnValue
        @NullableDecl
        public K remove(@NullableDecl Object obj) {
            return this.forward.d(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            this.forward.clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<V> keySet() {
            return this.forward.values();
        }

        @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
        public Set<K> values() {
            return this.forward.keySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<V, K>> entrySet() {
            Set<Map.Entry<V, K>> set = this.a;
            if (set != null) {
                return set;
            }
            e eVar = new e(this.forward);
            this.a = eVar;
            return eVar;
        }

        @GwtIncompatible("serialization")
        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            ((HashBiMap) this.forward).p = this;
        }
    }

    /* loaded from: classes2.dex */
    static class e<K, V> extends h<K, V, Map.Entry<V, K>> {
        e(HashBiMap<K, V> hashBiMap) {
            super(hashBiMap);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int b = this.b.b(key);
            return b != -1 && Objects.equal(this.b.a[b], value);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int a = au.a(key);
            int b = this.b.b(key, a);
            if (b == -1 || !Objects.equal(this.b.a[b], value)) {
                return false;
            }
            this.b.b(b, a);
            return true;
        }

        /* renamed from: a */
        public Map.Entry<V, K> b(int i) {
            return new b(this.b, i);
        }
    }

    /* loaded from: classes2.dex */
    public static final class b<K, V> extends f<V, K> {
        final HashBiMap<K, V> a;
        final V b;
        int c;

        b(HashBiMap<K, V> hashBiMap, int i) {
            this.a = hashBiMap;
            this.b = hashBiMap.b[i];
            this.c = i;
        }

        private void a() {
            int i = this.c;
            if (i == -1 || i > this.a.c || !Objects.equal(this.b, this.a.b[this.c])) {
                this.c = this.a.b(this.b);
            }
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public V getKey() {
            return this.b;
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public K getValue() {
            a();
            if (this.c == -1) {
                return null;
            }
            return this.a.a[this.c];
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public K setValue(K k) {
            a();
            if (this.c == -1) {
                return this.a.b((HashBiMap<K, V>) this.b, (V) k, false);
            }
            K k2 = this.a.a[this.c];
            if (Objects.equal(k2, k)) {
                return k;
            }
            this.a.b(this.c, (int) k, false);
            return k2;
        }
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        cc.a(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int a2 = cc.a(objectInputStream);
        a(16);
        cc.a(this, objectInputStream, a2);
    }
}
