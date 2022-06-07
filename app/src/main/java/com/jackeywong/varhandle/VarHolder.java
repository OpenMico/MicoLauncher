package com.jackeywong.varhandle;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes2.dex */
public final class VarHolder<V> implements IVarHolder<V> {
    private final AtomicReference<V> a = new AtomicReference<>();

    @Override // com.jackeywong.varhandle.IVarHandle
    public final V get() {
        return this.a.get();
    }

    @Override // com.jackeywong.varhandle.IVarHolder
    public final V set(V v) {
        return this.a.getAndSet(v);
    }
}
