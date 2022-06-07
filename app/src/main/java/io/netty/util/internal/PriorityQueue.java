package io.netty.util.internal;

import io.netty.util.internal.PriorityQueueNode;
import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/* loaded from: classes4.dex */
public final class PriorityQueue<T extends PriorityQueueNode<T>> extends AbstractQueue<T> implements Queue<T> {
    private static final PriorityQueueNode[] a = new PriorityQueueNode[0];
    private T[] b;
    private int c;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Queue
    public /* bridge */ /* synthetic */ boolean offer(Object obj) {
        return offer((PriorityQueue<T>) ((PriorityQueueNode) obj));
    }

    public PriorityQueue() {
        this(8);
    }

    public PriorityQueue(int i) {
        this.b = (T[]) (i != 0 ? new PriorityQueueNode[i] : a);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        return this.c;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return this.c == 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean contains(Object obj) {
        PriorityQueueNode priorityQueueNode;
        int priorityQueueIndex;
        return (obj instanceof PriorityQueueNode) && (priorityQueueIndex = (priorityQueueNode = (PriorityQueueNode) obj).priorityQueueIndex()) >= 0 && priorityQueueIndex < this.c && priorityQueueNode.equals(this.b[priorityQueueIndex]);
    }

    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        for (int i = 0; i < this.c; i++) {
            T t = this.b[i];
            if (t != null) {
                t.priorityQueueIndex(-1);
                this.b[i] = null;
            }
        }
        this.c = 0;
    }

    public boolean offer(T t) {
        ObjectUtil.checkNotNull(t, "e");
        if (t.priorityQueueIndex() == -1) {
            int i = this.c;
            T[] tArr = this.b;
            if (i >= tArr.length) {
                this.b = (T[]) ((PriorityQueueNode[]) Arrays.copyOf(tArr, tArr.length + (tArr.length < 64 ? tArr.length + 2 : tArr.length >>> 1)));
            }
            int i2 = this.c;
            this.c = i2 + 1;
            b(i2, t);
            return true;
        }
        throw new IllegalArgumentException("e.priorityQueueIndex(): " + t.priorityQueueIndex() + " (expected: -1)");
    }

    @Override // java.util.Queue
    public T poll() {
        if (this.c == 0) {
            return null;
        }
        T t = this.b[0];
        t.priorityQueueIndex(-1);
        T[] tArr = this.b;
        int i = this.c - 1;
        this.c = i;
        T t2 = tArr[i];
        int i2 = this.c;
        tArr[i2] = null;
        if (i2 != 0) {
            a(0, t2);
        }
        return t;
    }

    @Override // java.util.Queue
    public T peek() {
        if (this.c == 0) {
            return null;
        }
        return this.b[0];
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean remove(Object obj) {
        int i;
        if (!contains(obj)) {
            return false;
        }
        PriorityQueueNode priorityQueueNode = (PriorityQueueNode) obj;
        int priorityQueueIndex = priorityQueueNode.priorityQueueIndex();
        priorityQueueNode.priorityQueueIndex(-1);
        int i2 = this.c - 1;
        this.c = i2;
        if (i2 == 0 || (i = this.c) == priorityQueueIndex) {
            this.b[priorityQueueIndex] = null;
            return true;
        }
        T[] tArr = this.b;
        T t = tArr[i];
        tArr[priorityQueueIndex] = t;
        tArr[i] = null;
        if (priorityQueueNode.compareTo(t) < 0) {
            a(priorityQueueIndex, t);
        } else {
            b(priorityQueueIndex, t);
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public Object[] toArray() {
        return Arrays.copyOf(this.b, this.c);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public <X> X[] toArray(X[] xArr) {
        int length = xArr.length;
        int i = this.c;
        if (length < i) {
            return (X[]) Arrays.copyOf(this.b, i, xArr.getClass());
        }
        System.arraycopy(this.b, 0, xArr, 0, i);
        int length2 = xArr.length;
        int i2 = this.c;
        if (length2 > i2) {
            xArr[i2] = null;
        }
        return xArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        return new a();
    }

    /* loaded from: classes4.dex */
    private final class a implements Iterator<T> {
        private int b;

        private a() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b < PriorityQueue.this.c;
        }

        /* renamed from: a */
        public T next() {
            if (this.b < PriorityQueue.this.c) {
                PriorityQueueNode[] priorityQueueNodeArr = PriorityQueue.this.b;
                int i = this.b;
                this.b = i + 1;
                return (T) priorityQueueNodeArr[i];
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    private void a(int i, T t) {
        int i2 = this.c >>> 1;
        while (i < i2) {
            i = (i << 1) + 1;
            T[] tArr = this.b;
            T t2 = tArr[i];
            int i3 = i + 1;
            if (i3 < this.c && t2.compareTo(tArr[i3]) > 0) {
                t2 = this.b[i3];
                i = i3;
            }
            if (t.compareTo(t2) <= 0) {
                break;
            }
            this.b[i] = t2;
            t2.priorityQueueIndex(i);
        }
        this.b[i] = t;
        t.priorityQueueIndex(i);
    }

    private void b(int i, T t) {
        while (i > 0) {
            int i2 = (i - 1) >>> 1;
            T t2 = this.b[i2];
            if (t.compareTo(t2) >= 0) {
                break;
            }
            this.b[i] = t2;
            t2.priorityQueueIndex(i);
            i = i2;
        }
        this.b[i] = t;
        t.priorityQueueIndex(i);
    }
}
