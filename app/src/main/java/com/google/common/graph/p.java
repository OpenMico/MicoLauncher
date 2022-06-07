package com.google.common.graph;

import java.util.Set;

/* compiled from: ForwardingGraph.java */
/* loaded from: classes2.dex */
abstract class p<N> extends AbstractGraph<N> {
    protected abstract e<N> delegate();

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public Set<N> nodes() {
        return delegate().nodes();
    }

    @Override // com.google.common.graph.a
    protected long edgeCount() {
        return delegate().edges().size();
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public boolean isDirected() {
        return delegate().isDirected();
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public boolean allowsSelfLoops() {
        return delegate().allowsSelfLoops();
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public ElementOrder<N> nodeOrder() {
        return delegate().nodeOrder();
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public Set<N> adjacentNodes(N n) {
        return delegate().adjacentNodes(n);
    }

    @Override // com.google.common.graph.e, com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
    public Set<N> predecessors(N n) {
        return delegate().predecessors((e<N>) n);
    }

    @Override // com.google.common.graph.e, com.google.common.graph.SuccessorsFunction, com.google.common.graph.Graph
    public Set<N> successors(N n) {
        return delegate().successors((e<N>) n);
    }

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public int degree(N n) {
        return delegate().degree(n);
    }

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public int inDegree(N n) {
        return delegate().inDegree(n);
    }

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public int outDegree(N n) {
        return delegate().outDegree(n);
    }

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public boolean hasEdgeConnecting(N n, N n2) {
        return delegate().hasEdgeConnecting(n, n2);
    }
}
