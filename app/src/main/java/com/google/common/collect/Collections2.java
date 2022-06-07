package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.math.IntMath;
import com.xiaomi.infra.galaxy.fds.Constants;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public final class Collections2 {
    private Collections2() {
    }

    public static <E> Collection<E> filter(Collection<E> collection, Predicate<? super E> predicate) {
        if (collection instanceof a) {
            return ((a) collection).a(predicate);
        }
        return new a((Collection) Preconditions.checkNotNull(collection), (Predicate) Preconditions.checkNotNull(predicate));
    }

    public static boolean a(Collection<?> collection, @NullableDecl Object obj) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.contains(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    public static boolean b(Collection<?> collection, @NullableDecl Object obj) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.remove(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    /* loaded from: classes2.dex */
    public static class a<E> extends AbstractCollection<E> {
        final Collection<E> a;
        final Predicate<? super E> b;

        public a(Collection<E> collection, Predicate<? super E> predicate) {
            this.a = collection;
            this.b = predicate;
        }

        a<E> a(Predicate<? super E> predicate) {
            return new a<>(this.a, Predicates.and(this.b, predicate));
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean add(E e) {
            Preconditions.checkArgument(this.b.apply(e));
            return this.a.add(e);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            Iterator<? extends E> it = collection.iterator();
            while (it.hasNext()) {
                Preconditions.checkArgument(this.b.apply(it.next()));
            }
            return this.a.addAll(collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            Iterables.removeIf(this.a, this.b);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            if (Collections2.a((Collection<?>) this.a, obj)) {
                return this.b.apply(obj);
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return Collections2.a((Collection<?>) this, collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return !Iterables.any(this.a, this.b);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<E> iterator() {
            return Iterators.filter(this.a.iterator(), this.b);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            return contains(obj) && this.a.remove(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            Iterator<E> it = this.a.iterator();
            boolean z = false;
            while (it.hasNext()) {
                E next = it.next();
                if (this.b.apply(next) && collection.contains(next)) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            Iterator<E> it = this.a.iterator();
            boolean z = false;
            while (it.hasNext()) {
                E next = it.next();
                if (this.b.apply(next) && !collection.contains(next)) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            int i = 0;
            for (E e : this.a) {
                if (this.b.apply(e)) {
                    i++;
                }
            }
            return i;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return Lists.newArrayList(iterator()).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) Lists.newArrayList(iterator()).toArray(tArr);
        }
    }

    public static <F, T> Collection<T> transform(Collection<F> collection, Function<? super F, T> function) {
        return new f(collection, function);
    }

    /* loaded from: classes2.dex */
    public static class f<F, T> extends AbstractCollection<T> {
        final Collection<F> a;
        final Function<? super F, ? extends T> b;

        f(Collection<F> collection, Function<? super F, ? extends T> function) {
            this.a = (Collection) Preconditions.checkNotNull(collection);
            this.b = (Function) Preconditions.checkNotNull(function);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            this.a.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<T> iterator() {
            return Iterators.transform(this.a.iterator(), this.b);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.a.size();
        }
    }

    public static boolean a(Collection<?> collection, Collection<?> collection2) {
        Iterator<?> it = collection2.iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static String a(Collection<?> collection) {
        StringBuilder a2 = a(collection.size());
        a2.append('[');
        boolean z = true;
        for (Object obj : collection) {
            if (!z) {
                a2.append(", ");
            }
            z = false;
            if (obj == collection) {
                a2.append("(this Collection)");
            } else {
                a2.append(obj);
            }
        }
        a2.append(']');
        return a2.toString();
    }

    public static StringBuilder a(int i) {
        t.a(i, "size");
        return new StringBuilder((int) Math.min(i * 8, (long) Constants.DEFAULT_SPACE_LIMIT));
    }

    public static <T> Collection<T> a(Iterable<T> iterable) {
        return (Collection) iterable;
    }

    @Beta
    public static <E extends Comparable<? super E>> Collection<List<E>> orderedPermutations(Iterable<E> iterable) {
        return orderedPermutations(iterable, Ordering.natural());
    }

    @Beta
    public static <E> Collection<List<E>> orderedPermutations(Iterable<E> iterable, Comparator<? super E> comparator) {
        return new b(iterable, comparator);
    }

    /* loaded from: classes2.dex */
    public static final class b<E> extends AbstractCollection<List<E>> {
        final ImmutableList<E> a;
        final Comparator<? super E> b;
        final int c;

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return false;
        }

        b(Iterable<E> iterable, Comparator<? super E> comparator) {
            this.a = ImmutableList.sortedCopyOf(comparator, iterable);
            this.b = comparator;
            this.c = a(this.a, comparator);
        }

        private static <E> int a(List<E> list, Comparator<? super E> comparator) {
            int i = 1;
            int i2 = 1;
            int i3 = 1;
            while (i < list.size()) {
                if (comparator.compare(list.get(i - 1), list.get(i)) < 0) {
                    i2 = IntMath.saturatedMultiply(i2, IntMath.binomial(i, i3));
                    i3 = 0;
                    if (i2 == Integer.MAX_VALUE) {
                        return Integer.MAX_VALUE;
                    }
                }
                i++;
                i3++;
            }
            return IntMath.saturatedMultiply(i2, IntMath.binomial(i, i3));
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.c;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<List<E>> iterator() {
            return new c(this.a, this.b);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            return Collections2.b((List<?>) this.a, (List<?>) obj);
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            return "orderedPermutationCollection(" + this.a + ")";
        }
    }

    /* loaded from: classes2.dex */
    private static final class c<E> extends AbstractIterator<List<E>> {
        @NullableDecl
        List<E> a;
        final Comparator<? super E> b;

        c(List<E> list, Comparator<? super E> comparator) {
            this.a = Lists.newArrayList(list);
            this.b = comparator;
        }

        /* renamed from: a */
        public List<E> computeNext() {
            List<E> list = this.a;
            if (list == null) {
                return endOfData();
            }
            ImmutableList copyOf = ImmutableList.copyOf((Collection) list);
            b();
            return copyOf;
        }

        void b() {
            int c = c();
            if (c == -1) {
                this.a = null;
                return;
            }
            Collections.swap(this.a, c, a(c));
            Collections.reverse(this.a.subList(c + 1, this.a.size()));
        }

        int c() {
            for (int size = this.a.size() - 2; size >= 0; size--) {
                if (this.b.compare((E) this.a.get(size), (E) this.a.get(size + 1)) < 0) {
                    return size;
                }
            }
            return -1;
        }

        int a(int i) {
            E e = this.a.get(i);
            for (int size = this.a.size() - 1; size > i; size--) {
                if (this.b.compare(e, (E) this.a.get(size)) < 0) {
                    return size;
                }
            }
            throw new AssertionError("this statement should be unreachable");
        }
    }

    @Beta
    public static <E> Collection<List<E>> permutations(Collection<E> collection) {
        return new d(ImmutableList.copyOf((Collection) collection));
    }

    /* loaded from: classes2.dex */
    private static final class d<E> extends AbstractCollection<List<E>> {
        final ImmutableList<E> a;

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return false;
        }

        d(ImmutableList<E> immutableList) {
            this.a = immutableList;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return IntMath.factorial(this.a.size());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<List<E>> iterator() {
            return new e(this.a);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            return Collections2.b((List<?>) this.a, (List<?>) obj);
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            return "permutations(" + this.a + ")";
        }
    }

    /* loaded from: classes2.dex */
    private static class e<E> extends AbstractIterator<List<E>> {
        final List<E> a;
        final int[] b;
        final int[] c;
        int d = Integer.MAX_VALUE;

        e(List<E> list) {
            this.a = new ArrayList(list);
            int size = list.size();
            this.b = new int[size];
            this.c = new int[size];
            Arrays.fill(this.b, 0);
            Arrays.fill(this.c, 1);
        }

        /* renamed from: a */
        public List<E> computeNext() {
            if (this.d <= 0) {
                return endOfData();
            }
            ImmutableList copyOf = ImmutableList.copyOf((Collection) this.a);
            b();
            return copyOf;
        }

        void b() {
            this.d = this.a.size() - 1;
            if (this.d != -1) {
                int i = 0;
                while (true) {
                    int[] iArr = this.b;
                    int i2 = this.d;
                    int i3 = iArr[i2] + this.c[i2];
                    if (i3 < 0) {
                        c();
                    } else if (i3 != i2 + 1) {
                        Collections.swap(this.a, (i2 - iArr[i2]) + i, (i2 - i3) + i);
                        this.b[this.d] = i3;
                        return;
                    } else if (i2 != 0) {
                        i++;
                        c();
                    } else {
                        return;
                    }
                }
            }
        }

        void c() {
            int[] iArr = this.c;
            int i = this.d;
            iArr[i] = -iArr[i];
            this.d = i - 1;
        }
    }

    public static boolean b(List<?> list, List<?> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        bn b2 = b(list);
        bn b3 = b(list2);
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (b2.d(i) != b3.b(b2.c(i))) {
                return false;
            }
        }
        return true;
    }

    private static <E> bn<E> b(Collection<E> collection) {
        bn<E> bnVar = new bn<>();
        for (E e2 : collection) {
            bnVar.a((bn<E>) e2, bnVar.b(e2) + 1);
        }
        return bnVar;
    }
}
