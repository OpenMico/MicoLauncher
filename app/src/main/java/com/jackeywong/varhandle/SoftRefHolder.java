package com.jackeywong.varhandle;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

/* loaded from: classes2.dex */
public class SoftRefHolder<V> extends VarRefHolder<V> {
    public SoftRefHolder() {
        this(null);
    }

    public SoftRefHolder(V v) {
        super(v);
    }

    @Override // com.jackeywong.varhandle.VarRefHolder
    protected Reference<V> onBuildReference(V v) {
        return new SoftReference(v);
    }
}
