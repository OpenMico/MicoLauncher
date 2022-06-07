package org.apache.commons.lang.mutable;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class MutableObject implements Serializable, Mutable {
    private static final long serialVersionUID = 86241875189L;
    private Object value;

    public MutableObject() {
    }

    public MutableObject(Object obj) {
        this.value = obj;
    }

    @Override // org.apache.commons.lang.mutable.Mutable
    public Object getValue() {
        return this.value;
    }

    @Override // org.apache.commons.lang.mutable.Mutable
    public void setValue(Object obj) {
        this.value = obj;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MutableObject)) {
            return false;
        }
        Object obj2 = ((MutableObject) obj).value;
        Object obj3 = this.value;
        return obj3 == obj2 || (obj3 != null && obj3.equals(obj2));
    }

    public int hashCode() {
        Object obj = this.value;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public String toString() {
        Object obj = this.value;
        return obj == null ? "null" : obj.toString();
    }
}
