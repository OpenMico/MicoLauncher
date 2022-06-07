package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.Immutable;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable(containerOf = {"N"})
@Beta
/* loaded from: classes2.dex */
public abstract class EndpointPair<N> implements Iterable<N> {
    private final N a;
    private final N b;

    public abstract boolean equals(@NullableDecl Object obj);

    public abstract int hashCode();

    public abstract boolean isOrdered();

    public abstract N source();

    public abstract N target();

    private EndpointPair(N n, N n2) {
        this.a = (N) Preconditions.checkNotNull(n);
        this.b = (N) Preconditions.checkNotNull(n2);
    }

    public static <N> EndpointPair<N> ordered(N n, N n2) {
        return new a(n, n2);
    }

    public static <N> EndpointPair<N> unordered(N n, N n2) {
        return new b(n2, n);
    }

    public static <N> EndpointPair<N> a(Network<?, ?> network, N n, N n2) {
        return network.isDirected() ? ordered(n, n2) : unordered(n, n2);
    }

    public final N nodeU() {
        return this.a;
    }

    public final N nodeV() {
        return this.b;
    }

    public final N adjacentNode(Object obj) {
        if (obj.equals(this.a)) {
            return this.b;
        }
        if (obj.equals(this.b)) {
            return this.a;
        }
        throw new IllegalArgumentException("EndpointPair " + this + " does not contain node " + obj);
    }

    @Override // java.lang.Iterable
    public final UnmodifiableIterator<N> iterator() {
        return Iterators.forArray(this.a, this.b);
    }

    /* loaded from: classes2.dex */
    public static final class a<N> extends EndpointPair<N> {
        @Override // com.google.common.graph.EndpointPair
        public boolean isOrdered() {
            return true;
        }

        @Override // com.google.common.graph.EndpointPair, java.lang.Iterable
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return EndpointPair.super.iterator();
        }

        private a(N n, N n2) {
            super(n, n2);
        }

        @Override // com.google.common.graph.EndpointPair
        public N source() {
            return nodeU();
        }

        @Override // com.google.common.graph.EndpointPair
        public N target() {
            return nodeV();
        }

        @Override // com.google.common.graph.EndpointPair
        public boolean equals(@NullableDecl Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof EndpointPair)) {
                return false;
            }
            EndpointPair endpointPair = (EndpointPair) obj;
            if (isOrdered() != endpointPair.isOrdered()) {
                return false;
            }
            return source().equals(endpointPair.source()) && target().equals(endpointPair.target());
        }

        @Override // com.google.common.graph.EndpointPair
        public int hashCode() {
            return Objects.hashCode(source(), target());
        }

        public String toString() {
            return "<" + source() + " -> " + target() + ">";
        }
    }

    /* loaded from: classes2.dex */
    public static final class b<N> extends EndpointPair<N> {
        @Override // com.google.common.graph.EndpointPair
        public boolean isOrdered() {
            return false;
        }

        @Override // com.google.common.graph.EndpointPair, java.lang.Iterable
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return EndpointPair.super.iterator();
        }

        private b(N n, N n2) {
            super(n, n2);
        }

        @Override // com.google.common.graph.EndpointPair
        public N source() {
            throw new UnsupportedOperationException("Cannot call source()/target() on a EndpointPair from an undirected graph. Consider calling adjacentNode(node) if you already have a node, or nodeU()/nodeV() if you don't.");
        }

        @Override // com.google.common.graph.EndpointPair
        public N target() {
            throw new UnsupportedOperationException("Cannot call source()/target() on a EndpointPair from an undirected graph. Consider calling adjacentNode(node) if you already have a node, or nodeU()/nodeV() if you don't.");
        }

        @Override // com.google.common.graph.EndpointPair
        public boolean equals(@NullableDecl Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof EndpointPair)) {
                return false;
            }
            EndpointPair endpointPair = (EndpointPair) obj;
            if (isOrdered() != endpointPair.isOrdered()) {
                return false;
            }
            if (nodeU().equals(endpointPair.nodeU())) {
                return nodeV().equals(endpointPair.nodeV());
            }
            return nodeU().equals(endpointPair.nodeV()) && nodeV().equals(endpointPair.nodeU());
        }

        @Override // com.google.common.graph.EndpointPair
        public int hashCode() {
            return nodeU().hashCode() + nodeV().hashCode();
        }

        public String toString() {
            return "[" + nodeU() + ", " + nodeV() + "]";
        }
    }
}
