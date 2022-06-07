package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractNavigableMap.java */
@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class i<K, V> extends Maps.m<K, V> implements NavigableMap<K, V> {
    abstract Iterator<Map.Entry<K, V>> a();

    @Override // java.util.NavigableMap
    @NullableDecl
    public Map.Entry<K, V> firstEntry() {
        return (Map.Entry) Iterators.getNext(b(), null);
    }

    @Override // java.util.NavigableMap
    @NullableDecl
    public Map.Entry<K, V> lastEntry() {
        return (Map.Entry) Iterators.getNext(a(), null);
    }

    @NullableDecl
    public Map.Entry<K, V> pollFirstEntry() {
        return (Map.Entry) Iterators.a(b());
    }

    @NullableDecl
    public Map.Entry<K, V> pollLastEntry() {
        return (Map.Entry) Iterators.a(a());
    }

    @Override // java.util.SortedMap
    public K firstKey() {
        Map.Entry<K, V> firstEntry = firstEntry();
        if (firstEntry != null) {
            return firstEntry.getKey();
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedMap
    public K lastKey() {
        Map.Entry<K, V> lastEntry = lastEntry();
        if (lastEntry != null) {
            return lastEntry.getKey();
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.NavigableMap
    @NullableDecl
    public Map.Entry<K, V> lowerEntry(K k) {
        return headMap(k, false).lastEntry();
    }

    @Override // java.util.NavigableMap
    @NullableDecl
    public Map.Entry<K, V> floorEntry(K k) {
        return headMap(k, true).lastEntry();
    }

    @Override // java.util.NavigableMap
    @NullableDecl
    public Map.Entry<K, V> ceilingEntry(K k) {
        return tailMap(k, true).firstEntry();
    }

    @Override // java.util.NavigableMap
    @NullableDecl
    public Map.Entry<K, V> higherEntry(K k) {
        return tailMap(k, false).firstEntry();
    }

    @Override // java.util.NavigableMap
    public K lowerKey(K k) {
        return (K) Maps.b(lowerEntry(k));
    }

    @Override // java.util.NavigableMap
    public K floorKey(K k) {
        return (K) Maps.b(floorEntry(k));
    }

    @Override // java.util.NavigableMap
    public K ceilingKey(K k) {
        return (K) Maps.b(ceilingEntry(k));
    }

    @Override // java.util.NavigableMap
    public K higherKey(K k) {
        return (K) Maps.b(higherEntry(k));
    }

    @Override // java.util.NavigableMap, java.util.SortedMap
    public SortedMap<K, V> subMap(K k, K k2) {
        return subMap(k, true, k2, false);
    }

    @Override // java.util.NavigableMap, java.util.SortedMap
    public SortedMap<K, V> headMap(K k) {
        return headMap(k, false);
    }

    @Override // java.util.NavigableMap, java.util.SortedMap
    public SortedMap<K, V> tailMap(K k) {
        return tailMap(k, true);
    }

    public NavigableSet<K> navigableKeySet() {
        return new Maps.q(this);
    }

    @Override // java.util.AbstractMap, java.util.Map, java.util.SortedMap
    public Set<K> keySet() {
        return navigableKeySet();
    }

    @Override // java.util.NavigableMap
    public NavigableSet<K> descendingKeySet() {
        return descendingMap().navigableKeySet();
    }

    public NavigableMap<K, V> descendingMap() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AbstractNavigableMap.java */
    /* loaded from: classes2.dex */
    public final class a extends Maps.d<K, V> {
        private a() {
        }

        @Override // com.google.common.collect.Maps.d
        NavigableMap<K, V> a() {
            return i.this;
        }

        @Override // com.google.common.collect.Maps.d
        Iterator<Map.Entry<K, V>> entryIterator() {
            return i.this.a();
        }
    }
}
