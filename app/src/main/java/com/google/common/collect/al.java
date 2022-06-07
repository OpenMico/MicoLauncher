package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import java.util.Map;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FilteredEntrySetMultimap.java */
@GwtCompatible
/* loaded from: classes2.dex */
public final class al<K, V> extends ak<K, V> implements ar<K, V> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public al(SetMultimap<K, V> setMultimap, Predicate<? super Map.Entry<K, V>> predicate) {
        super(setMultimap, predicate);
    }

    @Override // com.google.common.collect.ar
    /* renamed from: d */
    public SetMultimap<K, V> a() {
        return (SetMultimap) this.a;
    }

    @Override // com.google.common.collect.ak, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> get(K k) {
        return (Set) super.get((al<K, V>) k);
    }

    @Override // com.google.common.collect.ak, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> removeAll(Object obj) {
        return (Set) super.removeAll(obj);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (Set) super.replaceValues((al<K, V>) k, (Iterable) iterable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: e */
    public Set<Map.Entry<K, V>> k() {
        return Sets.filter(a().entries(), b());
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public Set<Map.Entry<K, V>> entries() {
        return (Set) super.entries();
    }
}
