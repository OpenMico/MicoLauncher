package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ImmutableEntry.java */
@GwtCompatible(serializable = true)
/* loaded from: classes2.dex */
public class aw<K, V> extends f<K, V> implements Serializable {
    private static final long serialVersionUID = 0;
    @NullableDecl
    final K key;
    @NullableDecl
    final V value;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aw(@NullableDecl K k, @NullableDecl V v) {
        this.key = k;
        this.value = v;
    }

    @Override // com.google.common.collect.f, java.util.Map.Entry
    @NullableDecl
    public final K getKey() {
        return this.key;
    }

    @Override // com.google.common.collect.f, java.util.Map.Entry
    @NullableDecl
    public final V getValue() {
        return this.value;
    }

    @Override // com.google.common.collect.f, java.util.Map.Entry
    public final V setValue(V v) {
        throw new UnsupportedOperationException();
    }
}
