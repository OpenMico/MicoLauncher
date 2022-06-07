package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ImmutableMapKeySet.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class ba<K, V> extends bg<K> {
    @Weak
    private final ImmutableMap<K, V> map;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean a() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ba(ImmutableMap<K, V> immutableMap) {
        this.map = immutableMap;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.map.size();
    }

    @Override // com.google.common.collect.bg, com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<K> iterator() {
        return this.map.e_();
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    public boolean contains(@NullableDecl Object obj) {
        return this.map.containsKey(obj);
    }

    @Override // com.google.common.collect.bg
    K a(int i) {
        return this.map.entrySet().asList().get(i).getKey();
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    Object writeReplace() {
        return new a(this.map);
    }

    /* compiled from: ImmutableMapKeySet.java */
    @GwtIncompatible
    /* loaded from: classes2.dex */
    private static class a<K> implements Serializable {
        private static final long serialVersionUID = 0;
        final ImmutableMap<K, ?> map;

        a(ImmutableMap<K, ?> immutableMap) {
            this.map = immutableMap;
        }

        Object readResolve() {
            return this.map.keySet();
        }
    }
}
