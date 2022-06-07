package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.SortedSet;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SortedIterables.java */
@GwtCompatible
/* loaded from: classes2.dex */
public final class cg {
    public static boolean a(Comparator<?> comparator, Iterable<?> iterable) {
        Comparator comparator2;
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof SortedSet) {
            comparator2 = a((SortedSet) iterable);
        } else if (!(iterable instanceof cf)) {
            return false;
        } else {
            comparator2 = ((cf) iterable).comparator();
        }
        return comparator.equals(comparator2);
    }

    public static <E> Comparator<? super E> a(SortedSet<E> sortedSet) {
        Comparator<? super E> comparator = sortedSet.comparator();
        return comparator == null ? Ordering.natural() : comparator;
    }
}
