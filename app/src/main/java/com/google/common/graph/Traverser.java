package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public abstract class Traverser<N> {

    /* loaded from: classes2.dex */
    private enum b {
        PREORDER,
        POSTORDER
    }

    public abstract Iterable<N> breadthFirst(Iterable<? extends N> iterable);

    public abstract Iterable<N> breadthFirst(N n);

    public abstract Iterable<N> depthFirstPostOrder(Iterable<? extends N> iterable);

    public abstract Iterable<N> depthFirstPostOrder(N n);

    public abstract Iterable<N> depthFirstPreOrder(Iterable<? extends N> iterable);

    public abstract Iterable<N> depthFirstPreOrder(N n);

    public static <N> Traverser<N> forGraph(SuccessorsFunction<N> successorsFunction) {
        Preconditions.checkNotNull(successorsFunction);
        return new a(successorsFunction);
    }

    public static <N> Traverser<N> forTree(SuccessorsFunction<N> successorsFunction) {
        Preconditions.checkNotNull(successorsFunction);
        if (successorsFunction instanceof e) {
            Preconditions.checkArgument(((e) successorsFunction).isDirected(), "Undirected graphs can never be trees.");
        }
        if (successorsFunction instanceof Network) {
            Preconditions.checkArgument(((Network) successorsFunction).isDirected(), "Undirected networks can never be trees.");
        }
        return new c(successorsFunction);
    }

    private Traverser() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a<N> extends Traverser<N> {
        private final SuccessorsFunction<N> a;

        a(SuccessorsFunction<N> successorsFunction) {
            super();
            this.a = (SuccessorsFunction) Preconditions.checkNotNull(successorsFunction);
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> breadthFirst(N n) {
            Preconditions.checkNotNull(n);
            return breadthFirst((Iterable) ImmutableSet.of(n));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.Traverser
        public Iterable<N> breadthFirst(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<? extends N> it = iterable.iterator();
            while (it.hasNext()) {
                a((a<N>) it.next());
            }
            return new Iterable<N>() { // from class: com.google.common.graph.Traverser.a.1
                @Override // java.lang.Iterable
                public Iterator<N> iterator() {
                    return new C0111a(iterable);
                }
            };
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPreOrder(N n) {
            Preconditions.checkNotNull(n);
            return depthFirstPreOrder((Iterable) ImmutableSet.of(n));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPreOrder(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<? extends N> it = iterable.iterator();
            while (it.hasNext()) {
                a((a<N>) it.next());
            }
            return new Iterable<N>() { // from class: com.google.common.graph.Traverser.a.2
                @Override // java.lang.Iterable
                public Iterator<N> iterator() {
                    return new b(iterable, b.PREORDER);
                }
            };
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPostOrder(N n) {
            Preconditions.checkNotNull(n);
            return depthFirstPostOrder((Iterable) ImmutableSet.of(n));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPostOrder(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<? extends N> it = iterable.iterator();
            while (it.hasNext()) {
                a((a<N>) it.next());
            }
            return new Iterable<N>() { // from class: com.google.common.graph.Traverser.a.3
                @Override // java.lang.Iterable
                public Iterator<N> iterator() {
                    return new b(iterable, b.POSTORDER);
                }
            };
        }

        private void a(N n) {
            this.a.successors(n);
        }

        /* renamed from: com.google.common.graph.Traverser$a$a  reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        private final class C0111a extends UnmodifiableIterator<N> {
            private final Queue<N> b = new ArrayDeque();
            private final Set<N> c = new HashSet();

            /* JADX WARN: Multi-variable type inference failed */
            C0111a(Iterable<? extends N> iterable) {
                for (Object obj : iterable) {
                    if (this.c.add(obj)) {
                        this.b.add(obj);
                    }
                }
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return !this.b.isEmpty();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Iterator
            public N next() {
                N remove = this.b.remove();
                for (Object obj : a.this.a.successors(remove)) {
                    if (this.c.add(obj)) {
                        this.b.add(obj);
                    }
                }
                return remove;
            }
        }

        /* loaded from: classes2.dex */
        private final class b extends AbstractIterator<N> {
            private final Deque<a<N>.b.C0112a> b = new ArrayDeque();
            private final Set<N> c = new HashSet();
            private final b d;

            b(Iterable<? extends N> iterable, b bVar) {
                this.b.push(new C0112a(null, iterable));
                this.d = bVar;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.AbstractIterator
            protected N computeNext() {
                while (!this.b.isEmpty()) {
                    a<N>.b.C0112a first = this.b.getFirst();
                    boolean add = this.c.add(first.a);
                    boolean z = true;
                    boolean z2 = !first.b.hasNext();
                    if ((!add || this.d != b.PREORDER) && (!z2 || this.d != b.POSTORDER)) {
                        z = false;
                    }
                    if (z2) {
                        this.b.pop();
                    } else {
                        Object next = first.b.next();
                        if (!this.c.contains(next)) {
                            this.b.push(a(next));
                        }
                    }
                    if (z && first.a != null) {
                        return first.a;
                    }
                }
                return (N) endOfData();
            }

            a<N>.b.C0112a a(N n) {
                return new C0112a(n, a.this.a.successors(n));
            }

            /* JADX INFO: Access modifiers changed from: private */
            /* renamed from: com.google.common.graph.Traverser$a$b$a  reason: collision with other inner class name */
            /* loaded from: classes2.dex */
            public final class C0112a {
                @NullableDecl
                final N a;
                final Iterator<? extends N> b;

                C0112a(N n, @NullableDecl Iterable<? extends N> iterable) {
                    this.a = n;
                    this.b = iterable.iterator();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c<N> extends Traverser<N> {
        private final SuccessorsFunction<N> a;

        c(SuccessorsFunction<N> successorsFunction) {
            super();
            this.a = (SuccessorsFunction) Preconditions.checkNotNull(successorsFunction);
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> breadthFirst(N n) {
            Preconditions.checkNotNull(n);
            return breadthFirst((Iterable) ImmutableSet.of(n));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.Traverser
        public Iterable<N> breadthFirst(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<? extends N> it = iterable.iterator();
            while (it.hasNext()) {
                a((c<N>) it.next());
            }
            return new Iterable<N>() { // from class: com.google.common.graph.Traverser.c.1
                @Override // java.lang.Iterable
                public Iterator<N> iterator() {
                    return new a(iterable);
                }
            };
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPreOrder(N n) {
            Preconditions.checkNotNull(n);
            return depthFirstPreOrder((Iterable) ImmutableSet.of(n));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPreOrder(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<? extends N> it = iterable.iterator();
            while (it.hasNext()) {
                a((c<N>) it.next());
            }
            return new Iterable<N>() { // from class: com.google.common.graph.Traverser.c.2
                @Override // java.lang.Iterable
                public Iterator<N> iterator() {
                    return new C0113c(iterable);
                }
            };
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPostOrder(N n) {
            Preconditions.checkNotNull(n);
            return depthFirstPostOrder((Iterable) ImmutableSet.of(n));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPostOrder(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<? extends N> it = iterable.iterator();
            while (it.hasNext()) {
                a((c<N>) it.next());
            }
            return new Iterable<N>() { // from class: com.google.common.graph.Traverser.c.3
                @Override // java.lang.Iterable
                public Iterator<N> iterator() {
                    return new b(iterable);
                }
            };
        }

        private void a(N n) {
            this.a.successors(n);
        }

        /* loaded from: classes2.dex */
        private final class a extends UnmodifiableIterator<N> {
            private final Queue<N> b = new ArrayDeque();

            /* JADX WARN: Multi-variable type inference failed */
            a(Iterable<? extends N> iterable) {
                Iterator<? extends N> it = iterable.iterator();
                while (it.hasNext()) {
                    this.b.add(it.next());
                }
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return !this.b.isEmpty();
            }

            @Override // java.util.Iterator
            public N next() {
                N remove = this.b.remove();
                Iterables.addAll(this.b, c.this.a.successors(remove));
                return remove;
            }
        }

        /* renamed from: com.google.common.graph.Traverser$c$c  reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        private final class C0113c extends UnmodifiableIterator<N> {
            private final Deque<Iterator<? extends N>> b = new ArrayDeque();

            C0113c(Iterable<? extends N> iterable) {
                this.b.addLast(iterable.iterator());
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return !this.b.isEmpty();
            }

            @Override // java.util.Iterator
            public N next() {
                Iterator<? extends N> last = this.b.getLast();
                N n = (N) Preconditions.checkNotNull(last.next());
                if (!last.hasNext()) {
                    this.b.removeLast();
                }
                Iterator<? extends N> it = c.this.a.successors(n).iterator();
                if (it.hasNext()) {
                    this.b.addLast(it);
                }
                return n;
            }
        }

        /* loaded from: classes2.dex */
        private final class b extends AbstractIterator<N> {
            private final ArrayDeque<c<N>.b.a> b = new ArrayDeque<>();

            b(Iterable<? extends N> iterable) {
                this.b.addLast(new a(null, iterable));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.AbstractIterator
            protected N computeNext() {
                while (!this.b.isEmpty()) {
                    c<N>.b.a last = this.b.getLast();
                    if (last.b.hasNext()) {
                        this.b.addLast(a(last.b.next()));
                    } else {
                        this.b.removeLast();
                        if (last.a != null) {
                            return last.a;
                        }
                    }
                }
                return (N) endOfData();
            }

            c<N>.b.a a(N n) {
                return new a(n, c.this.a.successors(n));
            }

            /* JADX INFO: Access modifiers changed from: private */
            /* loaded from: classes2.dex */
            public final class a {
                @NullableDecl
                final N a;
                final Iterator<? extends N> b;

                a(N n, @NullableDecl Iterable<? extends N> iterable) {
                    this.a = n;
                    this.b = iterable.iterator();
                }
            }
        }
    }
}
