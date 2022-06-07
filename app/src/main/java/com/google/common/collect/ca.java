package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Iterator;

/* compiled from: ReverseNaturalOrdering.java */
@GwtCompatible(serializable = true)
/* loaded from: classes2.dex */
final class ca extends Ordering<Comparable> implements Serializable {
    static final ca a = new ca();
    private static final long serialVersionUID = 0;

    public String toString() {
        return "Ordering.natural().reverse()";
    }

    /* renamed from: a */
    public int compare(Comparable comparable, Comparable comparable2) {
        Preconditions.checkNotNull(comparable);
        if (comparable == comparable2) {
            return 0;
        }
        return comparable2.compareTo(comparable);
    }

    @Override // com.google.common.collect.Ordering
    public <S extends Comparable> Ordering<S> reverse() {
        return Ordering.natural();
    }

    /* renamed from: b */
    public <E extends Comparable> E min(E e, E e2) {
        return (E) ((Comparable) bk.a.max(e, e2));
    }

    /* renamed from: a */
    public <E extends Comparable> E min(E e, E e2, E e3, E... eArr) {
        return (E) ((Comparable) bk.a.max(e, e2, e3, eArr));
    }

    /* renamed from: a */
    public <E extends Comparable> E min(Iterator<E> it) {
        return (E) ((Comparable) bk.a.max(it));
    }

    /* renamed from: a */
    public <E extends Comparable> E min(Iterable<E> iterable) {
        return (E) ((Comparable) bk.a.max(iterable));
    }

    /* renamed from: c */
    public <E extends Comparable> E max(E e, E e2) {
        return (E) ((Comparable) bk.a.min(e, e2));
    }

    /* renamed from: b */
    public <E extends Comparable> E max(E e, E e2, E e3, E... eArr) {
        return (E) ((Comparable) bk.a.min(e, e2, e3, eArr));
    }

    /* renamed from: b */
    public <E extends Comparable> E max(Iterator<E> it) {
        return (E) ((Comparable) bk.a.min(it));
    }

    /* renamed from: b */
    public <E extends Comparable> E max(Iterable<E> iterable) {
        return (E) ((Comparable) bk.a.min(iterable));
    }

    private Object readResolve() {
        return a;
    }

    private ca() {
    }
}
