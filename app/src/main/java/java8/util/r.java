package java8.util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;
import java8.util.Iterators;

/* compiled from: ImmutableCollections.java */
/* loaded from: classes5.dex */
public final class r {
    static final long a = ((int) ((System.nanoTime() * 2611923443488327891L) >> 16)) & 4294967295L;
    static final boolean b;
    static final Object c;
    static final g<?> d;
    static final k<?> e;
    static final i<?, ?> f;

    static {
        b = (a & 1) == 0;
        c = new Object();
        d = new g<>(new Object[0]);
        e = new k<>(new Object[0]);
        f = new i<>(new Object[0]);
    }

    static UnsupportedOperationException a() {
        return new UnsupportedOperationException();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ImmutableCollections.java */
    /* loaded from: classes5.dex */
    public static abstract class a<E> extends AbstractCollection<E> {
        a() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean add(E e) {
            throw r.a();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            throw r.a();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            throw r.a();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            throw r.a();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw r.a();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw r.a();
        }
    }

    public static <E> List<E> a(Collection<? extends E> collection) {
        if (!(collection instanceof b) || collection.getClass() == l.class) {
            return Lists.of(collection.toArray());
        }
        return (List) collection;
    }

    /* compiled from: ImmutableCollections.java */
    /* loaded from: classes5.dex */
    public static abstract class b<E> extends a<E> implements List<E>, RandomAccess {
        b() {
        }

        @Override // java.util.List
        public void add(int i, E e) {
            throw r.a();
        }

        @Override // java.util.List
        public boolean addAll(int i, Collection<? extends E> collection) {
            r.a(i, size());
            throw r.a();
        }

        @Override // java.util.List
        public E remove(int i) {
            throw r.a();
        }

        @Override // java.util.List
        public E set(int i, E e) {
            throw r.a();
        }

        @Override // java.util.List
        public List<E> subList(int i, int i2) {
            a(i, i2, size());
            return l.a(this, i, i2);
        }

        static void a(int i, int i2, int i3) {
            if (i < 0) {
                throw new IndexOutOfBoundsException("fromIndex = " + i);
            } else if (i2 > i3) {
                throw new IndexOutOfBoundsException("toIndex = " + i2);
            } else if (i > i2) {
                throw new IllegalArgumentException("fromIndex(" + i + ") > toIndex(" + i2 + ")");
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
        public Iterator<E> iterator() {
            return new f(this, size());
        }

        @Override // java.util.List
        public ListIterator<E> listIterator() {
            return listIterator(0);
        }

        @Override // java.util.List
        public ListIterator<E> listIterator(int i) {
            int size = size();
            if (i >= 0 && i <= size) {
                return new f(this, size, i);
            }
            throw a(i);
        }

        @Override // java.util.Collection, java.util.List
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof List)) {
                return false;
            }
            Iterator<E> it = ((List) obj).iterator();
            int size = size();
            for (int i = 0; i < size; i++) {
                if (!it.hasNext() || !get(i).equals(it.next())) {
                    return false;
                }
            }
            return !it.hasNext();
        }

        @Override // java.util.List
        public int indexOf(Object obj) {
            Objects.requireNonNull(obj);
            int size = size();
            for (int i = 0; i < size; i++) {
                if (obj.equals(get(i))) {
                    return i;
                }
            }
            return -1;
        }

        @Override // java.util.List
        public int lastIndexOf(Object obj) {
            Objects.requireNonNull(obj);
            for (int size = size() - 1; size >= 0; size--) {
                if (obj.equals(get(size))) {
                    return size;
                }
            }
            return -1;
        }

        @Override // java.util.Collection, java.util.List
        public int hashCode() {
            int size = size();
            int i = 1;
            for (int i2 = 0; i2 < size; i2++) {
                i = (i * 31) + get(i2).hashCode();
            }
            return i;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object obj) {
            return indexOf(obj) >= 0;
        }

        IndexOutOfBoundsException a(int i) {
            return new IndexOutOfBoundsException("Index: " + i + " Size: " + size());
        }
    }

    /* compiled from: ImmutableCollections.java */
    /* loaded from: classes5.dex */
    public static final class f<E> implements ListIterator<E> {
        private final List<E> a;
        private final int b;
        private final boolean c;
        private int d;

        f(List<E> list, int i) {
            this.a = list;
            this.b = i;
            this.d = 0;
            this.c = false;
        }

        f(List<E> list, int i, int i2) {
            this.a = list;
            this.b = i;
            this.d = i2;
            this.c = true;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.d != this.b;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public E next() {
            try {
                int i = this.d;
                E e = this.a.get(i);
                this.d = i + 1;
                return e;
            } catch (IndexOutOfBoundsException unused) {
                throw new NoSuchElementException();
            }
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            throw r.a();
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            if (this.c) {
                return this.d != 0;
            }
            throw r.a();
        }

        @Override // java.util.ListIterator
        public E previous() {
            if (this.c) {
                try {
                    int i = this.d - 1;
                    E e = this.a.get(i);
                    this.d = i;
                    return e;
                } catch (IndexOutOfBoundsException unused) {
                    throw new NoSuchElementException();
                }
            } else {
                throw r.a();
            }
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            if (this.c) {
                return this.d;
            }
            throw r.a();
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            if (this.c) {
                return this.d - 1;
            }
            throw r.a();
        }

        @Override // java.util.ListIterator
        public void set(E e) {
            throw r.a();
        }

        @Override // java.util.ListIterator
        public void add(E e) {
            throw r.a();
        }
    }

    /* compiled from: ImmutableCollections.java */
    /* loaded from: classes5.dex */
    public static final class l<E> extends b<E> implements RandomAccess {
        private final List<E> a;
        private final int b;
        private final int c;

        private l(List<E> list, int i, int i2) {
            this.a = list;
            this.b = i;
            this.c = i2;
        }

        static <E> l<E> a(l<E> lVar, int i, int i2) {
            return new l<>(((l) lVar).a, ((l) lVar).b + i, i2 - i);
        }

        static <E> l<E> a(List<E> list, int i, int i2) {
            return new l<>(list, i, i2 - i);
        }

        @Override // java.util.List
        public E get(int i) {
            Objects.checkIndex(i, this.c);
            return this.a.get(this.b + i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.c;
        }

        @Override // java8.util.r.b, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
        public Iterator<E> iterator() {
            return new f(this, size());
        }

        @Override // java8.util.r.b, java.util.List
        public ListIterator<E> listIterator(int i) {
            b(i);
            return new f(this, size(), i);
        }

        @Override // java8.util.r.b, java.util.List
        public List<E> subList(int i, int i2) {
            a(i, i2, this.c);
            return a((l) this, i, i2);
        }

        private void b(int i) {
            if (i < 0 || i > this.c) {
                throw a(i);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public Object[] toArray() {
            Object[] objArr = new Object[this.c];
            for (int i = 0; i < this.c; i++) {
                objArr[i] = get(i);
            }
            return objArr;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public <T> T[] toArray(T[] tArr) {
            int i;
            if (tArr.length < this.c) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.c));
            }
            int i2 = 0;
            while (true) {
                i = this.c;
                if (i2 >= i) {
                    break;
                }
                tArr[i2] = get(i2);
                i2++;
            }
            if (tArr.length > i) {
                tArr[i] = null;
            }
            return tArr;
        }
    }

    /* compiled from: ImmutableCollections.java */
    /* loaded from: classes5.dex */
    public static final class e<E> extends b<E> implements Serializable {
        private final E e0;
        private final Object e1;

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return false;
        }

        public e(E e) {
            this.e0 = (E) Objects.requireNonNull(e);
            this.e1 = r.c;
        }

        public e(E e, E e2) {
            this.e0 = (E) Objects.requireNonNull(e);
            this.e1 = Objects.requireNonNull(e2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.e1 != r.c ? 2 : 1;
        }

        @Override // java.util.List
        public E get(int i) {
            if (i == 0) {
                return this.e0;
            }
            if (i == 1 && this.e1 != r.c) {
                return (E) this.e1;
            }
            throw a(i);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new InvalidObjectException("not serial proxy");
        }

        private Object writeReplace() {
            if (this.e1 == r.c) {
                return new h(1, this.e0);
            }
            return new h(1, this.e0, this.e1);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public Object[] toArray() {
            return this.e1 == r.c ? new Object[]{this.e0} : new Object[]{this.e0, this.e1};
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public <T> T[] toArray(T[] tArr) {
            int size = size();
            if (tArr.length < size) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
            }
            tArr[0] = this.e0;
            if (size == 2) {
                tArr[1] = this.e1;
            }
            if (tArr.length > size) {
                tArr[size] = null;
            }
            return tArr;
        }
    }

    /* compiled from: ImmutableCollections.java */
    /* loaded from: classes5.dex */
    public static final class g<E> extends b<E> implements Serializable {
        private final E[] elements;

        /* JADX WARN: Multi-variable type inference failed */
        public g(E... eArr) {
            E[] eArr2 = (E[]) new Object[eArr.length];
            for (int i = 0; i < eArr.length; i++) {
                eArr2[i] = Objects.requireNonNull(eArr[i]);
            }
            this.elements = eArr2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return this.elements.length == 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.elements.length;
        }

        @Override // java.util.List
        public E get(int i) {
            return this.elements[i];
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new InvalidObjectException("not serial proxy");
        }

        private Object writeReplace() {
            return new h(1, this.elements);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public Object[] toArray() {
            E[] eArr = this.elements;
            return Arrays.copyOf(eArr, eArr.length);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public <T> T[] toArray(T[] tArr) {
            E[] eArr = this.elements;
            int length = eArr.length;
            if (tArr.length < length) {
                return (T[]) Arrays.copyOf(eArr, length, tArr.getClass());
            }
            System.arraycopy(eArr, 0, tArr, 0, length);
            if (tArr.length > length) {
                tArr[length] = null;
            }
            return tArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ImmutableCollections.java */
    /* loaded from: classes5.dex */
    public static abstract class d<E> extends a<E> implements Set<E> {
        d() {
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0021  */
        @Override // java.util.Collection, java.util.Set
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean equals(java.lang.Object r5) {
            /*
                r4 = this;
                r0 = 1
                if (r5 != r4) goto L_0x0004
                return r0
            L_0x0004:
                boolean r1 = r5 instanceof java.util.Set
                r2 = 0
                if (r1 != 0) goto L_0x000a
                return r2
            L_0x000a:
                java.util.Collection r5 = (java.util.Collection) r5
                int r1 = r5.size()
                int r3 = r4.size()
                if (r1 == r3) goto L_0x0017
                return r2
            L_0x0017:
                java.util.Iterator r5 = r5.iterator()
            L_0x001b:
                boolean r1 = r5.hasNext()
                if (r1 == 0) goto L_0x002e
                java.lang.Object r1 = r5.next()
                if (r1 == 0) goto L_0x002d
                boolean r1 = r4.contains(r1)
                if (r1 != 0) goto L_0x001b
            L_0x002d:
                return r2
            L_0x002e:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: java8.util.r.d.equals(java.lang.Object):boolean");
        }
    }

    /* compiled from: ImmutableCollections.java */
    /* loaded from: classes5.dex */
    public static final class j<E> extends d<E> implements Serializable {
        final E e0;
        final Object e1;

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return false;
        }

        public j(E e) {
            this.e0 = (E) Objects.requireNonNull(e);
            this.e1 = r.c;
        }

        public j(E e, E e2) {
            if (!e.equals(Objects.requireNonNull(e2))) {
                this.e0 = e;
                this.e1 = e2;
                return;
            }
            throw new IllegalArgumentException("duplicate element: " + e);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.e1 == r.c ? 1 : 2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return obj.equals(this.e0) || this.e1.equals(obj);
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            return this.e0.hashCode() + (this.e1 == r.c ? 0 : this.e1.hashCode());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<E> iterator() {
            return new Iterators.b<E>() { // from class: java8.util.r.j.1
                private int b;

                {
                    j.this = this;
                    this.b = j.this.e1 == r.c ? 1 : 2;
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.b > 0;
                }

                @Override // java.util.Iterator
                public E next() {
                    int i = this.b;
                    if (i == 1) {
                        this.b = 0;
                        return (r.b || j.this.e1 == r.c) ? j.this.e0 : (E) j.this.e1;
                    } else if (i == 2) {
                        this.b = 1;
                        return r.b ? (E) j.this.e1 : j.this.e0;
                    } else {
                        throw new NoSuchElementException();
                    }
                }
            };
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new InvalidObjectException("not serial proxy");
        }

        private Object writeReplace() {
            if (this.e1 == r.c) {
                return new h(2, this.e0);
            }
            return new h(2, this.e0, this.e1);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return this.e1 == r.c ? new Object[]{this.e0} : r.b ? new Object[]{this.e1, this.e0} : new Object[]{this.e0, this.e1};
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            int size = size();
            if (tArr.length < size) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
            }
            if (size == 1) {
                tArr[0] = this.e0;
            } else if (r.b) {
                tArr[0] = this.e1;
                tArr[1] = this.e0;
            } else {
                tArr[0] = this.e0;
                tArr[1] = this.e1;
            }
            if (tArr.length > size) {
                tArr[size] = null;
            }
            return tArr;
        }
    }

    /* compiled from: ImmutableCollections.java */
    /* loaded from: classes5.dex */
    public static final class k<E> extends d<E> implements Serializable {
        final E[] elements;
        final int size;

        public k(E... eArr) {
            this.size = eArr.length;
            this.elements = (E[]) new Object[eArr.length * 2];
            for (E e : eArr) {
                int a2 = a(e);
                if (a2 < 0) {
                    this.elements[-(a2 + 1)] = e;
                } else {
                    throw new IllegalArgumentException("duplicate element: " + e);
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return this.size == 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            Objects.requireNonNull(obj);
            return this.size > 0 && a(obj) >= 0;
        }

        /* compiled from: ImmutableCollections.java */
        /* loaded from: classes5.dex */
        public final class a extends Iterators.b<E> implements Iterator<E> {
            private int b;
            private int c;

            a() {
                k.this = r5;
                this.b = r5.size;
                this.c = (int) ((r.a * r5.elements.length) >>> 32);
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.b > 0;
            }

            @Override // java.util.Iterator
            public E next() {
                E e;
                if (this.b > 0) {
                    int i = this.c;
                    int length = k.this.elements.length;
                    do {
                        if (r.b) {
                            i++;
                            if (i >= length) {
                                i = 0;
                            }
                        } else {
                            i--;
                            if (i < 0) {
                                i = length - 1;
                            }
                        }
                        e = k.this.elements[i];
                    } while (e == null);
                    this.c = i;
                    this.b--;
                    return e;
                }
                throw new NoSuchElementException();
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<E> iterator() {
            return new a();
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            E[] eArr = this.elements;
            int i = 0;
            for (E e : eArr) {
                if (e != null) {
                    i += e.hashCode();
                }
            }
            return i;
        }

        private int a(Object obj) {
            int b = r.b(obj.hashCode(), this.elements.length);
            while (true) {
                E e = this.elements[b];
                if (e == null) {
                    return (-b) - 1;
                }
                if (obj.equals(e)) {
                    return b;
                }
                b++;
                if (b == this.elements.length) {
                    b = 0;
                }
            }
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new InvalidObjectException("not serial proxy");
        }

        private Object writeReplace() {
            Object[] objArr = new Object[this.size];
            E[] eArr = this.elements;
            int i = 0;
            for (E e : eArr) {
                if (e != null) {
                    i++;
                    objArr[i] = e;
                }
            }
            return new h(2, objArr);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            Object[] objArr = new Object[this.size];
            Iterator<E> it = iterator();
            for (int i = 0; i < this.size; i++) {
                objArr[i] = it.next();
            }
            return objArr;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            int i;
            if (tArr.length < this.size) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.size));
            }
            Iterator<E> it = iterator();
            int i2 = 0;
            while (true) {
                i = this.size;
                if (i2 >= i) {
                    break;
                }
                tArr[i2] = it.next();
                i2++;
            }
            if (tArr.length > i) {
                tArr[i] = null;
            }
            return tArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ImmutableCollections.java */
    /* loaded from: classes5.dex */
    public static abstract class c<K, V> extends AbstractMap<K, V> implements Serializable {
        c() {
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            throw r.a();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(K k, V v) {
            throw r.a();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            throw r.a();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(Object obj) {
            throw r.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ImmutableCollections.java */
    /* loaded from: classes5.dex */
    public static final class h<K, V> extends c<K, V> {
        private final K k0;
        private final V v0;

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return false;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return 1;
        }

        public h(K k, V v) {
            this.k0 = (K) Objects.requireNonNull(k);
            this.v0 = (V) Objects.requireNonNull(v);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            return Sets.of(new w(this.k0, this.v0));
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(Object obj) {
            if (obj.equals(this.k0)) {
                return this.v0;
            }
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return obj.equals(this.k0);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsValue(Object obj) {
            return obj.equals(this.v0);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new InvalidObjectException("not serial proxy");
        }

        private Object writeReplace() {
            return new h(3, this.k0, this.v0);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int hashCode() {
            return this.k0.hashCode() ^ this.v0.hashCode();
        }
    }

    /* compiled from: ImmutableCollections.java */
    /* loaded from: classes5.dex */
    public static final class i<K, V> extends c<K, V> {
        final int size;
        final Object[] table;

        public i(Object... objArr) {
            if ((objArr.length & 1) == 0) {
                this.size = objArr.length >> 1;
                this.table = new Object[((objArr.length * 2) + 1) & (-2)];
                for (int i = 0; i < objArr.length; i += 2) {
                    Object requireNonNull = Objects.requireNonNull(objArr[i]);
                    Object requireNonNull2 = Objects.requireNonNull(objArr[i + 1]);
                    int a2 = a(requireNonNull);
                    if (a2 < 0) {
                        int i2 = -(a2 + 1);
                        Object[] objArr2 = this.table;
                        objArr2[i2] = requireNonNull;
                        objArr2[i2 + 1] = requireNonNull2;
                    } else {
                        throw new IllegalArgumentException("duplicate key: " + requireNonNull);
                    }
                }
                return;
            }
            throw new InternalError("length is odd");
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            Objects.requireNonNull(obj);
            return this.size > 0 && a(obj) >= 0;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsValue(Object obj) {
            Objects.requireNonNull(obj);
            int i = 1;
            while (true) {
                Object[] objArr = this.table;
                if (i >= objArr.length) {
                    return false;
                }
                Object obj2 = objArr[i];
                if (obj2 != null && obj.equals(obj2)) {
                    return true;
                }
                i += 2;
            }
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int hashCode() {
            int i = 0;
            int i2 = 0;
            while (true) {
                Object[] objArr = this.table;
                if (i >= objArr.length) {
                    return i2;
                }
                Object obj = objArr[i];
                if (obj != null) {
                    i2 += obj.hashCode() ^ this.table[i + 1].hashCode();
                }
                i += 2;
            }
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(Object obj) {
            if (this.size == 0) {
                Objects.requireNonNull(obj);
                return null;
            }
            int a2 = a(obj);
            if (a2 >= 0) {
                return (V) this.table[a2 + 1];
            }
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.size;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return this.size == 0;
        }

        /* compiled from: ImmutableCollections.java */
        /* loaded from: classes5.dex */
        class a extends Iterators.b<Map.Entry<K, V>> implements Iterator<Map.Entry<K, V>> {
            private int b;
            private int c;

            a() {
                i.this = r5;
                this.b = r5.size;
                this.c = ((int) ((r.a * (r5.table.length >> 1)) >>> 32)) << 1;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.b > 0;
            }

            private int b() {
                int i;
                int i2 = this.c;
                if (r.b) {
                    i = i2 + 2;
                    if (i >= i.this.table.length) {
                        i = 0;
                    }
                } else {
                    i = i2 - 2;
                    if (i < 0) {
                        i = i.this.table.length - 2;
                    }
                }
                this.c = i;
                return i;
            }

            /* renamed from: a */
            public Map.Entry<K, V> next() {
                Object[] objArr;
                int b;
                if (this.b > 0) {
                    do {
                        objArr = i.this.table;
                        b = b();
                    } while (objArr[b] == null);
                    this.b--;
                    return new w(i.this.table[b], i.this.table[b + 1]);
                }
                throw new NoSuchElementException();
            }
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            return new AbstractSet<Map.Entry<K, V>>() { // from class: java8.util.r.i.1
                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return i.this.size;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<Map.Entry<K, V>> iterator() {
                    return new a();
                }
            };
        }

        private int a(Object obj) {
            int b = r.b(obj.hashCode(), this.table.length >> 1) << 1;
            while (true) {
                Object obj2 = this.table[b];
                if (obj2 == null) {
                    return (-b) - 1;
                }
                if (obj.equals(obj2)) {
                    return b;
                }
                b += 2;
                if (b == this.table.length) {
                    b = 0;
                }
            }
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new InvalidObjectException("not serial proxy");
        }

        private Object writeReplace() {
            Object[] objArr = new Object[this.size * 2];
            int length = this.table.length;
            int i = 0;
            for (int i2 = 0; i2 < length; i2 += 2) {
                Object[] objArr2 = this.table;
                if (objArr2[i2] != null) {
                    int i3 = i + 1;
                    objArr[i] = objArr2[i2];
                    i = i3 + 1;
                    objArr[i3] = objArr2[i2 + 1];
                }
            }
            return new h(3, objArr);
        }
    }

    static void a(int i2, int i3) {
        if (i2 < 0 || i2 > i3) {
            throw new IndexOutOfBoundsException("Index: " + i2 + ", Size: " + i3);
        }
    }

    static int b(int i2, int i3) {
        int i4 = i2 % i3;
        return ((i4 ^ i3) >= 0 || i4 == 0) ? i4 : i4 + i3;
    }
}
