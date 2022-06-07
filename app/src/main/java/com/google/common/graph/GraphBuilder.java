package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

@Beta
/* loaded from: classes2.dex */
public final class GraphBuilder<N> extends c<N> {
    /* JADX WARN: Multi-variable type inference failed */
    private <N1 extends N> GraphBuilder<N1> a() {
        return this;
    }

    private GraphBuilder(boolean z) {
        super(z);
    }

    public static GraphBuilder<Object> directed() {
        return new GraphBuilder<>(true);
    }

    public static GraphBuilder<Object> undirected() {
        return new GraphBuilder<>(false);
    }

    public static <N> GraphBuilder<N> from(Graph<N> graph) {
        return (GraphBuilder<N>) new GraphBuilder(graph.isDirected()).allowsSelfLoops(graph.allowsSelfLoops()).nodeOrder(graph.nodeOrder());
    }

    public GraphBuilder<N> allowsSelfLoops(boolean z) {
        this.b = z;
        return this;
    }

    public GraphBuilder<N> expectedNodeCount(int i) {
        this.d = Optional.of(Integer.valueOf(Graphs.a(i)));
        return this;
    }

    public <N1 extends N> GraphBuilder<N1> nodeOrder(ElementOrder<N1> elementOrder) {
        GraphBuilder<N1> a = a();
        a.c = (ElementOrder) Preconditions.checkNotNull(elementOrder);
        return a;
    }

    public <N1 extends N> MutableGraph<N1> build() {
        return new f(this);
    }
}
