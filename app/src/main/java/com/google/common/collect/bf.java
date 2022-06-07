package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ImmutableSortedSet;

/* compiled from: ImmutableSortedSetFauxverideShim.java */
@GwtIncompatible
/* loaded from: classes2.dex */
abstract class bf<E> extends ImmutableSet<E> {
    @Deprecated
    public static <E> ImmutableSortedSet.Builder<E> builder() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet.Builder<E> builderWithExpectedSize(int i) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> of(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> of(E e, E e2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> of(E e, E e2, E e3) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> of(E e, E e2, E e3, E e4) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> of(E e, E e2, E e3, E e4, E e5) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> copyOf(E[] eArr) {
        throw new UnsupportedOperationException();
    }
}
