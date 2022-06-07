package java8.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java8.util.function.UnaryOperator;
import java8.util.r;

/* loaded from: classes5.dex */
public final class Lists {
    public static <E> void sort(List<E> list, Comparator<? super E> comparator) {
        Collections.sort(list, comparator);
    }

    public static <E> void replaceAll(List<E> list, UnaryOperator<E> unaryOperator) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(unaryOperator);
        ListIterator<E> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            listIterator.set((E) unaryOperator.apply(listIterator.next()));
        }
    }

    public static <E> Spliterator<E> spliterator(List<E> list) {
        return Spliterators.spliterator(list);
    }

    public static <E> List<E> of() {
        return r.d;
    }

    public static <E> List<E> of(E e) {
        return new r.e(e);
    }

    public static <E> List<E> of(E e, E e2) {
        return new r.e(e, e2);
    }

    public static <E> List<E> of(E e, E e2, E e3) {
        return new r.g(e, e2, e3);
    }

    public static <E> List<E> of(E e, E e2, E e3, E e4) {
        return new r.g(e, e2, e3, e4);
    }

    public static <E> List<E> of(E e, E e2, E e3, E e4, E e5) {
        return new r.g(e, e2, e3, e4, e5);
    }

    public static <E> List<E> of(E e, E e2, E e3, E e4, E e5, E e6) {
        return new r.g(e, e2, e3, e4, e5, e6);
    }

    public static <E> List<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7) {
        return new r.g(e, e2, e3, e4, e5, e6, e7);
    }

    public static <E> List<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return new r.g(e, e2, e3, e4, e5, e6, e7, e8);
    }

    public static <E> List<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return new r.g(e, e2, e3, e4, e5, e6, e7, e8, e9);
    }

    public static <E> List<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return new r.g(e, e2, e3, e4, e5, e6, e7, e8, e9, e10);
    }

    public static <E> List<E> of(E... eArr) {
        switch (eArr.length) {
            case 0:
                return r.d;
            case 1:
                return new r.e(eArr[0]);
            case 2:
                return new r.e(eArr[0], eArr[1]);
            default:
                return new r.g(eArr);
        }
    }

    public static <E> List<E> copyOf(Collection<? extends E> collection) {
        return r.a(collection);
    }

    private Lists() {
    }
}
