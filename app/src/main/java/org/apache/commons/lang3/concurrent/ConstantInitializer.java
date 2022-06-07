package org.apache.commons.lang3.concurrent;

import org.apache.commons.lang3.ObjectUtils;

/* loaded from: classes5.dex */
public class ConstantInitializer<T> implements ConcurrentInitializer<T> {
    private final T a;

    public ConstantInitializer(T t) {
        this.a = t;
    }

    public final T getObject() {
        return this.a;
    }

    @Override // org.apache.commons.lang3.concurrent.ConcurrentInitializer
    public T get() throws ConcurrentException {
        return getObject();
    }

    public int hashCode() {
        if (getObject() != null) {
            return getObject().hashCode();
        }
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConstantInitializer)) {
            return false;
        }
        return ObjectUtils.equals(getObject(), ((ConstantInitializer) obj).getObject());
    }

    public String toString() {
        return String.format("ConstantInitializer@%d [ object = %s ]", Integer.valueOf(System.identityHashCode(this)), String.valueOf(getObject()));
    }
}
