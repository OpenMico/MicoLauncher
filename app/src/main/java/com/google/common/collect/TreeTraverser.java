package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;

@Beta
@GwtCompatible
@Deprecated
/* loaded from: classes2.dex */
public abstract class TreeTraverser<T> {
    public abstract Iterable<T> children(T t);

    @Deprecated
    public static <T> TreeTraverser<T> using(final Function<T, ? extends Iterable<T>> function) {
        Preconditions.checkNotNull(function);
        return new TreeTraverser<T>() { // from class: com.google.common.collect.TreeTraverser.1
            @Override // com.google.common.collect.TreeTraverser
            public Iterable<T> children(T t) {
                return (Iterable) Function.this.apply(t);
            }
        };
    }

    @Deprecated
    public final FluentIterable<T> preOrderTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() { // from class: com.google.common.collect.TreeTraverser.2
            /* JADX WARN: Multi-variable type inference failed */
            /* renamed from: a */
            public UnmodifiableIterator<T> iterator() {
                return TreeTraverser.this.a(t);
            }
        };
    }

    UnmodifiableIterator<T> a(T t) {
        return new d(t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class d extends UnmodifiableIterator<T> {
        private final Deque<Iterator<T>> b = new ArrayDeque();

        d(T t) {
            this.b.addLast(Iterators.singletonIterator(Preconditions.checkNotNull(t)));
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.b.isEmpty();
        }

        @Override // java.util.Iterator
        public T next() {
            Iterator<T> last = this.b.getLast();
            T t = (T) Preconditions.checkNotNull(last.next());
            if (!last.hasNext()) {
                this.b.removeLast();
            }
            Iterator<T> it = TreeTraverser.this.children(t).iterator();
            if (it.hasNext()) {
                this.b.addLast(it);
            }
            return t;
        }
    }

    @Deprecated
    public final FluentIterable<T> postOrderTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() { // from class: com.google.common.collect.TreeTraverser.3
            /* JADX WARN: Multi-variable type inference failed */
            /* renamed from: a */
            public UnmodifiableIterator<T> iterator() {
                return TreeTraverser.this.b(t);
            }
        };
    }

    UnmodifiableIterator<T> b(T t) {
        return new b(t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c<T> {
        final T a;
        final Iterator<T> b;

        c(T t, Iterator<T> it) {
            this.a = (T) Preconditions.checkNotNull(t);
            this.b = (Iterator) Preconditions.checkNotNull(it);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class b extends AbstractIterator<T> {
        private final ArrayDeque<c<T>> b = new ArrayDeque<>();

        b(T t) {
            this.b.addLast(a(t));
        }

        @Override // com.google.common.collect.AbstractIterator
        protected T computeNext() {
            while (!this.b.isEmpty()) {
                c<T> last = this.b.getLast();
                if (last.b.hasNext()) {
                    this.b.addLast(a(last.b.next()));
                } else {
                    this.b.removeLast();
                    return last.a;
                }
            }
            return endOfData();
        }

        private c<T> a(T t) {
            return new c<>(t, TreeTraverser.this.children(t).iterator());
        }
    }

    @Deprecated
    public final FluentIterable<T> breadthFirstTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() { // from class: com.google.common.collect.TreeTraverser.4
            /* renamed from: a */
            public UnmodifiableIterator<T> iterator() {
                return new a(t);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class a extends UnmodifiableIterator<T> implements PeekingIterator<T> {
        private final Queue<T> b = new ArrayDeque();

        a(T t) {
            this.b.add(t);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.b.isEmpty();
        }

        @Override // com.google.common.collect.PeekingIterator
        public T peek() {
            return this.b.element();
        }

        @Override // java.util.Iterator, com.google.common.collect.PeekingIterator
        public T next() {
            T remove = this.b.remove();
            Iterables.addAll(this.b, TreeTraverser.this.children(remove));
            return remove;
        }
    }
}
