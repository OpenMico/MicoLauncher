package com.jackeywong.varhandle;

import androidx.annotation.Nullable;

/* loaded from: classes2.dex */
public abstract class LazyVarHandle<T> extends VarHandle<T> {
    private volatile T a;

    @Override // com.jackeywong.varhandle.VarHandle
    protected T onConstructor() {
        T constructor = constructor();
        this.a = constructor;
        return constructor;
    }

    @Override // com.jackeywong.varhandle.VarHandle
    @Nullable
    public T peek() {
        return this.a;
    }

    @Override // com.jackeywong.varhandle.VarHandle, com.jackeywong.varhandle.IDestructor
    public synchronized void destructor() {
        super.destructor();
        this.a = null;
        notifyAll();
    }
}
