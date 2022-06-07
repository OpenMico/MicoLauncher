package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: FilteredEntryMultimap.java */
@GwtCompatible
/* loaded from: classes2.dex */
public class ak<K, V> extends g<K, V> implements ap<K, V> {
    final Multimap<K, V> a;
    final Predicate<? super Map.Entry<K, V>> b;

    public ak(Multimap<K, V> multimap, Predicate<? super Map.Entry<K, V>> predicate) {
        this.a = (Multimap) Preconditions.checkNotNull(multimap);
        this.b = (Predicate) Preconditions.checkNotNull(predicate);
    }

    @Override // com.google.common.collect.ap
    public Multimap<K, V> a() {
        return this.a;
    }

    @Override // com.google.common.collect.ap
    public Predicate<? super Map.Entry<K, V>> b() {
        return this.b;
    }

    @Override // com.google.common.collect.Multimap
    public int size() {
        return entries().size();
    }

    public boolean a(K k, V v) {
        return this.b.apply(Maps.immutableEntry(k, v));
    }

    /* compiled from: FilteredEntryMultimap.java */
    /* loaded from: classes2.dex */
    public final class c implements Predicate<V> {
        private final K b;

        c(K k) {
            ak.this = r1;
            this.b = k;
        }

        @Override // com.google.common.base.Predicate
        public boolean apply(@NullableDecl V v) {
            return ak.this.a((ak) this.b, (K) v);
        }
    }

    static <E> Collection<E> a(Collection<E> collection, Predicate<? super E> predicate) {
        if (collection instanceof Set) {
            return Sets.filter((Set) collection, predicate);
        }
        return Collections2.filter(collection, predicate);
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsKey(@NullableDecl Object obj) {
        return asMap().get(obj) != null;
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> removeAll(@NullableDecl Object obj) {
        return (Collection) MoreObjects.firstNonNull(asMap().remove(obj), c());
    }

    Collection<V> c() {
        if (this.a instanceof SetMultimap) {
            return Collections.emptySet();
        }
        return Collections.emptyList();
    }

    @Override // com.google.common.collect.Multimap
    public void clear() {
        entries().clear();
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> get(K k) {
        return a((Collection) this.a.get(k), (Predicate) new c(k));
    }

    @Override // com.google.common.collect.g
    Collection<Map.Entry<K, V>> k() {
        return a((Collection) this.a.entries(), (Predicate) this.b);
    }

    @Override // com.google.common.collect.g
    Collection<V> h() {
        return new aq(this);
    }

    @Override // com.google.common.collect.g
    Iterator<Map.Entry<K, V>> l() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.g
    Map<K, Collection<V>> m() {
        return new a();
    }

    @Override // com.google.common.collect.g
    Set<K> f() {
        return asMap().keySet();
    }

    boolean a(Predicate<? super Map.Entry<K, Collection<V>>> predicate) {
        Iterator<Map.Entry<K, Collection<V>>> it = this.a.asMap().entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Map.Entry<K, Collection<V>> next = it.next();
            K key = next.getKey();
            Collection a2 = a((Collection) next.getValue(), (Predicate) new c(key));
            if (!a2.isEmpty() && predicate.apply(Maps.immutableEntry(key, a2))) {
                if (a2.size() == next.getValue().size()) {
                    it.remove();
                } else {
                    a2.clear();
                }
                z = true;
            }
        }
        return z;
    }

    /* compiled from: FilteredEntryMultimap.java */
    /* loaded from: classes2.dex */
    public class a extends Maps.ad<K, Collection<V>> {
        a() {
            ak.this = r1;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return get(obj) != null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            ak.this.clear();
        }

        /* renamed from: a */
        public Collection<V> get(@NullableDecl Object obj) {
            Collection<V> collection = ak.this.a.asMap().get(obj);
            if (collection == null) {
                return null;
            }
            Collection<V> a = ak.a((Collection) collection, (Predicate) new c(obj));
            if (a.isEmpty()) {
                return null;
            }
            return a;
        }

        /* renamed from: b */
        public Collection<V> remove(@NullableDecl Object obj) {
            Collection<V> collection = ak.this.a.asMap().get(obj);
            if (collection == null) {
                return null;
            }
            ArrayList newArrayList = Lists.newArrayList();
            Iterator<V> it = collection.iterator();
            while (it.hasNext()) {
                V next = it.next();
                if (ak.this.a((ak) obj, (Object) next)) {
                    it.remove();
                    newArrayList.add(next);
                }
            }
            if (newArrayList.isEmpty()) {
                return null;
            }
            if (ak.this.a instanceof SetMultimap) {
                return Collections.unmodifiableSet(Sets.newLinkedHashSet(newArrayList));
            }
            return Collections.unmodifiableList(newArrayList);
        }

        /* compiled from: FilteredEntryMultimap.java */
        /* loaded from: classes2.dex */
        class b extends Maps.n<K, Collection<V>> {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            b() {
                super(r1);
                a.this = r1;
            }

            @Override // com.google.common.collect.Sets.f, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean removeAll(Collection<?> collection) {
                return ak.this.a(Maps.a(Predicates.in(collection)));
            }

            @Override // com.google.common.collect.Sets.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                return ak.this.a(Maps.a(Predicates.not(Predicates.in(collection))));
            }

            @Override // com.google.common.collect.Maps.n, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(@NullableDecl Object obj) {
                return a.this.remove(obj) != null;
            }
        }

        @Override // com.google.common.collect.Maps.ad
        Set<K> h() {
            return new b();
        }

        /* compiled from: FilteredEntryMultimap.java */
        /* renamed from: com.google.common.collect.ak$a$a */
        /* loaded from: classes2.dex */
        public class C0099a extends Maps.f<K, Collection<V>> {
            C0099a() {
                a.this = r1;
            }

            @Override // com.google.common.collect.Maps.f
            Map<K, Collection<V>> a() {
                return a.this;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return new AbstractIterator<Map.Entry<K, Collection<V>>>() { // from class: com.google.common.collect.ak.a.a.1
                    final Iterator<Map.Entry<K, Collection<V>>> a;

                    {
                        C0099a.this = this;
                        this.a = ak.this.a.asMap().entrySet().iterator();
                    }

                    /* renamed from: a */
                    public Map.Entry<K, Collection<V>> computeNext() {
                        while (this.a.hasNext()) {
                            Map.Entry<K, Collection<V>> next = this.a.next();
                            K key = next.getKey();
                            Collection a = ak.a((Collection) next.getValue(), (Predicate) new c(key));
                            if (!a.isEmpty()) {
                                return Maps.immutableEntry(key, a);
                            }
                        }
                        return endOfData();
                    }
                };
            }

            @Override // com.google.common.collect.Maps.f, com.google.common.collect.Sets.f, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean removeAll(Collection<?> collection) {
                return ak.this.a(Predicates.in(collection));
            }

            @Override // com.google.common.collect.Maps.f, com.google.common.collect.Sets.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                return ak.this.a(Predicates.not(Predicates.in(collection)));
            }

            @Override // com.google.common.collect.Maps.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return Iterators.size(iterator());
            }
        }

        @Override // com.google.common.collect.Maps.ad
        Set<Map.Entry<K, Collection<V>>> a() {
            return new C0099a();
        }

        /* compiled from: FilteredEntryMultimap.java */
        /* loaded from: classes2.dex */
        class c extends Maps.ac<K, Collection<V>> {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            c() {
                super(r1);
                a.this = r1;
            }

            @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
            public boolean remove(@NullableDecl Object obj) {
                if (!(obj instanceof Collection)) {
                    return false;
                }
                Collection collection = (Collection) obj;
                Iterator<Map.Entry<K, Collection<V>>> it = ak.this.a.asMap().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<K, Collection<V>> next = it.next();
                    Collection a = ak.a((Collection) next.getValue(), (Predicate) new c(next.getKey()));
                    if (!a.isEmpty() && collection.equals(a)) {
                        if (a.size() == next.getValue().size()) {
                            it.remove();
                            return true;
                        }
                        a.clear();
                        return true;
                    }
                }
                return false;
            }

            @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
            public boolean removeAll(Collection<?> collection) {
                return ak.this.a(Maps.b(Predicates.in(collection)));
            }

            @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
            public boolean retainAll(Collection<?> collection) {
                return ak.this.a(Maps.b(Predicates.not(Predicates.in(collection))));
            }
        }

        @Override // com.google.common.collect.Maps.ad
        Collection<Collection<V>> b() {
            return new c();
        }
    }

    @Override // com.google.common.collect.g
    Multiset<K> j() {
        return new b();
    }

    /* compiled from: FilteredEntryMultimap.java */
    /* loaded from: classes2.dex */
    public class b extends Multimaps.g<K, V> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b() {
            super(r1);
            ak.this = r1;
        }

        @Override // com.google.common.collect.Multimaps.g, com.google.common.collect.h, com.google.common.collect.Multiset
        public int remove(@NullableDecl Object obj, int i) {
            t.a(i, "occurrences");
            if (i == 0) {
                return count(obj);
            }
            Collection<V> collection = ak.this.a.asMap().get(obj);
            int i2 = 0;
            if (collection == null) {
                return 0;
            }
            Iterator<V> it = collection.iterator();
            while (it.hasNext()) {
                if (ak.this.a((ak) obj, (Object) it.next()) && (i2 = i2 + 1) <= i) {
                    it.remove();
                }
            }
            return i2;
        }

        @Override // com.google.common.collect.h, com.google.common.collect.Multiset
        public Set<Multiset.Entry<K>> entrySet() {
            return new Multisets.d<K>() { // from class: com.google.common.collect.ak.b.1
                @Override // com.google.common.collect.Multisets.d
                Multiset<K> a() {
                    return b.this;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<Multiset.Entry<K>> iterator() {
                    return b.this.b();
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return ak.this.keySet().size();
                }

                private boolean a(final Predicate<? super Multiset.Entry<K>> predicate) {
                    return ak.this.a(new Predicate<Map.Entry<K, Collection<V>>>() { // from class: com.google.common.collect.ak.b.1.1
                        /* renamed from: a */
                        public boolean apply(Map.Entry<K, Collection<V>> entry) {
                            return predicate.apply(Multisets.immutableEntry(entry.getKey(), entry.getValue().size()));
                        }
                    });
                }

                @Override // com.google.common.collect.Sets.f, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean removeAll(Collection<?> collection) {
                    return a(Predicates.in(collection));
                }

                @Override // com.google.common.collect.Sets.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean retainAll(Collection<?> collection) {
                    return a(Predicates.not(Predicates.in(collection)));
                }
            };
        }
    }
}
