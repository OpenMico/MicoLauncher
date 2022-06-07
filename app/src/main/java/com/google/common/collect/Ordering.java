package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class Ordering<T> implements Comparator<T> {

    /* loaded from: classes2.dex */
    private static class b {
        static final Ordering<Object> a = new a();
    }

    @Override // java.util.Comparator
    @CanIgnoreReturnValue
    public abstract int compare(@NullableDecl T t, @NullableDecl T t2);

    @GwtCompatible(serializable = true)
    public static <C extends Comparable> Ordering<C> natural() {
        return bk.a;
    }

    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> from(Comparator<T> comparator) {
        return comparator instanceof Ordering ? (Ordering) comparator : new y(comparator);
    }

    @GwtCompatible(serializable = true)
    @Deprecated
    public static <T> Ordering<T> from(Ordering<T> ordering) {
        return (Ordering) Preconditions.checkNotNull(ordering);
    }

    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> explicit(List<T> list) {
        return new aj(list);
    }

    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> explicit(T t, T... tArr) {
        return explicit(Lists.asList(t, tArr));
    }

    @GwtCompatible(serializable = true)
    public static Ordering<Object> allEqual() {
        return p.a;
    }

    @GwtCompatible(serializable = true)
    public static Ordering<Object> usingToString() {
        return cs.a;
    }

    public static Ordering<Object> arbitrary() {
        return b.a;
    }

    @VisibleForTesting
    /* loaded from: classes2.dex */
    static class a extends Ordering<Object> {
        private final AtomicInteger a = new AtomicInteger(0);
        private final ConcurrentMap<Object, Integer> b = bp.a(new MapMaker()).makeMap();

        public String toString() {
            return "Ordering.arbitrary()";
        }

        a() {
        }

        private Integer b(Object obj) {
            Integer num = this.b.get(obj);
            if (num != null) {
                return num;
            }
            Integer valueOf = Integer.valueOf(this.a.getAndIncrement());
            Integer putIfAbsent = this.b.putIfAbsent(obj, valueOf);
            return putIfAbsent != null ? putIfAbsent : valueOf;
        }

        @Override // com.google.common.collect.Ordering, java.util.Comparator
        public int compare(Object obj, Object obj2) {
            if (obj == obj2) {
                return 0;
            }
            if (obj == null) {
                return -1;
            }
            if (obj2 == null) {
                return 1;
            }
            int a = a(obj);
            int a2 = a(obj2);
            if (a != a2) {
                return a < a2 ? -1 : 1;
            }
            int compareTo = b(obj).compareTo(b(obj2));
            if (compareTo != 0) {
                return compareTo;
            }
            throw new AssertionError();
        }

        int a(Object obj) {
            return System.identityHashCode(obj);
        }
    }

    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<S> reverse() {
        return new cb(this);
    }

    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<S> nullsFirst() {
        return new bl(this);
    }

    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<S> nullsLast() {
        return new bm(this);
    }

    @GwtCompatible(serializable = true)
    public <F> Ordering<F> onResultOf(Function<F, ? extends T> function) {
        return new r(function, this);
    }

    public <T2 extends T> Ordering<Map.Entry<T2, ?>> a() {
        return (Ordering<Map.Entry<T2, ?>>) onResultOf(Maps.a());
    }

    @GwtCompatible(serializable = true)
    public <U extends T> Ordering<U> compound(Comparator<? super U> comparator) {
        return new z(this, (Comparator) Preconditions.checkNotNull(comparator));
    }

    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> compound(Iterable<? extends Comparator<? super T>> iterable) {
        return new z(iterable);
    }

    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<Iterable<S>> lexicographical() {
        return new bh(this);
    }

    @CanIgnoreReturnValue
    public <E extends T> E min(Iterator<E> it) {
        E next = it.next();
        while (it.hasNext()) {
            next = (E) min(next, it.next());
        }
        return next;
    }

    @CanIgnoreReturnValue
    public <E extends T> E min(Iterable<E> iterable) {
        return (E) min(iterable.iterator());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CanIgnoreReturnValue
    public <E extends T> E min(@NullableDecl E e, @NullableDecl E e2) {
        return compare(e, e2) <= 0 ? e : e2;
    }

    @CanIgnoreReturnValue
    public <E extends T> E min(@NullableDecl E e, @NullableDecl E e2, @NullableDecl E e3, E... eArr) {
        E e4 = (E) min(min(e, e2), e3);
        for (E e5 : eArr) {
            e4 = (E) min(e4, e5);
        }
        return e4;
    }

    @CanIgnoreReturnValue
    public <E extends T> E max(Iterator<E> it) {
        E next = it.next();
        while (it.hasNext()) {
            next = (E) max(next, it.next());
        }
        return next;
    }

    @CanIgnoreReturnValue
    public <E extends T> E max(Iterable<E> iterable) {
        return (E) max(iterable.iterator());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CanIgnoreReturnValue
    public <E extends T> E max(@NullableDecl E e, @NullableDecl E e2) {
        return compare(e, e2) >= 0 ? e : e2;
    }

    @CanIgnoreReturnValue
    public <E extends T> E max(@NullableDecl E e, @NullableDecl E e2, @NullableDecl E e3, E... eArr) {
        E e4 = (E) max(max(e, e2), e3);
        for (E e5 : eArr) {
            e4 = (E) max(e4, e5);
        }
        return e4;
    }

    public <E extends T> List<E> leastOf(Iterable<E> iterable, int i) {
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            if (collection.size() <= i * 2) {
                Object[] array = collection.toArray();
                Arrays.sort(array, this);
                if (array.length > i) {
                    array = Arrays.copyOf(array, i);
                }
                return Collections.unmodifiableList(Arrays.asList(array));
            }
        }
        return leastOf(iterable.iterator(), i);
    }

    public <E extends T> List<E> leastOf(Iterator<E> it, int i) {
        Preconditions.checkNotNull(it);
        t.a(i, "k");
        if (i == 0 || !it.hasNext()) {
            return Collections.emptyList();
        }
        if (i >= 1073741823) {
            ArrayList newArrayList = Lists.newArrayList(it);
            Collections.sort(newArrayList, this);
            if (newArrayList.size() > i) {
                newArrayList.subList(i, newArrayList.size()).clear();
            }
            newArrayList.trimToSize();
            return Collections.unmodifiableList(newArrayList);
        }
        co a2 = co.a(i, this);
        a2.a((Iterator) it);
        return a2.a();
    }

    public <E extends T> List<E> greatestOf(Iterable<E> iterable, int i) {
        return reverse().leastOf(iterable, i);
    }

    public <E extends T> List<E> greatestOf(Iterator<E> it, int i) {
        return reverse().leastOf(it, i);
    }

    @CanIgnoreReturnValue
    public <E extends T> List<E> sortedCopy(Iterable<E> iterable) {
        Object[] a2 = Iterables.a((Iterable<?>) iterable);
        Arrays.sort(a2, this);
        return Lists.newArrayList(Arrays.asList(a2));
    }

    @CanIgnoreReturnValue
    public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable) {
        return ImmutableList.sortedCopyOf(this, iterable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isOrdered(Iterable<? extends T> iterable) {
        Iterator<? extends T> it = iterable.iterator();
        if (!it.hasNext()) {
            return true;
        }
        Object next = it.next();
        while (it.hasNext()) {
            Object next2 = it.next();
            if (compare(next, next2) > 0) {
                return false;
            }
            next = next2;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isStrictlyOrdered(Iterable<? extends T> iterable) {
        Iterator<? extends T> it = iterable.iterator();
        if (!it.hasNext()) {
            return true;
        }
        Object next = it.next();
        while (it.hasNext()) {
            Object next2 = it.next();
            if (compare(next, next2) >= 0) {
                return false;
            }
            next = next2;
        }
        return true;
    }

    @Deprecated
    public int binarySearch(List<? extends T> list, @NullableDecl T t) {
        return Collections.binarySearch(list, t, this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static class c extends ClassCastException {
        private static final long serialVersionUID = 0;
        final Object value;

        public c(Object obj) {
            super("Cannot compare value: " + obj);
            this.value = obj;
        }
    }
}
