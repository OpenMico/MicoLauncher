package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Synchronized.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class cn {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class o implements Serializable {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        final Object delegate;
        final Object mutex;

        o(Object obj, @NullableDecl Object obj2) {
            this.delegate = Preconditions.checkNotNull(obj);
            this.mutex = obj2 == null ? this : obj2;
        }

        Object c() {
            return this.delegate;
        }

        public String toString() {
            String obj;
            synchronized (this.mutex) {
                obj = this.delegate.toString();
            }
            return obj;
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            synchronized (this.mutex) {
                objectOutputStream.defaultWriteObject();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> Collection<E> c(Collection<E> collection, @NullableDecl Object obj) {
        return new e(collection, obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Synchronized.java */
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static class e<E> extends o implements Collection<E> {
        private static final long serialVersionUID = 0;

        private e(Collection<E> collection, @NullableDecl Object obj) {
            super(collection, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: a */
        public Collection<E> c() {
            return (Collection) super.c();
        }

        @Override // java.util.Collection
        public boolean add(E e) {
            boolean add;
            synchronized (this.mutex) {
                add = c().add(e);
            }
            return add;
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            boolean addAll;
            synchronized (this.mutex) {
                addAll = c().addAll(collection);
            }
            return addAll;
        }

        @Override // java.util.Collection
        public void clear() {
            synchronized (this.mutex) {
                c().clear();
            }
        }

        public boolean contains(Object obj) {
            boolean contains;
            synchronized (this.mutex) {
                contains = c().contains(obj);
            }
            return contains;
        }

        public boolean containsAll(Collection<?> collection) {
            boolean containsAll;
            synchronized (this.mutex) {
                containsAll = c().containsAll(collection);
            }
            return containsAll;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.mutex) {
                isEmpty = c().isEmpty();
            }
            return isEmpty;
        }

        public Iterator<E> iterator() {
            return c().iterator();
        }

        public boolean remove(Object obj) {
            boolean remove;
            synchronized (this.mutex) {
                remove = c().remove(obj);
            }
            return remove;
        }

        public boolean removeAll(Collection<?> collection) {
            boolean removeAll;
            synchronized (this.mutex) {
                removeAll = c().removeAll(collection);
            }
            return removeAll;
        }

        public boolean retainAll(Collection<?> collection) {
            boolean retainAll;
            synchronized (this.mutex) {
                retainAll = c().retainAll(collection);
            }
            return retainAll;
        }

        @Override // java.util.Collection
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = c().size();
            }
            return size;
        }

        public Object[] toArray() {
            Object[] array;
            synchronized (this.mutex) {
                array = c().toArray();
            }
            return array;
        }

        public <T> T[] toArray(T[] tArr) {
            T[] tArr2;
            synchronized (this.mutex) {
                tArr2 = (T[]) c().toArray(tArr);
            }
            return tArr2;
        }
    }

    @VisibleForTesting
    static <E> Set<E> a(Set<E> set, @NullableDecl Object obj) {
        return new r(set, obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class r<E> extends e<E> implements Set<E> {
        private static final long serialVersionUID = 0;

        r(Set<E> set, @NullableDecl Object obj) {
            super(set, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: e */
        public Set<E> c() {
            return (Set) super.c();
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                equals = c().equals(obj);
            }
            return equals;
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> SortedSet<E> b(SortedSet<E> sortedSet, @NullableDecl Object obj) {
        return new u(sortedSet, obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class u<E> extends r<E> implements SortedSet<E> {
        private static final long serialVersionUID = 0;

        u(SortedSet<E> sortedSet, @NullableDecl Object obj) {
            super(sortedSet, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: d */
        public SortedSet<E> e() {
            return (SortedSet) super.c();
        }

        @Override // java.util.SortedSet
        public Comparator<? super E> comparator() {
            Comparator<? super E> comparator;
            synchronized (this.mutex) {
                comparator = e().comparator();
            }
            return comparator;
        }

        public SortedSet<E> subSet(E e, E e2) {
            SortedSet<E> b;
            synchronized (this.mutex) {
                b = cn.b((SortedSet) e().subSet(e, e2), this.mutex);
            }
            return b;
        }

        public SortedSet<E> headSet(E e) {
            SortedSet<E> b;
            synchronized (this.mutex) {
                b = cn.b((SortedSet) e().headSet(e), this.mutex);
            }
            return b;
        }

        public SortedSet<E> tailSet(E e) {
            SortedSet<E> b;
            synchronized (this.mutex) {
                b = cn.b((SortedSet) e().tailSet(e), this.mutex);
            }
            return b;
        }

        @Override // java.util.SortedSet
        public E first() {
            E first;
            synchronized (this.mutex) {
                first = e().first();
            }
            return first;
        }

        @Override // java.util.SortedSet
        public E last() {
            E last;
            synchronized (this.mutex) {
                last = e().last();
            }
            return last;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> List<E> b(List<E> list, @NullableDecl Object obj) {
        return list instanceof RandomAccess ? new q(list, obj) : new h(list, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class h<E> extends e<E> implements List<E> {
        private static final long serialVersionUID = 0;

        h(List<E> list, @NullableDecl Object obj) {
            super(list, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: b */
        public List<E> c() {
            return (List) super.c();
        }

        @Override // java.util.List
        public void add(int i, E e) {
            synchronized (this.mutex) {
                c().add(i, e);
            }
        }

        @Override // java.util.List
        public boolean addAll(int i, Collection<? extends E> collection) {
            boolean addAll;
            synchronized (this.mutex) {
                addAll = c().addAll(i, collection);
            }
            return addAll;
        }

        @Override // java.util.List
        public E get(int i) {
            E e;
            synchronized (this.mutex) {
                e = c().get(i);
            }
            return e;
        }

        @Override // java.util.List
        public int indexOf(Object obj) {
            int indexOf;
            synchronized (this.mutex) {
                indexOf = c().indexOf(obj);
            }
            return indexOf;
        }

        @Override // java.util.List
        public int lastIndexOf(Object obj) {
            int lastIndexOf;
            synchronized (this.mutex) {
                lastIndexOf = c().lastIndexOf(obj);
            }
            return lastIndexOf;
        }

        @Override // java.util.List
        public ListIterator<E> listIterator() {
            return c().listIterator();
        }

        @Override // java.util.List
        public ListIterator<E> listIterator(int i) {
            return c().listIterator(i);
        }

        @Override // java.util.List
        public E remove(int i) {
            E remove;
            synchronized (this.mutex) {
                remove = c().remove(i);
            }
            return remove;
        }

        @Override // java.util.List
        public E set(int i, E e) {
            E e2;
            synchronized (this.mutex) {
                e2 = c().set(i, e);
            }
            return e2;
        }

        @Override // java.util.List
        public List<E> subList(int i, int i2) {
            List<E> b;
            synchronized (this.mutex) {
                b = cn.b((List) c().subList(i, i2), this.mutex);
            }
            return b;
        }

        @Override // java.util.Collection, java.util.List
        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                equals = c().equals(obj);
            }
            return equals;
        }

        @Override // java.util.Collection, java.util.List
        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class q<E> extends h<E> implements RandomAccess {
        private static final long serialVersionUID = 0;

        q(List<E> list, @NullableDecl Object obj) {
            super(list, obj);
        }
    }

    static <E> Multiset<E> a(Multiset<E> multiset, @NullableDecl Object obj) {
        return ((multiset instanceof l) || (multiset instanceof ImmutableMultiset)) ? multiset : new l(multiset, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class l<E> extends e<E> implements Multiset<E> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Set<E> a;
        @MonotonicNonNullDecl
        transient Set<Multiset.Entry<E>> b;

        l(Multiset<E> multiset, @NullableDecl Object obj) {
            super(multiset, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: b */
        public Multiset<E> c() {
            return (Multiset) super.c();
        }

        @Override // com.google.common.collect.Multiset
        public int count(Object obj) {
            int count;
            synchronized (this.mutex) {
                count = c().count(obj);
            }
            return count;
        }

        @Override // com.google.common.collect.Multiset
        public int add(E e, int i) {
            int add;
            synchronized (this.mutex) {
                add = c().add(e, i);
            }
            return add;
        }

        @Override // com.google.common.collect.Multiset
        public int remove(Object obj, int i) {
            int remove;
            synchronized (this.mutex) {
                remove = c().remove(obj, i);
            }
            return remove;
        }

        @Override // com.google.common.collect.Multiset
        public int setCount(E e, int i) {
            int count;
            synchronized (this.mutex) {
                count = c().setCount(e, i);
            }
            return count;
        }

        @Override // com.google.common.collect.Multiset
        public boolean setCount(E e, int i, int i2) {
            boolean count;
            synchronized (this.mutex) {
                count = c().setCount(e, i, i2);
            }
            return count;
        }

        @Override // com.google.common.collect.Multiset
        public Set<E> elementSet() {
            Set<E> set;
            synchronized (this.mutex) {
                if (this.a == null) {
                    this.a = cn.c((Set) c().elementSet(), this.mutex);
                }
                set = this.a;
            }
            return set;
        }

        @Override // com.google.common.collect.Multiset
        public Set<Multiset.Entry<E>> entrySet() {
            Set<Multiset.Entry<E>> set;
            synchronized (this.mutex) {
                if (this.b == null) {
                    this.b = cn.c((Set) c().entrySet(), this.mutex);
                }
                set = this.b;
            }
            return set;
        }

        @Override // java.util.Collection, com.google.common.collect.Multiset
        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                equals = c().equals(obj);
            }
            return equals;
        }

        @Override // java.util.Collection, com.google.common.collect.Multiset
        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> Multimap<K, V> a(Multimap<K, V> multimap, @NullableDecl Object obj) {
        return ((multimap instanceof k) || (multimap instanceof ImmutableMultimap)) ? multimap : new k(multimap, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class k<K, V> extends o implements Multimap<K, V> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Set<K> a;
        @MonotonicNonNullDecl
        transient Collection<V> b;
        @MonotonicNonNullDecl
        transient Collection<Map.Entry<K, V>> c;
        @MonotonicNonNullDecl
        transient Map<K, Collection<V>> d;
        @MonotonicNonNullDecl
        transient Multiset<K> e;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: b */
        public Multimap<K, V> c() {
            return (Multimap) super.c();
        }

        k(Multimap<K, V> multimap, @NullableDecl Object obj) {
            super(multimap, obj);
        }

        @Override // com.google.common.collect.Multimap
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = c().size();
            }
            return size;
        }

        @Override // com.google.common.collect.Multimap
        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.mutex) {
                isEmpty = c().isEmpty();
            }
            return isEmpty;
        }

        @Override // com.google.common.collect.Multimap
        public boolean containsKey(Object obj) {
            boolean containsKey;
            synchronized (this.mutex) {
                containsKey = c().containsKey(obj);
            }
            return containsKey;
        }

        @Override // com.google.common.collect.Multimap
        public boolean containsValue(Object obj) {
            boolean containsValue;
            synchronized (this.mutex) {
                containsValue = c().containsValue(obj);
            }
            return containsValue;
        }

        @Override // com.google.common.collect.Multimap
        public boolean containsEntry(Object obj, Object obj2) {
            boolean containsEntry;
            synchronized (this.mutex) {
                containsEntry = c().containsEntry(obj, obj2);
            }
            return containsEntry;
        }

        public Collection<V> get(K k) {
            Collection<V> d;
            synchronized (this.mutex) {
                d = cn.d(c().get(k), this.mutex);
            }
            return d;
        }

        @Override // com.google.common.collect.Multimap
        public boolean put(K k, V v) {
            boolean put;
            synchronized (this.mutex) {
                put = c().put(k, v);
            }
            return put;
        }

        @Override // com.google.common.collect.Multimap
        public boolean putAll(K k, Iterable<? extends V> iterable) {
            boolean putAll;
            synchronized (this.mutex) {
                putAll = c().putAll(k, iterable);
            }
            return putAll;
        }

        @Override // com.google.common.collect.Multimap
        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            boolean putAll;
            synchronized (this.mutex) {
                putAll = c().putAll(multimap);
            }
            return putAll;
        }

        public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
            Collection<V> replaceValues;
            synchronized (this.mutex) {
                replaceValues = c().replaceValues(k, iterable);
            }
            return replaceValues;
        }

        @Override // com.google.common.collect.Multimap
        public boolean remove(Object obj, Object obj2) {
            boolean remove;
            synchronized (this.mutex) {
                remove = c().remove(obj, obj2);
            }
            return remove;
        }

        public Collection<V> removeAll(Object obj) {
            Collection<V> removeAll;
            synchronized (this.mutex) {
                removeAll = c().removeAll(obj);
            }
            return removeAll;
        }

        @Override // com.google.common.collect.Multimap
        public void clear() {
            synchronized (this.mutex) {
                c().clear();
            }
        }

        @Override // com.google.common.collect.Multimap
        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.mutex) {
                if (this.a == null) {
                    this.a = cn.c((Set) c().keySet(), this.mutex);
                }
                set = this.a;
            }
            return set;
        }

        @Override // com.google.common.collect.Multimap
        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.mutex) {
                if (this.b == null) {
                    this.b = cn.c(c().values(), this.mutex);
                }
                collection = this.b;
            }
            return collection;
        }

        @Override // com.google.common.collect.Multimap
        public Collection<Map.Entry<K, V>> entries() {
            Collection<Map.Entry<K, V>> collection;
            synchronized (this.mutex) {
                if (this.c == null) {
                    this.c = cn.d(c().entries(), this.mutex);
                }
                collection = this.c;
            }
            return collection;
        }

        @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> map;
            synchronized (this.mutex) {
                if (this.d == null) {
                    this.d = new a(c().asMap(), this.mutex);
                }
                map = this.d;
            }
            return map;
        }

        @Override // com.google.common.collect.Multimap
        public Multiset<K> keys() {
            Multiset<K> multiset;
            synchronized (this.mutex) {
                if (this.e == null) {
                    this.e = cn.a((Multiset) c().keys(), this.mutex);
                }
                multiset = this.e;
            }
            return multiset;
        }

        @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                equals = c().equals(obj);
            }
            return equals;
        }

        @Override // com.google.common.collect.Multimap
        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> ListMultimap<K, V> a(ListMultimap<K, V> listMultimap, @NullableDecl Object obj) {
        return ((listMultimap instanceof i) || (listMultimap instanceof ImmutableListMultimap)) ? listMultimap : new i(listMultimap, obj);
    }

    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    private static class i<K, V> extends k<K, V> implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0;

        i(ListMultimap<K, V> listMultimap, @NullableDecl Object obj) {
            super(listMultimap, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: a */
        public ListMultimap<K, V> c() {
            return (ListMultimap) super.c();
        }

        @Override // com.google.common.collect.cn.k, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V> get(K k) {
            List<V> b;
            synchronized (this.mutex) {
                b = cn.b((List) c().get((ListMultimap<K, V>) k), this.mutex);
            }
            return b;
        }

        @Override // com.google.common.collect.cn.k, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V> removeAll(Object obj) {
            List<V> removeAll;
            synchronized (this.mutex) {
                removeAll = c().removeAll(obj);
            }
            return removeAll;
        }

        @Override // com.google.common.collect.cn.k, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
            List<V> replaceValues;
            synchronized (this.mutex) {
                replaceValues = c().replaceValues((ListMultimap<K, V>) k, (Iterable) iterable);
            }
            return replaceValues;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> SetMultimap<K, V> a(SetMultimap<K, V> setMultimap, @NullableDecl Object obj) {
        return ((setMultimap instanceof s) || (setMultimap instanceof ImmutableSetMultimap)) ? setMultimap : new s(setMultimap, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class s<K, V> extends k<K, V> implements SetMultimap<K, V> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Set<Map.Entry<K, V>> f;

        s(SetMultimap<K, V> setMultimap, @NullableDecl Object obj) {
            super(setMultimap, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: a */
        public SetMultimap<K, V> c() {
            return (SetMultimap) super.c();
        }

        @Override // com.google.common.collect.cn.k, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> get(K k) {
            Set<V> a;
            synchronized (this.mutex) {
                a = cn.a((Set) c().get((SetMultimap<K, V>) k), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.cn.k, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> removeAll(Object obj) {
            Set<V> removeAll;
            synchronized (this.mutex) {
                removeAll = c().removeAll(obj);
            }
            return removeAll;
        }

        @Override // com.google.common.collect.cn.k, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            Set<V> replaceValues;
            synchronized (this.mutex) {
                replaceValues = c().replaceValues((SetMultimap<K, V>) k, (Iterable) iterable);
            }
            return replaceValues;
        }

        @Override // com.google.common.collect.cn.k, com.google.common.collect.Multimap
        public Set<Map.Entry<K, V>> entries() {
            Set<Map.Entry<K, V>> set;
            synchronized (this.mutex) {
                if (this.f == null) {
                    this.f = cn.a((Set) c().entries(), this.mutex);
                }
                set = this.f;
            }
            return set;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> SortedSetMultimap<K, V> a(SortedSetMultimap<K, V> sortedSetMultimap, @NullableDecl Object obj) {
        return sortedSetMultimap instanceof v ? sortedSetMultimap : new v(sortedSetMultimap, obj);
    }

    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    private static class v<K, V> extends s<K, V> implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        v(SortedSetMultimap<K, V> sortedSetMultimap, @NullableDecl Object obj) {
            super(sortedSetMultimap, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: d */
        public SortedSetMultimap<K, V> c() {
            return (SortedSetMultimap) super.c();
        }

        @Override // com.google.common.collect.cn.s, com.google.common.collect.cn.k, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public SortedSet<V> get(K k) {
            SortedSet<V> b;
            synchronized (this.mutex) {
                b = cn.b((SortedSet) c().get((SortedSetMultimap<K, V>) k), this.mutex);
            }
            return b;
        }

        @Override // com.google.common.collect.cn.s, com.google.common.collect.cn.k, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public SortedSet<V> removeAll(Object obj) {
            SortedSet<V> removeAll;
            synchronized (this.mutex) {
                removeAll = c().removeAll(obj);
            }
            return removeAll;
        }

        @Override // com.google.common.collect.cn.s, com.google.common.collect.cn.k, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
            SortedSet<V> replaceValues;
            synchronized (this.mutex) {
                replaceValues = c().replaceValues((SortedSetMultimap<K, V>) k, (Iterable) iterable);
            }
            return replaceValues;
        }

        @Override // com.google.common.collect.SortedSetMultimap
        public Comparator<? super V> valueComparator() {
            Comparator<? super V> valueComparator;
            synchronized (this.mutex) {
                valueComparator = c().valueComparator();
            }
            return valueComparator;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> Collection<E> d(Collection<E> collection, @NullableDecl Object obj) {
        if (collection instanceof SortedSet) {
            return b((SortedSet) collection, obj);
        }
        if (collection instanceof Set) {
            return a((Set) collection, obj);
        }
        if (collection instanceof List) {
            return b((List) collection, obj);
        }
        return c(collection, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> Set<E> c(Set<E> set, @NullableDecl Object obj) {
        if (set instanceof SortedSet) {
            return b((SortedSet) set, obj);
        }
        return a((Set) set, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class b<K, V> extends r<Map.Entry<K, Collection<V>>> {
        private static final long serialVersionUID = 0;

        b(Set<Map.Entry<K, Collection<V>>> set, @NullableDecl Object obj) {
            super(set, obj);
        }

        @Override // com.google.common.collect.cn.e, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, Collection<V>>> iterator() {
            return new cp<Map.Entry<K, Collection<V>>, Map.Entry<K, Collection<V>>>(super.iterator()) { // from class: com.google.common.collect.cn.b.1
                @Override // com.google.common.collect.cp
                /* bridge */ /* synthetic */ Object a(Object obj) {
                    return a((Map.Entry) ((Map.Entry) obj));
                }

                Map.Entry<K, Collection<V>> a(final Map.Entry<K, Collection<V>> entry) {
                    return new ForwardingMapEntry<K, Collection<V>>() { // from class: com.google.common.collect.cn.b.1.1
                        /* JADX INFO: Access modifiers changed from: protected */
                        @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
                        public Map.Entry<K, Collection<V>> delegate() {
                            return entry;
                        }

                        /* renamed from: a */
                        public Collection<V> getValue() {
                            return cn.d((Collection) entry.getValue(), b.this.mutex);
                        }
                    };
                }
            };
        }

        @Override // com.google.common.collect.cn.e, java.util.Collection, java.util.Set
        public Object[] toArray() {
            Object[] a;
            synchronized (this.mutex) {
                a = ObjectArrays.a(c());
            }
            return a;
        }

        @Override // com.google.common.collect.cn.e, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            T[] tArr2;
            synchronized (this.mutex) {
                tArr2 = (T[]) ObjectArrays.a((Collection<?>) c(), (Object[]) tArr);
            }
            return tArr2;
        }

        @Override // com.google.common.collect.cn.e, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            boolean a;
            synchronized (this.mutex) {
                a = Maps.a(c(), obj);
            }
            return a;
        }

        @Override // com.google.common.collect.cn.e, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            boolean a;
            synchronized (this.mutex) {
                a = Collections2.a((Collection<?>) c(), collection);
            }
            return a;
        }

        @Override // com.google.common.collect.cn.r, java.util.Collection, java.util.Set
        public boolean equals(Object obj) {
            boolean a;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                a = Sets.a(c(), obj);
            }
            return a;
        }

        @Override // com.google.common.collect.cn.e, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            boolean b;
            synchronized (this.mutex) {
                b = Maps.b(c(), obj);
            }
            return b;
        }

        @Override // com.google.common.collect.cn.e, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            boolean removeAll;
            synchronized (this.mutex) {
                removeAll = Iterators.removeAll(c().iterator(), collection);
            }
            return removeAll;
        }

        @Override // com.google.common.collect.cn.e, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            boolean retainAll;
            synchronized (this.mutex) {
                retainAll = Iterators.retainAll(c().iterator(), collection);
            }
            return retainAll;
        }
    }

    @VisibleForTesting
    static <K, V> Map<K, V> a(Map<K, V> map, @NullableDecl Object obj) {
        return new j(map, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class j<K, V> extends o implements Map<K, V> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Set<K> c;
        @MonotonicNonNullDecl
        transient Collection<V> d;
        @MonotonicNonNullDecl
        transient Set<Map.Entry<K, V>> e;

        j(Map<K, V> map, @NullableDecl Object obj) {
            super(map, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: b */
        public Map<K, V> c() {
            return (Map) super.c();
        }

        @Override // java.util.Map
        public void clear() {
            synchronized (this.mutex) {
                c().clear();
            }
        }

        @Override // java.util.Map
        public boolean containsKey(Object obj) {
            boolean containsKey;
            synchronized (this.mutex) {
                containsKey = c().containsKey(obj);
            }
            return containsKey;
        }

        public boolean containsValue(Object obj) {
            boolean containsValue;
            synchronized (this.mutex) {
                containsValue = c().containsValue(obj);
            }
            return containsValue;
        }

        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set;
            synchronized (this.mutex) {
                if (this.e == null) {
                    this.e = cn.a((Set) c().entrySet(), this.mutex);
                }
                set = this.e;
            }
            return set;
        }

        public V get(Object obj) {
            V v;
            synchronized (this.mutex) {
                v = c().get(obj);
            }
            return v;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.mutex) {
                isEmpty = c().isEmpty();
            }
            return isEmpty;
        }

        @Override // java.util.Map
        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.mutex) {
                if (this.c == null) {
                    this.c = cn.a((Set) c().keySet(), this.mutex);
                }
                set = this.c;
            }
            return set;
        }

        @Override // java.util.Map
        public V put(K k, V v) {
            V put;
            synchronized (this.mutex) {
                put = c().put(k, v);
            }
            return put;
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            synchronized (this.mutex) {
                c().putAll(map);
            }
        }

        @Override // java.util.Map
        public V remove(Object obj) {
            V remove;
            synchronized (this.mutex) {
                remove = c().remove(obj);
            }
            return remove;
        }

        @Override // java.util.Map
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = c().size();
            }
            return size;
        }

        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.mutex) {
                if (this.d == null) {
                    this.d = cn.c(c().values(), this.mutex);
                }
                collection = this.d;
            }
            return collection;
        }

        @Override // java.util.Map
        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                equals = c().equals(obj);
            }
            return equals;
        }

        @Override // java.util.Map
        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }
    }

    static <K, V> SortedMap<K, V> a(SortedMap<K, V> sortedMap, @NullableDecl Object obj) {
        return new t(sortedMap, obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class t<K, V> extends j<K, V> implements SortedMap<K, V> {
        private static final long serialVersionUID = 0;

        t(SortedMap<K, V> sortedMap, @NullableDecl Object obj) {
            super(sortedMap, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: d */
        public SortedMap<K, V> c() {
            return (SortedMap) super.c();
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            Comparator<? super K> comparator;
            synchronized (this.mutex) {
                comparator = c().comparator();
            }
            return comparator;
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            K firstKey;
            synchronized (this.mutex) {
                firstKey = c().firstKey();
            }
            return firstKey;
        }

        public SortedMap<K, V> headMap(K k) {
            SortedMap<K, V> a;
            synchronized (this.mutex) {
                a = cn.a((SortedMap) c().headMap(k), this.mutex);
            }
            return a;
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            K lastKey;
            synchronized (this.mutex) {
                lastKey = c().lastKey();
            }
            return lastKey;
        }

        public SortedMap<K, V> subMap(K k, K k2) {
            SortedMap<K, V> a;
            synchronized (this.mutex) {
                a = cn.a((SortedMap) c().subMap(k, k2), this.mutex);
            }
            return a;
        }

        public SortedMap<K, V> tailMap(K k) {
            SortedMap<K, V> a;
            synchronized (this.mutex) {
                a = cn.a((SortedMap) c().tailMap(k), this.mutex);
            }
            return a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> BiMap<K, V> a(BiMap<K, V> biMap, @NullableDecl Object obj) {
        return ((biMap instanceof d) || (biMap instanceof ImmutableBiMap)) ? biMap : new d(biMap, obj, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Synchronized.java */
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static class d<K, V> extends j<K, V> implements BiMap<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        private transient Set<V> a;
        @RetainedWith
        @MonotonicNonNullDecl
        private transient BiMap<V, K> b;

        private d(BiMap<K, V> biMap, @NullableDecl Object obj, @NullableDecl BiMap<V, K> biMap2) {
            super(biMap, obj);
            this.b = biMap2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: a */
        public BiMap<K, V> c() {
            return (BiMap) super.c();
        }

        @Override // com.google.common.collect.cn.j, java.util.Map
        public Set<V> values() {
            Set<V> set;
            synchronized (this.mutex) {
                if (this.a == null) {
                    this.a = cn.a((Set) c().values(), this.mutex);
                }
                set = this.a;
            }
            return set;
        }

        @Override // com.google.common.collect.BiMap
        public V forcePut(K k, V v) {
            V forcePut;
            synchronized (this.mutex) {
                forcePut = c().forcePut(k, v);
            }
            return forcePut;
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<V, K> inverse() {
            BiMap<V, K> biMap;
            synchronized (this.mutex) {
                if (this.b == null) {
                    this.b = new d(c().inverse(), this.mutex, this);
                }
                biMap = this.b;
            }
            return biMap;
        }
    }

    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    private static class a<K, V> extends j<K, Collection<V>> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Set<Map.Entry<K, Collection<V>>> a;
        @MonotonicNonNullDecl
        transient Collection<Collection<V>> b;

        a(Map<K, Collection<V>> map, @NullableDecl Object obj) {
            super(map, obj);
        }

        /* renamed from: a */
        public Collection<V> get(Object obj) {
            Collection<V> d;
            synchronized (this.mutex) {
                Collection collection = (Collection) super.get(obj);
                d = collection == null ? null : cn.d(collection, this.mutex);
            }
            return d;
        }

        @Override // com.google.common.collect.cn.j, java.util.Map
        public Set<Map.Entry<K, Collection<V>>> entrySet() {
            Set<Map.Entry<K, Collection<V>>> set;
            synchronized (this.mutex) {
                if (this.a == null) {
                    this.a = new b(c().entrySet(), this.mutex);
                }
                set = this.a;
            }
            return set;
        }

        @Override // com.google.common.collect.cn.j, java.util.Map
        public Collection<Collection<V>> values() {
            Collection<Collection<V>> collection;
            synchronized (this.mutex) {
                if (this.b == null) {
                    this.b = new c(c().values(), this.mutex);
                }
                collection = this.b;
            }
            return collection;
        }

        @Override // com.google.common.collect.cn.j, java.util.Map
        public boolean containsValue(Object obj) {
            return values().contains(obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class c<V> extends e<Collection<V>> {
        private static final long serialVersionUID = 0;

        c(Collection<Collection<V>> collection, @NullableDecl Object obj) {
            super(collection, obj);
        }

        @Override // com.google.common.collect.cn.e, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Collection<V>> iterator() {
            return new cp<Collection<V>, Collection<V>>(super.iterator()) { // from class: com.google.common.collect.cn.c.1
                @Override // com.google.common.collect.cp
                /* bridge */ /* synthetic */ Object a(Object obj) {
                    return a((Collection) ((Collection) obj));
                }

                Collection<V> a(Collection<V> collection) {
                    return cn.d(collection, c.this.mutex);
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Synchronized.java */
    @GwtIncompatible
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static class n<E> extends u<E> implements NavigableSet<E> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient NavigableSet<E> a;

        n(NavigableSet<E> navigableSet, @NullableDecl Object obj) {
            super(navigableSet, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: b */
        public NavigableSet<E> e() {
            return (NavigableSet) super.e();
        }

        @Override // java.util.NavigableSet
        public E ceiling(E e) {
            E ceiling;
            synchronized (this.mutex) {
                ceiling = e().ceiling(e);
            }
            return ceiling;
        }

        @Override // java.util.NavigableSet
        public Iterator<E> descendingIterator() {
            return e().descendingIterator();
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> descendingSet() {
            synchronized (this.mutex) {
                if (this.a == null) {
                    NavigableSet<E> a = cn.a((NavigableSet) e().descendingSet(), this.mutex);
                    this.a = a;
                    return a;
                }
                return this.a;
            }
        }

        @Override // java.util.NavigableSet
        public E floor(E e) {
            E floor;
            synchronized (this.mutex) {
                floor = e().floor(e);
            }
            return floor;
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> headSet(E e, boolean z) {
            NavigableSet<E> a;
            synchronized (this.mutex) {
                a = cn.a((NavigableSet) e().headSet(e, z), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.cn.u, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<E> headSet(E e) {
            return headSet(e, false);
        }

        @Override // java.util.NavigableSet
        public E higher(E e) {
            E higher;
            synchronized (this.mutex) {
                higher = e().higher(e);
            }
            return higher;
        }

        @Override // java.util.NavigableSet
        public E lower(E e) {
            E lower;
            synchronized (this.mutex) {
                lower = e().lower(e);
            }
            return lower;
        }

        @Override // java.util.NavigableSet
        public E pollFirst() {
            E pollFirst;
            synchronized (this.mutex) {
                pollFirst = e().pollFirst();
            }
            return pollFirst;
        }

        @Override // java.util.NavigableSet
        public E pollLast() {
            E pollLast;
            synchronized (this.mutex) {
                pollLast = e().pollLast();
            }
            return pollLast;
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            NavigableSet<E> a;
            synchronized (this.mutex) {
                a = cn.a((NavigableSet) e().subSet(e, z, e2, z2), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.cn.u, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<E> subSet(E e, E e2) {
            return subSet(e, true, e2, false);
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> tailSet(E e, boolean z) {
            NavigableSet<E> a;
            synchronized (this.mutex) {
                a = cn.a((NavigableSet) e().tailSet(e, z), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.cn.u, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<E> tailSet(E e) {
            return tailSet(e, true);
        }
    }

    @GwtIncompatible
    static <E> NavigableSet<E> a(NavigableSet<E> navigableSet, @NullableDecl Object obj) {
        return new n(navigableSet, obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @GwtIncompatible
    public static <E> NavigableSet<E> a(NavigableSet<E> navigableSet) {
        return a((NavigableSet) navigableSet, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> a(NavigableMap<K, V> navigableMap) {
        return a((NavigableMap) navigableMap, (Object) null);
    }

    @GwtIncompatible
    static <K, V> NavigableMap<K, V> a(NavigableMap<K, V> navigableMap, @NullableDecl Object obj) {
        return new m(navigableMap, obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Synchronized.java */
    @GwtIncompatible
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static class m<K, V> extends t<K, V> implements NavigableMap<K, V> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient NavigableSet<K> a;
        @MonotonicNonNullDecl
        transient NavigableMap<K, V> b;
        @MonotonicNonNullDecl
        transient NavigableSet<K> f;

        m(NavigableMap<K, V> navigableMap, @NullableDecl Object obj) {
            super(navigableMap, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: a */
        public NavigableMap<K, V> d() {
            return (NavigableMap) super.c();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> ceilingEntry(K k) {
            Map.Entry<K, V> b;
            synchronized (this.mutex) {
                b = cn.b(d().ceilingEntry(k), this.mutex);
            }
            return b;
        }

        @Override // java.util.NavigableMap
        public K ceilingKey(K k) {
            K ceilingKey;
            synchronized (this.mutex) {
                ceilingKey = d().ceilingKey(k);
            }
            return ceilingKey;
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> descendingKeySet() {
            synchronized (this.mutex) {
                if (this.a == null) {
                    NavigableSet<K> a = cn.a((NavigableSet) d().descendingKeySet(), this.mutex);
                    this.a = a;
                    return a;
                }
                return this.a;
            }
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> descendingMap() {
            synchronized (this.mutex) {
                if (this.b == null) {
                    NavigableMap<K, V> a = cn.a((NavigableMap) d().descendingMap(), this.mutex);
                    this.b = a;
                    return a;
                }
                return this.b;
            }
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> firstEntry() {
            Map.Entry<K, V> b;
            synchronized (this.mutex) {
                b = cn.b(d().firstEntry(), this.mutex);
            }
            return b;
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> floorEntry(K k) {
            Map.Entry<K, V> b;
            synchronized (this.mutex) {
                b = cn.b(d().floorEntry(k), this.mutex);
            }
            return b;
        }

        @Override // java.util.NavigableMap
        public K floorKey(K k) {
            K floorKey;
            synchronized (this.mutex) {
                floorKey = d().floorKey(k);
            }
            return floorKey;
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> headMap(K k, boolean z) {
            NavigableMap<K, V> a;
            synchronized (this.mutex) {
                a = cn.a((NavigableMap) d().headMap(k, z), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.cn.t, java.util.SortedMap, java.util.NavigableMap
        public SortedMap<K, V> headMap(K k) {
            return headMap(k, false);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> higherEntry(K k) {
            Map.Entry<K, V> b;
            synchronized (this.mutex) {
                b = cn.b(d().higherEntry(k), this.mutex);
            }
            return b;
        }

        @Override // java.util.NavigableMap
        public K higherKey(K k) {
            K higherKey;
            synchronized (this.mutex) {
                higherKey = d().higherKey(k);
            }
            return higherKey;
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> lastEntry() {
            Map.Entry<K, V> b;
            synchronized (this.mutex) {
                b = cn.b(d().lastEntry(), this.mutex);
            }
            return b;
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> lowerEntry(K k) {
            Map.Entry<K, V> b;
            synchronized (this.mutex) {
                b = cn.b(d().lowerEntry(k), this.mutex);
            }
            return b;
        }

        @Override // java.util.NavigableMap
        public K lowerKey(K k) {
            K lowerKey;
            synchronized (this.mutex) {
                lowerKey = d().lowerKey(k);
            }
            return lowerKey;
        }

        @Override // com.google.common.collect.cn.j, java.util.Map
        public Set<K> keySet() {
            return navigableKeySet();
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            synchronized (this.mutex) {
                if (this.f == null) {
                    NavigableSet<K> a = cn.a((NavigableSet) d().navigableKeySet(), this.mutex);
                    this.f = a;
                    return a;
                }
                return this.f;
            }
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> pollFirstEntry() {
            Map.Entry<K, V> b;
            synchronized (this.mutex) {
                b = cn.b(d().pollFirstEntry(), this.mutex);
            }
            return b;
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> pollLastEntry() {
            Map.Entry<K, V> b;
            synchronized (this.mutex) {
                b = cn.b(d().pollLastEntry(), this.mutex);
            }
            return b;
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            NavigableMap<K, V> a;
            synchronized (this.mutex) {
                a = cn.a((NavigableMap) d().subMap(k, z, k2, z2), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.cn.t, java.util.SortedMap, java.util.NavigableMap
        public SortedMap<K, V> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> tailMap(K k, boolean z) {
            NavigableMap<K, V> a;
            synchronized (this.mutex) {
                a = cn.a((NavigableMap) d().tailMap(k, z), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.cn.t, java.util.SortedMap, java.util.NavigableMap
        public SortedMap<K, V> tailMap(K k) {
            return tailMap(k, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GwtIncompatible
    public static <K, V> Map.Entry<K, V> b(@NullableDecl Map.Entry<K, V> entry, @NullableDecl Object obj) {
        if (entry == null) {
            return null;
        }
        return new g(entry, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Synchronized.java */
    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static class g<K, V> extends o implements Map.Entry<K, V> {
        private static final long serialVersionUID = 0;

        g(Map.Entry<K, V> entry, @NullableDecl Object obj) {
            super(entry, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: a */
        public Map.Entry<K, V> c() {
            return (Map.Entry) super.c();
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            boolean equals;
            synchronized (this.mutex) {
                equals = c().equals(obj);
            }
            return equals;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            K key;
            synchronized (this.mutex) {
                key = c().getKey();
            }
            return key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            V value;
            synchronized (this.mutex) {
                value = c().getValue();
            }
            return value;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V value;
            synchronized (this.mutex) {
                value = c().setValue(v);
            }
            return value;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <E> Queue<E> a(Queue<E> queue, @NullableDecl Object obj) {
        return queue instanceof p ? queue : new p(queue, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static class p<E> extends e<E> implements Queue<E> {
        private static final long serialVersionUID = 0;

        p(Queue<E> queue, @NullableDecl Object obj) {
            super(queue, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: d */
        public Queue<E> c() {
            return (Queue) super.c();
        }

        @Override // java.util.Queue
        public E element() {
            E element;
            synchronized (this.mutex) {
                element = c().element();
            }
            return element;
        }

        @Override // java.util.Queue
        public boolean offer(E e) {
            boolean offer;
            synchronized (this.mutex) {
                offer = c().offer(e);
            }
            return offer;
        }

        @Override // java.util.Queue
        public E peek() {
            E peek;
            synchronized (this.mutex) {
                peek = c().peek();
            }
            return peek;
        }

        @Override // java.util.Queue
        public E poll() {
            E poll;
            synchronized (this.mutex) {
                poll = c().poll();
            }
            return poll;
        }

        @Override // java.util.Queue
        public E remove() {
            E remove;
            synchronized (this.mutex) {
                remove = c().remove();
            }
            return remove;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <E> Deque<E> a(Deque<E> deque, @NullableDecl Object obj) {
        return new f(deque, obj);
    }

    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    private static final class f<E> extends p<E> implements Deque<E> {
        private static final long serialVersionUID = 0;

        f(Deque<E> deque, @NullableDecl Object obj) {
            super(deque, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: b */
        public Deque<E> d() {
            return (Deque) super.c();
        }

        @Override // java.util.Deque
        public void addFirst(E e) {
            synchronized (this.mutex) {
                d().addFirst(e);
            }
        }

        @Override // java.util.Deque
        public void addLast(E e) {
            synchronized (this.mutex) {
                d().addLast(e);
            }
        }

        @Override // java.util.Deque
        public boolean offerFirst(E e) {
            boolean offerFirst;
            synchronized (this.mutex) {
                offerFirst = d().offerFirst(e);
            }
            return offerFirst;
        }

        @Override // java.util.Deque
        public boolean offerLast(E e) {
            boolean offerLast;
            synchronized (this.mutex) {
                offerLast = d().offerLast(e);
            }
            return offerLast;
        }

        @Override // java.util.Deque
        public E removeFirst() {
            E removeFirst;
            synchronized (this.mutex) {
                removeFirst = d().removeFirst();
            }
            return removeFirst;
        }

        @Override // java.util.Deque
        public E removeLast() {
            E removeLast;
            synchronized (this.mutex) {
                removeLast = d().removeLast();
            }
            return removeLast;
        }

        @Override // java.util.Deque
        public E pollFirst() {
            E pollFirst;
            synchronized (this.mutex) {
                pollFirst = d().pollFirst();
            }
            return pollFirst;
        }

        @Override // java.util.Deque
        public E pollLast() {
            E pollLast;
            synchronized (this.mutex) {
                pollLast = d().pollLast();
            }
            return pollLast;
        }

        @Override // java.util.Deque
        public E getFirst() {
            E first;
            synchronized (this.mutex) {
                first = d().getFirst();
            }
            return first;
        }

        @Override // java.util.Deque
        public E getLast() {
            E last;
            synchronized (this.mutex) {
                last = d().getLast();
            }
            return last;
        }

        @Override // java.util.Deque
        public E peekFirst() {
            E peekFirst;
            synchronized (this.mutex) {
                peekFirst = d().peekFirst();
            }
            return peekFirst;
        }

        @Override // java.util.Deque
        public E peekLast() {
            E peekLast;
            synchronized (this.mutex) {
                peekLast = d().peekLast();
            }
            return peekLast;
        }

        @Override // java.util.Deque
        public boolean removeFirstOccurrence(Object obj) {
            boolean removeFirstOccurrence;
            synchronized (this.mutex) {
                removeFirstOccurrence = d().removeFirstOccurrence(obj);
            }
            return removeFirstOccurrence;
        }

        @Override // java.util.Deque
        public boolean removeLastOccurrence(Object obj) {
            boolean removeLastOccurrence;
            synchronized (this.mutex) {
                removeLastOccurrence = d().removeLastOccurrence(obj);
            }
            return removeLastOccurrence;
        }

        @Override // java.util.Deque
        public void push(E e) {
            synchronized (this.mutex) {
                d().push(e);
            }
        }

        @Override // java.util.Deque
        public E pop() {
            E pop;
            synchronized (this.mutex) {
                pop = d().pop();
            }
            return pop;
        }

        @Override // java.util.Deque
        public Iterator<E> descendingIterator() {
            Iterator<E> descendingIterator;
            synchronized (this.mutex) {
                descendingIterator = d().descendingIterator();
            }
            return descendingIterator;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <R, C, V> Table<R, C, V> a(Table<R, C, V> table, Object obj) {
        return new w(table, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Synchronized.java */
    /* loaded from: classes2.dex */
    public static final class w<R, C, V> extends o implements Table<R, C, V> {
        w(Table<R, C, V> table, Object obj) {
            super(table, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: a */
        public Table<R, C, V> c() {
            return (Table) super.c();
        }

        @Override // com.google.common.collect.Table
        public boolean contains(@NullableDecl Object obj, @NullableDecl Object obj2) {
            boolean contains;
            synchronized (this.mutex) {
                contains = c().contains(obj, obj2);
            }
            return contains;
        }

        @Override // com.google.common.collect.Table
        public boolean containsRow(@NullableDecl Object obj) {
            boolean containsRow;
            synchronized (this.mutex) {
                containsRow = c().containsRow(obj);
            }
            return containsRow;
        }

        @Override // com.google.common.collect.Table
        public boolean containsColumn(@NullableDecl Object obj) {
            boolean containsColumn;
            synchronized (this.mutex) {
                containsColumn = c().containsColumn(obj);
            }
            return containsColumn;
        }

        @Override // com.google.common.collect.Table
        public boolean containsValue(@NullableDecl Object obj) {
            boolean containsValue;
            synchronized (this.mutex) {
                containsValue = c().containsValue(obj);
            }
            return containsValue;
        }

        @Override // com.google.common.collect.Table
        public V get(@NullableDecl Object obj, @NullableDecl Object obj2) {
            V v;
            synchronized (this.mutex) {
                v = c().get(obj, obj2);
            }
            return v;
        }

        @Override // com.google.common.collect.Table
        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.mutex) {
                isEmpty = c().isEmpty();
            }
            return isEmpty;
        }

        @Override // com.google.common.collect.Table
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = c().size();
            }
            return size;
        }

        @Override // com.google.common.collect.Table
        public void clear() {
            synchronized (this.mutex) {
                c().clear();
            }
        }

        @Override // com.google.common.collect.Table
        public V put(@NullableDecl R r, @NullableDecl C c, @NullableDecl V v) {
            V put;
            synchronized (this.mutex) {
                put = c().put(r, c, v);
            }
            return put;
        }

        @Override // com.google.common.collect.Table
        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            synchronized (this.mutex) {
                c().putAll(table);
            }
        }

        @Override // com.google.common.collect.Table
        public V remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
            V remove;
            synchronized (this.mutex) {
                remove = c().remove(obj, obj2);
            }
            return remove;
        }

        @Override // com.google.common.collect.Table
        public Map<C, V> row(@NullableDecl R r) {
            Map<C, V> a;
            synchronized (this.mutex) {
                a = cn.a(c().row(r), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.Table
        public Map<R, V> column(@NullableDecl C c) {
            Map<R, V> a;
            synchronized (this.mutex) {
                a = cn.a(c().column(c), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.Table
        public Set<Table.Cell<R, C, V>> cellSet() {
            Set<Table.Cell<R, C, V>> a;
            synchronized (this.mutex) {
                a = cn.a((Set) c().cellSet(), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.Table
        public Set<R> rowKeySet() {
            Set<R> a;
            synchronized (this.mutex) {
                a = cn.a((Set) c().rowKeySet(), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.Table
        public Set<C> columnKeySet() {
            Set<C> a;
            synchronized (this.mutex) {
                a = cn.a((Set) c().columnKeySet(), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.Table
        public Collection<V> values() {
            Collection<V> c;
            synchronized (this.mutex) {
                c = cn.c(c().values(), this.mutex);
            }
            return c;
        }

        @Override // com.google.common.collect.Table
        public Map<R, Map<C, V>> rowMap() {
            Map<R, Map<C, V>> a;
            synchronized (this.mutex) {
                a = cn.a(Maps.transformValues(c().rowMap(), new Function<Map<C, V>, Map<C, V>>() { // from class: com.google.common.collect.cn.w.1
                    /* renamed from: a */
                    public Map<C, V> apply(Map<C, V> map) {
                        return cn.a(map, w.this.mutex);
                    }
                }), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.Table
        public Map<C, Map<R, V>> columnMap() {
            Map<C, Map<R, V>> a;
            synchronized (this.mutex) {
                a = cn.a(Maps.transformValues(c().columnMap(), new Function<Map<R, V>, Map<R, V>>() { // from class: com.google.common.collect.cn.w.2
                    /* renamed from: a */
                    public Map<R, V> apply(Map<R, V> map) {
                        return cn.a(map, w.this.mutex);
                    }
                }), this.mutex);
            }
            return a;
        }

        @Override // com.google.common.collect.Table
        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }

        @Override // com.google.common.collect.Table
        public boolean equals(@NullableDecl Object obj) {
            boolean equals;
            if (this == obj) {
                return true;
            }
            synchronized (this.mutex) {
                equals = c().equals(obj);
            }
            return equals;
        }
    }
}
