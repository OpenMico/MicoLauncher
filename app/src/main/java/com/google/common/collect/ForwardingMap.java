package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class ForwardingMap<K, V> extends ForwardingObject implements Map<K, V> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingObject
    public abstract Map<K, V> delegate();

    @Override // java.util.Map
    public int size() {
        return delegate().size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return delegate().isEmpty();
    }

    @CanIgnoreReturnValue
    public V remove(Object obj) {
        return delegate().remove(obj);
    }

    public void clear() {
        delegate().clear();
    }

    @Override // java.util.Map
    public boolean containsKey(@NullableDecl Object obj) {
        return delegate().containsKey(obj);
    }

    public boolean containsValue(@NullableDecl Object obj) {
        return delegate().containsValue(obj);
    }

    @Override // java.util.Map
    public V get(@NullableDecl Object obj) {
        return delegate().get(obj);
    }

    @CanIgnoreReturnValue
    public V put(K k, V v) {
        return delegate().put(k, v);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        delegate().putAll(map);
    }

    public Set<K> keySet() {
        return delegate().keySet();
    }

    public Collection<V> values() {
        return delegate().values();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return delegate().entrySet();
    }

    @Override // java.util.Map
    public boolean equals(@NullableDecl Object obj) {
        return obj == this || delegate().equals(obj);
    }

    @Override // java.util.Map
    public int hashCode() {
        return delegate().hashCode();
    }

    protected void standardPutAll(Map<? extends K, ? extends V> map) {
        Maps.a((Map) this, (Map) map);
    }

    @Beta
    protected V standardRemove(@NullableDecl Object obj) {
        Iterator<Map.Entry<K, V>> it = entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (Objects.equal(next.getKey(), obj)) {
                V value = next.getValue();
                it.remove();
                return value;
            }
        }
        return null;
    }

    protected void standardClear() {
        Iterators.b(entrySet().iterator());
    }

    @Beta
    /* loaded from: classes2.dex */
    protected class StandardKeySet extends Maps.n<K, V> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public StandardKeySet() {
            super(r1);
            ForwardingMap.this = r1;
        }
    }

    @Beta
    protected boolean standardContainsKey(@NullableDecl Object obj) {
        return Maps.d(this, obj);
    }

    @Beta
    /* loaded from: classes2.dex */
    protected class StandardValues extends Maps.ac<K, V> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public StandardValues() {
            super(r1);
            ForwardingMap.this = r1;
        }
    }

    protected boolean standardContainsValue(@NullableDecl Object obj) {
        return Maps.e(this, obj);
    }

    @Beta
    /* loaded from: classes2.dex */
    protected abstract class StandardEntrySet extends Maps.f<K, V> {
        public StandardEntrySet() {
            ForwardingMap.this = r1;
        }
    }

    protected boolean standardIsEmpty() {
        return !entrySet().iterator().hasNext();
    }

    protected boolean standardEquals(@NullableDecl Object obj) {
        return Maps.f(this, obj);
    }

    protected int standardHashCode() {
        return Sets.a(entrySet());
    }

    protected String standardToString() {
        return Maps.a(this);
    }
}
