package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ImmutableMapEntrySet.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public abstract class az<K, V> extends ImmutableSet<Map.Entry<K, V>> {
    abstract ImmutableMap<K, V> b();

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return b().size();
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    public boolean contains(@NullableDecl Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        V v = b().get(entry.getKey());
        return v != null && v.equals(entry.getValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean a() {
        return b().b();
    }

    @Override // com.google.common.collect.ImmutableSet
    @GwtIncompatible
    boolean e() {
        return b().f();
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return b().hashCode();
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    Object writeReplace() {
        return new a(b());
    }

    /* compiled from: ImmutableMapEntrySet.java */
    @GwtIncompatible
    /* loaded from: classes2.dex */
    private static class a<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        final ImmutableMap<K, V> map;

        a(ImmutableMap<K, V> immutableMap) {
            this.map = immutableMap;
        }

        Object readResolve() {
            return this.map.entrySet();
        }
    }
}
