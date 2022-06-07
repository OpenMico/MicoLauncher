package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.util.AbstractSet;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractBaseGraph.java */
/* loaded from: classes2.dex */
public abstract class a<N> implements e<N> {
    protected long edgeCount() {
        long j = 0;
        for (N n : nodes()) {
            j += degree(n);
        }
        Preconditions.checkState((1 & j) == 0);
        return j >>> 1;
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public Set<EndpointPair<N>> edges() {
        return new AbstractSet<EndpointPair<N>>() { // from class: com.google.common.graph.a.1
            /* renamed from: a */
            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return o.a(a.this);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return Ints.saturatedCast(a.this.edgeCount());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                throw new UnsupportedOperationException();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                if (!(obj instanceof EndpointPair)) {
                    return false;
                }
                EndpointPair endpointPair = (EndpointPair) obj;
                return a.this.isDirected() == endpointPair.isOrdered() && a.this.nodes().contains(endpointPair.nodeU()) && a.this.successors((a) endpointPair.nodeU()).contains(endpointPair.nodeV());
            }
        };
    }

    public Set<EndpointPair<N>> incidentEdges(N n) {
        Preconditions.checkNotNull(n);
        Preconditions.checkArgument(nodes().contains(n), "Node %s is not an element of this graph.", n);
        return AbstractC0114a.a(this, n);
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public int degree(N n) {
        if (isDirected()) {
            return IntMath.saturatedAdd(predecessors((a<N>) n).size(), successors((a<N>) n).size());
        }
        Set<N> adjacentNodes = adjacentNodes(n);
        return IntMath.saturatedAdd(adjacentNodes.size(), (!allowsSelfLoops() || !adjacentNodes.contains(n)) ? 0 : 1);
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public int inDegree(N n) {
        return isDirected() ? predecessors((a<N>) n).size() : degree(n);
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public int outDegree(N n) {
        return isDirected() ? successors((a<N>) n).size() : degree(n);
    }

    @Override // com.google.common.graph.e, com.google.common.graph.Graph
    public boolean hasEdgeConnecting(N n, N n2) {
        Preconditions.checkNotNull(n);
        Preconditions.checkNotNull(n2);
        return nodes().contains(n) && successors((a<N>) n).contains(n2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AbstractBaseGraph.java */
    /* renamed from: com.google.common.graph.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public static abstract class AbstractC0114a<N> extends AbstractSet<EndpointPair<N>> {
        protected final N a;
        protected final e<N> b;

        public static <N> AbstractC0114a<N> a(e<N> eVar, N n) {
            return eVar.isDirected() ? new C0115a(eVar, n) : new b(eVar, n);
        }

        private AbstractC0114a(e<N> eVar, N n) {
            this.b = eVar;
            this.a = n;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: AbstractBaseGraph.java */
        /* renamed from: com.google.common.graph.a$a$a  reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        public static final class C0115a<N> extends AbstractC0114a<N> {
            private C0115a(e<N> eVar, N n) {
                super(eVar, n);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* renamed from: a */
            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return Iterators.unmodifiableIterator(Iterators.concat(Iterators.transform(this.b.predecessors((e) this.a).iterator(), new Function<N, EndpointPair<N>>() { // from class: com.google.common.graph.a.a.a.1
                    /* renamed from: a */
                    public EndpointPair<N> apply(N n) {
                        return EndpointPair.ordered(n, C0115a.this.a);
                    }
                }), Iterators.transform(Sets.difference(this.b.successors((e) this.a), ImmutableSet.of(this.a)).iterator(), new Function<N, EndpointPair<N>>() { // from class: com.google.common.graph.a.a.a.2
                    /* renamed from: a */
                    public EndpointPair<N> apply(N n) {
                        return EndpointPair.ordered(C0115a.this.a, n);
                    }
                })));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return (this.b.inDegree(this.a) + this.b.outDegree(this.a)) - (this.b.successors((e) this.a).contains(this.a) ? 1 : 0);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                if (!(obj instanceof EndpointPair)) {
                    return false;
                }
                EndpointPair endpointPair = (EndpointPair) obj;
                if (!endpointPair.isOrdered()) {
                    return false;
                }
                Object source = endpointPair.source();
                Object target = endpointPair.target();
                return (this.a.equals(source) && this.b.successors((e) this.a).contains(target)) || (this.a.equals(target) && this.b.predecessors((e) this.a).contains(source));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: AbstractBaseGraph.java */
        /* renamed from: com.google.common.graph.a$a$b */
        /* loaded from: classes2.dex */
        public static final class b<N> extends AbstractC0114a<N> {
            private b(e<N> eVar, N n) {
                super(eVar, n);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* renamed from: a */
            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return Iterators.unmodifiableIterator(Iterators.transform(this.b.adjacentNodes(this.a).iterator(), new Function<N, EndpointPair<N>>() { // from class: com.google.common.graph.a.a.b.1
                    /* renamed from: a */
                    public EndpointPair<N> apply(N n) {
                        return EndpointPair.unordered(b.this.a, n);
                    }
                }));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return this.b.adjacentNodes(this.a).size();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                if (!(obj instanceof EndpointPair)) {
                    return false;
                }
                EndpointPair endpointPair = (EndpointPair) obj;
                if (endpointPair.isOrdered()) {
                    return false;
                }
                Set adjacentNodes = this.b.adjacentNodes(this.a);
                Object nodeU = endpointPair.nodeU();
                Object nodeV = endpointPair.nodeV();
                return (this.a.equals(nodeV) && adjacentNodes.contains(nodeU)) || (this.a.equals(nodeU) && adjacentNodes.contains(nodeV));
            }
        }
    }
}
