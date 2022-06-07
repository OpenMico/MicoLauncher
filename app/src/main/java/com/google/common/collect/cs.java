package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;

/* compiled from: UsingToStringOrdering.java */
@GwtCompatible(serializable = true)
/* loaded from: classes2.dex */
final class cs extends Ordering<Object> implements Serializable {
    static final cs a = new cs();
    private static final long serialVersionUID = 0;

    public String toString() {
        return "Ordering.usingToString()";
    }

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    public int compare(Object obj, Object obj2) {
        return obj.toString().compareTo(obj2.toString());
    }

    private Object readResolve() {
        return a;
    }

    private cs() {
    }
}
