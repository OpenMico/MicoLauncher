package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.d;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableSet;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractSortedSetMultimap.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class n<K, V> extends k<K, V> implements SortedSetMultimap<K, V> {
    private static final long serialVersionUID = 430848587173315748L;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: p */
    public abstract SortedSet<V> c();

    /* JADX INFO: Access modifiers changed from: protected */
    public n(Map<K, Collection<V>> map) {
        super(map);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: q */
    public SortedSet<V> d() {
        return (SortedSet<V>) a((Collection) c());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: c */
    public <E> SortedSet<E> a(Collection<E> collection) {
        if (collection instanceof NavigableSet) {
            return Sets.unmodifiableNavigableSet((NavigableSet) collection);
        }
        return Collections.unmodifiableSortedSet((SortedSet) collection);
    }

    @Override // com.google.common.collect.k, com.google.common.collect.d
    Collection<V> a(K k, Collection<V> collection) {
        if (collection instanceof NavigableSet) {
            return new d.k(k, (NavigableSet) collection, null);
        }
        return new d.m(k, (SortedSet) collection, null);
    }

    @Override // com.google.common.collect.k, com.google.common.collect.d, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public SortedSet<V> get(@NullableDecl K k) {
        return (SortedSet) super.get((n<K, V>) k);
    }

    @Override // com.google.common.collect.k, com.google.common.collect.d, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public SortedSet<V> removeAll(@NullableDecl Object obj) {
        return (SortedSet) super.removeAll(obj);
    }

    @Override // com.google.common.collect.k, com.google.common.collect.d, com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public SortedSet<V> replaceValues(@NullableDecl K k, Iterable<? extends V> iterable) {
        return (SortedSet) super.replaceValues((n<K, V>) k, (Iterable) iterable);
    }

    @Override // com.google.common.collect.k, com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override // com.google.common.collect.d, com.google.common.collect.g, com.google.common.collect.Multimap
    public Collection<V> values() {
        return super.values();
    }
}
