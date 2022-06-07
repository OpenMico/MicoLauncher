package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Comparator;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class ForwardingSortedSetMultimap<K, V> extends ForwardingSetMultimap<K, V> implements SortedSetMultimap<K, V> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingSetMultimap, com.google.common.collect.ForwardingMultimap, com.google.common.collect.ForwardingObject
    public abstract SortedSetMultimap<K, V> delegate();

    protected ForwardingSortedSetMultimap() {
    }

    @Override // com.google.common.collect.ForwardingSetMultimap, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public SortedSet<V> get(@NullableDecl K k) {
        return delegate().get((SortedSetMultimap<K, V>) k);
    }

    @Override // com.google.common.collect.ForwardingSetMultimap, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public SortedSet<V> removeAll(@NullableDecl Object obj) {
        return delegate().removeAll(obj);
    }

    @Override // com.google.common.collect.ForwardingSetMultimap, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return delegate().replaceValues((SortedSetMultimap<K, V>) k, (Iterable) iterable);
    }

    @Override // com.google.common.collect.SortedSetMultimap
    public Comparator<? super V> valueComparator() {
        return delegate().valueComparator();
    }
}
