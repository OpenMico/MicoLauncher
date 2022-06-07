package com.jackeywong.varhandle;

import androidx.annotation.Nullable;

/* loaded from: classes2.dex */
public abstract class VarHandle<T> implements IDestructor, IVarHandle<T> {
    protected abstract T constructor();

    protected abstract T onConstructor();

    @Nullable
    protected abstract T peek();

    @Override // com.jackeywong.varhandle.IVarHandle
    public T get() {
        T peek = peek();
        if (peek != null) {
            return peek;
        }
        synchronized (this) {
            T peek2 = peek();
            if (peek2 != null) {
                return peek2;
            }
            T onConstructor = onConstructor();
            if (onConstructor != null) {
                notifyAll();
                return onConstructor;
            }
            throw new NullPointerException("call onConstructor return null.");
        }
    }

    @Override // com.jackeywong.varhandle.IDestructor
    public synchronized void destructor() {
    }
}
