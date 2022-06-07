package com.google.common.graph;

import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: ForwardingValueGraph.java */
/* loaded from: classes2.dex */
abstract class r<N, V> extends AbstractValueGraph<N, V> {
    protected abstract ValueGraph<N, V> a();

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public Set<N> nodes() {
        return a().nodes();
    }

    @Override // com.google.common.graph.a
    protected long edgeCount() {
        return a().edges().size();
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public boolean isDirected() {
        return a().isDirected();
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public boolean allowsSelfLoops() {
        return a().allowsSelfLoops();
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public ElementOrder<N> nodeOrder() {
        return a().nodeOrder();
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public Set<N> adjacentNodes(N n) {
        return a().adjacentNodes(n);
    }

    @Override // com.google.common.graph.e, com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
    public Set<N> predecessors(N n) {
        return a().predecessors((ValueGraph<N, V>) n);
    }

    @Override // com.google.common.graph.e, com.google.common.graph.SuccessorsFunction, com.google.common.graph.Graph
    public Set<N> successors(N n) {
        return a().successors((ValueGraph<N, V>) n);
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public int degree(N n) {
        return a().degree(n);
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public int inDegree(N n) {
        return a().inDegree(n);
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public int outDegree(N n) {
        return a().outDegree(n);
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public boolean hasEdgeConnecting(N n, N n2) {
        return a().hasEdgeConnecting(n, n2);
    }

    @NullableDecl
    public V edgeValueOrDefault(N n, N n2, @NullableDecl V v) {
        return a().edgeValueOrDefault(n, n2, v);
    }
}
