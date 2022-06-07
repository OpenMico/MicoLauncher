package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.AbstractMap;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RegularImmutableMap.java */
@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public final class bu<K, V> extends ImmutableMap<K, V> {
    static final ImmutableMap<Object, Object> b = new bu(null, new Object[0], 0);
    private static final long serialVersionUID = 0;
    @VisibleForTesting
    final transient Object[] c;
    private final transient int[] d;
    private final transient int e;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableMap
    public boolean b() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> bu<K, V> a(int i, Object[] objArr) {
        if (i == 0) {
            return (bu) b;
        }
        if (i == 1) {
            t.a(objArr[0], objArr[1]);
            return new bu<>(null, objArr, 1);
        }
        Preconditions.checkPositionIndex(i, objArr.length >> 1);
        return new bu<>(a(objArr, i, ImmutableSet.c(i), 0), objArr, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0034, code lost:
        r12[r7] = r5;
        r3 = r3 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int[] a(java.lang.Object[] r10, int r11, int r12, int r13) {
        /*
            r0 = 1
            if (r11 != r0) goto L_0x000e
            r11 = r10[r13]
            r12 = r13 ^ 1
            r10 = r10[r12]
            com.google.common.collect.t.a(r11, r10)
            r10 = 0
            return r10
        L_0x000e:
            int r1 = r12 + (-1)
            int[] r12 = new int[r12]
            r2 = -1
            java.util.Arrays.fill(r12, r2)
            r3 = 0
        L_0x0017:
            if (r3 >= r11) goto L_0x0079
            int r4 = r3 * 2
            int r5 = r4 + r13
            r6 = r10[r5]
            r7 = r13 ^ 1
            int r4 = r4 + r7
            r4 = r10[r4]
            com.google.common.collect.t.a(r6, r4)
            int r7 = r6.hashCode()
            int r7 = com.google.common.collect.au.a(r7)
        L_0x002f:
            r7 = r7 & r1
            r8 = r12[r7]
            if (r8 != r2) goto L_0x0039
            r12[r7] = r5
            int r3 = r3 + 1
            goto L_0x0017
        L_0x0039:
            r9 = r10[r8]
            boolean r9 = r9.equals(r6)
            if (r9 != 0) goto L_0x0044
            int r7 = r7 + 1
            goto L_0x002f
        L_0x0044:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "Multiple entries with same key: "
            r12.append(r13)
            r12.append(r6)
            java.lang.String r13 = "="
            r12.append(r13)
            r12.append(r4)
            java.lang.String r13 = " and "
            r12.append(r13)
            r13 = r10[r8]
            r12.append(r13)
            java.lang.String r13 = "="
            r12.append(r13)
            r13 = r8 ^ 1
            r10 = r10[r13]
            r12.append(r10)
            java.lang.String r10 = r12.toString()
            r11.<init>(r10)
            throw r11
        L_0x0079:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.bu.a(java.lang.Object[], int, int, int):int[]");
    }

    private bu(int[] iArr, Object[] objArr, int i) {
        this.d = iArr;
        this.c = objArr;
        this.e = i;
    }

    @Override // java.util.Map
    public int size() {
        return this.e;
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    @NullableDecl
    public V get(@NullableDecl Object obj) {
        return (V) a(this.d, this.c, this.e, 0, obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object a(@NullableDecl int[] iArr, @NullableDecl Object[] objArr, int i, int i2, @NullableDecl Object obj) {
        if (obj == null) {
            return null;
        }
        if (i == 1) {
            if (objArr[i2].equals(obj)) {
                return objArr[i2 ^ 1];
            }
            return null;
        } else if (iArr == null) {
            return null;
        } else {
            int length = iArr.length - 1;
            int a2 = au.a(obj.hashCode());
            while (true) {
                int i3 = a2 & length;
                int i4 = iArr[i3];
                if (i4 == -1) {
                    return null;
                }
                if (objArr[i4].equals(obj)) {
                    return objArr[i4 ^ 1];
                }
                a2 = i3 + 1;
            }
        }
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<Map.Entry<K, V>> e() {
        return new a(this, this.c, 0, this.e);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RegularImmutableMap.java */
    /* loaded from: classes2.dex */
    public static class a<K, V> extends ImmutableSet<Map.Entry<K, V>> {
        private final transient ImmutableMap<K, V> a;
        private final transient Object[] b;
        private final transient int c;
        private final transient int d;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(ImmutableMap<K, V> immutableMap, Object[] objArr, int i, int i2) {
            this.a = immutableMap;
            this.b = objArr;
            this.c = i;
            this.d = i2;
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return asList().iterator();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableCollection
        public int a(Object[] objArr, int i) {
            return asList().a(objArr, i);
        }

        @Override // com.google.common.collect.ImmutableSet
        ImmutableList<Map.Entry<K, V>> f() {
            return new ImmutableList<Map.Entry<K, V>>() { // from class: com.google.common.collect.bu.a.1
                @Override // com.google.common.collect.ImmutableCollection
                public boolean a() {
                    return true;
                }

                /* renamed from: a */
                public Map.Entry<K, V> get(int i) {
                    Preconditions.checkElementIndex(i, a.this.d);
                    int i2 = i * 2;
                    return new AbstractMap.SimpleImmutableEntry(a.this.b[a.this.c + i2], a.this.b[i2 + (a.this.c ^ 1)]);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return a.this.d;
                }
            };
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            return value != null && value.equals(this.a.get(key));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.d;
        }
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<K> c() {
        return new b(this, new c(this.c, 0, this.e));
    }

    /* compiled from: RegularImmutableMap.java */
    /* loaded from: classes2.dex */
    static final class c extends ImmutableList<Object> {
        private final transient Object[] a;
        private final transient int b;
        private final transient int c;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public c(Object[] objArr, int i, int i2) {
            this.a = objArr;
            this.b = i;
            this.c = i2;
        }

        @Override // java.util.List
        public Object get(int i) {
            Preconditions.checkElementIndex(i, this.c);
            return this.a[(i * 2) + this.b];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.c;
        }
    }

    /* compiled from: RegularImmutableMap.java */
    /* loaded from: classes2.dex */
    static final class b<K> extends ImmutableSet<K> {
        private final transient ImmutableMap<K, ?> a;
        private final transient ImmutableList<K> b;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(ImmutableMap<K, ?> immutableMap, ImmutableList<K> immutableList) {
            this.a = immutableMap;
            this.b = immutableList;
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public UnmodifiableIterator<K> iterator() {
            return asList().iterator();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableCollection
        public int a(Object[] objArr, int i) {
            return asList().a(objArr, i);
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        public ImmutableList<K> asList() {
            return this.b;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            return this.a.get(obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.a.size();
        }
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableCollection<V> d() {
        return new c(this.c, 1, this.e);
    }
}
