package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.bu;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: RegularImmutableBiMap.java */
@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
final class bs<K, V> extends ImmutableBiMap<K, V> {
    static final bs<Object, Object> b = new bs<>();
    @VisibleForTesting
    final transient Object[] c;
    private final transient int[] d;
    private final transient int e;
    private final transient int f;
    private final transient bs<V, K> g;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableMap
    public boolean b() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private bs() {
        this.d = null;
        this.c = new Object[0];
        this.e = 0;
        this.f = 0;
        this.g = this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public bs(Object[] objArr, int i) {
        this.c = objArr;
        this.f = i;
        this.e = 0;
        int c = i >= 2 ? ImmutableSet.c(i) : 0;
        this.d = bu.a(objArr, i, c, 0);
        this.g = new bs<>(bu.a(objArr, i, c, 1), objArr, i, this);
    }

    private bs(int[] iArr, Object[] objArr, int i, bs<V, K> bsVar) {
        this.d = iArr;
        this.c = objArr;
        this.e = 1;
        this.f = i;
        this.g = bsVar;
    }

    @Override // java.util.Map
    public int size() {
        return this.f;
    }

    @Override // com.google.common.collect.ImmutableBiMap, com.google.common.collect.BiMap
    public ImmutableBiMap<V, K> inverse() {
        return this.g;
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public V get(@NullableDecl Object obj) {
        return (V) bu.a(this.d, this.c, this.f, this.e, obj);
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<Map.Entry<K, V>> e() {
        return new bu.a(this, this.c, this.e, this.f);
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<K> c() {
        return new bu.b(this, new bu.c(this.c, this.e, this.f));
    }
}
