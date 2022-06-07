package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NullsFirstOrdering.java */
@GwtCompatible(serializable = true)
/* loaded from: classes2.dex */
public final class bl<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    final Ordering<? super T> ordering;

    @Override // com.google.common.collect.Ordering
    public <S extends T> Ordering<S> nullsFirst() {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public bl(Ordering<? super T> ordering) {
        this.ordering = ordering;
    }

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    public int compare(@NullableDecl T t, @NullableDecl T t2) {
        if (t == t2) {
            return 0;
        }
        if (t == null) {
            return -1;
        }
        if (t2 == null) {
            return 1;
        }
        return this.ordering.compare(t, t2);
    }

    @Override // com.google.common.collect.Ordering
    public <S extends T> Ordering<S> reverse() {
        return this.ordering.reverse().nullsLast();
    }

    @Override // com.google.common.collect.Ordering
    public <S extends T> Ordering<S> nullsLast() {
        return this.ordering.nullsLast();
    }

    @Override // java.util.Comparator
    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof bl) {
            return this.ordering.equals(((bl) obj).ordering);
        }
        return false;
    }

    public int hashCode() {
        return this.ordering.hashCode() ^ 957692532;
    }

    public String toString() {
        return this.ordering + ".nullsFirst()";
    }
}
