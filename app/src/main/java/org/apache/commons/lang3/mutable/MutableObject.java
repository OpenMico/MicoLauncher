package org.apache.commons.lang3.mutable;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class MutableObject<T> implements Serializable, Mutable<T> {
    private static final long serialVersionUID = 86241875189L;
    private T value;

    public MutableObject() {
    }

    public MutableObject(T t) {
        this.value = t;
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    public T getValue() {
        return this.value;
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    public void setValue(T t) {
        this.value = t;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() == obj.getClass()) {
            return this.value.equals(((MutableObject) obj).value);
        }
        return false;
    }

    public int hashCode() {
        T t = this.value;
        if (t == null) {
            return 0;
        }
        return t.hashCode();
    }

    public String toString() {
        T t = this.value;
        return t == null ? "null" : t.toString();
    }
}
