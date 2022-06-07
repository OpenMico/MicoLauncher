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

/* compiled from: UndirectedMultiNetworkConnections.java */
/* loaded from: classes2.dex */
final class z<N, E> extends d<N, E> {
    @LazyInit
    private transient Reference<Multiset<N>> b;

    private z(Map<E, N> map) {
        super(map);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <N, E> z<N, E> g() {
        return new z<>(new HashMap(2, 1.0f));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <N, E> z<N, E> a(Map<E, N> map) {
        return new z<>(ImmutableMap.copyOf(map));
    }

    @Override // com.google.common.graph.x
    public Set<N> a() {
        return Collections.unmodifiableSet(h().elementSet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Multiset<N> h() {
        Multiset<N> multiset = (Multiset) a((Reference<Object>) this.b);
        if (multiset != null) {
            return multiset;
        }
        HashMultiset create = HashMultiset.create(this.a.values());
        this.b = new SoftReference(create);
        return create;
    }

    @Override // com.google.common.graph.x
    public Set<E> c(final N n) {
        return new w<E>(this.a, n) { // from class: com.google.common.graph.z.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return z.this.h().count(n);
            }
        };
    }

    @Override // com.google.common.graph.d, com.google.common.graph.x
    public N a(E e, boolean z) {
        if (!z) {
            return b(e);
        }
        return null;
    }

    @Override // com.google.common.graph.d, com.google.common.graph.x
    public N b(E e) {
        N n = (N) super.b(e);
        Multiset multiset = (Multiset) a((Reference<Object>) this.b);
        if (multiset != null) {
            Preconditions.checkState(multiset.remove(n));
        }
        return n;
    }

    @Override // com.google.common.graph.d, com.google.common.graph.x
    public void a(E e, N n, boolean z) {
        if (!z) {
            a((z<N, E>) e, (E) n);
        }
    }

    @Override // com.google.common.graph.d, com.google.common.graph.x
    public void a(E e, N n) {
        super.a((z<N, E>) e, (E) n);
        Multiset multiset = (Multiset) a((Reference<Object>) this.b);
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
