package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: ConfigurableValueGraph.java */
/* loaded from: classes2.dex */
class j<N, V> extends AbstractValueGraph<N, V> {
    private final boolean a;
    private final boolean b;
    private final ElementOrder<N> c;
    protected long edgeCount;
    protected final u<N, s<N, V>> nodeConnections;

    /* JADX INFO: Access modifiers changed from: package-private */
    public j(c<? super N> cVar) {
        this(cVar, cVar.c.a(cVar.d.or((Optional<Integer>) 10).intValue()), 0L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public j(c<? super N> cVar, Map<N, s<N, V>> map, long j) {
        this.a = cVar.a;
        this.b = cVar.b;
        this.c = (ElementOrder<N>) cVar.c.a();
        this.nodeConnections = map instanceof TreeMap ? new v<>(map) : new u<>(map);
        this.edgeCount = Graphs.a(j);
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public Set<N> nodes() {
        return this.nodeConnections.a();
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public boolean isDirected() {
        return this.a;
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public boolean allowsSelfLoops() {
        return this.b;
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public ElementOrder<N> nodeOrder() {
        return this.c;
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public Set<N> adjacentNodes(N n) {
        return checkedConnections(n).b();
    }

    @Override // com.google.common.graph.e, com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
    public Set<N> predecessors(N n) {
        return checkedConnections(n).c();
    }

    @Override // com.google.common.graph.e, com.google.common.graph.SuccessorsFunction, com.google.common.graph.Graph
    public Set<N> successors(N n) {
        return checkedConnections(n).d();
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public boolean hasEdgeConnecting(N n, N n2) {
        Preconditions.checkNotNull(n);
        Preconditions.checkNotNull(n2);
        s<N, V> b = this.nodeConnections.b(n);
        return b != null && b.d().contains(n2);
    }

    @NullableDecl
    public V edgeValueOrDefault(N n, N n2, @NullableDecl V v) {
        Preconditions.checkNotNull(n);
        Preconditions.checkNotNull(n2);
        s<N, V> b = this.nodeConnections.b(n);
        V a = b == null ? null : b.a(n2);
        return a == null ? v : a;
    }

    @Override // com.google.common.graph.a
    protected long edgeCount() {
        return this.edgeCount;
    }

    protected final s<N, V> checkedConnections(N n) {
        s<N, V> b = this.nodeConnections.b(n);
        if (b != null) {
            return b;
        }
        Preconditions.checkNotNull(n);
        throw new IllegalArgumentException("Node " + n + " is not an element of this graph.");
    }

    protected final boolean containsNode(@NullableDecl N n) {
        return this.nodeConnections.d(n);
    }
}
