package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

@Beta
/* loaded from: classes2.dex */
public final class ValueGraphBuilder<N, V> extends c<N> {
    /* JADX WARN: Multi-variable type inference failed */
    private <N1 extends N, V1 extends V> ValueGraphBuilder<N1, V1> a() {
        return this;
    }

    private ValueGraphBuilder(boolean z) {
        super(z);
    }

    public static ValueGraphBuilder<Object, Object> directed() {
        return new ValueGraphBuilder<>(true);
    }

    public static ValueGraphBuilder<Object, Object> undirected() {
        return new ValueGraphBuilder<>(false);
    }

    public static <N, V> ValueGraphBuilder<N, V> from(ValueGraph<N, V> valueGraph) {
        return (ValueGraphBuilder<N, V>) new ValueGraphBuilder(valueGraph.isDirected()).allowsSelfLoops(valueGraph.allowsSelfLoops()).nodeOrder(valueGraph.nodeOrder());
    }

    public ValueGraphBuilder<N, V> allowsSelfLoops(boolean z) {
        this.b = z;
        return this;
    }

    public ValueGraphBuilder<N, V> expectedNodeCount(int i) {
        this.d = Optional.of(Integer.valueOf(Graphs.a(i)));
        return this;
    }

    public <N1 extends N> ValueGraphBuilder<N1, V> nodeOrder(ElementOrder<N1> elementOrder) {
        ValueGraphBuilder<N1, V> valueGraphBuilder = (ValueGraphBuilder<N1, V>) a();
        valueGraphBuilder.c = (ElementOrder) Preconditions.checkNotNull(elementOrder);
        return valueGraphBuilder;
    }

    public <N1 extends N, V1 extends V> MutableValueGraph<N1, V1> build() {
        return new h(this);
    }
}
