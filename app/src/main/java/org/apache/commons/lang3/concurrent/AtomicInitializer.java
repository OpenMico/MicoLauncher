package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public abstract class AtomicInitializer<T> implements ConcurrentInitializer<T> {
    private final AtomicReference<T> a = new AtomicReference<>();

    protected abstract T initialize() throws ConcurrentException;

    @Override // org.apache.commons.lang3.concurrent.ConcurrentInitializer
    public T get() throws ConcurrentException {
        T t = this.a.get();
        if (t != null) {
            return t;
        }
        T initialize = initialize();
        return !this.a.compareAndSet(null, initialize) ? this.a.get() : initialize;
    }
}
