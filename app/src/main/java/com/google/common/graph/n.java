package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: EdgesConnecting.java */
/* loaded from: classes2.dex */
final class n<E> extends AbstractSet<E> {
    private final Map<?, E> a;
    private final Object b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(Map<?, E> map, Object obj) {
        this.a = (Map) Preconditions.checkNotNull(map);
        this.b = Preconditions.checkNotNull(obj);
    }

    /* renamed from: a */
    public UnmodifiableIterator<E> iterator() {
        E b = b();
        if (b == null) {
            return ImmutableSet.of().iterator();
        }
        return Iterators.singletonIterator(b);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return b() == null ? 0 : 1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(@NullableDecl Object obj) {
        E b = b();
        return b != null && b.equals(obj);
    }

    @NullableDecl
    private E b() {
        return this.a.get(this.b);
    }
}
