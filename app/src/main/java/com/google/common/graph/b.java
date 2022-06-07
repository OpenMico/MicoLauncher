package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: AbstractDirectedNetworkConnections.java */
/* loaded from: classes2.dex */
public abstract class b<N, E> implements x<N, E> {
    protected final Map<E, N> a;
    protected final Map<E, N> b;
    private int c;

    public b(Map<E, N> map, Map<E, N> map2, int i) {
        this.a = (Map) Preconditions.checkNotNull(map);
        this.b = (Map) Preconditions.checkNotNull(map2);
        this.c = Graphs.a(i);
        Preconditions.checkState(i <= map.size() && i <= map2.size());
    }

    @Override // com.google.common.graph.x
    public Set<N> a() {
        return Sets.union(e(), f());
    }

    @Override // com.google.common.graph.x
    public Set<E> b() {
        return new AbstractSet<E>() { // from class: com.google.common.graph.b.1
            /* renamed from: a */
            public UnmodifiableIterator<E> iterator() {
                Iterable iterable;
                if (b.this.c == 0) {
                    iterable = Iterables.concat(b.this.a.keySet(), b.this.b.keySet());
                } else {
                    iterable = Sets.union(b.this.a.keySet(), b.this.b.keySet());
                }
                return Iterators.unmodifiableIterator(iterable.iterator());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return IntMath.saturatedAdd(b.this.a.size(), b.this.b.size() - b.this.c);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                return b.this.a.containsKey(obj) || b.this.b.containsKey(obj);
            }
        };
    }

    @Override // com.google.common.graph.x
    public Set<E> c() {
        return Collections.unmodifiableSet(this.a.keySet());
    }

    @Override // com.google.common.graph.x
    public Set<E> d() {
        return Collections.unmodifiableSet(this.b.keySet());
    }

    @Override // com.google.common.graph.x
    public N a(E e) {
        return (N) Preconditions.checkNotNull(this.b.get(e));
    }

    @Override // com.google.common.graph.x
    public N a(E e, boolean z) {
        if (z) {
            int i = this.c - 1;
            this.c = i;
            Graphs.a(i);
        }
        return (N) Preconditions.checkNotNull(this.a.remove(e));
    }

    @Override // com.google.common.graph.x
    public N b(E e) {
        return (N) Preconditions.checkNotNull(this.b.remove(e));
    }

    @Override // com.google.common.graph.x
    public void a(E e, N n, boolean z) {
        boolean z2 = true;
        if (z) {
            int i = this.c + 1;
            this.c = i;
            Graphs.b(i);
        }
        if (this.a.put(e, n) != null) {
            z2 = false;
        }
        Preconditions.checkState(z2);
    }

    @Override // com.google.common.graph.x
    public void a(E e, N n) {
        Preconditions.checkState(this.b.put(e, n) == null);
    }
}
