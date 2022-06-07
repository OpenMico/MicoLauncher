package java8.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java8.util.r;

/* loaded from: classes5.dex */
public final class Sets {
    public static <E> Set<E> of() {
        return r.e;
    }

    public static <E> Set<E> of(E e) {
        return new r.j(e);
    }

    public static <E> Set<E> of(E e, E e2) {
        return new r.j(e, e2);
    }

    public static <E> Set<E> of(E e, E e2, E e3) {
        return new r.k(e, e2, e3);
    }

    public static <E> Set<E> of(E e, E e2, E e3, E e4) {
        return new r.k(e, e2, e3, e4);
    }

    public static <E> Set<E> of(E e, E e2, E e3, E e4, E e5) {
        return new r.k(e, e2, e3, e4, e5);
    }

    public static <E> Set<E> of(E e, E e2, E e3, E e4, E e5, E e6) {
        return new r.k(e, e2, e3, e4, e5, e6);
    }

    public static <E> Set<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7) {
        return new r.k(e, e2, e3, e4, e5, e6, e7);
    }

    public static <E> Set<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return new r.k(e, e2, e3, e4, e5, e6, e7, e8);
    }

    public static <E> Set<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return new r.k(e, e2, e3, e4, e5, e6, e7, e8, e9);
    }

    public static <E> Set<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return new r.k(e, e2, e3, e4, e5, e6, e7, e8, e9, e10);
    }

    public static <E> Set<E> of(E... eArr) {
        switch (eArr.length) {
            case 0:
                return r.e;
            case 1:
                return new r.j(eArr[0]);
            case 2:
                return new r.j(eArr[0], eArr[1]);
            default:
                return new r.k(eArr);
        }
    }

    public static <E> Set<E> copyOf(Collection<? extends E> collection) {
        if (collection instanceof r.d) {
            return (Set) collection;
        }
        return of(new HashSet(collection).toArray());
    }

    private Sets() {
    }
}
