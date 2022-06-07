package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

/* compiled from: AbstractSortedKeySortedSetMultimap.java */
@GwtCompatible
/* loaded from: classes2.dex */
abstract class l<K, V> extends n<K, V> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public l(SortedMap<K, Collection<V>> sortedMap) {
        super(sortedMap);
    }

    @Override // com.google.common.collect.n, com.google.common.collect.k, com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public SortedMap<K, Collection<V>> asMap() {
        return (SortedMap) super.asMap();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: o */
    public SortedMap<K, Collection<V>> e() {
        return (SortedMap) super.e();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public SortedSet<K> keySet() {
        return (SortedSet) super.keySet();
    }

    @Override // com.google.common.collect.d, com.google.common.collect.g
    Set<K> f() {
        return g();
    }
}
