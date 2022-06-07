package com.jackeywong.varhandle;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class WeakRefHolder<V> extends VarRefHolder<V> {
    public WeakRefHolder() {
        this(null);
    }

    public WeakRefHolder(V v) {
        super(v);
    }

    @Override // com.jackeywong.varhandle.VarRefHolder
    protected Reference<V> onBuildReference(V v) {
        return new WeakReference(v);
    }
}
