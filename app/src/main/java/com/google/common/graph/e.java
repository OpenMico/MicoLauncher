package com.google.common.graph;

import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BaseGraph.java */
/* loaded from: classes2.dex */
public interface e<N> extends PredecessorsFunction<N>, SuccessorsFunction<N> {
    Set<N> adjacentNodes(N n);

    boolean allowsSelfLoops();

    int degree(N n);

    Set<EndpointPair<N>> edges();

    boolean hasEdgeConnecting(N n, N n2);

    int inDegree(N n);

    boolean isDirected();

    ElementOrder<N> nodeOrder();

    Set<N> nodes();

    int outDegree(N n);

    @Override // com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
    Set<N> predecessors(N n);

    Set<N> successors(N n);
}
