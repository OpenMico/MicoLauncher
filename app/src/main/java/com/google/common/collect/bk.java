package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

/* compiled from: NaturalOrdering.java */
@GwtCompatible(serializable = true)
/* loaded from: classes2.dex */
public final class bk extends Ordering<Comparable> implements Serializable {
    static final bk a = new bk();
    private static final long serialVersionUID = 0;
    @MonotonicNonNullDecl
    private transient Ordering<Comparable> b;
    @MonotonicNonNullDecl
    private transient Ordering<Comparable> c;

    public String toString() {
        return "Ordering.natural()";
    }

    /* renamed from: a */
    public int compare(Comparable comparable, Comparable comparable2) {
        Preconditions.checkNotNull(comparable);
        Preconditions.checkNotNull(comparable2);
        return comparable.compareTo(comparable2);
    }

    @Override // com.google.common.collect.Ordering
    public <S extends Comparable> Ordering<S> nullsFirst() {
        Ordering<S> ordering = (Ordering<S>) this.b;
        if (ordering != null) {
            return ordering;
        }
        Ordering<S> nullsFirst = super.nullsFirst();
        this.b = nullsFirst;
        return nullsFirst;
    }

    @Override // com.google.common.collect.Ordering
    public <S extends Comparable> Ordering<S> nullsLast() {
        Ordering<S> ordering = (Ordering<S>) this.c;
        if (ordering != null) {
            return ordering;
        }
        Ordering<S> nullsLast = super.nullsLast();
        this.c = nullsLast;
        return nullsLast;
    }

    @Override // com.google.common.collect.Ordering
    public <S extends Comparable> Ordering<S> reverse() {
        return ca.a;
    }

    private Object readResolve() {
        return a;
    }

    private bk() {
    }
}
