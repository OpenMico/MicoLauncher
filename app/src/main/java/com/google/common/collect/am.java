package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FilteredKeyListMultimap.java */
@GwtCompatible
/* loaded from: classes2.dex */
public final class am<K, V> extends an<K, V> implements ListMultimap<K, V> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public am(ListMultimap<K, V> listMultimap, Predicate<? super K> predicate) {
        super(listMultimap, predicate);
    }

    /* renamed from: c */
    public ListMultimap<K, V> a() {
        return (ListMultimap) super.a();
    }

    @Override // com.google.common.collect.an, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public List<V> get(K k) {
        return (List) super.get((am<K, V>) k);
    }

    @Override // com.google.common.collect.an, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public List<V> removeAll(@NullableDecl Object obj) {
        return (List) super.removeAll(obj);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (List) super.replaceValues((am<K, V>) k, (Iterable) iterable);
    }
}
