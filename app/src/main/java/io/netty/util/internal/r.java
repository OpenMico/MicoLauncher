package io.netty.util.internal;

import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.api.b;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MpscLinkedQueue.java */
/* loaded from: classes4.dex */
public final class r<E> extends v<E> implements Queue<E> {
    private static final long serialVersionUID = -1878402552271506449L;
    long p00;
    long p01;
    long p02;
    long p03;
    long p04;
    long p05;
    long p06;
    long p07;
    long p30;
    long p31;
    long p32;
    long p33;
    long p34;
    long p35;
    long p36;
    long p37;

    /* JADX INFO: Access modifiers changed from: package-private */
    public r() {
        a aVar = new a(null);
        a(aVar);
        c(aVar);
    }

    private MpscLinkedQueueNode<E> c() {
        MpscLinkedQueueNode<E> a2 = a();
        MpscLinkedQueueNode<E> d = a2.d();
        if (d != null || a2 == b()) {
            return d;
        }
        do {
            d = a2.d();
        } while (d == null);
        return d;
    }

    @Override // java.util.Queue
    public boolean offer(E e) {
        MpscLinkedQueueNode<E> mpscLinkedQueueNode;
        if (e != null) {
            if (e instanceof MpscLinkedQueueNode) {
                mpscLinkedQueueNode = (MpscLinkedQueueNode) e;
                mpscLinkedQueueNode.a(null);
            } else {
                mpscLinkedQueueNode = new a(e);
            }
            d(mpscLinkedQueueNode).a(mpscLinkedQueueNode);
            return true;
        }
        throw new NullPointerException(b.p);
    }

    @Override // java.util.Queue
    public E poll() {
        MpscLinkedQueueNode<E> c = c();
        if (c == null) {
            return null;
        }
        MpscLinkedQueueNode<E> a2 = a();
        b(c);
        a2.e();
        return c.clearMaybe();
    }

    @Override // java.util.Queue
    public E peek() {
        MpscLinkedQueueNode<E> c = c();
        if (c == null) {
            return null;
        }
        return c.value();
    }

    @Override // java.util.Collection
    public int size() {
        MpscLinkedQueueNode<E> c = c();
        int i = 0;
        while (c != null && c.value() != null) {
            MpscLinkedQueueNode<E> d = c.d();
            if (c == d || (i = i + 1) == Integer.MAX_VALUE) {
                break;
            }
            c = d;
        }
        return i;
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return a() == b();
    }

    @Override // java.util.Collection
    public boolean contains(Object obj) {
        E value;
        MpscLinkedQueueNode<E> c = c();
        while (c != null && (value = c.value()) != null) {
            if (value == obj) {
                return true;
            }
            MpscLinkedQueueNode<E> d = c.d();
            if (c == d) {
                return false;
            }
            c = d;
        }
        return false;
    }

    @Override // java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new ReadOnlyIterator(d().iterator());
    }

    @Override // java.util.Queue, java.util.Collection
    public boolean add(E e) {
        if (offer(e)) {
            return true;
        }
        throw new IllegalStateException("queue full");
    }

    @Override // java.util.Queue
    public E remove() {
        E poll = poll();
        if (poll != null) {
            return poll;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Queue
    public E element() {
        E peek = peek();
        if (peek != null) {
            return peek;
        }
        throw new NoSuchElementException();
    }

    private List<E> a(int i) {
        return a(new ArrayList(i));
    }

    private List<E> d() {
        return a(new ArrayList());
    }

    private List<E> a(List<E> list) {
        MpscLinkedQueueNode<E> d;
        MpscLinkedQueueNode<E> c = c();
        while (c != null) {
            E value = c.value();
            if (value == null || !list.add(value) || c == (d = c.d())) {
                break;
            }
            c = d;
        }
        return list;
    }

    @Override // java.util.Collection
    public Object[] toArray() {
        return d().toArray();
    }

    @Override // java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) a(tArr.length).toArray(tArr);
    }

    @Override // java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Collection
    public boolean addAll(Collection<? extends E> collection) {
        if (collection == null) {
            throw new NullPointerException(ai.aD);
        } else if (collection != this) {
            boolean z = false;
            Iterator<? extends E> it = collection.iterator();
            while (it.hasNext()) {
                add(it.next());
                z = true;
            }
            return z;
        } else {
            throw new IllegalArgumentException("c == this");
        }
    }

    @Override // java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public void clear() {
        do {
        } while (poll() != null);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            objectOutputStream.writeObject(it.next());
        }
        objectOutputStream.writeObject(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        a aVar = new a(null);
        a(aVar);
        c(aVar);
        while (true) {
            Object readObject = objectInputStream.readObject();
            if (readObject != null) {
                add(readObject);
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MpscLinkedQueue.java */
    /* loaded from: classes4.dex */
    public static final class a<T> extends MpscLinkedQueueNode<T> {
        private T a;

        a(T t) {
            this.a = t;
        }

        @Override // io.netty.util.internal.MpscLinkedQueueNode
        public T value() {
            return this.a;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.util.internal.MpscLinkedQueueNode
        public T clearMaybe() {
            T t = this.a;
            this.a = null;
            return t;
        }
    }
}
