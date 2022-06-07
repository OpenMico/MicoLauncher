package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.Immutable;
import java.util.Map;
import java.util.Set;

@Immutable(containerOf = {"N", "E"})
@Beta
/* loaded from: classes2.dex */
public final class ImmutableNetwork<N, E> extends i<N, E> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.i, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set adjacentNodes(Object obj) {
        return super.adjacentNodes(obj);
    }

    @Override // com.google.common.graph.i, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ boolean allowsParallelEdges() {
        return super.allowsParallelEdges();
    }

    @Override // com.google.common.graph.i, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ boolean allowsSelfLoops() {
        return super.allowsSelfLoops();
    }

    @Override // com.google.common.graph.i, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ ElementOrder edgeOrder() {
        return super.edgeOrder();
    }

    @Override // com.google.common.graph.i, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set edges() {
        return super.edges();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.i, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set edgesConnecting(Object obj, Object obj2) {
        return super.edgesConnecting(obj, obj2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.i, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set inEdges(Object obj) {
        return super.inEdges(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.i, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set incidentEdges(Object obj) {
        return super.incidentEdges(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.i, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ EndpointPair incidentNodes(Object obj) {
        return super.incidentNodes(obj);
    }

    @Override // com.google.common.graph.i, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ boolean isDirected() {
        return super.isDirected();
    }

    @Override // com.google.common.graph.i, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ ElementOrder nodeOrder() {
        return super.nodeOrder();
    }

    @Override // com.google.common.graph.i, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set nodes() {
        return super.nodes();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.i, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set outEdges(Object obj) {
        return super.outEdges(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.i, com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ Set predecessors(Object obj) {
        return super.predecessors((ImmutableNetwork<N, E>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.i, com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ Set successors(Object obj) {
        return super.successors((ImmutableNetwork<N, E>) obj);
    }

    private ImmutableNetwork(Network<N, E> network) {
        super(NetworkBuilder.from(network), a(network), b(network));
    }

    public static <N, E> ImmutableNetwork<N, E> copyOf(Network<N, E> network) {
        return network instanceof ImmutableNetwork ? (ImmutableNetwork) network : new ImmutableNetwork<>(network);
    }

    @Deprecated
    public static <N, E> ImmutableNetwork<N, E> copyOf(ImmutableNetwork<N, E> immutableNetwork) {
        return (ImmutableNetwork) Preconditions.checkNotNull(immutableNetwork);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public ImmutableGraph<N> asGraph() {
        return new ImmutableGraph<>(super.asGraph());
    }

    private static <N, E> Map<N, x<N, E>> a(Network<N, E> network) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (N n : network.nodes()) {
            builder.put(n, a((Network) network, (Object) n));
        }
        return builder.build();
    }

    private static <N, E> Map<E, N> b(Network<N, E> network) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (E e : network.edges()) {
            builder.put(e, network.incidentNodes(e).nodeU());
        }
        return builder.build();
    }

    private static <N, E> x<N, E> a(Network<N, E> network, N n) {
        if (network.isDirected()) {
            Map asMap = Maps.asMap(network.inEdges(n), c(network));
            Map asMap2 = Maps.asMap(network.outEdges(n), d(network));
            int size = network.edgesConnecting(n, n).size();
            if (network.allowsParallelEdges()) {
                return l.a(asMap, asMap2, size);
            }
            return m.a(asMap, asMap2, size);
        }
        Map asMap3 = Maps.asMap(network.incidentEdges(n), b(network, n));
        if (network.allowsParallelEdges()) {
            return z.a(asMap3);
        }
        return aa.a(asMap3);
    }

    private static <N, E> Function<E, N> c(final Network<N, E> network) {
        return new Function<E, N>() { // from class: com.google.common.graph.ImmutableNetwork.1
            @Override // com.google.common.base.Function
            public N apply(E e) {
                return Network.this.incidentNodes(e).source();
            }
        };
    }

    private static <N, E> Function<E, N> d(final Network<N, E> network) {
        return new Function<E, N>() { // from class: com.google.common.graph.ImmutableNetwork.2
            @Override // com.google.common.base.Function
            public N apply(E e) {
                return Network.this.incidentNodes(e).target();
            }
        };
    }

    private static <N, E> Function<E, N> b(final Network<N, E> network, final N n) {
        return new Function<E, N>() { // from class: com.google.common.graph.ImmutableNetwork.3
            @Override // com.google.common.base.Function
            public N apply(E e) {
                return Network.this.incidentNodes(e).adjacentNode(n);
            }
        };
    }
}
