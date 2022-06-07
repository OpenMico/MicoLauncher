package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Sets;
import com.google.common.collect.d;
import com.google.common.collect.g;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class Multimaps {
    private Multimaps() {
    }

    public static <K, V> Multimap<K, V> newMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> supplier) {
        return new c(map, supplier);
    }

    /* loaded from: classes2.dex */
    private static class c<K, V> extends d<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        transient Supplier<? extends Collection<V>> a;

        c(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> supplier) {
            super(map);
            this.a = (Supplier) Preconditions.checkNotNull(supplier);
        }

        @Override // com.google.common.collect.d, com.google.common.collect.g
        Set<K> f() {
            return g();
        }

        @Override // com.google.common.collect.d, com.google.common.collect.g
        Map<K, Collection<V>> m() {
            return n();
        }

        @Override // com.google.common.collect.d
        protected Collection<V> c() {
            return (Collection) this.a.get();
        }

        @Override // com.google.common.collect.d
        <E> Collection<E> a(Collection<E> collection) {
            if (collection instanceof NavigableSet) {
                return Sets.unmodifiableNavigableSet((NavigableSet) collection);
            }
            if (collection instanceof SortedSet) {
                return Collections.unmodifiableSortedSet((SortedSet) collection);
            }
            if (collection instanceof Set) {
                return Collections.unmodifiableSet((Set) collection);
            }
            if (collection instanceof List) {
                return Collections.unmodifiableList((List) collection);
            }
            return Collections.unmodifiableCollection(collection);
        }

        @Override // com.google.common.collect.d
        Collection<V> a(K k, Collection<V> collection) {
            if (collection instanceof List) {
                return a(k, (List) collection, null);
            }
            if (collection instanceof NavigableSet) {
                return new d.k(k, (NavigableSet) collection, null);
            }
            if (collection instanceof SortedSet) {
                return new d.m(k, (SortedSet) collection, null);
            }
            if (collection instanceof Set) {
                return new d.l(k, (Set) collection);
            }
            return new d.i(k, collection, null);
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.a);
            objectOutputStream.writeObject(e());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.a = (Supplier) objectInputStream.readObject();
            a((Map) ((Map) objectInputStream.readObject()));
        }
    }

    public static <K, V> ListMultimap<K, V> newListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> supplier) {
        return new b(map, supplier);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class b<K, V> extends c<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        transient Supplier<? extends List<V>> a;

        b(Map<K, Collection<V>> map, Supplier<? extends List<V>> supplier) {
            super(map);
            this.a = (Supplier) Preconditions.checkNotNull(supplier);
        }

        @Override // com.google.common.collect.d, com.google.common.collect.g
        Set<K> f() {
            return g();
        }

        @Override // com.google.common.collect.d, com.google.common.collect.g
        Map<K, Collection<V>> m() {
            return n();
        }

        @Override // com.google.common.collect.c
        /* renamed from: a */
        public List<V> c() {
            return (List) this.a.get();
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.a);
            objectOutputStream.writeObject(e());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.a = (Supplier) objectInputStream.readObject();
            a((Map) ((Map) objectInputStream.readObject()));
        }
    }

    public static <K, V> SetMultimap<K, V> newSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> supplier) {
        return new d(map, supplier);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class d<K, V> extends k<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        transient Supplier<? extends Set<V>> a;

        d(Map<K, Collection<V>> map, Supplier<? extends Set<V>> supplier) {
            super(map);
            this.a = (Supplier) Preconditions.checkNotNull(supplier);
        }

        @Override // com.google.common.collect.d, com.google.common.collect.g
        Set<K> f() {
            return g();
        }

        @Override // com.google.common.collect.d, com.google.common.collect.g
        Map<K, Collection<V>> m() {
            return n();
        }

        @Override // com.google.common.collect.k
        /* renamed from: a */
        public Set<V> c() {
            return (Set) this.a.get();
        }

        @Override // com.google.common.collect.k, com.google.common.collect.d
        <E> Collection<E> a(Collection<E> collection) {
            if (collection instanceof NavigableSet) {
                return Sets.unmodifiableNavigableSet((NavigableSet) collection);
            }
            if (collection instanceof SortedSet) {
                return Collections.unmodifiableSortedSet((SortedSet) collection);
            }
            return Collections.unmodifiableSet((Set) collection);
        }

        @Override // com.google.common.collect.k, com.google.common.collect.d
        Collection<V> a(K k, Collection<V> collection) {
            if (collection instanceof NavigableSet) {
                return new d.k(k, (NavigableSet) collection, null);
            }
            if (collection instanceof SortedSet) {
                return new d.m(k, (SortedSet) collection, null);
            }
            return new d.l(k, (Set) collection);
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.a);
            objectOutputStream.writeObject(e());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.a = (Supplier) objectInputStream.readObject();
            a((Map) ((Map) objectInputStream.readObject()));
        }
    }

    public static <K, V> SortedSetMultimap<K, V> newSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> supplier) {
        return new e(map, supplier);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class e<K, V> extends n<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        transient Supplier<? extends SortedSet<V>> a;
        transient Comparator<? super V> b;

        e(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> supplier) {
            super(map);
            this.a = (Supplier) Preconditions.checkNotNull(supplier);
            this.b = ((SortedSet) supplier.get()).comparator();
        }

        @Override // com.google.common.collect.d, com.google.common.collect.g
        Set<K> f() {
            return g();
        }

        @Override // com.google.common.collect.d, com.google.common.collect.g
        Map<K, Collection<V>> m() {
            return n();
        }

        @Override // com.google.common.collect.n
        /* renamed from: p */
        public SortedSet<V> c() {
            return (SortedSet) this.a.get();
        }

        @Override // com.google.common.collect.SortedSetMultimap
        public Comparator<? super V> valueComparator() {
            return this.b;
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.a);
            objectOutputStream.writeObject(e());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.a = (Supplier) objectInputStream.readObject();
            this.b = ((SortedSet) this.a.get()).comparator();
            a((Map) ((Map) objectInputStream.readObject()));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CanIgnoreReturnValue
    public static <K, V, M extends Multimap<K, V>> M invertFrom(Multimap<? extends V, ? extends K> multimap, M m2) {
        Preconditions.checkNotNull(m2);
        for (Map.Entry<? extends V, ? extends K> entry : multimap.entries()) {
            m2.put(entry.getValue(), entry.getKey());
        }
        return m2;
    }

    public static <K, V> Multimap<K, V> synchronizedMultimap(Multimap<K, V> multimap) {
        return cn.a(multimap, (Object) null);
    }

    public static <K, V> Multimap<K, V> unmodifiableMultimap(Multimap<K, V> multimap) {
        return ((multimap instanceof l) || (multimap instanceof ImmutableMultimap)) ? multimap : new l(multimap);
    }

    @Deprecated
    public static <K, V> Multimap<K, V> unmodifiableMultimap(ImmutableMultimap<K, V> immutableMultimap) {
        return (Multimap) Preconditions.checkNotNull(immutableMultimap);
    }

    /* loaded from: classes2.dex */
    public static class l<K, V> extends ForwardingMultimap<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Collection<Map.Entry<K, V>> a;
        @MonotonicNonNullDecl
        transient Multiset<K> b;
        @MonotonicNonNullDecl
        transient Set<K> c;
        @MonotonicNonNullDecl
        transient Collection<V> d;
        final Multimap<K, V> delegate;
        @MonotonicNonNullDecl
        transient Map<K, Collection<V>> e;

        l(Multimap<K, V> multimap) {
            this.delegate = (Multimap) Preconditions.checkNotNull(multimap);
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.ForwardingObject
        public Multimap<K, V> delegate() {
            return this.delegate;
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> map = this.e;
            if (map != null) {
                return map;
            }
            Map<K, Collection<V>> unmodifiableMap = Collections.unmodifiableMap(Maps.transformValues(this.delegate.asMap(), new Function<Collection<V>, Collection<V>>() { // from class: com.google.common.collect.Multimaps.l.1
                /* renamed from: a */
                public Collection<V> apply(Collection<V> collection) {
                    return Multimaps.c(collection);
                }
            }));
            this.e = unmodifiableMap;
            return unmodifiableMap;
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public Collection<Map.Entry<K, V>> entries() {
            Collection<Map.Entry<K, V>> collection = this.a;
            if (collection != null) {
                return collection;
            }
            Collection<Map.Entry<K, V>> d = Multimaps.d(this.delegate.entries());
            this.a = d;
            return d;
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Collection<V> get(K k) {
            return Multimaps.c(this.delegate.get(k));
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public Multiset<K> keys() {
            Multiset<K> multiset = this.b;
            if (multiset != null) {
                return multiset;
            }
            Multiset<K> unmodifiableMultiset = Multisets.unmodifiableMultiset(this.delegate.keys());
            this.b = unmodifiableMultiset;
            return unmodifiableMultiset;
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public Set<K> keySet() {
            Set<K> set = this.c;
            if (set != null) {
                return set;
            }
            Set<K> unmodifiableSet = Collections.unmodifiableSet(this.delegate.keySet());
            this.c = unmodifiableSet;
            return unmodifiableSet;
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public boolean put(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public boolean putAll(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public boolean remove(Object obj, Object obj2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Collection<V> removeAll(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public Collection<V> values() {
            Collection<V> collection = this.d;
            if (collection != null) {
                return collection;
            }
            Collection<V> unmodifiableCollection = Collections.unmodifiableCollection(this.delegate.values());
            this.d = unmodifiableCollection;
            return unmodifiableCollection;
        }
    }

    /* loaded from: classes2.dex */
    private static class k<K, V> extends l<K, V> implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0;

        k(ListMultimap<K, V> listMultimap) {
            super(listMultimap);
        }

        /* renamed from: a */
        public ListMultimap<K, V> delegate() {
            return (ListMultimap) super.delegate();
        }

        @Override // com.google.common.collect.Multimaps.l, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V> get(K k) {
            return Collections.unmodifiableList(delegate().get((ListMultimap<K, V>) k));
        }

        @Override // com.google.common.collect.Multimaps.l, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V> removeAll(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.Multimaps.l, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: classes2.dex */
    public static class m<K, V> extends l<K, V> implements SetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        m(SetMultimap<K, V> setMultimap) {
            super(setMultimap);
        }

        /* renamed from: a */
        public SetMultimap<K, V> delegate() {
            return (SetMultimap) super.delegate();
        }

        @Override // com.google.common.collect.Multimaps.l, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> get(K k) {
            return Collections.unmodifiableSet(delegate().get((SetMultimap<K, V>) k));
        }

        @Override // com.google.common.collect.Multimaps.l, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public Set<Map.Entry<K, V>> entries() {
            return Maps.a((Set) delegate().entries());
        }

        @Override // com.google.common.collect.Multimaps.l, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> removeAll(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.Multimaps.l, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: classes2.dex */
    private static class n<K, V> extends m<K, V> implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        n(SortedSetMultimap<K, V> sortedSetMultimap) {
            super(sortedSetMultimap);
        }

        /* renamed from: b */
        public SortedSetMultimap<K, V> delegate() {
            return (SortedSetMultimap) super.delegate();
        }

        @Override // com.google.common.collect.Multimaps.m, com.google.common.collect.Multimaps.l, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public SortedSet<V> get(K k) {
            return Collections.unmodifiableSortedSet(delegate().get((SortedSetMultimap<K, V>) k));
        }

        @Override // com.google.common.collect.Multimaps.m, com.google.common.collect.Multimaps.l, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public SortedSet<V> removeAll(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.Multimaps.m, com.google.common.collect.Multimaps.l, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.SortedSetMultimap
        public Comparator<? super V> valueComparator() {
            return delegate().valueComparator();
        }
    }

    public static <K, V> SetMultimap<K, V> synchronizedSetMultimap(SetMultimap<K, V> setMultimap) {
        return cn.a((SetMultimap) setMultimap, (Object) null);
    }

    public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(SetMultimap<K, V> setMultimap) {
        return ((setMultimap instanceof m) || (setMultimap instanceof ImmutableSetMultimap)) ? setMultimap : new m(setMultimap);
    }

    @Deprecated
    public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(ImmutableSetMultimap<K, V> immutableSetMultimap) {
        return (SetMultimap) Preconditions.checkNotNull(immutableSetMultimap);
    }

    public static <K, V> SortedSetMultimap<K, V> synchronizedSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap) {
        return cn.a((SortedSetMultimap) sortedSetMultimap, (Object) null);
    }

    public static <K, V> SortedSetMultimap<K, V> unmodifiableSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap) {
        return sortedSetMultimap instanceof n ? sortedSetMultimap : new n(sortedSetMultimap);
    }

    public static <K, V> ListMultimap<K, V> synchronizedListMultimap(ListMultimap<K, V> listMultimap) {
        return cn.a((ListMultimap) listMultimap, (Object) null);
    }

    public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ListMultimap<K, V> listMultimap) {
        return ((listMultimap instanceof k) || (listMultimap instanceof ImmutableListMultimap)) ? listMultimap : new k(listMultimap);
    }

    @Deprecated
    public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ImmutableListMultimap<K, V> immutableListMultimap) {
        return (ListMultimap) Preconditions.checkNotNull(immutableListMultimap);
    }

    public static <V> Collection<V> c(Collection<V> collection) {
        if (collection instanceof SortedSet) {
            return Collections.unmodifiableSortedSet((SortedSet) collection);
        }
        if (collection instanceof Set) {
            return Collections.unmodifiableSet((Set) collection);
        }
        if (collection instanceof List) {
            return Collections.unmodifiableList((List) collection);
        }
        return Collections.unmodifiableCollection(collection);
    }

    public static <K, V> Collection<Map.Entry<K, V>> d(Collection<Map.Entry<K, V>> collection) {
        if (collection instanceof Set) {
            return Maps.a((Set) collection);
        }
        return new Maps.y(Collections.unmodifiableCollection(collection));
    }

    @Beta
    public static <K, V> Map<K, List<V>> asMap(ListMultimap<K, V> listMultimap) {
        return (Map<K, Collection<V>>) listMultimap.asMap();
    }

    @Beta
    public static <K, V> Map<K, Set<V>> asMap(SetMultimap<K, V> setMultimap) {
        return (Map<K, Collection<V>>) setMultimap.asMap();
    }

    @Beta
    public static <K, V> Map<K, SortedSet<V>> asMap(SortedSetMultimap<K, V> sortedSetMultimap) {
        return (Map<K, Collection<V>>) sortedSetMultimap.asMap();
    }

    @Beta
    public static <K, V> Map<K, Collection<V>> asMap(Multimap<K, V> multimap) {
        return multimap.asMap();
    }

    public static <K, V> SetMultimap<K, V> forMap(Map<K, V> map) {
        return new h(map);
    }

    /* loaded from: classes2.dex */
    public static class h<K, V> extends g<K, V> implements SetMultimap<K, V>, Serializable {
        private static final long serialVersionUID = 7845222491160860175L;
        final Map<K, V> map;

        h(Map<K, V> map) {
            this.map = (Map) Preconditions.checkNotNull(map);
        }

        @Override // com.google.common.collect.Multimap
        public int size() {
            return this.map.size();
        }

        @Override // com.google.common.collect.Multimap
        public boolean containsKey(Object obj) {
            return this.map.containsKey(obj);
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public boolean containsValue(Object obj) {
            return this.map.containsValue(obj);
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public boolean containsEntry(Object obj, Object obj2) {
            return this.map.entrySet().contains(Maps.immutableEntry(obj, obj2));
        }

        @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> get(final K k) {
            return new Sets.f<V>() { // from class: com.google.common.collect.Multimaps.h.1
                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<V> iterator() {
                    return new Iterator<V>() { // from class: com.google.common.collect.Multimaps.h.1.1
                        int a;

                        @Override // java.util.Iterator
                        public boolean hasNext() {
                            return this.a == 0 && h.this.map.containsKey(k);
                        }

                        @Override // java.util.Iterator
                        public V next() {
                            if (hasNext()) {
                                this.a++;
                                return h.this.map.get(k);
                            }
                            throw new NoSuchElementException();
                        }

                        @Override // java.util.Iterator
                        public void remove() {
                            boolean z = true;
                            if (this.a != 1) {
                                z = false;
                            }
                            t.a(z);
                            this.a = -1;
                            h.this.map.remove(k);
                        }
                    };
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return h.this.map.containsKey(k) ? 1 : 0;
                }
            };
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public boolean put(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public boolean putAll(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public boolean remove(Object obj, Object obj2) {
            return this.map.entrySet().remove(Maps.immutableEntry(obj, obj2));
        }

        @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> removeAll(Object obj) {
            HashSet hashSet = new HashSet(2);
            if (!this.map.containsKey(obj)) {
                return hashSet;
            }
            hashSet.add(this.map.remove(obj));
            return hashSet;
        }

        @Override // com.google.common.collect.Multimap
        public void clear() {
            this.map.clear();
        }

        @Override // com.google.common.collect.g
        Set<K> f() {
            return this.map.keySet();
        }

        @Override // com.google.common.collect.g
        Collection<V> h() {
            return this.map.values();
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public Set<Map.Entry<K, V>> entries() {
            return this.map.entrySet();
        }

        @Override // com.google.common.collect.g
        Collection<Map.Entry<K, V>> k() {
            throw new AssertionError("unreachable");
        }

        @Override // com.google.common.collect.g
        Multiset<K> j() {
            return new g(this);
        }

        @Override // com.google.common.collect.g
        Iterator<Map.Entry<K, V>> l() {
            return this.map.entrySet().iterator();
        }

        @Override // com.google.common.collect.g
        Map<K, Collection<V>> m() {
            return new a(this);
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public int hashCode() {
            return this.map.hashCode();
        }
    }

    public static <K, V1, V2> Multimap<K, V2> transformValues(Multimap<K, V1> multimap, Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return transformEntries(multimap, Maps.a(function));
    }

    public static <K, V1, V2> ListMultimap<K, V2> transformValues(ListMultimap<K, V1> listMultimap, Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return transformEntries((ListMultimap) listMultimap, Maps.a(function));
    }

    public static <K, V1, V2> Multimap<K, V2> transformEntries(Multimap<K, V1> multimap, Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new j(multimap, entryTransformer);
    }

    public static <K, V1, V2> ListMultimap<K, V2> transformEntries(ListMultimap<K, V1> listMultimap, Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new i(listMultimap, entryTransformer);
    }

    /* loaded from: classes2.dex */
    public static class j<K, V1, V2> extends g<K, V2> {
        final Multimap<K, V1> a;
        final Maps.EntryTransformer<? super K, ? super V1, V2> b;

        j(Multimap<K, V1> multimap, Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            this.a = (Multimap) Preconditions.checkNotNull(multimap);
            this.b = (Maps.EntryTransformer) Preconditions.checkNotNull(entryTransformer);
        }

        Collection<V2> b(K k, Collection<V1> collection) {
            Function a = Maps.a(this.b, k);
            if (collection instanceof List) {
                return Lists.transform((List) collection, a);
            }
            return Collections2.transform(collection, a);
        }

        @Override // com.google.common.collect.g
        Map<K, Collection<V2>> m() {
            return Maps.transformEntries(this.a.asMap(), new Maps.EntryTransformer<K, Collection<V1>, Collection<V2>>() { // from class: com.google.common.collect.Multimaps.j.1
                /* renamed from: a */
                public Collection<V2> transformEntry(K k, Collection<V1> collection) {
                    return j.this.b(k, collection);
                }
            });
        }

        @Override // com.google.common.collect.Multimap
        public void clear() {
            this.a.clear();
        }

        @Override // com.google.common.collect.Multimap
        public boolean containsKey(Object obj) {
            return this.a.containsKey(obj);
        }

        @Override // com.google.common.collect.g
        Collection<Map.Entry<K, V2>> k() {
            return new g.a();
        }

        @Override // com.google.common.collect.g
        Iterator<Map.Entry<K, V2>> l() {
            return Iterators.transform(this.a.entries().iterator(), Maps.b(this.b));
        }

        @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Collection<V2> get(K k) {
            return b(k, this.a.get(k));
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        @Override // com.google.common.collect.g
        Set<K> f() {
            return this.a.keySet();
        }

        @Override // com.google.common.collect.g
        Multiset<K> j() {
            return this.a.keys();
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public boolean put(K k, V2 v2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public boolean putAll(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public boolean putAll(Multimap<? extends K, ? extends V2> multimap) {
            throw new UnsupportedOperationException();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.g, com.google.common.collect.Multimap
        public boolean remove(Object obj, Object obj2) {
            return get(obj).remove(obj2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Collection<V2> removeAll(Object obj) {
            return b(obj, this.a.removeAll(obj));
        }

        @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Collection<V2> replaceValues(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.Multimap
        public int size() {
            return this.a.size();
        }

        @Override // com.google.common.collect.g
        Collection<V2> h() {
            return Collections2.transform(this.a.entries(), Maps.a(this.b));
        }
    }

    /* loaded from: classes2.dex */
    public static final class i<K, V1, V2> extends j<K, V1, V2> implements ListMultimap<K, V2> {
        i(ListMultimap<K, V1> listMultimap, Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(listMultimap, entryTransformer);
        }

        /* renamed from: a */
        public List<V2> b(K k, Collection<V1> collection) {
            return Lists.transform((List) collection, Maps.a(this.b, k));
        }

        @Override // com.google.common.collect.Multimaps.j, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V2> get(K k) {
            return b(k, this.a.get(k));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Multimaps.j, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V2> removeAll(Object obj) {
            return b(obj, this.a.removeAll(obj));
        }

        @Override // com.google.common.collect.Multimaps.j, com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V2> replaceValues(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    public static <K, V> ImmutableListMultimap<K, V> index(Iterable<V> iterable, Function<? super V, K> function) {
        return index(iterable.iterator(), function);
    }

    public static <K, V> ImmutableListMultimap<K, V> index(Iterator<V> it, Function<? super V, K> function) {
        Preconditions.checkNotNull(function);
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        while (it.hasNext()) {
            V next = it.next();
            Preconditions.checkNotNull(next, it);
            builder.put((ImmutableListMultimap.Builder) function.apply(next), (K) next);
        }
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class g<K, V> extends h<K> {
        @Weak
        final Multimap<K, V> b;

        public g(Multimap<K, V> multimap) {
            this.b = multimap;
        }

        @Override // com.google.common.collect.h
        Iterator<Multiset.Entry<K>> b() {
            return new cp<Map.Entry<K, Collection<V>>, Multiset.Entry<K>>(this.b.asMap().entrySet().iterator()) { // from class: com.google.common.collect.Multimaps.g.1
                @Override // com.google.common.collect.cp
                /* bridge */ /* synthetic */ Object a(Object obj) {
                    return a((Map.Entry) ((Map.Entry) obj));
                }

                Multiset.Entry<K> a(final Map.Entry<K, Collection<V>> entry) {
                    return new Multisets.a<K>() { // from class: com.google.common.collect.Multimaps.g.1.1
                        @Override // com.google.common.collect.Multiset.Entry
                        public K getElement() {
                            return (K) entry.getKey();
                        }

                        @Override // com.google.common.collect.Multiset.Entry
                        public int getCount() {
                            return ((Collection) entry.getValue()).size();
                        }
                    };
                }
            };
        }

        @Override // com.google.common.collect.h
        int c() {
            return this.b.asMap().size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
        public int size() {
            return this.b.size();
        }

        @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
        public boolean contains(@NullableDecl Object obj) {
            return this.b.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
        public Iterator<K> iterator() {
            return Maps.a(this.b.entries().iterator());
        }

        @Override // com.google.common.collect.Multiset
        public int count(@NullableDecl Object obj) {
            Collection collection = (Collection) Maps.a((Map<?, Object>) this.b.asMap(), obj);
            if (collection == null) {
                return 0;
            }
            return collection.size();
        }

        @Override // com.google.common.collect.h, com.google.common.collect.Multiset
        public int remove(@NullableDecl Object obj, int i) {
            t.a(i, "occurrences");
            if (i == 0) {
                return count(obj);
            }
            Collection collection = (Collection) Maps.a((Map<?, Object>) this.b.asMap(), obj);
            if (collection == null) {
                return 0;
            }
            int size = collection.size();
            if (i >= size) {
                collection.clear();
            } else {
                Iterator it = collection.iterator();
                for (int i2 = 0; i2 < i; i2++) {
                    it.next();
                    it.remove();
                }
            }
            return size;
        }

        @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            this.b.clear();
        }

        @Override // com.google.common.collect.h, com.google.common.collect.Multiset
        public Set<K> elementSet() {
            return this.b.keySet();
        }

        @Override // com.google.common.collect.h
        Iterator<K> a() {
            throw new AssertionError("should never be called");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class f<K, V> extends AbstractCollection<Map.Entry<K, V>> {
        abstract Multimap<K, V> a();

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return a().size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return a().containsEntry(entry.getKey(), entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return a().remove(entry.getKey(), entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            a().clear();
        }
    }

    /* loaded from: classes2.dex */
    public static final class a<K, V> extends Maps.ad<K, Collection<V>> {
        @Weak
        private final Multimap<K, V> a;

        public a(Multimap<K, V> multimap) {
            this.a = (Multimap) Preconditions.checkNotNull(multimap);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.a.keySet().size();
        }

        @Override // com.google.common.collect.Maps.ad
        protected Set<Map.Entry<K, Collection<V>>> a() {
            return new C0090a();
        }

        void a(Object obj) {
            this.a.keySet().remove(obj);
        }

        /* renamed from: com.google.common.collect.Multimaps$a$a */
        /* loaded from: classes2.dex */
        public class C0090a extends Maps.f<K, Collection<V>> {
            C0090a() {
                a.this = r1;
            }

            @Override // com.google.common.collect.Maps.f
            Map<K, Collection<V>> a() {
                return a.this;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return Maps.a((Set) a.this.a.keySet(), (Function) new Function<K, Collection<V>>() { // from class: com.google.common.collect.Multimaps.a.a.1
                    /* renamed from: a */
                    public Collection<V> apply(K k) {
                        return a.this.a.get(k);
                    }
                });
            }

            @Override // com.google.common.collect.Maps.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!contains(obj)) {
                    return false;
                }
                a.this.a(((Map.Entry) obj).getKey());
                return true;
            }
        }

        /* renamed from: b */
        public Collection<V> get(Object obj) {
            if (containsKey(obj)) {
                return this.a.get(obj);
            }
            return null;
        }

        /* renamed from: c */
        public Collection<V> remove(Object obj) {
            if (containsKey(obj)) {
                return this.a.removeAll(obj);
            }
            return null;
        }

        @Override // com.google.common.collect.Maps.ad, java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return this.a.keySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return this.a.containsKey(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            this.a.clear();
        }
    }

    public static <K, V> Multimap<K, V> filterKeys(Multimap<K, V> multimap, Predicate<? super K> predicate) {
        if (multimap instanceof SetMultimap) {
            return filterKeys((SetMultimap) multimap, (Predicate) predicate);
        }
        if (multimap instanceof ListMultimap) {
            return filterKeys((ListMultimap) multimap, (Predicate) predicate);
        }
        if (multimap instanceof an) {
            an anVar = (an) multimap;
            return new an(anVar.a, Predicates.and(anVar.b, predicate));
        } else if (multimap instanceof ap) {
            return a((ap) multimap, Maps.a(predicate));
        } else {
            return new an(multimap, predicate);
        }
    }

    public static <K, V> SetMultimap<K, V> filterKeys(SetMultimap<K, V> setMultimap, Predicate<? super K> predicate) {
        if (setMultimap instanceof ao) {
            ao aoVar = (ao) setMultimap;
            return new ao(aoVar.a(), Predicates.and(aoVar.b, predicate));
        } else if (setMultimap instanceof ar) {
            return a((ar) setMultimap, Maps.a(predicate));
        } else {
            return new ao(setMultimap, predicate);
        }
    }

    public static <K, V> ListMultimap<K, V> filterKeys(ListMultimap<K, V> listMultimap, Predicate<? super K> predicate) {
        if (!(listMultimap instanceof am)) {
            return new am(listMultimap, predicate);
        }
        am amVar = (am) listMultimap;
        return new am(amVar.a(), Predicates.and(amVar.b, predicate));
    }

    public static <K, V> Multimap<K, V> filterValues(Multimap<K, V> multimap, Predicate<? super V> predicate) {
        return filterEntries(multimap, Maps.b(predicate));
    }

    public static <K, V> SetMultimap<K, V> filterValues(SetMultimap<K, V> setMultimap, Predicate<? super V> predicate) {
        return filterEntries((SetMultimap) setMultimap, Maps.b(predicate));
    }

    public static <K, V> Multimap<K, V> filterEntries(Multimap<K, V> multimap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (multimap instanceof SetMultimap) {
            return filterEntries((SetMultimap) multimap, (Predicate) predicate);
        }
        if (multimap instanceof ap) {
            return a((ap) multimap, (Predicate) predicate);
        }
        return new ak((Multimap) Preconditions.checkNotNull(multimap), predicate);
    }

    public static <K, V> SetMultimap<K, V> filterEntries(SetMultimap<K, V> setMultimap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (setMultimap instanceof ar) {
            return a((ar) setMultimap, (Predicate) predicate);
        }
        return new al((SetMultimap) Preconditions.checkNotNull(setMultimap), predicate);
    }

    private static <K, V> Multimap<K, V> a(ap<K, V> apVar, Predicate<? super Map.Entry<K, V>> predicate) {
        return new ak(apVar.a(), Predicates.and(apVar.b(), predicate));
    }

    private static <K, V> SetMultimap<K, V> a(ar<K, V> arVar, Predicate<? super Map.Entry<K, V>> predicate) {
        return new al(arVar.d(), Predicates.and(arVar.b(), predicate));
    }

    public static boolean a(Multimap<?, ?> multimap, @NullableDecl Object obj) {
        if (obj == multimap) {
            return true;
        }
        if (obj instanceof Multimap) {
            return multimap.asMap().equals(((Multimap) obj).asMap());
        }
        return false;
    }
}
