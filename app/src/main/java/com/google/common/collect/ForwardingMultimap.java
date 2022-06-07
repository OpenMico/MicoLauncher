package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class ForwardingMultimap<K, V> extends ForwardingObject implements Multimap<K, V> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingObject
    public abstract Multimap<K, V> delegate();

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Map<K, Collection<V>> asMap() {
        return delegate().asMap();
    }

    @Override // com.google.common.collect.Multimap
    public void clear() {
        delegate().clear();
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsEntry(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return delegate().containsEntry(obj, obj2);
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsKey(@NullableDecl Object obj) {
        return delegate().containsKey(obj);
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsValue(@NullableDecl Object obj) {
        return delegate().containsValue(obj);
    }

    @Override // com.google.common.collect.Multimap
    public Collection<Map.Entry<K, V>> entries() {
        return delegate().entries();
    }

    public Collection<V> get(@NullableDecl K k) {
        return delegate().get(k);
    }

    @Override // com.google.common.collect.Multimap
    public boolean isEmpty() {
        return delegate().isEmpty();
    }

    @Override // com.google.common.collect.Multimap
    public Multiset<K> keys() {
        return delegate().keys();
    }

    @Override // com.google.common.collect.Multimap
    public Set<K> keySet() {
        return delegate().keySet();
    }

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public boolean put(K k, V v) {
        return delegate().put(k, v);
    }

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public boolean putAll(K k, Iterable<? extends V> iterable) {
        return delegate().putAll(k, iterable);
    }

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        return delegate().putAll(multimap);
    }

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public boolean remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return delegate().remove(obj, obj2);
    }

    @CanIgnoreReturnValue
    public Collection<V> removeAll(@NullableDecl Object obj) {
        return delegate().removeAll(obj);
    }

    @CanIgnoreReturnValue
    public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return delegate().replaceValues(k, iterable);
    }

    @Override // com.google.common.collect.Multimap
    public int size() {
        return delegate().size();
    }

    @Override // com.google.common.collect.Multimap
    public Collection<V> values() {
        return delegate().values();
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public boolean equals(@NullableDecl Object obj) {
        return obj == this || delegate().equals(obj);
    }

    @Override // com.google.common.collect.Multimap
    public int hashCode() {
        return delegate().hashCode();
    }
}
