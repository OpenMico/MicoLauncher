package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;
import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public final class MinMaxPriorityQueue<E> extends AbstractQueue<E> {
    @VisibleForTesting
    final int a;
    private final MinMaxPriorityQueue<E>.a b;
    private final MinMaxPriorityQueue<E>.a c;
    private Object[] d;
    private int e;
    private int f;

    public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create() {
        return new Builder(Ordering.natural()).create();
    }

    public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create(Iterable<? extends E> iterable) {
        return new Builder(Ordering.natural()).create(iterable);
    }

    public static <B> Builder<B> orderedBy(Comparator<B> comparator) {
        return new Builder<>(comparator);
    }

    public static Builder<Comparable> expectedSize(int i) {
        return new Builder(Ordering.natural()).expectedSize(i);
    }

    public static Builder<Comparable> maximumSize(int i) {
        return new Builder(Ordering.natural()).maximumSize(i);
    }

    @Beta
    /* loaded from: classes2.dex */
    public static final class Builder<B> {
        private final Comparator<B> a;
        private int b;
        private int c;

        private Builder(Comparator<B> comparator) {
            this.b = -1;
            this.c = Integer.MAX_VALUE;
            this.a = (Comparator) Preconditions.checkNotNull(comparator);
        }

        @CanIgnoreReturnValue
        public Builder<B> expectedSize(int i) {
            Preconditions.checkArgument(i >= 0);
            this.b = i;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<B> maximumSize(int i) {
            Preconditions.checkArgument(i > 0);
            this.c = i;
            return this;
        }

        public <T extends B> MinMaxPriorityQueue<T> create() {
            return create(Collections.emptySet());
        }

        /* JADX WARN: Multi-variable type inference failed */
        public <T extends B> MinMaxPriorityQueue<T> create(Iterable<? extends T> iterable) {
            MinMaxPriorityQueue<T> minMaxPriorityQueue = (MinMaxPriorityQueue<T>) new MinMaxPriorityQueue(this, MinMaxPriorityQueue.a(this.b, this.c, iterable));
            Iterator<? extends T> it = iterable.iterator();
            while (it.hasNext()) {
                minMaxPriorityQueue.offer(it.next());
            }
            return minMaxPriorityQueue;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public <T extends B> Ordering<T> a() {
            return Ordering.from(this.a);
        }
    }

    private MinMaxPriorityQueue(Builder<? super E> builder, int i) {
        Ordering a2 = builder.a();
        this.b = new a(a2);
        this.c = new a(a2.reverse());
        MinMaxPriorityQueue<E>.a aVar = this.b;
        MinMaxPriorityQueue<E>.a aVar2 = this.c;
        aVar.b = aVar2;
        aVar2.b = aVar;
        this.a = ((Builder) builder).c;
        this.d = new Object[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        return this.e;
    }

    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection, java.util.Queue
    @CanIgnoreReturnValue
    public boolean add(E e) {
        offer(e);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
    @CanIgnoreReturnValue
    public boolean addAll(Collection<? extends E> collection) {
        Iterator<? extends E> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            offer(it.next());
            z = true;
        }
        return z;
    }

    @Override // java.util.Queue
    @CanIgnoreReturnValue
    public boolean offer(E e) {
        Preconditions.checkNotNull(e);
        this.f++;
        int i = this.e;
        this.e = i + 1;
        b();
        e(i).a(i, (int) e);
        return this.e <= this.a || pollLast() != e;
    }

    @Override // java.util.Queue
    @CanIgnoreReturnValue
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        return d(0);
    }

    E a(int i) {
        return (E) this.d[i];
    }

    @Override // java.util.Queue
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return a(0);
    }

    private int a() {
        switch (this.e) {
            case 1:
                return 0;
            case 2:
                return 1;
            default:
                return this.c.a(1, 2) <= 0 ? 1 : 2;
        }
    }

    @CanIgnoreReturnValue
    public E pollFirst() {
        return poll();
    }

    @CanIgnoreReturnValue
    public E removeFirst() {
        return remove();
    }

    public E peekFirst() {
        return peek();
    }

    @CanIgnoreReturnValue
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        return d(a());
    }

    @CanIgnoreReturnValue
    public E removeLast() {
        if (!isEmpty()) {
            return d(a());
        }
        throw new NoSuchElementException();
    }

    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return a(a());
    }

    @VisibleForTesting
    @CanIgnoreReturnValue
    b<E> b(int i) {
        Preconditions.checkPositionIndex(i, this.e);
        this.f++;
        this.e--;
        int i2 = this.e;
        if (i2 == i) {
            this.d[i2] = null;
            return null;
        }
        E a2 = a(i2);
        int a3 = e(this.e).a((MinMaxPriorityQueue<E>.a) a2);
        if (a3 == i) {
            this.d[this.e] = null;
            return null;
        }
        E a4 = a(this.e);
        this.d[this.e] = null;
        b<E> a5 = a(i, (int) a4);
        if (a3 >= i) {
            return a5;
        }
        if (a5 == null) {
            return new b<>(a2, a4);
        }
        return new b<>(a2, a5.b);
    }

    private b<E> a(int i, E e) {
        MinMaxPriorityQueue<E>.a e2 = e(i);
        int c2 = e2.c(i);
        int b2 = e2.b(c2, (int) e);
        if (b2 == c2) {
            return e2.a(i, c2, e);
        }
        if (b2 < i) {
            return new b<>(e, a(i));
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class b<E> {
        final E a;
        final E b;

        b(E e, E e2) {
            this.a = e;
            this.b = e2;
        }
    }

    private E d(int i) {
        E a2 = a(i);
        b(i);
        return a2;
    }

    private MinMaxPriorityQueue<E>.a e(int i) {
        return c(i) ? this.b : this.c;
    }

    @VisibleForTesting
    static boolean c(int i) {
        int i2 = ~(~(i + 1));
        Preconditions.checkState(i2 > 0, "negative index");
        return (1431655765 & i2) > (i2 & (-1431655766));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class a {
        final Ordering<E> a;
        @Weak
        @MonotonicNonNullDecl
        MinMaxPriorityQueue<E>.a b;

        private int d(int i) {
            return (i * 2) + 1;
        }

        private int e(int i) {
            return (i * 2) + 2;
        }

        a(Ordering<E> ordering) {
            this.a = ordering;
        }

        int a(int i, int i2) {
            return ((Ordering<E>) this.a).compare(MinMaxPriorityQueue.this.a(i), MinMaxPriorityQueue.this.a(i2));
        }

        b<E> a(int i, int i2, E e) {
            Object obj;
            int d = d(i2, e);
            if (d == i2) {
                return null;
            }
            if (d < i) {
                obj = MinMaxPriorityQueue.this.a(i);
            } else {
                obj = MinMaxPriorityQueue.this.a(f(i));
            }
            if (this.b.b(d, (int) e) < i) {
                return new b<>(e, obj);
            }
            return null;
        }

        void a(int i, E e) {
            a aVar;
            int c = c(i, e);
            if (c == i) {
                c = i;
                aVar = this;
            } else {
                aVar = this.b;
            }
            aVar.b(c, (int) e);
        }

        @CanIgnoreReturnValue
        int b(int i, E e) {
            while (i > 2) {
                int g = g(i);
                Object a = MinMaxPriorityQueue.this.a(g);
                if (((Ordering<E>) this.a).compare(a, e) <= 0) {
                    break;
                }
                MinMaxPriorityQueue.this.d[i] = a;
                i = g;
            }
            MinMaxPriorityQueue.this.d[i] = e;
            return i;
        }

        int b(int i, int i2) {
            if (i >= MinMaxPriorityQueue.this.e) {
                return -1;
            }
            Preconditions.checkState(i > 0);
            int min = Math.min(i, MinMaxPriorityQueue.this.e - i2) + i2;
            for (int i3 = i + 1; i3 < min; i3++) {
                if (a(i3, i) < 0) {
                    i = i3;
                }
            }
            return i;
        }

        int a(int i) {
            return b(d(i), 2);
        }

        int b(int i) {
            int d = d(i);
            if (d < 0) {
                return -1;
            }
            return b(d(d), 4);
        }

        int c(int i, E e) {
            int e2;
            if (i == 0) {
                MinMaxPriorityQueue.this.d[0] = e;
                return 0;
            }
            int f = f(i);
            Object a = MinMaxPriorityQueue.this.a(f);
            if (!(f == 0 || (e2 = e(f(f))) == f || d(e2) < MinMaxPriorityQueue.this.e)) {
                Object a2 = MinMaxPriorityQueue.this.a(e2);
                if (((Ordering<E>) this.a).compare(a2, a) < 0) {
                    f = e2;
                    a = a2;
                }
            }
            if (((Ordering<E>) this.a).compare(a, e) < 0) {
                MinMaxPriorityQueue.this.d[i] = a;
                MinMaxPriorityQueue.this.d[f] = e;
                return f;
            }
            MinMaxPriorityQueue.this.d[i] = e;
            return i;
        }

        int a(E e) {
            int e2;
            int f = f(MinMaxPriorityQueue.this.e);
            if (!(f == 0 || (e2 = e(f(f))) == f || d(e2) < MinMaxPriorityQueue.this.e)) {
                Object a = MinMaxPriorityQueue.this.a(e2);
                if (((Ordering<E>) this.a).compare(a, e) < 0) {
                    MinMaxPriorityQueue.this.d[e2] = e;
                    MinMaxPriorityQueue.this.d[MinMaxPriorityQueue.this.e] = a;
                    return e2;
                }
            }
            return MinMaxPriorityQueue.this.e;
        }

        int d(int i, E e) {
            int a = a(i);
            if (a <= 0 || ((Ordering<E>) this.a).compare(MinMaxPriorityQueue.this.a(a), e) >= 0) {
                return c(i, e);
            }
            MinMaxPriorityQueue.this.d[i] = MinMaxPriorityQueue.this.a(a);
            MinMaxPriorityQueue.this.d[a] = e;
            return a;
        }

        int c(int i) {
            while (true) {
                int b = b(i);
                if (b <= 0) {
                    return i;
                }
                MinMaxPriorityQueue.this.d[i] = MinMaxPriorityQueue.this.a(b);
                i = b;
            }
        }

        private int f(int i) {
            return (i - 1) / 2;
        }

        private int g(int i) {
            return f(f(i));
        }
    }

    /* loaded from: classes2.dex */
    private class c implements Iterator<E> {
        private int b;
        private int c;
        private int d;
        @MonotonicNonNullDecl
        private Queue<E> e;
        @MonotonicNonNullDecl
        private List<E> f;
        @NullableDecl
        private E g;
        private boolean h;

        private c() {
            this.b = -1;
            this.c = -1;
            this.d = MinMaxPriorityQueue.this.f;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            a();
            a(this.b + 1);
            if (this.c < MinMaxPriorityQueue.this.size()) {
                return true;
            }
            Queue<E> queue = this.e;
            return queue != null && !queue.isEmpty();
        }

        @Override // java.util.Iterator
        public E next() {
            a();
            a(this.b + 1);
            if (this.c < MinMaxPriorityQueue.this.size()) {
                this.b = this.c;
                this.h = true;
                return (E) MinMaxPriorityQueue.this.a(this.b);
            }
            if (this.e != null) {
                this.b = MinMaxPriorityQueue.this.size();
                this.g = this.e.poll();
                E e = this.g;
                if (e != null) {
                    this.h = true;
                    return e;
                }
            }
            throw new NoSuchElementException("iterator moved past last element in queue.");
        }

        @Override // java.util.Iterator
        public void remove() {
            t.a(this.h);
            a();
            this.h = false;
            this.d++;
            if (this.b < MinMaxPriorityQueue.this.size()) {
                b<E> b = MinMaxPriorityQueue.this.b(this.b);
                if (b != null) {
                    if (this.e == null) {
                        this.e = new ArrayDeque();
                        this.f = new ArrayList(3);
                    }
                    if (!a(this.f, b.a)) {
                        this.e.add(b.a);
                    }
                    if (!a(this.e, b.b)) {
                        this.f.add(b.b);
                    }
                }
                this.b--;
                this.c--;
                return;
            }
            Preconditions.checkState(a(this.g));
            this.g = null;
        }

        private boolean a(Iterable<E> iterable, E e) {
            Iterator<E> it = iterable.iterator();
            while (it.hasNext()) {
                if (it.next() == e) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }

        private boolean a(Object obj) {
            for (int i = 0; i < MinMaxPriorityQueue.this.e; i++) {
                if (MinMaxPriorityQueue.this.d[i] == obj) {
                    MinMaxPriorityQueue.this.b(i);
                    return true;
                }
            }
            return false;
        }

        private void a() {
            if (MinMaxPriorityQueue.this.f != this.d) {
                throw new ConcurrentModificationException();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void a(int i) {
            if (this.c < i) {
                if (this.f != null) {
                    while (i < MinMaxPriorityQueue.this.size() && a(this.f, MinMaxPriorityQueue.this.a(i))) {
                        i++;
                    }
                }
                this.c = i;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new c();
    }

    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        for (int i = 0; i < this.e; i++) {
            this.d[i] = null;
        }
        this.e = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public Object[] toArray() {
        int i = this.e;
        Object[] objArr = new Object[i];
        System.arraycopy(this.d, 0, objArr, 0, i);
        return objArr;
    }

    public Comparator<? super E> comparator() {
        return this.b.a;
    }

    @VisibleForTesting
    static int a(int i, int i2, Iterable<?> iterable) {
        if (i == -1) {
            i = 11;
        }
        if (iterable instanceof Collection) {
            i = Math.max(i, ((Collection) iterable).size());
        }
        return a(i, i2);
    }

    private void b() {
        if (this.e > this.d.length) {
            Object[] objArr = new Object[c()];
            Object[] objArr2 = this.d;
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
            this.d = objArr;
        }
    }

    private int c() {
        int length = this.d.length;
        return a(length < 64 ? (length + 1) * 2 : IntMath.checkedMultiply(length / 2, 3), this.a);
    }

    private static int a(int i, int i2) {
        return Math.min(i - 1, i2) + 1;
    }
}
