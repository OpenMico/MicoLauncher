package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConfigurableMutableNetwork.java */
/* loaded from: classes2.dex */
public final class g<N, E> extends i<N, E> implements MutableNetwork<N, E> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public g(NetworkBuilder<? super N, ? super E> networkBuilder) {
        super(networkBuilder);
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean addNode(N n) {
        Preconditions.checkNotNull(n, "node");
        if (containsNode(n)) {
            return false;
        }
        a((g<N, E>) n);
        return true;
    }

    @CanIgnoreReturnValue
    private x<N, E> a(N n) {
        x<N, E> a = a();
        Preconditions.checkState(this.nodeConnections.a((u) n, (N) a) == null);
        return a;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean addEdge(N n, N n2, E e) {
        Preconditions.checkNotNull(n, "nodeU");
        Preconditions.checkNotNull(n2, "nodeV");
        Preconditions.checkNotNull(e, "edge");
        boolean z = false;
        if (containsEdge(e)) {
            EndpointPair<N> incidentNodes = incidentNodes(e);
            EndpointPair a = EndpointPair.a(this, n, n2);
            Preconditions.checkArgument(incidentNodes.equals(a), "Edge %s already exists between the following nodes: %s, so it cannot be reused to connect the following nodes: %s.", e, incidentNodes, a);
            return false;
        }
        x<N, E> xVar = (x) this.nodeConnections.b(n);
        if (!allowsParallelEdges()) {
            if (xVar == null || !xVar.f().contains(n2)) {
                z = true;
            }
            Preconditions.checkArgument(z, "Nodes %s and %s are already connected by a different edge. To construct a graph that allows parallel edges, call allowsParallelEdges(true) on the Builder.", n, n2);
        }
        boolean equals = n.equals(n2);
        if (!allowsSelfLoops()) {
            Preconditions.checkArgument(!equals, "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", n);
        }
        if (xVar == null) {
            xVar = a((g<N, E>) n);
        }
        xVar.a((x<N, E>) e, (E) n2);
        x<N, E> xVar2 = (x) this.nodeConnections.b(n2);
        if (xVar2 == null) {
            xVar2 = a((g<N, E>) n2);
        }
        xVar2.a(e, n, equals);
        this.edgeToReferenceNode.a((u) e, (E) n);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean removeNode(N n) {
        Preconditions.checkNotNull(n, "node");
        x xVar = (x) this.nodeConnections.b(n);
        if (xVar == null) {
            return false;
        }
        UnmodifiableIterator<E> it = ImmutableList.copyOf((Collection) xVar.b()).iterator();
        while (it.hasNext()) {
            removeEdge(it.next());
        }
        this.nodeConnections.a(n);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean removeEdge(E e) {
        Preconditions.checkNotNull(e, "edge");
        Object b = this.edgeToReferenceNode.b(e);
        boolean z = false;
        if (b == null) {
            return false;
        }
        x xVar = (x) this.nodeConnections.b(b);
        Object a = xVar.a(e);
        x xVar2 = (x) this.nodeConnections.b(a);
        xVar.b(e);
        if (allowsSelfLoops() && b.equals(a)) {
            z = true;
        }
        xVar2.a((x) e, z);
        this.edgeToReferenceNode.a(e);
        return true;
    }

    private x<N, E> a() {
        if (isDirected()) {
            if (allowsParallelEdges()) {
                return l.g();
            }
            return m.g();
        } else if (allowsParallelEdges()) {
            return z.g();
        } else {
            return aa.g();
        }
    }
}
