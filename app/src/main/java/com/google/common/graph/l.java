package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DirectedMultiNetworkConnections.java */
/* loaded from: classes2.dex */
public final class l<N, E> extends b<N, E> {
    @LazyInit
    private transient Reference<Multiset<N>> c;
    @LazyInit
    private transient Reference<Multiset<N>> d;

    private l(Map<E, N> map, Map<E, N> map2, int i) {
        super(map, map2, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <N, E> l<N, E> g() {
        return new l<>(new HashMap(2, 1.0f), new HashMap(2, 1.0f), 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <N, E> l<N, E> a(Map<E, N> map, Map<E, N> map2, int i) {
        return new l<>(ImmutableMap.copyOf(map), ImmutableMap.copyOf(map2), i);
    }

    @Override // com.google.common.graph.x
    public Set<N> e() {
        return Collections.unmodifiableSet(h().elementSet());
    }

    private Multiset<N> h() {
        Multiset<N> multiset = (Multiset) a((Reference<Object>) this.c);
        if (multiset != null) {
            return multiset;
        }
        HashMultiset create = HashMultiset.create(this.a.values());
        this.c = new SoftReference(create);
        return create;
    }

    @Override // com.google.common.graph.x
    public Set<N> f() {
        return Collections.unmodifiableSet(i().elementSet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Multiset<N> i() {
        Multiset<N> multiset = (Multiset) a((Reference<Object>) this.d);
        if (multiset != null) {
            return multiset;
        }
        HashMultiset create = HashMultiset.create(this.b.values());
        this.d = new SoftReference(create);
        return create;
    }

    @Override // com.google.common.graph.x
    public Set<E> c(final N n) {
        return new w<E>(this.b, n) { // from class: com.google.common.graph.l.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return l.this.i().count(n);
            }
        };
    }

    @Override // com.google.common.graph.b, com.google.common.graph.x
    public N a(E e, boolean z) {
        N n = (N) super.a((l<N, E>) e, z);
        Multiset multiset = (Multiset) a((Reference<Object>) this.c);
        if (multiset != null) {
            Preconditions.checkState(multiset.remove(n));
        }
        return n;
    }

    @Override // com.google.common.graph.b, com.google.common.graph.x
    public N b(E e) {
        N n = (N) super.b(e);
        Multiset multiset = (Multiset) a((Reference<Object>) this.d);
        if (multiset != null) {
            Preconditions.checkState(multiset.remove(n));
        }
        return n;
    }

    @Override // com.google.common.graph.b, com.google.common.graph.x
    public void a(E e, N n, boolean z) {
        super.a((l<N, E>) e, (E) n, z);
        Multiset multiset = (Multiset) a((Reference<Object>) this.c);
        if (multiset != null) {
            Preconditions.checkState(multiset.add(n));
        }
    }

    @Override // com.google.common.graph.b, com.google.common.graph.x
    public void a(E e, N n) {
        super.a((l<N, E>) e, (E) n);
        Multiset multiset = (Multiset) a((Reference<Object>) this.d);
        if (multiset != null) {
            Preconditions.checkState(multiset.add(n));
        }
    }

    @NullableDecl
    private static <T> T a(@NullableDecl Reference<T> reference) {
        if (reference == null) {
            return null;
        }
        return reference.get();
    }
}
