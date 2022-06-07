package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.g;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: AbstractMapBasedMultimap.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class d<K, V> extends g<K, V> implements Serializable {
    private static final long serialVersionUID = 2447537837011683357L;
    private transient Map<K, Collection<V>> a;
    private transient int b;

    abstract Collection<V> c();

    static /* synthetic */ int b(d dVar) {
        int i2 = dVar.b;
        dVar.b = i2 - 1;
        return i2;
    }

    static /* synthetic */ int c(d dVar) {
        int i2 = dVar.b;
        dVar.b = i2 + 1;
        return i2;
    }

    public d(Map<K, Collection<V>> map) {
        Preconditions.checkArgument(map.isEmpty());
        this.a = map;
    }

    final void a(Map<K, Collection<V>> map) {
        this.a = map;
        this.b = 0;
        for (Collection<V> collection : map.values()) {
            Preconditions.checkArgument(!collection.isEmpty());
            this.b += collection.size();
        }
    }

    Collection<V> d() {
        return (Collection<V>) a(c());
    }

    Collection<V> a(@NullableDecl K k2) {
        return c();
    }

    Map<K, Collection<V>> e() {
        return this.a;
    }

    @Override // com.google.common.collect.Multimap
    public int size() {
        return this.b;
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsKey(@NullableDecl Object obj) {
        return this.a.containsKey(obj);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public boolean put(@NullableDecl K k2, @NullableDecl V v) {
        Collection<V> collection = this.a.get(k2);
        if (collection == null) {
            Collection<V> a2 = a((d<K, V>) k2);
            if (a2.add(v)) {
                this.b++;
                this.a.put(k2, a2);
                return true;
            }
            throw new AssertionError("New Collection violated the Collection spec");
        } else if (!collection.add(v)) {
            return false;
        } else {
            this.b++;
            return true;
        }
    }

    private Collection<V> b(@NullableDecl K k2) {
        Collection<V> collection = this.a.get(k2);
        if (collection != null) {
            return collection;
        }
        Collection<V> a2 = a((d<K, V>) k2);
        this.a.put(k2, a2);
        return a2;
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> replaceValues(@NullableDecl K k2, Iterable<? extends V> iterable) {
        Iterator<? extends V> it = iterable.iterator();
        if (!it.hasNext()) {
            return removeAll(k2);
        }
        Collection<V> b2 = b((d<K, V>) k2);
        Collection c2 = c();
        c2.addAll(b2);
        this.b -= b2.size();
        b2.clear();
        while (it.hasNext()) {
            if (b2.add((V) it.next())) {
                this.b++;
            }
        }
        return (Collection<V>) a(c2);
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> removeAll(@NullableDecl Object obj) {
        Collection<V> remove = this.a.remove(obj);
        if (remove == null) {
            return d();
        }
        Collection c2 = c();
        c2.addAll(remove);
        this.b -= remove.size();
        remove.clear();
        return (Collection<V>) a(c2);
    }

    <E> Collection<E> a(Collection<E> collection) {
        return Collections.unmodifiableCollection(collection);
    }

    @Override // com.google.common.collect.Multimap
    public void clear() {
        for (Collection<V> collection : this.a.values()) {
            collection.clear();
        }
        this.a.clear();
        this.b = 0;
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> get(@NullableDecl K k2) {
        Collection<V> collection = this.a.get(k2);
        if (collection == null) {
            collection = a((d<K, V>) k2);
        }
        return a((d<K, V>) k2, (Collection) collection);
    }

    Collection<V> a(@NullableDecl K k2, Collection<V> collection) {
        return new i(k2, collection, null);
    }

    final List<V> a(@NullableDecl K k2, List<V> list, @NullableDecl d<K, V>.i iVar) {
        return list instanceof RandomAccess ? new f(k2, list, iVar) : new j(k2, list, iVar);
    }

    /* compiled from: AbstractMapBasedMultimap.java */
    /* loaded from: classes2.dex */
    public class i extends AbstractCollection<V> {
        @NullableDecl
        final K b;
        Collection<V> c;
        @NullableDecl
        final d<K, V>.i d;
        @NullableDecl
        final Collection<V> e;

        public i(K k, @NullableDecl Collection<V> collection, d<K, V>.i iVar) {
            d.this = r1;
            this.b = k;
            this.c = collection;
            this.d = iVar;
            this.e = iVar == null ? null : iVar.e();
        }

        void a() {
            Collection<V> collection;
            d<K, V>.i iVar = this.d;
            if (iVar != null) {
                iVar.a();
                if (this.d.e() != this.e) {
                    throw new ConcurrentModificationException();
                }
            } else if (this.c.isEmpty() && (collection = (Collection) d.this.a.get(this.b)) != null) {
                this.c = collection;
            }
        }

        void b() {
            d<K, V>.i iVar = this.d;
            if (iVar != null) {
                iVar.b();
            } else if (this.c.isEmpty()) {
                d.this.a.remove(this.b);
            }
        }

        K c() {
            return this.b;
        }

        void d() {
            d<K, V>.i iVar = this.d;
            if (iVar != null) {
                iVar.d();
            } else {
                d.this.a.put(this.b, this.c);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            a();
            return this.c.size();
        }

        @Override // java.util.Collection
        public boolean equals(@NullableDecl Object obj) {
            if (obj == this) {
                return true;
            }
            a();
            return this.c.equals(obj);
        }

        @Override // java.util.Collection
        public int hashCode() {
            a();
            return this.c.hashCode();
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            a();
            return this.c.toString();
        }

        Collection<V> e() {
            return this.c;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            a();
            return new a();
        }

        /* compiled from: AbstractMapBasedMultimap.java */
        /* loaded from: classes2.dex */
        public class a implements Iterator<V> {
            final Iterator<V> a;
            final Collection<V> b;

            a() {
                i.this = r2;
                this.b = i.this.c;
                this.a = d.c((Collection) r2.c);
            }

            a(Iterator<V> it) {
                i.this = r1;
                this.b = i.this.c;
                this.a = it;
            }

            void a() {
                i.this.a();
                if (i.this.c != this.b) {
                    throw new ConcurrentModificationException();
                }
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                a();
                return this.a.hasNext();
            }

            @Override // java.util.Iterator
            public V next() {
                a();
                return this.a.next();
            }

            @Override // java.util.Iterator
            public void remove() {
                this.a.remove();
                d.b(d.this);
                i.this.b();
            }

            Iterator<V> b() {
                a();
                return this.a;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean add(V v) {
            a();
            boolean isEmpty = this.c.isEmpty();
            boolean add = this.c.add(v);
            if (add) {
                d.c(d.this);
                if (isEmpty) {
                    d();
                }
            }
            return add;
        }

        d<K, V>.i f() {
            return this.d;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean addAll = this.c.addAll(collection);
            if (addAll) {
                int size2 = this.c.size();
                d.this.b += size2 - size;
                if (size == 0) {
                    d();
                }
            }
            return addAll;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            a();
            return this.c.contains(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            a();
            return this.c.containsAll(collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            int size = size();
            if (size != 0) {
                this.c.clear();
                d.this.b -= size;
                b();
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            a();
            boolean remove = this.c.remove(obj);
            if (remove) {
                d.b(d.this);
                b();
            }
            return remove;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean removeAll = this.c.removeAll(collection);
            if (removeAll) {
                int size2 = this.c.size();
                d.this.b += size2 - size;
                b();
            }
            return removeAll;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            int size = size();
            boolean retainAll = this.c.retainAll(collection);
            if (retainAll) {
                int size2 = this.c.size();
                d.this.b += size2 - size;
                b();
            }
            return retainAll;
        }
    }

    public static <E> Iterator<E> c(Collection<E> collection) {
        if (collection instanceof List) {
            return ((List) collection).listIterator();
        }
        return collection.iterator();
    }

    /* compiled from: AbstractMapBasedMultimap.java */
    /* loaded from: classes2.dex */
    class l extends d<K, V>.i implements Set<V> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public l(K k, @NullableDecl Set<V> set) {
            super(k, set, null);
            d.this = r2;
        }

        @Override // com.google.common.collect.d.i, java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean a = Sets.a((Set<?>) this.c, collection);
            if (a) {
                int size2 = this.c.size();
                d.this.b += size2 - size;
                b();
            }
            return a;
        }
    }

    /* compiled from: AbstractMapBasedMultimap.java */
    /* loaded from: classes2.dex */
    public class m extends d<K, V>.i implements SortedSet<V> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public m(K k, @NullableDecl SortedSet<V> sortedSet, d<K, V>.i iVar) {
            super(k, sortedSet, iVar);
            d.this = r1;
        }

        SortedSet<V> h() {
            return (SortedSet) e();
        }

        @Override // java.util.SortedSet
        public Comparator<? super V> comparator() {
            return h().comparator();
        }

        @Override // java.util.SortedSet
        public V first() {
            a();
            return h().first();
        }

        @Override // java.util.SortedSet
        public V last() {
            a();
            return h().last();
        }

        @Override // java.util.SortedSet
        public SortedSet<V> headSet(V v) {
            a();
            return new m(c(), h().headSet(v), f() == null ? this : f());
        }

        @Override // java.util.SortedSet
        public SortedSet<V> subSet(V v, V v2) {
            a();
            return new m(c(), h().subSet(v, v2), f() == null ? this : f());
        }

        @Override // java.util.SortedSet
        public SortedSet<V> tailSet(V v) {
            a();
            return new m(c(), h().tailSet(v), f() == null ? this : f());
        }
    }

    /* compiled from: AbstractMapBasedMultimap.java */
    /* loaded from: classes2.dex */
    class k extends d<K, V>.m implements NavigableSet<V> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public k(K k, @NullableDecl NavigableSet<V> navigableSet, d<K, V>.i iVar) {
            super(k, navigableSet, iVar);
            d.this = r1;
        }

        /* renamed from: g */
        public NavigableSet<V> h() {
            return (NavigableSet) super.h();
        }

        @Override // java.util.NavigableSet
        public V lower(V v) {
            return h().lower(v);
        }

        @Override // java.util.NavigableSet
        public V floor(V v) {
            return h().floor(v);
        }

        @Override // java.util.NavigableSet
        public V ceiling(V v) {
            return h().ceiling(v);
        }

        @Override // java.util.NavigableSet
        public V higher(V v) {
            return h().higher(v);
        }

        @Override // java.util.NavigableSet
        public V pollFirst() {
            return (V) Iterators.a(iterator());
        }

        @Override // java.util.NavigableSet
        public V pollLast() {
            return (V) Iterators.a(descendingIterator());
        }

        private NavigableSet<V> a(NavigableSet<V> navigableSet) {
            return new k(this.b, navigableSet, f() == null ? this : f());
        }

        @Override // java.util.NavigableSet
        public NavigableSet<V> descendingSet() {
            return a(h().descendingSet());
        }

        @Override // java.util.NavigableSet
        public Iterator<V> descendingIterator() {
            return new i.a(h().descendingIterator());
        }

        @Override // java.util.NavigableSet
        public NavigableSet<V> subSet(V v, boolean z, V v2, boolean z2) {
            return a(h().subSet(v, z, v2, z2));
        }

        @Override // java.util.NavigableSet
        public NavigableSet<V> headSet(V v, boolean z) {
            return a(h().headSet(v, z));
        }

        @Override // java.util.NavigableSet
        public NavigableSet<V> tailSet(V v, boolean z) {
            return a(h().tailSet(v, z));
        }
    }

    /* compiled from: AbstractMapBasedMultimap.java */
    /* loaded from: classes2.dex */
    public class j extends d<K, V>.i implements List<V> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        j(K k, @NullableDecl List<V> list, d<K, V>.i iVar) {
            super(k, list, iVar);
            d.this = r1;
        }

        List<V> g() {
            return (List) e();
        }

        @Override // java.util.List
        public boolean addAll(int i, Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean addAll = g().addAll(i, collection);
            if (addAll) {
                int size2 = e().size();
                d.this.b += size2 - size;
                if (size == 0) {
                    d();
                }
            }
            return addAll;
        }

        @Override // java.util.List
        public V get(int i) {
            a();
            return g().get(i);
        }

        @Override // java.util.List
        public V set(int i, V v) {
            a();
            return g().set(i, v);
        }

        @Override // java.util.List
        public void add(int i, V v) {
            a();
            boolean isEmpty = e().isEmpty();
            g().add(i, v);
            d.c(d.this);
            if (isEmpty) {
                d();
            }
        }

        @Override // java.util.List
        public V remove(int i) {
            a();
            V remove = g().remove(i);
            d.b(d.this);
            b();
            return remove;
        }

        @Override // java.util.List
        public int indexOf(Object obj) {
            a();
            return g().indexOf(obj);
        }

        @Override // java.util.List
        public int lastIndexOf(Object obj) {
            a();
            return g().lastIndexOf(obj);
        }

        @Override // java.util.List
        public ListIterator<V> listIterator() {
            a();
            return new a();
        }

        @Override // java.util.List
        public ListIterator<V> listIterator(int i) {
            a();
            return new a(i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.List
        public List<V> subList(int i, int i2) {
            a();
            return d.this.a(c(), g().subList(i, i2), f() == null ? this : f());
        }

        /* compiled from: AbstractMapBasedMultimap.java */
        /* loaded from: classes2.dex */
        private class a extends d<K, V>.i.a implements ListIterator<V> {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a() {
                super();
                j.this = r1;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public a(int i) {
                super(r2.g().listIterator(i));
                j.this = r2;
            }

            private ListIterator<V> c() {
                return (ListIterator) b();
            }

            @Override // java.util.ListIterator
            public boolean hasPrevious() {
                return c().hasPrevious();
            }

            @Override // java.util.ListIterator
            public V previous() {
                return c().previous();
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return c().nextIndex();
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return c().previousIndex();
            }

            @Override // java.util.ListIterator
            public void set(V v) {
                c().set(v);
            }

            @Override // java.util.ListIterator
            public void add(V v) {
                boolean isEmpty = j.this.isEmpty();
                c().add(v);
                d.c(d.this);
                if (isEmpty) {
                    j.this.d();
                }
            }
        }
    }

    /* compiled from: AbstractMapBasedMultimap.java */
    /* loaded from: classes2.dex */
    public class f extends d<K, V>.j implements RandomAccess {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        f(K k, @NullableDecl List<V> list, d<K, V>.i iVar) {
            super(k, list, iVar);
            d.this = r1;
        }
    }

    @Override // com.google.common.collect.g
    Set<K> f() {
        return new c(this.a);
    }

    final Set<K> g() {
        Map<K, Collection<V>> map = this.a;
        if (map instanceof NavigableMap) {
            return new e((NavigableMap) map);
        }
        if (map instanceof SortedMap) {
            return new h((SortedMap) map);
        }
        return new c(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AbstractMapBasedMultimap.java */
    /* loaded from: classes2.dex */
    public class c extends Maps.n<K, Collection<V>> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        c(Map<K, Collection<V>> map) {
            super(map);
            d.this = r1;
        }

        @Override // com.google.common.collect.Maps.n, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            final Iterator<Map.Entry<K, Collection<V>>> it = c().entrySet().iterator();
            return new Iterator<K>() { // from class: com.google.common.collect.d.c.1
                @NullableDecl
                Map.Entry<K, Collection<V>> a;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override // java.util.Iterator
                public K next() {
                    this.a = (Map.Entry) it.next();
                    return this.a.getKey();
                }

                @Override // java.util.Iterator
                public void remove() {
                    t.a(this.a != null);
                    Collection<V> value = this.a.getValue();
                    it.remove();
                    d.this.b -= value.size();
                    value.clear();
                    this.a = null;
                }
            };
        }

        @Override // com.google.common.collect.Maps.n, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            int i;
            Collection<V> remove = c().remove(obj);
            if (remove != null) {
                i = remove.size();
                remove.clear();
                d.this.b -= i;
            } else {
                i = 0;
            }
            return i > 0;
        }

        @Override // com.google.common.collect.Maps.n, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Iterators.b(iterator());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            return c().keySet().containsAll(collection);
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        public boolean equals(@NullableDecl Object obj) {
            return this == obj || c().keySet().equals(obj);
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        public int hashCode() {
            return c().keySet().hashCode();
        }
    }

    /* compiled from: AbstractMapBasedMultimap.java */
    /* loaded from: classes2.dex */
    public class h extends d<K, V>.c implements SortedSet<K> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        h(SortedMap<K, Collection<V>> sortedMap) {
            super(sortedMap);
            d.this = r1;
        }

        SortedMap<K, Collection<V>> b() {
            return (SortedMap) super.c();
        }

        @Override // java.util.SortedSet
        public Comparator<? super K> comparator() {
            return b().comparator();
        }

        @Override // java.util.SortedSet
        public K first() {
            return b().firstKey();
        }

        public SortedSet<K> headSet(K k) {
            return new h(b().headMap(k));
        }

        @Override // java.util.SortedSet
        public K last() {
            return b().lastKey();
        }

        public SortedSet<K> subSet(K k, K k2) {
            return new h(b().subMap(k, k2));
        }

        public SortedSet<K> tailSet(K k) {
            return new h(b().tailMap(k));
        }
    }

    /* compiled from: AbstractMapBasedMultimap.java */
    /* loaded from: classes2.dex */
    public class e extends d<K, V>.h implements NavigableSet<K> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        e(NavigableMap<K, Collection<V>> navigableMap) {
            super(navigableMap);
            d.this = r1;
        }

        /* renamed from: a */
        public NavigableMap<K, Collection<V>> b() {
            return (NavigableMap) super.b();
        }

        @Override // java.util.NavigableSet
        public K lower(K k) {
            return b().lowerKey(k);
        }

        @Override // java.util.NavigableSet
        public K floor(K k) {
            return b().floorKey(k);
        }

        @Override // java.util.NavigableSet
        public K ceiling(K k) {
            return b().ceilingKey(k);
        }

        @Override // java.util.NavigableSet
        public K higher(K k) {
            return b().higherKey(k);
        }

        @Override // java.util.NavigableSet
        public K pollFirst() {
            return (K) Iterators.a(iterator());
        }

        @Override // java.util.NavigableSet
        public K pollLast() {
            return (K) Iterators.a(descendingIterator());
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> descendingSet() {
            return new e(b().descendingMap());
        }

        @Override // java.util.NavigableSet
        public Iterator<K> descendingIterator() {
            return descendingSet().iterator();
        }

        /* renamed from: a */
        public NavigableSet<K> headSet(K k) {
            return headSet(k, false);
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> headSet(K k, boolean z) {
            return new e(b().headMap(k, z));
        }

        /* renamed from: a */
        public NavigableSet<K> subSet(K k, K k2) {
            return subSet(k, true, k2, false);
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> subSet(K k, boolean z, K k2, boolean z2) {
            return new e(b().subMap(k, z, k2, z2));
        }

        /* renamed from: b */
        public NavigableSet<K> tailSet(K k) {
            return tailSet(k, true);
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> tailSet(K k, boolean z) {
            return new e(b().tailMap(k, z));
        }
    }

    public void c(Object obj) {
        Collection collection = (Collection) Maps.c(this.a, obj);
        if (collection != null) {
            int size = collection.size();
            collection.clear();
            this.b -= size;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AbstractMapBasedMultimap.java */
    /* loaded from: classes2.dex */
    public abstract class b<T> implements Iterator<T> {
        final Iterator<Map.Entry<K, Collection<V>>> b;
        @NullableDecl
        K c = null;
        @MonotonicNonNullDecl
        Collection<V> d = null;
        Iterator<V> e = Iterators.c();

        abstract T a(K k, V v);

        b() {
            d.this = r1;
            this.b = (Iterator<Map.Entry<K, V>>) r1.a.entrySet().iterator();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b.hasNext() || this.e.hasNext();
        }

        @Override // java.util.Iterator
        public T next() {
            if (!this.e.hasNext()) {
                Map.Entry<K, Collection<V>> next = this.b.next();
                this.c = next.getKey();
                this.d = next.getValue();
                this.e = this.d.iterator();
            }
            return a(this.c, this.e.next());
        }

        @Override // java.util.Iterator
        public void remove() {
            this.e.remove();
            if (this.d.isEmpty()) {
                this.b.remove();
            }
            d.b(d.this);
        }
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public Collection<V> values() {
        return super.values();
    }

    @Override // com.google.common.collect.g
    Collection<V> h() {
        return new g.c();
    }

    @Override // com.google.common.collect.g
    Iterator<V> i() {
        return new d<K, V>.b() { // from class: com.google.common.collect.d.1
            @Override // com.google.common.collect.d.b
            V a(K k2, V v) {
                return v;
            }
        };
    }

    @Override // com.google.common.collect.g
    Multiset<K> j() {
        return new Multimaps.g(this);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public Collection<Map.Entry<K, V>> entries() {
        return super.entries();
    }

    @Override // com.google.common.collect.g
    Collection<Map.Entry<K, V>> k() {
        if (this instanceof SetMultimap) {
            return new g.b();
        }
        return new g.a();
    }

    @Override // com.google.common.collect.g
    Iterator<Map.Entry<K, V>> l() {
        return new d<K, V>.b() { // from class: com.google.common.collect.d.2
            /* renamed from: b */
            public Map.Entry<K, V> a(K k2, V v) {
                return Maps.immutableEntry(k2, v);
            }
        };
    }

    @Override // com.google.common.collect.g
    Map<K, Collection<V>> m() {
        return new a(this.a);
    }

    final Map<K, Collection<V>> n() {
        Map<K, Collection<V>> map = this.a;
        if (map instanceof NavigableMap) {
            return new C0105d((NavigableMap) map);
        }
        if (map instanceof SortedMap) {
            return new g((SortedMap) map);
        }
        return new a(map);
    }

    /* compiled from: AbstractMapBasedMultimap.java */
    /* loaded from: classes2.dex */
    public class a extends Maps.ad<K, Collection<V>> {
        final transient Map<K, Collection<V>> a;

        a(Map<K, Collection<V>> map) {
            d.this = r1;
            this.a = map;
        }

        @Override // com.google.common.collect.Maps.ad
        protected Set<Map.Entry<K, Collection<V>>> a() {
            return new C0104a();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return Maps.b((Map<?, ?>) this.a, obj);
        }

        /* renamed from: a */
        public Collection<V> get(Object obj) {
            Collection<V> collection = (Collection) Maps.a((Map<?, Object>) this.a, obj);
            if (collection == null) {
                return null;
            }
            return d.this.a((d) obj, (Collection) collection);
        }

        @Override // com.google.common.collect.Maps.ad, java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return d.this.keySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.a.size();
        }

        /* renamed from: b */
        public Collection<V> remove(Object obj) {
            Collection<V> remove = this.a.remove(obj);
            if (remove == null) {
                return null;
            }
            Collection<V> c = d.this.c();
            c.addAll(remove);
            d.this.b -= remove.size();
            remove.clear();
            return c;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean equals(@NullableDecl Object obj) {
            return this == obj || this.a.equals(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int hashCode() {
            return this.a.hashCode();
        }

        @Override // java.util.AbstractMap
        public String toString() {
            return this.a.toString();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            if (this.a == d.this.a) {
                d.this.clear();
            } else {
                Iterators.b(new b());
            }
        }

        Map.Entry<K, Collection<V>> a(Map.Entry<K, Collection<V>> entry) {
            K key = entry.getKey();
            return Maps.immutableEntry(key, d.this.a((d) key, (Collection) entry.getValue()));
        }

        /* compiled from: AbstractMapBasedMultimap.java */
        /* renamed from: com.google.common.collect.d$a$a */
        /* loaded from: classes2.dex */
        class C0104a extends Maps.f<K, Collection<V>> {
            C0104a() {
                a.this = r1;
            }

            @Override // com.google.common.collect.Maps.f
            Map<K, Collection<V>> a() {
                return a.this;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return new b();
            }

            @Override // com.google.common.collect.Maps.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return Collections2.a(a.this.a.entrySet(), obj);
            }

            @Override // com.google.common.collect.Maps.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!contains(obj)) {
                    return false;
                }
                d.this.c(((Map.Entry) obj).getKey());
                return true;
            }
        }

        /* compiled from: AbstractMapBasedMultimap.java */
        /* loaded from: classes2.dex */
        class b implements Iterator<Map.Entry<K, Collection<V>>> {
            final Iterator<Map.Entry<K, Collection<V>>> a;
            @NullableDecl
            Collection<V> b;

            b() {
                a.this = r1;
                this.a = a.this.a.entrySet().iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.a.hasNext();
            }

            /* renamed from: a */
            public Map.Entry<K, Collection<V>> next() {
                Map.Entry<K, Collection<V>> next = this.a.next();
                this.b = next.getValue();
                return a.this.a((Map.Entry) next);
            }

            @Override // java.util.Iterator
            public void remove() {
                t.a(this.b != null);
                this.a.remove();
                d.this.b -= this.b.size();
                this.b.clear();
                this.b = null;
            }
        }
    }

    /* compiled from: AbstractMapBasedMultimap.java */
    /* loaded from: classes2.dex */
    public class g extends d<K, V>.a implements SortedMap<K, Collection<V>> {
        @MonotonicNonNullDecl
        SortedSet<K> d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        g(SortedMap<K, Collection<V>> sortedMap) {
            super(sortedMap);
            d.this = r1;
        }

        SortedMap<K, Collection<V>> g() {
            return (SortedMap) this.a;
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            return g().comparator();
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            return g().firstKey();
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            return g().lastKey();
        }

        public SortedMap<K, Collection<V>> headMap(K k) {
            return new g(g().headMap(k));
        }

        public SortedMap<K, Collection<V>> subMap(K k, K k2) {
            return new g(g().subMap(k, k2));
        }

        public SortedMap<K, Collection<V>> tailMap(K k) {
            return new g(g().tailMap(k));
        }

        /* renamed from: f */
        public SortedSet<K> keySet() {
            SortedSet<K> sortedSet = this.d;
            if (sortedSet != null) {
                return sortedSet;
            }
            SortedSet<K> e = h();
            this.d = e;
            return e;
        }

        /* renamed from: e */
        public SortedSet<K> h() {
            return new h(g());
        }
    }

    /* compiled from: AbstractMapBasedMultimap.java */
    /* renamed from: com.google.common.collect.d$d */
    /* loaded from: classes2.dex */
    class C0105d extends d<K, V>.g implements NavigableMap<K, Collection<V>> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C0105d(NavigableMap<K, Collection<V>> navigableMap) {
            super(navigableMap);
            d.this = r1;
        }

        /* renamed from: b_ */
        public NavigableMap<K, Collection<V>> g() {
            return (NavigableMap) super.g();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, Collection<V>> lowerEntry(K k) {
            Map.Entry<K, Collection<V>> lowerEntry = g().lowerEntry(k);
            if (lowerEntry == null) {
                return null;
            }
            return a((Map.Entry) lowerEntry);
        }

        @Override // java.util.NavigableMap
        public K lowerKey(K k) {
            return g().lowerKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, Collection<V>> floorEntry(K k) {
            Map.Entry<K, Collection<V>> floorEntry = g().floorEntry(k);
            if (floorEntry == null) {
                return null;
            }
            return a((Map.Entry) floorEntry);
        }

        @Override // java.util.NavigableMap
        public K floorKey(K k) {
            return g().floorKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, Collection<V>> ceilingEntry(K k) {
            Map.Entry<K, Collection<V>> ceilingEntry = g().ceilingEntry(k);
            if (ceilingEntry == null) {
                return null;
            }
            return a((Map.Entry) ceilingEntry);
        }

        @Override // java.util.NavigableMap
        public K ceilingKey(K k) {
            return g().ceilingKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, Collection<V>> higherEntry(K k) {
            Map.Entry<K, Collection<V>> higherEntry = g().higherEntry(k);
            if (higherEntry == null) {
                return null;
            }
            return a((Map.Entry) higherEntry);
        }

        @Override // java.util.NavigableMap
        public K higherKey(K k) {
            return g().higherKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, Collection<V>> firstEntry() {
            Map.Entry<K, Collection<V>> firstEntry = g().firstEntry();
            if (firstEntry == null) {
                return null;
            }
            return a((Map.Entry) firstEntry);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, Collection<V>> lastEntry() {
            Map.Entry<K, Collection<V>> lastEntry = g().lastEntry();
            if (lastEntry == null) {
                return null;
            }
            return a((Map.Entry) lastEntry);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, Collection<V>> pollFirstEntry() {
            return a((Iterator) entrySet().iterator());
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, Collection<V>> pollLastEntry() {
            return a((Iterator) descendingMap().entrySet().iterator());
        }

        Map.Entry<K, Collection<V>> a(Iterator<Map.Entry<K, Collection<V>>> it) {
            if (!it.hasNext()) {
                return null;
            }
            Map.Entry<K, Collection<V>> next = it.next();
            Collection<V> c = d.this.c();
            c.addAll(next.getValue());
            it.remove();
            return Maps.immutableEntry(next.getKey(), d.this.a((Collection) c));
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, Collection<V>> descendingMap() {
            return new C0105d(g().descendingMap());
        }

        /* renamed from: c */
        public NavigableSet<K> keySet() {
            return (NavigableSet) super.keySet();
        }

        /* renamed from: d */
        public NavigableSet<K> h() {
            return new e(g());
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            return keySet();
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> descendingKeySet() {
            return descendingMap().navigableKeySet();
        }

        /* renamed from: a */
        public NavigableMap<K, Collection<V>> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, Collection<V>> subMap(K k, boolean z, K k2, boolean z2) {
            return new C0105d(g().subMap(k, z, k2, z2));
        }

        /* renamed from: c */
        public NavigableMap<K, Collection<V>> headMap(K k) {
            return headMap(k, false);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, Collection<V>> headMap(K k, boolean z) {
            return new C0105d(g().headMap(k, z));
        }

        /* renamed from: d */
        public NavigableMap<K, Collection<V>> tailMap(K k) {
            return tailMap(k, true);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, Collection<V>> tailMap(K k, boolean z) {
            return new C0105d(g().tailMap(k, z));
        }
    }
}
