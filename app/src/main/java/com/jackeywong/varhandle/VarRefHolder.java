package com.jackeywong.varhandle;

import java.lang.ref.Reference;

/* loaded from: classes2.dex */
public abstract class VarRefHolder<V> implements IVarHolder<V> {
    private final VarHolder<Reference<V>> a = new VarHolder<>();
    private final V b;

    protected abstract Reference<V> onBuildReference(V v);

    public VarRefHolder(V v) {
        this.b = v;
    }

    @Override // com.jackeywong.varhandle.IVarHolder
    public V set(V v) {
        V v2;
        Reference<V> reference = this.a.get();
        if (reference == null || (v2 = reference.get()) == null) {
            this.a.set(onBuildReference(v));
            return null;
        }
        this.a.set(onBuildReference(v));
        return v2;
    }

    @Override // com.jackeywong.varhandle.IVarHandle
    public V get() {
        Reference<V> reference = this.a.get();
        V v = reference != null ? reference.get() : null;
        return v != null ? v : this.b;
    }
}
