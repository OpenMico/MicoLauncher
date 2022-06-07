package com.google.common.graph;

import androidx.exifinterface.media.ExifInterface;
import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.Immutable;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable(containerOf = {"N", ExifInterface.GPS_MEASUREMENT_INTERRUPTED})
@Beta
/* loaded from: classes2.dex */
public final class ImmutableValueGraph<N, V> extends j<N, V> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.j, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ Set adjacentNodes(Object obj) {
        return super.adjacentNodes(obj);
    }

    @Override // com.google.common.graph.j, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ boolean allowsSelfLoops() {
        return super.allowsSelfLoops();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.j, com.google.common.graph.ValueGraph
    @NullableDecl
    public /* bridge */ /* synthetic */ Object edgeValueOrDefault(Object obj, Object obj2, @NullableDecl Object obj3) {
        return super.edgeValueOrDefault(obj, obj2, obj3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.j, com.google.common.graph.AbstractValueGraph, com.google.common.graph.a, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ boolean hasEdgeConnecting(Object obj, Object obj2) {
        return super.hasEdgeConnecting(obj, obj2);
    }

    @Override // com.google.common.graph.j, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ boolean isDirected() {
        return super.isDirected();
    }

    @Override // com.google.common.graph.j, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ ElementOrder nodeOrder() {
        return super.nodeOrder();
    }

    @Override // com.google.common.graph.j, com.google.common.graph.e, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ Set nodes() {
        return super.nodes();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.j, com.google.common.graph.e, com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ Set predecessors(Object obj) {
        return super.predecessors((ImmutableValueGraph<N, V>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.j, com.google.common.graph.e, com.google.common.graph.SuccessorsFunction, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ Set successors(Object obj) {
        return super.successors((ImmutableValueGraph<N, V>) obj);
    }

    private ImmutableValueGraph(ValueGraph<N, V> valueGraph) {
        super(ValueGraphBuilder.from(valueGraph), a((ValueGraph) valueGraph), valueGraph.edges().size());
    }

    public static <N, V> ImmutableValueGraph<N, V> copyOf(ValueGraph<N, V> valueGraph) {
        return valueGraph instanceof ImmutableValueGraph ? (ImmutableValueGraph) valueGraph : new ImmutableValueGraph<>(valueGraph);
    }

    @Deprecated
    public static <N, V> ImmutableValueGraph<N, V> copyOf(ImmutableValueGraph<N, V> immutableValueGraph) {
        return (ImmutableValueGraph) Preconditions.checkNotNull(immutableValueGraph);
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.ValueGraph
    public ImmutableGraph<N> asGraph() {
        return new ImmutableGraph<>(this);
    }

    private static <N, V> ImmutableMap<N, s<N, V>> a(ValueGraph<N, V> valueGraph) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (N n : valueGraph.nodes()) {
            builder.put(n, a(valueGraph, n));
        }
        return builder.build();
    }

    private static <N, V> s<N, V> a(final ValueGraph<N, V> valueGraph, final N n) {
        Function<N, V> function = new Function<N, V>() { // from class: com.google.common.graph.ImmutableValueGraph.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.base.Function
            public V apply(N n2) {
                return (V) ValueGraph.this.edgeValueOrDefault(n, n2, null);
            }
        };
        if (valueGraph.isDirected()) {
            return k.a((Set) valueGraph.predecessors((ValueGraph<N, V>) n), Maps.asMap(valueGraph.successors((ValueGraph<N, V>) n), function));
        }
        return y.a(Maps.asMap(valueGraph.adjacentNodes(n), function));
    }
}
