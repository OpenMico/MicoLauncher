package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FilteredKeySetMultimap.java */
@GwtCompatible
/* loaded from: classes2.dex */
public final class ao<K, V> extends an<K, V> implements ar<K, V> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public ao(SetMultimap<K, V> setMultimap, Predicate<? super K> predicate) {
        super(setMultimap, predicate);
    }

    @Override // com.google.common.collect.ar
    /* renamed from: d */
    public SetMultimap<K, V> a() {
        return (SetMultimap) this.a;
    }

    @Override // com.google.common.collect.an, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> get(K k) {
        return (Set) super.get((ao<K, V>) k);
    }

    @Override // com.google.common.collect.an, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> removeAll(Object obj) {
        return (Set) super.removeAll(obj);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (Set) super.replaceValues((ao<K, V>) k, (Iterable) iterable);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public Set<Map.Entry<K, V>> entries() {
        return (Set) super.entries();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: c */
    public Set<Map.Entry<K, V>> k() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FilteredKeySetMultimap.java */
    /* loaded from: classes2.dex */
    public class a extends an<K, V>.c implements Set<Map.Entry<K, V>> {
        a() {
            super();
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            return Sets.a(this);
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(@NullableDecl Object obj) {
            return Sets.a(this, obj);
        }
    }
}
