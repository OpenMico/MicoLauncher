package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.graph.t;
import com.google.errorprone.annotations.Immutable;
import java.util.Set;

@Immutable(containerOf = {"N"})
@Beta
/* loaded from: classes2.dex */
public class ImmutableGraph<N> extends p<N> {
    private final e<N> a;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.p, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ Set adjacentNodes(Object obj) {
        return super.adjacentNodes(obj);
    }

    @Override // com.google.common.graph.p, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ boolean allowsSelfLoops() {
        return super.allowsSelfLoops();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.p, com.google.common.graph.AbstractGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ int degree(Object obj) {
        return super.degree(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.p, com.google.common.graph.AbstractGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ boolean hasEdgeConnecting(Object obj, Object obj2) {
        return super.hasEdgeConnecting(obj, obj2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.p, com.google.common.graph.AbstractGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ int inDegree(Object obj) {
        return super.inDegree(obj);
    }

    @Override // com.google.common.graph.p, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ boolean isDirected() {
        return super.isDirected();
    }

    @Override // com.google.common.graph.p, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ ElementOrder nodeOrder() {
        return super.nodeOrder();
    }

    @Override // com.google.common.graph.p, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ Set nodes() {
        return super.nodes();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.p, com.google.common.graph.AbstractGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ int outDegree(Object obj) {
        return super.outDegree(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.p, com.google.common.graph.e, com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ Set predecessors(Object obj) {
        return super.predecessors((ImmutableGraph<N>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.p, com.google.common.graph.e, com.google.common.graph.SuccessorsFunction, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ Set successors(Object obj) {
        return super.successors((ImmutableGraph<N>) obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImmutableGraph(e<N> eVar) {
        this.a = eVar;
    }

    public static <N> ImmutableGraph<N> copyOf(Graph<N> graph) {
        return graph instanceof ImmutableGraph ? (ImmutableGraph) graph : new ImmutableGraph<>(new j(GraphBuilder.from(graph), a(graph), graph.edges().size()));
    }

    @Deprecated
    public static <N> ImmutableGraph<N> copyOf(ImmutableGraph<N> immutableGraph) {
        return (ImmutableGraph) Preconditions.checkNotNull(immutableGraph);
    }

    private static <N> ImmutableMap<N, s<N, t.a>> a(Graph<N> graph) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (N n : graph.nodes()) {
            builder.put(n, a(graph, n));
        }
        return builder.build();
    }

    private static <N> s<N, t.a> a(Graph<N> graph, N n) {
        Function constant = Functions.constant(t.a.EDGE_EXISTS);
        if (graph.isDirected()) {
            return k.a((Set) graph.predecessors((Graph<N>) n), Maps.asMap(graph.successors((Graph<N>) n), constant));
        }
        return y.a(Maps.asMap(graph.adjacentNodes(n), constant));
    }

    @Override // com.google.common.graph.p
    protected e<N> delegate() {
        return this.a;
    }
}
