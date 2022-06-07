package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import kotlin.text.Typography;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class Iterators {
    private Iterators() {
    }

    public static <T> UnmodifiableIterator<T> a() {
        return b();
    }

    static <T> UnmodifiableListIterator<T> b() {
        return (UnmodifiableListIterator<T>) a.a;
    }

    /* loaded from: classes2.dex */
    public enum c implements Iterator<Object> {
        INSTANCE;

        @Override // java.util.Iterator
        public boolean hasNext() {
            return false;
        }

        @Override // java.util.Iterator
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            t.a(false);
        }
    }

    public static <T> Iterator<T> c() {
        return c.INSTANCE;
    }

    public static <T> UnmodifiableIterator<T> unmodifiableIterator(final Iterator<? extends T> it) {
        Preconditions.checkNotNull(it);
        if (it instanceof UnmodifiableIterator) {
            return (UnmodifiableIterator) it;
        }
        return new UnmodifiableIterator<T>() { // from class: com.google.common.collect.Iterators.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            /* JADX WARN: Type inference failed for: r0v1, types: [T, java.lang.Object] */
            @Override // java.util.Iterator
            public T next() {
                return it.next();
            }
        };
    }

    @Deprecated
    public static <T> UnmodifiableIterator<T> unmodifiableIterator(UnmodifiableIterator<T> unmodifiableIterator) {
        return (UnmodifiableIterator) Preconditions.checkNotNull(unmodifiableIterator);
    }

    public static int size(Iterator<?> it) {
        long j = 0;
        while (it.hasNext()) {
            it.next();
            j++;
        }
        return Ints.saturatedCast(j);
    }

    public static boolean contains(Iterator<?> it, @NullableDecl Object obj) {
        if (obj == null) {
            while (it.hasNext()) {
                if (it.next() == null) {
                    return true;
                }
            }
            return false;
        }
        while (it.hasNext()) {
            if (obj.equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    @CanIgnoreReturnValue
    public static boolean removeAll(Iterator<?> it, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        boolean z = false;
        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @CanIgnoreReturnValue
    public static <T> boolean removeIf(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        boolean z = false;
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @CanIgnoreReturnValue
    public static boolean retainAll(Iterator<?> it, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        boolean z = false;
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0006  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean elementsEqual(java.util.Iterator<?> r3, java.util.Iterator<?> r4) {
        /*
        L_0x0000:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x001d
            boolean r0 = r4.hasNext()
            r1 = 0
            if (r0 != 0) goto L_0x000e
            return r1
        L_0x000e:
            java.lang.Object r0 = r3.next()
            java.lang.Object r2 = r4.next()
            boolean r0 = com.google.common.base.Objects.equal(r0, r2)
            if (r0 != 0) goto L_0x0000
            return r1
        L_0x001d:
            boolean r3 = r4.hasNext()
            r3 = r3 ^ 1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Iterators.elementsEqual(java.util.Iterator, java.util.Iterator):boolean");
    }

    public static String toString(Iterator<?> it) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        boolean z = true;
        while (it.hasNext()) {
            if (!z) {
                sb.append(", ");
            }
            z = false;
            sb.append(it.next());
        }
        sb.append(']');
        return sb.toString();
    }

    @CanIgnoreReturnValue
    public static <T> T getOnlyElement(Iterator<T> it) {
        T next = it.next();
        if (!it.hasNext()) {
            return next;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected one element but was: <");
        sb.append(next);
        for (int i = 0; i < 4 && it.hasNext(); i++) {
            sb.append(", ");
            sb.append(it.next());
        }
        if (it.hasNext()) {
            sb.append(", ...");
        }
        sb.append(Typography.greater);
        throw new IllegalArgumentException(sb.toString());
    }

    @CanIgnoreReturnValue
    @NullableDecl
    public static <T> T getOnlyElement(Iterator<? extends T> it, @NullableDecl T t) {
        return it.hasNext() ? (T) getOnlyElement(it) : t;
    }

    @GwtIncompatible
    public static <T> T[] toArray(Iterator<? extends T> it, Class<T> cls) {
        return (T[]) Iterables.toArray(Lists.newArrayList(it), cls);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CanIgnoreReturnValue
    public static <T> boolean addAll(Collection<T> collection, Iterator<? extends T> it) {
        Preconditions.checkNotNull(collection);
        Preconditions.checkNotNull(it);
        boolean z = false;
        while (it.hasNext()) {
            z |= collection.add(it.next());
        }
        return z;
    }

    public static int frequency(Iterator<?> it, @NullableDecl Object obj) {
        int i = 0;
        while (contains(it, obj)) {
            i++;
        }
        return i;
    }

    public static <T> Iterator<T> cycle(final Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterator<T>() { // from class: com.google.common.collect.Iterators.4
            Iterator<T> a = Iterators.c();

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.a.hasNext() || iterable.iterator().hasNext();
            }

            /* JADX WARN: Type inference failed for: r0v3, types: [T, java.lang.Object] */
            @Override // java.util.Iterator
            public T next() {
                if (!this.a.hasNext()) {
                    this.a = iterable.iterator();
                    if (!this.a.hasNext()) {
                        throw new NoSuchElementException();
                    }
                }
                return this.a.next();
            }

            @Override // java.util.Iterator
            public void remove() {
                this.a.remove();
            }
        };
    }

    @SafeVarargs
    public static <T> Iterator<T> cycle(T... tArr) {
        return cycle(Lists.newArrayList(tArr));
    }

    private static <T> Iterator<T> a(final T... tArr) {
        return new UnmodifiableIterator<T>() { // from class: com.google.common.collect.Iterators.5
            int a = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.a < tArr.length;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Iterator
            public T next() {
                if (hasNext()) {
                    Object[] objArr = tArr;
                    int i = this.a;
                    T t = objArr[i];
                    objArr[i] = 0;
                    this.a = i + 1;
                    return t;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> it, Iterator<? extends T> it2) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(it2);
        return concat(a((Object[]) new Iterator[]{it, it2}));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> it, Iterator<? extends T> it2, Iterator<? extends T> it3) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(it2);
        Preconditions.checkNotNull(it3);
        return concat(a((Object[]) new Iterator[]{it, it2, it3}));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> it, Iterator<? extends T> it2, Iterator<? extends T> it3, Iterator<? extends T> it4) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(it2);
        Preconditions.checkNotNull(it3);
        Preconditions.checkNotNull(it4);
        return concat(a((Object[]) new Iterator[]{it, it2, it3, it4}));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T>... itArr) {
        return a((Iterator[]) Arrays.copyOf(itArr, itArr.length));
    }

    public static <T> Iterator<T> concat(Iterator<? extends Iterator<? extends T>> it) {
        return new b(it);
    }

    static <T> Iterator<T> a(Iterator<? extends T>... itArr) {
        for (Iterator it : (Iterator[]) Preconditions.checkNotNull(itArr)) {
            Preconditions.checkNotNull(it);
        }
        return concat(a((Object[]) itArr));
    }

    public static <T> UnmodifiableIterator<List<T>> partition(Iterator<T> it, int i) {
        return a(it, i, false);
    }

    public static <T> UnmodifiableIterator<List<T>> paddedPartition(Iterator<T> it, int i) {
        return a(it, i, true);
    }

    private static <T> UnmodifiableIterator<List<T>> a(final Iterator<T> it, final int i, final boolean z) {
        Preconditions.checkNotNull(it);
        Preconditions.checkArgument(i > 0);
        return new UnmodifiableIterator<List<T>>() { // from class: com.google.common.collect.Iterators.6
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            /* renamed from: a */
            public List<T> next() {
                if (hasNext()) {
                    Object[] objArr = new Object[i];
                    int i2 = 0;
                    while (i2 < i && it.hasNext()) {
                        objArr[i2] = it.next();
                        i2++;
                    }
                    for (int i3 = i2; i3 < i; i3++) {
                        objArr[i3] = null;
                    }
                    List<T> unmodifiableList = Collections.unmodifiableList(Arrays.asList(objArr));
                    return (z || i2 == i) ? unmodifiableList : unmodifiableList.subList(0, i2);
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static <T> UnmodifiableIterator<T> filter(final Iterator<T> it, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        return new AbstractIterator<T>() { // from class: com.google.common.collect.Iterators.7
            /* JADX WARN: Type inference failed for: r0v4, types: [T, java.lang.Object] */
            /* JADX WARN: Unknown variable types count: 1 */
            @Override // com.google.common.collect.AbstractIterator
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            protected T computeNext() {
                /*
                    r2 = this;
                L_0x0000:
                    java.util.Iterator r0 = r1
                    boolean r0 = r0.hasNext()
                    if (r0 == 0) goto L_0x0017
                    java.util.Iterator r0 = r1
                    java.lang.Object r0 = r0.next()
                    com.google.common.base.Predicate r1 = r2
                    boolean r1 = r1.apply(r0)
                    if (r1 == 0) goto L_0x0000
                    return r0
                L_0x0017:
                    java.lang.Object r0 = r2.endOfData()
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Iterators.AnonymousClass7.computeNext():java.lang.Object");
            }
        };
    }

    @GwtIncompatible
    public static <T> UnmodifiableIterator<T> filter(Iterator<?> it, Class<T> cls) {
        return filter(it, Predicates.instanceOf(cls));
    }

    public static <T> boolean any(Iterator<T> it, Predicate<? super T> predicate) {
        return indexOf(it, predicate) != -1;
    }

    public static <T> boolean all(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        while (it.hasNext()) {
            if (!predicate.apply(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> T find(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        while (it.hasNext()) {
            T next = it.next();
            if (predicate.apply(next)) {
                return next;
            }
        }
        throw new NoSuchElementException();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [T, java.lang.Object] */
    /* JADX WARN: Unknown variable types count: 1 */
    @org.checkerframework.checker.nullness.compatqual.NullableDecl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static <T> T find(java.util.Iterator<? extends T> r2, com.google.common.base.Predicate<? super T> r3, @org.checkerframework.checker.nullness.compatqual.NullableDecl T r4) {
        /*
            com.google.common.base.Preconditions.checkNotNull(r2)
            com.google.common.base.Preconditions.checkNotNull(r3)
        L_0x0006:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0017
            java.lang.Object r0 = r2.next()
            boolean r1 = r3.apply(r0)
            if (r1 == 0) goto L_0x0006
            return r0
        L_0x0017:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Iterators.find(java.util.Iterator, com.google.common.base.Predicate, java.lang.Object):java.lang.Object");
    }

    public static <T> Optional<T> tryFind(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        while (it.hasNext()) {
            T next = it.next();
            if (predicate.apply(next)) {
                return Optional.of(next);
            }
        }
        return Optional.absent();
    }

    public static <T> int indexOf(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate, "predicate");
        int i = 0;
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static <F, T> Iterator<T> transform(Iterator<F> it, final Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(function);
        return new cp<F, T>(it) { // from class: com.google.common.collect.Iterators.8
            /* JADX WARN: Type inference failed for: r2v1, types: [T, java.lang.Object] */
            @Override // com.google.common.collect.cp
            T a(F f) {
                return function.apply(f);
            }
        };
    }

    public static <T> T get(Iterator<T> it, int i) {
        a(i);
        int advance = advance(it, i);
        if (it.hasNext()) {
            return it.next();
        }
        throw new IndexOutOfBoundsException("position (" + i + ") must be less than the number of elements that remained (" + advance + ")");
    }

    @NullableDecl
    public static <T> T get(Iterator<? extends T> it, int i, @NullableDecl T t) {
        a(i);
        advance(it, i);
        return (T) getNext(it, t);
    }

    public static void a(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("position (" + i + ") must not be negative");
        }
    }

    @NullableDecl
    public static <T> T getNext(Iterator<? extends T> it, @NullableDecl T t) {
        return it.hasNext() ? (T) it.next() : t;
    }

    public static <T> T getLast(Iterator<T> it) {
        T next;
        do {
            next = it.next();
        } while (it.hasNext());
        return next;
    }

    @NullableDecl
    public static <T> T getLast(Iterator<? extends T> it, @NullableDecl T t) {
        return it.hasNext() ? (T) getLast(it) : t;
    }

    @CanIgnoreReturnValue
    public static int advance(Iterator<?> it, int i) {
        Preconditions.checkNotNull(it);
        int i2 = 0;
        Preconditions.checkArgument(i >= 0, "numberToAdvance must be nonnegative");
        while (i2 < i && it.hasNext()) {
            it.next();
            i2++;
        }
        return i2;
    }

    public static <T> Iterator<T> limit(final Iterator<T> it, final int i) {
        Preconditions.checkNotNull(it);
        Preconditions.checkArgument(i >= 0, "limit is negative");
        return new Iterator<T>() { // from class: com.google.common.collect.Iterators.9
            private int c;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.c < i && it.hasNext();
            }

            /* JADX WARN: Type inference failed for: r0v5, types: [T, java.lang.Object] */
            @Override // java.util.Iterator
            public T next() {
                if (hasNext()) {
                    this.c++;
                    return it.next();
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                it.remove();
            }
        };
    }

    public static <T> Iterator<T> consumingIterator(final Iterator<T> it) {
        Preconditions.checkNotNull(it);
        return new UnmodifiableIterator<T>() { // from class: com.google.common.collect.Iterators.10
            public String toString() {
                return "Iterators.consumingIterator(...)";
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            /* JADX WARN: Type inference failed for: r0v1, types: [T, java.lang.Object] */
            /* JADX WARN: Unknown variable types count: 1 */
            @Override // java.util.Iterator
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public T next() {
                /*
                    r2 = this;
                    java.util.Iterator r0 = r1
                    java.lang.Object r0 = r0.next()
                    java.util.Iterator r1 = r1
                    r1.remove()
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Iterators.AnonymousClass10.next():java.lang.Object");
            }
        };
    }

    @NullableDecl
    public static <T> T a(Iterator<T> it) {
        if (!it.hasNext()) {
            return null;
        }
        T next = it.next();
        it.remove();
        return next;
    }

    public static void b(Iterator<?> it) {
        Preconditions.checkNotNull(it);
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    @SafeVarargs
    public static <T> UnmodifiableIterator<T> forArray(T... tArr) {
        return a(tArr, 0, tArr.length, 0);
    }

    static <T> UnmodifiableListIterator<T> a(T[] tArr, int i, int i2, int i3) {
        Preconditions.checkArgument(i2 >= 0);
        Preconditions.checkPositionIndexes(i, i + i2, tArr.length);
        Preconditions.checkPositionIndex(i3, i2);
        if (i2 == 0) {
            return b();
        }
        return new a(tArr, i, i2, i3);
    }

    /* loaded from: classes2.dex */
    public static final class a<T> extends b<T> {
        static final UnmodifiableListIterator<Object> a = new a(new Object[0], 0, 0, 0);
        private final T[] b;
        private final int c;

        a(T[] tArr, int i, int i2, int i3) {
            super(i2, i3);
            this.b = tArr;
            this.c = i;
        }

        @Override // com.google.common.collect.b
        protected T a(int i) {
            return this.b[this.c + i];
        }
    }

    public static <T> UnmodifiableIterator<T> singletonIterator(@NullableDecl final T t) {
        return new UnmodifiableIterator<T>() { // from class: com.google.common.collect.Iterators.11
            boolean a;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return !this.a;
            }

            /* JADX WARN: Type inference failed for: r0v3, types: [T, java.lang.Object] */
            @Override // java.util.Iterator
            public T next() {
                if (!this.a) {
                    this.a = true;
                    return t;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static <T> UnmodifiableIterator<T> forEnumeration(final Enumeration<T> enumeration) {
        Preconditions.checkNotNull(enumeration);
        return new UnmodifiableIterator<T>() { // from class: com.google.common.collect.Iterators.2
            @Override // java.util.Iterator
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            /* JADX WARN: Type inference failed for: r0v1, types: [T, java.lang.Object] */
            @Override // java.util.Iterator
            public T next() {
                return enumeration.nextElement();
            }
        };
    }

    public static <T> Enumeration<T> asEnumeration(final Iterator<T> it) {
        Preconditions.checkNotNull(it);
        return new Enumeration<T>() { // from class: com.google.common.collect.Iterators.3
            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                return it.hasNext();
            }

            /* JADX WARN: Type inference failed for: r0v1, types: [T, java.lang.Object] */
            @Override // java.util.Enumeration
            public T nextElement() {
                return it.next();
            }
        };
    }

    /* loaded from: classes2.dex */
    public static class e<E> implements PeekingIterator<E> {
        private final Iterator<? extends E> a;
        private boolean b;
        @NullableDecl
        private E c;

        public e(Iterator<? extends E> it) {
            this.a = (Iterator) Preconditions.checkNotNull(it);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b || this.a.hasNext();
        }

        @Override // com.google.common.collect.PeekingIterator, java.util.Iterator
        public E next() {
            if (!this.b) {
                return (E) this.a.next();
            }
            E e = this.c;
            this.b = false;
            this.c = null;
            return e;
        }

        @Override // com.google.common.collect.PeekingIterator, java.util.Iterator
        public void remove() {
            Preconditions.checkState(!this.b, "Can't remove after you've peeked at next");
            this.a.remove();
        }

        @Override // com.google.common.collect.PeekingIterator
        public E peek() {
            if (!this.b) {
                this.c = (E) this.a.next();
                this.b = true;
            }
            return this.c;
        }
    }

    public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> it) {
        if (it instanceof e) {
            return (e) it;
        }
        return new e(it);
    }

    @Deprecated
    public static <T> PeekingIterator<T> peekingIterator(PeekingIterator<T> peekingIterator) {
        return (PeekingIterator) Preconditions.checkNotNull(peekingIterator);
    }

    @Beta
    public static <T> UnmodifiableIterator<T> mergeSorted(Iterable<? extends Iterator<? extends T>> iterable, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(iterable, "iterators");
        Preconditions.checkNotNull(comparator, "comparator");
        return new d(iterable, comparator);
    }

    /* loaded from: classes2.dex */
    public static class d<T> extends UnmodifiableIterator<T> {
        final Queue<PeekingIterator<T>> a;

        public d(Iterable<? extends Iterator<? extends T>> iterable, final Comparator<? super T> comparator) {
            this.a = new PriorityQueue(2, new Comparator<PeekingIterator<T>>() { // from class: com.google.common.collect.Iterators.d.1
                /* renamed from: a */
                public int compare(PeekingIterator<T> peekingIterator, PeekingIterator<T> peekingIterator2) {
                    return comparator.compare(peekingIterator.peek(), peekingIterator2.peek());
                }
            });
            Iterator<? extends Iterator<? extends T>> it = iterable.iterator();
            while (it.hasNext()) {
                Iterator it2 = (Iterator) it.next();
                if (it2.hasNext()) {
                    this.a.add(Iterators.peekingIterator(it2));
                }
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.a.isEmpty();
        }

        @Override // java.util.Iterator
        public T next() {
            PeekingIterator<T> remove = this.a.remove();
            T next = remove.next();
            if (remove.hasNext()) {
                this.a.add(remove);
            }
            return next;
        }
    }

    /* loaded from: classes2.dex */
    public static class b<T> implements Iterator<T> {
        @NullableDecl
        private Iterator<? extends T> a;
        private Iterator<? extends T> b = Iterators.a();
        private Iterator<? extends Iterator<? extends T>> c;
        @NullableDecl
        private Deque<Iterator<? extends Iterator<? extends T>>> d;

        b(Iterator<? extends Iterator<? extends T>> it) {
            this.c = (Iterator) Preconditions.checkNotNull(it);
        }

        @NullableDecl
        private Iterator<? extends Iterator<? extends T>> a() {
            while (true) {
                Iterator<? extends Iterator<? extends T>> it = this.c;
                if (it != null && it.hasNext()) {
                    return this.c;
                }
                Deque<Iterator<? extends Iterator<? extends T>>> deque = this.d;
                if (deque == null || deque.isEmpty()) {
                    return null;
                }
                this.c = this.d.removeFirst();
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            while (!((Iterator) Preconditions.checkNotNull(this.b)).hasNext()) {
                this.c = a();
                Iterator<? extends Iterator<? extends T>> it = this.c;
                if (it == null) {
                    return false;
                }
                this.b = (Iterator) it.next();
                Iterator<? extends T> it2 = this.b;
                if (it2 instanceof b) {
                    b bVar = (b) it2;
                    this.b = bVar.b;
                    if (this.d == null) {
                        this.d = new ArrayDeque();
                    }
                    this.d.addFirst(this.c);
                    if (bVar.d != null) {
                        while (!bVar.d.isEmpty()) {
                            this.d.addFirst(bVar.d.removeLast());
                        }
                    }
                    this.c = bVar.c;
                }
            }
            return true;
        }

        @Override // java.util.Iterator
        public T next() {
            if (hasNext()) {
                Iterator<? extends T> it = this.b;
                this.a = it;
                return (T) it.next();
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            t.a(this.a != null);
            this.a.remove();
            this.a = null;
        }
    }

    public static <T> ListIterator<T> c(Iterator<T> it) {
        return (ListIterator) it;
    }
}
