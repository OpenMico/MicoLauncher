package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.d;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractSetMultimap.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class k<K, V> extends d<K, V> implements SetMultimap<K, V> {
    private static final long serialVersionUID = 7431625294878419160L;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public abstract Set<V> c();

    /* JADX INFO: Access modifiers changed from: protected */
    public k(Map<K, Collection<V>> map) {
        super(map);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b */
    public Set<V> d() {
        return Collections.emptySet();
    }

    @Override // com.google.common.collect.d
    <E> Collection<E> a(Collection<E> collection) {
        return Collections.unmodifiableSet((Set) collection);
    }

    @Override // com.google.common.collect.d
    Collection<V> a(K k, Collection<V> collection) {
        return new d.l(k, (Set) collection);
    }

    @Override // com.google.common.collect.d, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> get(@NullableDecl K k) {
        return (Set) super.get((k<K, V>) k);
    }

    @Override // com.google.common.collect.d, com.google.common.collect.g, com.google.common.collect.Multimap
    public Set<Map.Entry<K, V>> entries() {
        return (Set) super.entries();
    }

    @Override // com.google.common.collect.d, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public Set<V> removeAll(@NullableDecl Object obj) {
        return (Set) super.removeAll(obj);
    }

    @Override // com.google.common.collect.d, com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public Set<V> replaceValues(@NullableDecl K k, Iterable<? extends V> iterable) {
        return (Set) super.replaceValues((k<K, V>) k, (Iterable) iterable);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override // com.google.common.collect.d, com.google.common.collect.g, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public boolean put(@NullableDecl K k, @NullableDecl V v) {
        return super.put(k, v);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }
}
