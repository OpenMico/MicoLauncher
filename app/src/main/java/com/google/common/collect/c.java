package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractListMultimap.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class c<K, V> extends d<K, V> implements ListMultimap<K, V> {
    private static final long serialVersionUID = 6588350623831699109L;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public abstract List<V> c();

    public c(Map<K, Collection<V>> map) {
        super(map);
    }

    /* renamed from: b */
    public List<V> d() {
        return Collections.emptyList();
    }

    @Override // com.google.common.collect.d
    <E> Collection<E> a(Collection<E> collection) {
        return Collections.unmodifiableList((List) collection);
    }

    @Override // com.google.common.collect.d
    Collection<V> a(K k, Collection<V> collection) {
        return a(k, (List) collection, null);
    }

    @Override // com.google.common.collect.d, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public List<V> get(@NullableDecl K k) {
        return (List) super.get((c<K, V>) k);
    }

    @Override // com.google.common.collect.d, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public List<V> removeAll(@NullableDecl Object obj) {
        return (List) super.removeAll(obj);
    }

    @Override // com.google.common.collect.d, com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public List<V> replaceValues(@NullableDecl K k, Iterable<? extends V> iterable) {
        return (List) super.replaceValues((c<K, V>) k, (Iterable) iterable);
    }

    @Override // com.google.common.collect.d, com.google.common.collect.g, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public boolean put(@NullableDecl K k, @NullableDecl V v) {
        return super.put(k, v);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }
}
