package org.apache.commons.lang3.concurrent;

/* loaded from: classes5.dex */
public abstract class LazyInitializer<T> implements ConcurrentInitializer<T> {
    private volatile T a;

    protected abstract T initialize() throws ConcurrentException;

    @Override // org.apache.commons.lang3.concurrent.ConcurrentInitializer
    public T get() throws ConcurrentException {
        T t = this.a;
        if (t == null) {
            synchronized (this) {
                t = this.a;
                if (t == null) {
                    t = initialize();
                    this.a = t;
                }
            }
        }
        return t;
    }
}
