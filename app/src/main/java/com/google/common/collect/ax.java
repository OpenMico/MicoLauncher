package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.io.Serializable;
import java.lang.Enum;
import java.util.EnumMap;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: ImmutableEnumMap.java */
@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
final class ax<K extends Enum<K>, V> extends ImmutableMap.a<K, V> {
    private final transient EnumMap<K, V> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableMap
    public boolean b() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K extends Enum<K>, V> ImmutableMap<K, V> a(EnumMap<K, V> enumMap) {
        switch (enumMap.size()) {
            case 0:
                return ImmutableMap.of();
            case 1:
                Map.Entry entry = (Map.Entry) Iterables.getOnlyElement(enumMap.entrySet());
                return ImmutableMap.of(entry.getKey(), entry.getValue());
            default:
                return new ax(enumMap);
        }
    }

    private ax(EnumMap<K, V> enumMap) {
        this.b = enumMap;
        Preconditions.checkArgument(!enumMap.isEmpty());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableMap
    public UnmodifiableIterator<K> e_() {
        return Iterators.unmodifiableIterator(this.b.keySet().iterator());
    }

    @Override // java.util.Map
    public int size() {
        return this.b.size();
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public boolean containsKey(@NullableDecl Object obj) {
        return this.b.containsKey(obj);
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public V get(Object obj) {
        return this.b.get(obj);
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ax) {
            obj = ((ax) obj).b;
        }
        return this.b.equals(obj);
    }

    @Override // com.google.common.collect.ImmutableMap.a
    UnmodifiableIterator<Map.Entry<K, V>> d_() {
        return Maps.c(this.b.entrySet().iterator());
    }

    @Override // com.google.common.collect.ImmutableMap
    Object writeReplace() {
        return new a(this.b);
    }

    /* compiled from: ImmutableEnumMap.java */
    /* loaded from: classes2.dex */
    private static class a<K extends Enum<K>, V> implements Serializable {
        private static final long serialVersionUID = 0;
        final EnumMap<K, V> delegate;

        a(EnumMap<K, V> enumMap) {
            this.delegate = enumMap;
        }

        Object readResolve() {
            return new ax(this.delegate);
        }
    }
}
