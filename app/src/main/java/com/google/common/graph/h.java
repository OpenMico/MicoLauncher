package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.xiaomi.onetrack.api.b;

/* compiled from: ConfigurableMutableValueGraph.java */
/* loaded from: classes2.dex */
public final class h<N, V> extends j<N, V> implements MutableValueGraph<N, V> {
    public h(c<? super N> cVar) {
        super(cVar);
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    public boolean addNode(N n) {
        Preconditions.checkNotNull(n, "node");
        if (containsNode(n)) {
            return false;
        }
        a((h<N, V>) n);
        return true;
    }

    @CanIgnoreReturnValue
    private s<N, V> a(N n) {
        s<N, V> a = a();
        Preconditions.checkState(this.nodeConnections.a((u) n, (N) a) == null);
        return a;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    public V putEdgeValue(N n, N n2, V v) {
        Preconditions.checkNotNull(n, "nodeU");
        Preconditions.checkNotNull(n2, "nodeV");
        Preconditions.checkNotNull(v, b.p);
        if (!allowsSelfLoops()) {
            Preconditions.checkArgument(!n.equals(n2), "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", n);
        }
        s<N, V> sVar = (s) this.nodeConnections.b(n);
        if (sVar == null) {
            sVar = a((h<N, V>) n);
        }
        V b = sVar.b(n2, v);
        s<N, V> sVar2 = (s) this.nodeConnections.b(n2);
        if (sVar2 == null) {
            sVar2 = a((h<N, V>) n2);
        }
        sVar2.a(n, v);
        if (b == null) {
            long j = this.edgeCount + 1;
            this.edgeCount = j;
            Graphs.b(j);
        }
        return b;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    public boolean removeNode(N n) {
        Preconditions.checkNotNull(n, "node");
        s sVar = (s) this.nodeConnections.b(n);
        if (sVar == null) {
            return false;
        }
        if (allowsSelfLoops() && sVar.c(n) != null) {
            sVar.b(n);
            this.edgeCount--;
        }
        for (N n2 : sVar.d()) {
            ((s) this.nodeConnections.c(n2)).b(n);
            this.edgeCount--;
        }
        if (isDirected()) {
            for (N n3 : sVar.c()) {
                Preconditions.checkState(((s) this.nodeConnections.c(n3)).c(n) != null);
                this.edgeCount--;
            }
        }
        this.nodeConnections.a(n);
        Graphs.a(this.edgeCount);
        return true;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    public V removeEdge(N n, N n2) {
        Preconditions.checkNotNull(n, "nodeU");
        Preconditions.checkNotNull(n2, "nodeV");
        s sVar = (s) this.nodeConnections.b(n);
        s sVar2 = (s) this.nodeConnections.b(n2);
        if (sVar == null || sVar2 == null) {
            return null;
        }
        V v = (V) sVar.c(n2);
        if (v != null) {
            sVar2.b(n);
            long j = this.edgeCount - 1;
            this.edgeCount = j;
            Graphs.a(j);
        }
        return v;
    }

    private s<N, V> a() {
        if (isDirected()) {
            return k.a();
        }
        return y.a();
    }
}
