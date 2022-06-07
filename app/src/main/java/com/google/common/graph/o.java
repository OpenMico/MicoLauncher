package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: EndpointPairIterator.java */
/* loaded from: classes2.dex */
public abstract class o<N> extends AbstractIterator<EndpointPair<N>> {
    protected N a;
    protected Iterator<N> b;
    private final e<N> c;
    private final Iterator<N> d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <N> o<N> a(e<N> eVar) {
        return eVar.isDirected() ? new a(eVar) : new b(eVar);
    }

    private o(e<N> eVar) {
        this.a = null;
        this.b = ImmutableSet.of().iterator();
        this.c = eVar;
        this.d = eVar.nodes().iterator();
    }

    protected final boolean a() {
        Preconditions.checkState(!this.b.hasNext());
        if (!this.d.hasNext()) {
            return false;
        }
        this.a = this.d.next();
        this.b = this.c.successors((e<N>) this.a).iterator();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: EndpointPairIterator.java */
    /* loaded from: classes2.dex */
    public static final class a<N> extends o<N> {
        private a(e<N> eVar) {
            super(eVar);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public EndpointPair<N> computeNext() {
            while (!this.b.hasNext()) {
                if (!a()) {
                    return endOfData();
                }
            }
            return EndpointPair.ordered(this.a, this.b.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: EndpointPairIterator.java */
    /* loaded from: classes2.dex */
    public static final class b<N> extends o<N> {
        private Set<N> c;

        private b(e<N> eVar) {
            super(eVar);
            this.c = Sets.newHashSetWithExpectedSize(eVar.nodes().size());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: b */
        public EndpointPair<N> computeNext() {
            while (true) {
                if (this.b.hasNext()) {
                    Object next = this.b.next();
                    if (!this.c.contains(next)) {
                        return EndpointPair.unordered(this.a, next);
                    }
                } else {
                    this.c.add(this.a);
                    if (!a()) {
                        this.c = null;
                        return endOfData();
                    }
                }
            }
        }
    }
}
