package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DirectedGraphConnections.java */
/* loaded from: classes2.dex */
public final class k<N, V> implements s<N, V> {
    private static final Object a = new Object();
    private final Map<N, Object> b;
    private int c;
    private int d;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DirectedGraphConnections.java */
    /* loaded from: classes2.dex */
    public static final class a {
        private final Object a;

        a(Object obj) {
            this.a = obj;
        }
    }

    private k(Map<N, Object> map, int i, int i2) {
        this.b = (Map) Preconditions.checkNotNull(map);
        this.c = Graphs.a(i);
        this.d = Graphs.a(i2);
        Preconditions.checkState(i <= map.size() && i2 <= map.size());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <N, V> k<N, V> a() {
        return new k<>(new HashMap(4, 1.0f), 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static <N, V> k<N, V> a(Set<N> set, Map<N, V> map) {
        HashMap hashMap = new HashMap();
        hashMap.putAll(map);
        for (N n : set) {
            Object put = hashMap.put(n, a);
            if (put != null) {
                hashMap.put(n, new a(put));
            }
        }
        return new k<>(ImmutableMap.copyOf(hashMap), set.size(), map.size());
    }

    @Override // com.google.common.graph.s
    public Set<N> b() {
        return Collections.unmodifiableSet(this.b.keySet());
    }

    @Override // com.google.common.graph.s
    public Set<N> c() {
        return new AbstractSet<N>() { // from class: com.google.common.graph.k.1
            /* renamed from: a */
            public UnmodifiableIterator<N> iterator() {
                final Iterator it = k.this.b.entrySet().iterator();
                return new AbstractIterator<N>() { // from class: com.google.common.graph.k.1.1
                    @Override // com.google.common.collect.AbstractIterator
                    protected N computeNext() {
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            if (k.f(entry.getValue())) {
                                return (N) entry.getKey();
                            }
                        }
                        return endOfData();
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return k.this.c;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                return k.f(k.this.b.get(obj));
            }
        };
    }

    @Override // com.google.common.graph.s
    public Set<N> d() {
        return new AbstractSet<N>() { // from class: com.google.common.graph.k.2
            /* renamed from: a */
            public UnmodifiableIterator<N> iterator() {
                final Iterator it = k.this.b.entrySet().iterator();
                return new AbstractIterator<N>() { // from class: com.google.common.graph.k.2.1
                    @Override // com.google.common.collect.AbstractIterator
                    protected N computeNext() {
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            if (k.g(entry.getValue())) {
                                return (N) entry.getKey();
                            }
                        }
                        return endOfData();
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return k.this.d;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                return k.g(k.this.b.get(obj));
            }
        };
    }

    @Override // com.google.common.graph.s
    public V a(N n) {
        V v = (V) this.b.get(n);
        if (v == a) {
            return null;
        }
        return v instanceof a ? (V) ((a) v).a : v;
    }

    @Override // com.google.common.graph.s
    public void b(N n) {
        Object obj = this.b.get(n);
        if (obj == a) {
            this.b.remove(n);
            int i = this.c - 1;
            this.c = i;
            Graphs.a(i);
        } else if (obj instanceof a) {
            this.b.put(n, ((a) obj).a);
            int i2 = this.c - 1;
            this.c = i2;
            Graphs.a(i2);
        }
    }

    @Override // com.google.common.graph.s
    public V c(Object obj) {
        Object obj2;
        V v = (V) this.b.get(obj);
        if (v == null || v == (obj2 = a)) {
            return null;
        }
        if (v instanceof a) {
            this.b.put(obj, obj2);
            int i = this.d - 1;
            this.d = i;
            Graphs.a(i);
            return (V) ((a) v).a;
        }
        this.b.remove(obj);
        int i2 = this.d - 1;
        this.d = i2;
        Graphs.a(i2);
        return v;
    }

    @Override // com.google.common.graph.s
    public void a(N n, V v) {
        Object put = this.b.put(n, a);
        if (put == null) {
            int i = this.c + 1;
            this.c = i;
            Graphs.b(i);
        } else if (put instanceof a) {
            this.b.put(n, put);
        } else if (put != a) {
            this.b.put(n, new a(put));
            int i2 = this.c + 1;
            this.c = i2;
            Graphs.b(i2);
        }
    }

    @Override // com.google.common.graph.s
    public V b(N n, V v) {
        V v2 = (V) this.b.put(n, v);
        if (v2 == null) {
            int i = this.d + 1;
            this.d = i;
            Graphs.b(i);
            return null;
        } else if (v2 instanceof a) {
            this.b.put(n, new a(v));
            return (V) ((a) v2).a;
        } else if (v2 != a) {
            return v2;
        } else {
            this.b.put(n, new a(v));
            int i2 = this.d + 1;
            this.d = i2;
            Graphs.b(i2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean f(@NullableDecl Object obj) {
        return obj == a || (obj instanceof a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean g(@NullableDecl Object obj) {
        return (obj == a || obj == null) ? false : true;
    }
}
