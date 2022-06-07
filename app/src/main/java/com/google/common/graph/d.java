package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/* compiled from: AbstractUndirectedNetworkConnections.java */
/* loaded from: classes2.dex */
abstract class d<N, E> implements x<N, E> {
    protected final Map<E, N> a;

    /* JADX INFO: Access modifiers changed from: protected */
    public d(Map<E, N> map) {
        this.a = (Map) Preconditions.checkNotNull(map);
    }

    @Override // com.google.common.graph.x
    public Set<N> e() {
        return a();
    }

    @Override // com.google.common.graph.x
    public Set<N> f() {
        return a();
    }

    @Override // com.google.common.graph.x
    public Set<E> b() {
        return Collections.unmodifiableSet(this.a.keySet());
    }

    @Override // com.google.common.graph.x
    public Set<E> c() {
        return b();
    }

    @Override // com.google.common.graph.x
    public Set<E> d() {
        return b();
    }

    @Override // com.google.common.graph.x
    public N a(E e) {
        return (N) Preconditions.checkNotNull(this.a.get(e));
    }

    @Override // com.google.common.graph.x
    public N a(E e, boolean z) {
        if (!z) {
            return b(e);
        }
        return null;
    }

    @Override // com.google.common.graph.x
    public N b(E e) {
        return (N) Preconditions.checkNotNull(this.a.remove(e));
    }

    @Override // com.google.common.graph.x
    public void a(E e, N n, boolean z) {
        if (!z) {
            a((d<N, E>) e, (E) n);
        }
    }

    @Override // com.google.common.graph.x
    public void a(E e, N n) {
        Preconditions.checkState(this.a.put(e, n) == null);
    }
}
