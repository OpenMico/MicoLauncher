package com.jackeywong.varhandle;

import androidx.annotation.NonNull;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

/* loaded from: classes2.dex */
public abstract class SoftVarHandle<T> extends a<T> {
    @Override // com.jackeywong.varhandle.a, com.jackeywong.varhandle.VarHandle, com.jackeywong.varhandle.IDestructor
    public /* bridge */ /* synthetic */ void destructor() {
        super.destructor();
    }

    @Override // com.jackeywong.varhandle.a
    @NonNull
    protected Reference<T> buildVarRef(T t) {
        return new SoftReference(t);
    }
}
