package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: FilteredKeyMultimap.java */
@GwtCompatible
/* loaded from: classes2.dex */
public class an<K, V> extends g<K, V> implements ap<K, V> {
    final Multimap<K, V> a;
    final Predicate<? super K> b;

    public an(Multimap<K, V> multimap, Predicate<? super K> predicate) {
        this.a = (Multimap) Preconditions.checkNotNull(multimap);
        this.b = (Predicate) Preconditions.checkNotNull(predicate);
    }

    public Multimap<K, V> a() {
        return this.a;
    }

    @Override // com.google.common.collect.ap
    public Predicate<? super Map.Entry<K, V>> b() {
        return Maps.a(this.b);
    }

    @Override // com.google.common.collect.Multimap
    public int size() {
        int i = 0;
        for (Collection<V> collection : asMap().values()) {
            i += collection.size();
        }
        return i;
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsKey(@NullableDecl Object obj) {
        if (this.a.containsKey(obj)) {
            return this.b.apply(obj);
        }
        return false;
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> removeAll(Object obj) {
        return containsKey(obj) ? this.a.removeAll(obj) : e();
    }

    Collection<V> e() {
        if (this.a instanceof SetMultimap) {
            return ImmutableSet.of();
        }
        return ImmutableList.of();
    }

    @Override // com.google.common.collect.Multimap
    public void clear() {
        keySet().clear();
    }

    @Override // com.google.common.collect.g
    Set<K> f() {
        return Sets.filter(this.a.keySet(), this.b);
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> get(K k) {
        if (this.b.apply(k)) {
            return this.a.get(k);
        }
        if (this.a instanceof SetMultimap) {
            return new b(k);
        }
        return new a(k);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FilteredKeyMultimap.java */
    /* loaded from: classes2.dex */
    public static class b<K, V> extends ForwardingSet<V> {
        final K a;

        b(K k) {
            this.a = k;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
        public boolean add(V v) {
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.a);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.a);
        }

        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Set<V> delegate() {
            return Collections.emptySet();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FilteredKeyMultimap.java */
    /* loaded from: classes2.dex */
    public static class a<K, V> extends ForwardingList<V> {
        final K a;

        a(K k) {
            this.a = k;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
        public boolean add(V v) {
            add(0, v);
            return true;
        }

        @Override // com.google.common.collect.ForwardingList, java.util.List
        public void add(int i, V v) {
            Preconditions.checkPositionIndex(i, 0);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.a);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            addAll(0, collection);
            return true;
        }

        @Override // com.google.common.collect.ForwardingList, java.util.List
        @CanIgnoreReturnValue
        public boolean addAll(int i, Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            Preconditions.checkPositionIndex(i, 0);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.a);
        }

        @Override // com.google.common.collect.ForwardingList, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public List<V> delegate() {
            return Collections.emptyList();
        }
    }

    @Override // com.google.common.collect.g
    Iterator<Map.Entry<K, V>> l() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.g
    Collection<Map.Entry<K, V>> k() {
        return new c();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FilteredKeyMultimap.java */
    /* loaded from: classes2.dex */
    public class c extends ForwardingCollection<Map.Entry<K, V>> {
        public c() {
            an.this = r1;
        }

        @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Collection<Map.Entry<K, V>> delegate() {
            return Collections2.filter(an.this.a.entries(), an.this.b());
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean remove(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (!an.this.a.containsKey(entry.getKey()) || !an.this.b.apply((Object) entry.getKey())) {
                return false;
            }
            return an.this.a.remove(entry.getKey(), entry.getValue());
        }
    }

    @Override // com.google.common.collect.g
    Collection<V> h() {
        return new aq(this);
    }

    @Override // com.google.common.collect.g
    Map<K, Collection<V>> m() {
        return Maps.filterKeys(this.a.asMap(), this.b);
    }

    @Override // com.google.common.collect.g
    Multiset<K> j() {
        return Multisets.filter(this.a.keys(), this.b);
    }
}
