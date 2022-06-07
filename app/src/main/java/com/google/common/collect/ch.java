package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: SortedLists.java */
@Beta
@GwtCompatible
/* loaded from: classes2.dex */
final class ch {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SortedLists.java */
    /* loaded from: classes2.dex */
    public enum a {
        NEXT_LOWER {
            @Override // com.google.common.collect.ch.a
            int a(int i) {
                return i - 1;
            }
        },
        NEXT_HIGHER {
            @Override // com.google.common.collect.ch.a
            public int a(int i) {
                return i;
            }
        },
        INVERTED_INSERTION_INDEX {
            @Override // com.google.common.collect.ch.a
            public int a(int i) {
                return ~i;
            }
        };

        abstract int a(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SortedLists.java */
    /* loaded from: classes2.dex */
    public enum b {
        ANY_PRESENT {
            @Override // com.google.common.collect.ch.b
            <E> int a(Comparator<? super E> comparator, E e, List<? extends E> list, int i) {
                return i;
            }
        },
        LAST_PRESENT {
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.ch.b
            <E> int a(Comparator<? super E> comparator, E e, List<? extends E> list, int i) {
                int size = list.size() - 1;
                while (i < size) {
                    int i2 = ((i + size) + 1) >>> 1;
                    if (comparator.compare((Object) list.get(i2), e) > 0) {
                        size = i2 - 1;
                    } else {
                        i = i2;
                    }
                }
                return i;
            }
        },
        FIRST_PRESENT {
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.ch.b
            <E> int a(Comparator<? super E> comparator, E e, List<? extends E> list, int i) {
                int i2 = 0;
                while (i2 < i) {
                    int i3 = (i2 + i) >>> 1;
                    if (comparator.compare((Object) list.get(i3), e) < 0) {
                        i2 = i3 + 1;
                    } else {
                        i = i3;
                    }
                }
                return i2;
            }
        },
        FIRST_AFTER {
            @Override // com.google.common.collect.ch.b
            public <E> int a(Comparator<? super E> comparator, E e, List<? extends E> list, int i) {
                return LAST_PRESENT.a(comparator, e, list, i) + 1;
            }
        },
        LAST_BEFORE {
            @Override // com.google.common.collect.ch.b
            public <E> int a(Comparator<? super E> comparator, E e, List<? extends E> list, int i) {
                return FIRST_PRESENT.a(comparator, e, list, i) - 1;
            }
        };

        abstract <E> int a(Comparator<? super E> comparator, E e, List<? extends E> list, int i);
    }

    public static <E, K extends Comparable> int a(List<E> list, Function<? super E, K> function, @NullableDecl K k, b bVar, a aVar) {
        return a(list, function, k, Ordering.natural(), bVar, aVar);
    }

    public static <E, K> int a(List<E> list, Function<? super E, K> function, @NullableDecl K k, Comparator<? super K> comparator, b bVar, a aVar) {
        return a(Lists.transform(list, function), k, comparator, bVar, aVar);
    }

    public static <E> int a(List<? extends E> list, @NullableDecl E e, Comparator<? super E> comparator, b bVar, a aVar) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(list);
        Preconditions.checkNotNull(bVar);
        Preconditions.checkNotNull(aVar);
        if (!(list instanceof RandomAccess)) {
            list = Lists.newArrayList(list);
        }
        int i = 0;
        int size = list.size() - 1;
        while (i <= size) {
            int i2 = (i + size) >>> 1;
            int compare = comparator.compare(e, (Object) list.get(i2));
            if (compare < 0) {
                size = i2 - 1;
            } else if (compare <= 0) {
                return i + bVar.a(comparator, e, list.subList(i, size + 1), i2 - i);
            } else {
                i = i2 + 1;
            }
        }
        return aVar.a(i);
    }
}
