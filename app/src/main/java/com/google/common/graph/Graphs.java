package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public final class Graphs {

    /* loaded from: classes2.dex */
    public enum a {
        PENDING,
        COMPLETE
    }

    private Graphs() {
    }

    public static <N> boolean hasCycle(Graph<N> graph) {
        int size = graph.edges().size();
        if (size == 0) {
            return false;
        }
        if (!graph.isDirected() && size >= graph.nodes().size()) {
            return true;
        }
        HashMap newHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(graph.nodes().size());
        for (N n : graph.nodes()) {
            if (a(graph, newHashMapWithExpectedSize, n, null)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasCycle(Network<?, ?> network) {
        if (network.isDirected() || !network.allowsParallelEdges() || network.edges().size() <= network.asGraph().edges().size()) {
            return hasCycle(network.asGraph());
        }
        return true;
    }

    private static <N> boolean a(Graph<N> graph, Map<Object, a> map, N n, @NullableDecl N n2) {
        a aVar = map.get(n);
        if (aVar == a.COMPLETE) {
            return false;
        }
        if (aVar == a.PENDING) {
            return true;
        }
        map.put(n, a.PENDING);
        for (N n3 : graph.successors((Graph<N>) n)) {
            if (a(graph, n3, n2) && a(graph, map, n3, n)) {
                return true;
            }
        }
        map.put(n, a.COMPLETE);
        return false;
    }

    private static boolean a(Graph<?> graph, Object obj, @NullableDecl Object obj2) {
        return graph.isDirected() || !Objects.equal(obj2, obj);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.google.common.graph.MutableGraph, com.google.common.graph.Graph<N>] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static <N> com.google.common.graph.Graph<N> transitiveClosure(com.google.common.graph.Graph<N> r10) {
        /*
            com.google.common.graph.GraphBuilder r0 = com.google.common.graph.GraphBuilder.from(r10)
            r1 = 1
            com.google.common.graph.GraphBuilder r0 = r0.allowsSelfLoops(r1)
            com.google.common.graph.MutableGraph r0 = r0.build()
            boolean r2 = r10.isDirected()
            if (r2 == 0) goto L_0x003b
            java.util.Set r1 = r10.nodes()
            java.util.Iterator r1 = r1.iterator()
        L_0x001b:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0088
            java.lang.Object r2 = r1.next()
            java.util.Set r3 = reachableNodes(r10, r2)
            java.util.Iterator r3 = r3.iterator()
        L_0x002d:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x001b
            java.lang.Object r4 = r3.next()
            r0.putEdge(r2, r4)
            goto L_0x002d
        L_0x003b:
            java.util.HashSet r2 = new java.util.HashSet
            r2.<init>()
            java.util.Set r3 = r10.nodes()
            java.util.Iterator r3 = r3.iterator()
        L_0x0048:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0088
            java.lang.Object r4 = r3.next()
            boolean r5 = r2.contains(r4)
            if (r5 != 0) goto L_0x0048
            java.util.Set r4 = reachableNodes(r10, r4)
            r2.addAll(r4)
            java.util.Iterator r5 = r4.iterator()
            r6 = r1
        L_0x0064:
            boolean r7 = r5.hasNext()
            if (r7 == 0) goto L_0x0048
            java.lang.Object r7 = r5.next()
            int r8 = r6 + 1
            java.lang.Iterable r6 = com.google.common.collect.Iterables.limit(r4, r6)
            java.util.Iterator r6 = r6.iterator()
        L_0x0078:
            boolean r9 = r6.hasNext()
            if (r9 == 0) goto L_0x0086
            java.lang.Object r9 = r6.next()
            r0.putEdge(r7, r9)
            goto L_0x0078
        L_0x0086:
            r6 = r8
            goto L_0x0064
        L_0x0088:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.graph.Graphs.transitiveClosure(com.google.common.graph.Graph):com.google.common.graph.Graph");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <N> Set<N> reachableNodes(Graph<N> graph, N n) {
        Preconditions.checkArgument(graph.nodes().contains(n), "Node %s is not an element of this graph.", n);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayDeque arrayDeque = new ArrayDeque();
        linkedHashSet.add(n);
        arrayDeque.add(n);
        while (!arrayDeque.isEmpty()) {
            for (Object obj : graph.successors((Graph<N>) arrayDeque.remove())) {
                if (linkedHashSet.add(obj)) {
                    arrayDeque.add(obj);
                }
            }
        }
        return Collections.unmodifiableSet(linkedHashSet);
    }

    public static <N> Graph<N> transpose(Graph<N> graph) {
        if (!graph.isDirected()) {
            return graph;
        }
        if (graph instanceof b) {
            return ((b) graph).a;
        }
        return new b(graph);
    }

    public static <N, V> ValueGraph<N, V> transpose(ValueGraph<N, V> valueGraph) {
        if (!valueGraph.isDirected()) {
            return valueGraph;
        }
        if (valueGraph instanceof d) {
            return ((d) valueGraph).a;
        }
        return new d(valueGraph);
    }

    public static <N, E> Network<N, E> transpose(Network<N, E> network) {
        if (!network.isDirected()) {
            return network;
        }
        if (network instanceof c) {
            return ((c) network).a;
        }
        return new c(network);
    }

    /* loaded from: classes2.dex */
    private static class b<N> extends p<N> {
        private final Graph<N> a;

        b(Graph<N> graph) {
            this.a = graph;
        }

        /* renamed from: a */
        public Graph<N> delegate() {
            return this.a;
        }

        @Override // com.google.common.graph.p, com.google.common.graph.e, com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
        public Set<N> predecessors(N n) {
            return delegate().successors((Graph<N>) n);
        }

        @Override // com.google.common.graph.p, com.google.common.graph.e, com.google.common.graph.SuccessorsFunction, com.google.common.graph.Graph
        public Set<N> successors(N n) {
            return delegate().predecessors((Graph<N>) n);
        }

        @Override // com.google.common.graph.p, com.google.common.graph.AbstractGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
        public int inDegree(N n) {
            return delegate().outDegree(n);
        }

        @Override // com.google.common.graph.p, com.google.common.graph.AbstractGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
        public int outDegree(N n) {
            return delegate().inDegree(n);
        }

        @Override // com.google.common.graph.p, com.google.common.graph.AbstractGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
        public boolean hasEdgeConnecting(N n, N n2) {
            return delegate().hasEdgeConnecting(n2, n);
        }
    }

    /* loaded from: classes2.dex */
    private static class d<N, V> extends r<N, V> {
        private final ValueGraph<N, V> a;

        d(ValueGraph<N, V> valueGraph) {
            this.a = valueGraph;
        }

        @Override // com.google.common.graph.r
        protected ValueGraph<N, V> a() {
            return this.a;
        }

        @Override // com.google.common.graph.r, com.google.common.graph.e, com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
        public Set<N> predecessors(N n) {
            return a().successors((ValueGraph<N, V>) n);
        }

        @Override // com.google.common.graph.r, com.google.common.graph.e, com.google.common.graph.SuccessorsFunction, com.google.common.graph.Graph
        public Set<N> successors(N n) {
            return a().predecessors((ValueGraph<N, V>) n);
        }

        @Override // com.google.common.graph.r, com.google.common.graph.AbstractValueGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
        public int inDegree(N n) {
            return a().outDegree(n);
        }

        @Override // com.google.common.graph.r, com.google.common.graph.AbstractValueGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
        public int outDegree(N n) {
            return a().inDegree(n);
        }

        @Override // com.google.common.graph.r, com.google.common.graph.AbstractValueGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
        public boolean hasEdgeConnecting(N n, N n2) {
            return a().hasEdgeConnecting(n2, n);
        }

        @Override // com.google.common.graph.r, com.google.common.graph.ValueGraph
        @NullableDecl
        public V edgeValueOrDefault(N n, N n2, @NullableDecl V v) {
            return a().edgeValueOrDefault(n2, n, v);
        }
    }

    /* loaded from: classes2.dex */
    private static class c<N, E> extends q<N, E> {
        private final Network<N, E> a;

        c(Network<N, E> network) {
            this.a = network;
        }

        @Override // com.google.common.graph.q
        protected Network<N, E> a() {
            return this.a;
        }

        @Override // com.google.common.graph.q, com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
        public Set<N> predecessors(N n) {
            return a().successors((Network<N, E>) n);
        }

        @Override // com.google.common.graph.q, com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction, com.google.common.graph.Graph
        public Set<N> successors(N n) {
            return a().predecessors((Network<N, E>) n);
        }

        @Override // com.google.common.graph.q, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public int inDegree(N n) {
            return a().outDegree(n);
        }

        @Override // com.google.common.graph.q, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public int outDegree(N n) {
            return a().inDegree(n);
        }

        @Override // com.google.common.graph.q, com.google.common.graph.Network
        public Set<E> inEdges(N n) {
            return a().outEdges(n);
        }

        @Override // com.google.common.graph.q, com.google.common.graph.Network
        public Set<E> outEdges(N n) {
            return a().inEdges(n);
        }

        @Override // com.google.common.graph.q, com.google.common.graph.Network
        public EndpointPair<N> incidentNodes(E e) {
            EndpointPair<N> incidentNodes = a().incidentNodes(e);
            return EndpointPair.a(this.a, incidentNodes.nodeV(), incidentNodes.nodeU());
        }

        @Override // com.google.common.graph.q, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public Set<E> edgesConnecting(N n, N n2) {
            return a().edgesConnecting(n2, n);
        }

        @Override // com.google.common.graph.q, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public E edgeConnectingOrNull(N n, N n2) {
            return a().edgeConnectingOrNull(n2, n);
        }

        @Override // com.google.common.graph.q, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public boolean hasEdgeConnecting(N n, N n2) {
            return a().hasEdgeConnecting(n2, n);
        }
    }

    public static <N> MutableGraph<N> inducedSubgraph(Graph<N> graph, Iterable<? extends N> iterable) {
        MutableGraph<N> mutableGraph;
        if (iterable instanceof Collection) {
            mutableGraph = GraphBuilder.from(graph).expectedNodeCount(((Collection) iterable).size()).build();
        } else {
            mutableGraph = GraphBuilder.from(graph).build();
        }
        Iterator<? extends N> it = iterable.iterator();
        while (it.hasNext()) {
            mutableGraph.addNode(it.next());
        }
        for (N n : mutableGraph.nodes()) {
            for (N n2 : graph.successors((Graph<N>) n)) {
                if (mutableGraph.nodes().contains(n2)) {
                    mutableGraph.putEdge(n, n2);
                }
            }
        }
        return mutableGraph;
    }

    public static <N, V> MutableValueGraph<N, V> inducedSubgraph(ValueGraph<N, V> valueGraph, Iterable<? extends N> iterable) {
        MutableValueGraph<N, V> mutableValueGraph;
        if (iterable instanceof Collection) {
            mutableValueGraph = ValueGraphBuilder.from(valueGraph).expectedNodeCount(((Collection) iterable).size()).build();
        } else {
            mutableValueGraph = ValueGraphBuilder.from(valueGraph).build();
        }
        Iterator<? extends N> it = iterable.iterator();
        while (it.hasNext()) {
            mutableValueGraph.addNode(it.next());
        }
        for (N n : mutableValueGraph.nodes()) {
            for (N n2 : valueGraph.successors((ValueGraph<N, V>) n)) {
                if (mutableValueGraph.nodes().contains(n2)) {
                    mutableValueGraph.putEdgeValue(n, n2, valueGraph.edgeValueOrDefault(n, n2, null));
                }
            }
        }
        return mutableValueGraph;
    }

    public static <N, E> MutableNetwork<N, E> inducedSubgraph(Network<N, E> network, Iterable<? extends N> iterable) {
        MutableNetwork<N, E> mutableNetwork;
        if (iterable instanceof Collection) {
            mutableNetwork = NetworkBuilder.from(network).expectedNodeCount(((Collection) iterable).size()).build();
        } else {
            mutableNetwork = NetworkBuilder.from(network).build();
        }
        Iterator<? extends N> it = iterable.iterator();
        while (it.hasNext()) {
            mutableNetwork.addNode(it.next());
        }
        for (N n : mutableNetwork.nodes()) {
            for (E e : network.outEdges(n)) {
                N adjacentNode = network.incidentNodes(e).adjacentNode(n);
                if (mutableNetwork.nodes().contains(adjacentNode)) {
                    mutableNetwork.addEdge(n, adjacentNode, e);
                }
            }
        }
        return mutableNetwork;
    }

    public static <N> MutableGraph<N> copyOf(Graph<N> graph) {
        MutableGraph<N> mutableGraph = (MutableGraph<N>) GraphBuilder.from(graph).expectedNodeCount(graph.nodes().size()).build();
        for (N n : graph.nodes()) {
            mutableGraph.addNode(n);
        }
        for (EndpointPair<N> endpointPair : graph.edges()) {
            mutableGraph.putEdge(endpointPair.nodeU(), endpointPair.nodeV());
        }
        return mutableGraph;
    }

    public static <N, V> MutableValueGraph<N, V> copyOf(ValueGraph<N, V> valueGraph) {
        MutableValueGraph<N, V> mutableValueGraph = (MutableValueGraph<N, V>) ValueGraphBuilder.from(valueGraph).expectedNodeCount(valueGraph.nodes().size()).build();
        for (N n : valueGraph.nodes()) {
            mutableValueGraph.addNode(n);
        }
        for (EndpointPair<N> endpointPair : valueGraph.edges()) {
            mutableValueGraph.putEdgeValue(endpointPair.nodeU(), endpointPair.nodeV(), valueGraph.edgeValueOrDefault(endpointPair.nodeU(), endpointPair.nodeV(), null));
        }
        return mutableValueGraph;
    }

    public static <N, E> MutableNetwork<N, E> copyOf(Network<N, E> network) {
        MutableNetwork<N, E> mutableNetwork = (MutableNetwork<N, E>) NetworkBuilder.from(network).expectedNodeCount(network.nodes().size()).expectedEdgeCount(network.edges().size()).build();
        for (N n : network.nodes()) {
            mutableNetwork.addNode(n);
        }
        for (E e : network.edges()) {
            EndpointPair<N> incidentNodes = network.incidentNodes(e);
            mutableNetwork.addEdge(incidentNodes.nodeU(), incidentNodes.nodeV(), e);
        }
        return mutableNetwork;
    }

    @CanIgnoreReturnValue
    public static int a(int i) {
        Preconditions.checkArgument(i >= 0, "Not true that %s is non-negative.", i);
        return i;
    }

    @CanIgnoreReturnValue
    public static long a(long j) {
        Preconditions.checkArgument(j >= 0, "Not true that %s is non-negative.", j);
        return j;
    }

    @CanIgnoreReturnValue
    public static int b(int i) {
        Preconditions.checkArgument(i > 0, "Not true that %s is positive.", i);
        return i;
    }

    @CanIgnoreReturnValue
    public static long b(long j) {
        Preconditions.checkArgument(j > 0, "Not true that %s is positive.", j);
        return j;
    }
}
