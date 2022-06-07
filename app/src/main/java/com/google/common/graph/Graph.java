package com.google.common.graph;

import com.google.common.annotations.Beta;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public interface Graph<N> extends e<N> {
    @Override // 
    Set<N> adjacentNodes(N n);

    @Override // 
    boolean allowsSelfLoops();

    @Override // 
    int degree(N n);

    @Override // 
    Set<EndpointPair<N>> edges();

    boolean equals(@NullableDecl Object obj);

    @Override // 
    boolean hasEdgeConnecting(N n, N n2);

    int hashCode();

    @Override // 
    int inDegree(N n);

    Set<EndpointPair<N>> incidentEdges(N n);

    @Override // 
    boolean isDirected();

    @Override // 
    ElementOrder<N> nodeOrder();

    @Override // 
    Set<N> nodes();

    @Override // 
    int outDegree(N n);

    @Override // 
    Set<N> predecessors(N n);

    @Override // 
    Set<N> successors(N n);
}
