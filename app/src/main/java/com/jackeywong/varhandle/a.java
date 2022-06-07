package com.jackeywong.varhandle;

import androidx.annotation.NonNull;
import java.lang.ref.Reference;

/* compiled from: ReferenceVarHandle.java */
/* loaded from: classes2.dex */
abstract class a<T> extends VarHandle<T> {
    private Reference<T> a;

    @NonNull
    protected abstract Reference<T> buildVarRef(T t);

    @Override // com.jackeywong.varhandle.VarHandle
    protected T onConstructor() {
        T constructor = constructor();
        this.a = buildVarRef(constructor);
        return constructor;
    }

    @Override // com.jackeywong.varhandle.VarHandle
    protected T peek() {
        T t;
        Reference<T> reference = this.a;
        if (reference == null || (t = reference.get()) == null) {
            return null;
        }
        return t;
    }

    @Override // com.jackeywong.varhandle.VarHandle, com.jackeywong.varhandle.IDestructor
    public synchronized void destructor() {
        super.destructor();
        this.a = null;
    }
}
