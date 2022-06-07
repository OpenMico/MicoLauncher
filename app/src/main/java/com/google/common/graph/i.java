package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConfigurableNetwork.java */
/* loaded from: classes2.dex */
public class i<N, E> extends AbstractNetwork<N, E> {
    private final boolean a;
    private final boolean b;
    private final boolean c;
    private final ElementOrder<N> d;
    private final ElementOrder<E> e;
    protected final u<E, N> edgeToReferenceNode;
    protected final u<N, x<N, E>> nodeConnections;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(NetworkBuilder<? super N, ? super E> networkBuilder) {
        this(networkBuilder, networkBuilder.c.a(((Integer) networkBuilder.d.or((Optional) 10)).intValue()), networkBuilder.f.a(networkBuilder.g.or((Optional<Integer>) 20).intValue()));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(NetworkBuilder<? super N, ? super E> networkBuilder, Map<N, x<N, E>> map, Map<E, N> map2) {
        this.a = networkBuilder.a;
        this.b = networkBuilder.e;
        this.c = networkBuilder.b;
        this.d = networkBuilder.c.a();
        this.e = (ElementOrder<E>) networkBuilder.f.a();
        this.nodeConnections = map instanceof TreeMap ? new v<>(map) : new u<>(map);
        this.edgeToReferenceNode = new u<>(map2);
    }

    @Override // com.google.common.graph.Network
    public Set<N> nodes() {
        return this.nodeConnections.a();
    }

    @Override // com.google.common.graph.Network
    public Set<E> edges() {
        return this.edgeToReferenceNode.a();
    }

    @Override // com.google.common.graph.Network
    public boolean isDirected() {
        return this.a;
    }

    @Override // com.google.common.graph.Network
    public boolean allowsParallelEdges() {
        return this.b;
    }

    @Override // com.google.common.graph.Network
    public boolean allowsSelfLoops() {
        return this.c;
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<N> nodeOrder() {
        return this.d;
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<E> edgeOrder() {
        return this.e;
    }

    @Override // com.google.common.graph.Network
    public Set<E> incidentEdges(N n) {
        return checkedConnections(n).b();
    }

    @Override // com.google.common.graph.Network
    public EndpointPair<N> incidentNodes(E e) {
        N checkedReferenceNode = checkedReferenceNode(e);
        return EndpointPair.a(this, checkedReferenceNode, this.nodeConnections.b(checkedReferenceNode).a(e));
    }

    @Override // com.google.common.graph.Network
    public Set<N> adjacentNodes(N n) {
        return checkedConnections(n).a();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public Set<E> edgesConnecting(N n, N n2) {
        x<N, E> checkedConnections = checkedConnections(n);
        if (!this.c && n == n2) {
            return ImmutableSet.of();
        }
        Preconditions.checkArgument(containsNode(n2), "Node %s is not an element of this graph.", n2);
        return checkedConnections.c(n2);
    }

    @Override // com.google.common.graph.Network
    public Set<E> inEdges(N n) {
        return checkedConnections(n).c();
    }

    @Override // com.google.common.graph.Network
    public Set<E> outEdges(N n) {
        return checkedConnections(n).d();
    }

    @Override // com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
    public Set<N> predecessors(N n) {
        return checkedConnections(n).e();
    }

    @Override // com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction, com.google.common.graph.Graph
    public Set<N> successors(N n) {
        return checkedConnections(n).f();
    }

    protected final x<N, E> checkedConnections(N n) {
        x<N, E> b = this.nodeConnections.b(n);
        if (b != null) {
            return b;
        }
        Preconditions.checkNotNull(n);
        throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", n));
    }

    protected final N checkedReferenceNode(E e) {
        N b = this.edgeToReferenceNode.b(e);
        if (b != null) {
            return b;
        }
        Preconditions.checkNotNull(e);
        throw new IllegalArgumentException(String.format("Edge %s is not an element of this graph.", e));
    }

    protected final boolean containsNode(@NullableDecl N n) {
        return this.nodeConnections.d(n);
    }

    protected final boolean containsEdge(@NullableDecl E e) {
        return this.edgeToReferenceNode.d(e);
    }
}
