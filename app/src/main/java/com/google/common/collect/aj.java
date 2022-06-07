package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ExplicitOrdering.java */
@GwtCompatible(serializable = true)
/* loaded from: classes2.dex */
public final class aj<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    final ImmutableMap<T, Integer> rankMap;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aj(List<T> list) {
        this(Maps.a(list));
    }

    aj(ImmutableMap<T, Integer> immutableMap) {
        this.rankMap = immutableMap;
    }

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    public int compare(T t, T t2) {
        return a(t) - a(t2);
    }

    private int a(T t) {
        Integer num = this.rankMap.get(t);
        if (num != null) {
            return num.intValue();
        }
        throw new Ordering.c(t);
    }

    @Override // java.util.Comparator
    public boolean equals(@NullableDecl Object obj) {
        if (obj instanceof aj) {
            return this.rankMap.equals(((aj) obj).rankMap);
        }
        return false;
    }

    public int hashCode() {
        return this.rankMap.hashCode();
    }

    public String toString() {
        return "Ordering.explicit(" + this.rankMap.keySet() + ")";
    }
}
