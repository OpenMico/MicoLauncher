package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public final class Multisets {
    private Multisets() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E> Multiset<E> unmodifiableMultiset(Multiset<? extends E> multiset) {
        return ((multiset instanceof h) || (multiset instanceof ImmutableMultiset)) ? multiset : new h((Multiset) Preconditions.checkNotNull(multiset));
    }

    @Deprecated
    public static <E> Multiset<E> unmodifiableMultiset(ImmutableMultiset<E> immutableMultiset) {
        return (Multiset) Preconditions.checkNotNull(immutableMultiset);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class h<E> extends ForwardingMultiset<E> implements Serializable {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Set<E> a;
        @MonotonicNonNullDecl
        transient Set<Multiset.Entry<E>> b;
        final Multiset<? extends E> delegate;

        public h(Multiset<? extends E> multiset) {
            this.delegate = multiset;
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Multiset<E> delegate() {
            return (Multiset<? extends E>) this.delegate;
        }

        Set<E> a() {
            return Collections.unmodifiableSet(this.delegate.elementSet());
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
        public Set<E> elementSet() {
            Set<E> set = this.a;
            if (set != null) {
                return set;
            }
            Set<E> a = a();
            this.a = a;
            return a;
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
        public Set<Multiset.Entry<E>> entrySet() {
            Set<Multiset.Entry<E>> set = this.b;
            if (set != null) {
                return set;
            }
            Set<Multiset.Entry<E>> unmodifiableSet = Collections.unmodifiableSet(this.delegate.entrySet());
            this.b = unmodifiableSet;
            return unmodifiableSet;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable
        public Iterator<E> iterator() {
            return Iterators.unmodifiableIterator(this.delegate.iterator());
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
        public int add(E e, int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
        public int remove(Object obj, int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
        public int setCount(E e, int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
        public boolean setCount(E e, int i, int i2) {
            throw new UnsupportedOperationException();
        }
    }

    @Beta
    public static <E> SortedMultiset<E> unmodifiableSortedMultiset(SortedMultiset<E> sortedMultiset) {
        return new cr((SortedMultiset) Preconditions.checkNotNull(sortedMultiset));
    }

    public static <E> Multiset.Entry<E> immutableEntry(@NullableDecl E e2, int i2) {
        return new f(e2, i2);
    }

    /* loaded from: classes2.dex */
    public static class f<E> extends a<E> implements Serializable {
        private static final long serialVersionUID = 0;
        private final int count;
        @NullableDecl
        private final E element;

        f(@NullableDecl E e, int i) {
            this.element = e;
            this.count = i;
            t.a(i, "count");
        }

        @Override // com.google.common.collect.Multiset.Entry
        @NullableDecl
        public final E getElement() {
            return this.element;
        }

        @Override // com.google.common.collect.Multiset.Entry
        public final int getCount() {
            return this.count;
        }
    }

    @Beta
    public static <E> Multiset<E> filter(Multiset<E> multiset, Predicate<? super E> predicate) {
        if (!(multiset instanceof e)) {
            return new e(multiset, predicate);
        }
        e eVar = (e) multiset;
        return new e(eVar.a, Predicates.and(eVar.b, predicate));
    }

    /* loaded from: classes2.dex */
    public static final class e<E> extends i<E> {
        final Multiset<E> a;
        final Predicate<? super E> b;

        e(Multiset<E> multiset, Predicate<? super E> predicate) {
            super();
            this.a = (Multiset) Preconditions.checkNotNull(multiset);
            this.b = (Predicate) Preconditions.checkNotNull(predicate);
        }

        /* renamed from: e */
        public UnmodifiableIterator<E> iterator() {
            return Iterators.filter(this.a.iterator(), this.b);
        }

        @Override // com.google.common.collect.h
        Set<E> d() {
            return Sets.filter(this.a.elementSet(), this.b);
        }

        @Override // com.google.common.collect.h
        Iterator<E> a() {
            throw new AssertionError("should never be called");
        }

        @Override // com.google.common.collect.h
        Set<Multiset.Entry<E>> createEntrySet() {
            return Sets.filter(this.a.entrySet(), new Predicate<Multiset.Entry<E>>() { // from class: com.google.common.collect.Multisets.e.1
                /* renamed from: a */
                public boolean apply(Multiset.Entry<E> entry) {
                    return e.this.b.apply(entry.getElement());
                }
            });
        }

        @Override // com.google.common.collect.h
        Iterator<Multiset.Entry<E>> b() {
            throw new AssertionError("should never be called");
        }

        @Override // com.google.common.collect.Multiset
        public int count(@NullableDecl Object obj) {
            int count = this.a.count(obj);
            if (count <= 0 || !this.b.apply(obj)) {
                return 0;
            }
            return count;
        }

        @Override // com.google.common.collect.h, com.google.common.collect.Multiset
        public int add(@NullableDecl E e, int i) {
            Preconditions.checkArgument(this.b.apply(e), "Element %s does not match predicate %s", e, this.b);
            return this.a.add(e, i);
        }

        @Override // com.google.common.collect.h, com.google.common.collect.Multiset
        public int remove(@NullableDecl Object obj, int i) {
            t.a(i, "occurrences");
            if (i == 0) {
                return count(obj);
            }
            if (contains(obj)) {
                return this.a.remove(obj, i);
            }
            return 0;
        }
    }

    public static int a(Iterable<?> iterable) {
        if (iterable instanceof Multiset) {
            return ((Multiset) iterable).elementSet().size();
        }
        return 11;
    }

    @Beta
    public static <E> Multiset<E> union(final Multiset<? extends E> multiset, final Multiset<? extends E> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new i<E>() { // from class: com.google.common.collect.Multisets.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
            public boolean contains(@NullableDecl Object obj) {
                return multiset.contains(obj) || multiset2.contains(obj);
            }

            @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection
            public boolean isEmpty() {
                return multiset.isEmpty() && multiset2.isEmpty();
            }

            @Override // com.google.common.collect.Multiset
            public int count(Object obj) {
                return Math.max(multiset.count(obj), multiset2.count(obj));
            }

            @Override // com.google.common.collect.h
            Set<E> d() {
                return Sets.union(multiset.elementSet(), multiset2.elementSet());
            }

            @Override // com.google.common.collect.h
            Iterator<E> a() {
                throw new AssertionError("should never be called");
            }

            @Override // com.google.common.collect.h
            Iterator<Multiset.Entry<E>> b() {
                final Iterator it = multiset.entrySet().iterator();
                final Iterator it2 = multiset2.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.Multisets.1.1
                    /* renamed from: a */
                    public Multiset.Entry<E> computeNext() {
                        if (it.hasNext()) {
                            Multiset.Entry entry = (Multiset.Entry) it.next();
                            Object element = entry.getElement();
                            return Multisets.immutableEntry(element, Math.max(entry.getCount(), multiset2.count(element)));
                        }
                        while (it2.hasNext()) {
                            Multiset.Entry entry2 = (Multiset.Entry) it2.next();
                            Object element2 = entry2.getElement();
                            if (!multiset.contains(element2)) {
                                return Multisets.immutableEntry(element2, entry2.getCount());
                            }
                        }
                        return (Multiset.Entry) endOfData();
                    }
                };
            }
        };
    }

    public static <E> Multiset<E> intersection(final Multiset<E> multiset, final Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new i<E>() { // from class: com.google.common.collect.Multisets.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.common.collect.Multiset
            public int count(Object obj) {
                int count = multiset.count(obj);
                if (count == 0) {
                    return 0;
                }
                return Math.min(count, multiset2.count(obj));
            }

            @Override // com.google.common.collect.h
            Set<E> d() {
                return Sets.intersection(multiset.elementSet(), multiset2.elementSet());
            }

            @Override // com.google.common.collect.h
            Iterator<E> a() {
                throw new AssertionError("should never be called");
            }

            @Override // com.google.common.collect.h
            Iterator<Multiset.Entry<E>> b() {
                final Iterator it = multiset.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.Multisets.2.1
                    /* renamed from: a */
                    public Multiset.Entry<E> computeNext() {
                        while (it.hasNext()) {
                            Multiset.Entry entry = (Multiset.Entry) it.next();
                            Object element = entry.getElement();
                            int min = Math.min(entry.getCount(), multiset2.count(element));
                            if (min > 0) {
                                return Multisets.immutableEntry(element, min);
                            }
                        }
                        return (Multiset.Entry) endOfData();
                    }
                };
            }
        };
    }

    @Beta
    public static <E> Multiset<E> sum(final Multiset<? extends E> multiset, final Multiset<? extends E> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new i<E>() { // from class: com.google.common.collect.Multisets.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
            public boolean contains(@NullableDecl Object obj) {
                return multiset.contains(obj) || multiset2.contains(obj);
            }

            @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection
            public boolean isEmpty() {
                return multiset.isEmpty() && multiset2.isEmpty();
            }

            @Override // com.google.common.collect.Multisets.i, java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
            public int size() {
                return IntMath.saturatedAdd(multiset.size(), multiset2.size());
            }

            @Override // com.google.common.collect.Multiset
            public int count(Object obj) {
                return multiset.count(obj) + multiset2.count(obj);
            }

            @Override // com.google.common.collect.h
            Set<E> d() {
                return Sets.union(multiset.elementSet(), multiset2.elementSet());
            }

            @Override // com.google.common.collect.h
            Iterator<E> a() {
                throw new AssertionError("should never be called");
            }

            @Override // com.google.common.collect.h
            Iterator<Multiset.Entry<E>> b() {
                final Iterator it = multiset.entrySet().iterator();
                final Iterator it2 = multiset2.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.Multisets.3.1
                    /* renamed from: a */
                    public Multiset.Entry<E> computeNext() {
                        if (it.hasNext()) {
                            Multiset.Entry entry = (Multiset.Entry) it.next();
                            Object element = entry.getElement();
                            return Multisets.immutableEntry(element, entry.getCount() + multiset2.count(element));
                        }
                        while (it2.hasNext()) {
                            Multiset.Entry entry2 = (Multiset.Entry) it2.next();
                            Object element2 = entry2.getElement();
                            if (!multiset.contains(element2)) {
                                return Multisets.immutableEntry(element2, entry2.getCount());
                            }
                        }
                        return (Multiset.Entry) endOfData();
                    }
                };
            }
        };
    }

    @Beta
    public static <E> Multiset<E> difference(final Multiset<E> multiset, final Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new i<E>() { // from class: com.google.common.collect.Multisets.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.common.collect.Multiset
            public int count(@NullableDecl Object obj) {
                int count = multiset.count(obj);
                if (count == 0) {
                    return 0;
                }
                return Math.max(0, count - multiset2.count(obj));
            }

            @Override // com.google.common.collect.Multisets.i, com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection
            public void clear() {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.h
            Iterator<E> a() {
                final Iterator it = multiset.entrySet().iterator();
                return new AbstractIterator<E>() { // from class: com.google.common.collect.Multisets.4.1
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r1v0, types: [E, java.lang.Object] */
                    /* JADX WARN: Unknown variable types count: 1 */
                    @Override // com.google.common.collect.AbstractIterator
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    protected E computeNext() {
                        /*
                            r3 = this;
                        L_0x0000:
                            java.util.Iterator r0 = r2
                            boolean r0 = r0.hasNext()
                            if (r0 == 0) goto L_0x0023
                            java.util.Iterator r0 = r2
                            java.lang.Object r0 = r0.next()
                            com.google.common.collect.Multiset$Entry r0 = (com.google.common.collect.Multiset.Entry) r0
                            java.lang.Object r1 = r0.getElement()
                            int r0 = r0.getCount()
                            com.google.common.collect.Multisets$4 r2 = com.google.common.collect.Multisets.AnonymousClass4.this
                            com.google.common.collect.Multiset r2 = r2
                            int r2 = r2.count(r1)
                            if (r0 <= r2) goto L_0x0000
                            return r1
                        L_0x0023:
                            java.lang.Object r0 = r3.endOfData()
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Multisets.AnonymousClass4.AnonymousClass1.computeNext():java.lang.Object");
                    }
                };
            }

            @Override // com.google.common.collect.h
            Iterator<Multiset.Entry<E>> b() {
                final Iterator it = multiset.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.Multisets.4.2
                    /* renamed from: a */
                    public Multiset.Entry<E> computeNext() {
                        while (it.hasNext()) {
                            Multiset.Entry entry = (Multiset.Entry) it.next();
                            Object element = entry.getElement();
                            int count = entry.getCount() - multiset2.count(element);
                            if (count > 0) {
                                return Multisets.immutableEntry(element, count);
                            }
                        }
                        return (Multiset.Entry) endOfData();
                    }
                };
            }

            @Override // com.google.common.collect.Multisets.i, com.google.common.collect.h
            int c() {
                return Iterators.size(b());
            }
        };
    }

    @CanIgnoreReturnValue
    public static boolean containsOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        for (Multiset.Entry<?> entry : multiset2.entrySet()) {
            if (multiset.count(entry.getElement()) < entry.getCount()) {
                return false;
            }
        }
        return true;
    }

    @CanIgnoreReturnValue
    public static boolean retainOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        return a((Multiset) multiset, multiset2);
    }

    private static <E> boolean a(Multiset<E> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        Iterator<Multiset.Entry<E>> it = multiset.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Multiset.Entry<E> next = it.next();
            int count = multiset2.count(next.getElement());
            if (count == 0) {
                it.remove();
                z = true;
            } else if (count < next.getCount()) {
                multiset.setCount(next.getElement(), count);
                z = true;
            }
        }
        return z;
    }

    @CanIgnoreReturnValue
    public static boolean removeOccurrences(Multiset<?> multiset, Iterable<?> iterable) {
        if (iterable instanceof Multiset) {
            return removeOccurrences(multiset, (Multiset<?>) iterable);
        }
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(iterable);
        boolean z = false;
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            z |= multiset.remove(it.next());
        }
        return z;
    }

    @CanIgnoreReturnValue
    public static boolean removeOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        Iterator<Multiset.Entry<?>> it = multiset.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Multiset.Entry<?> next = it.next();
            int count = multiset2.count(next.getElement());
            if (count >= next.getCount()) {
                it.remove();
                z = true;
            } else if (count > 0) {
                multiset.remove(next.getElement(), count);
                z = true;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class a<E> implements Multiset.Entry<E> {
        @Override // com.google.common.collect.Multiset.Entry
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof Multiset.Entry)) {
                return false;
            }
            Multiset.Entry entry = (Multiset.Entry) obj;
            return getCount() == entry.getCount() && Objects.equal(getElement(), entry.getElement());
        }

        @Override // com.google.common.collect.Multiset.Entry
        public int hashCode() {
            E element = getElement();
            return (element == null ? 0 : element.hashCode()) ^ getCount();
        }

        @Override // com.google.common.collect.Multiset.Entry
        public String toString() {
            String valueOf = String.valueOf(getElement());
            int count = getCount();
            if (count == 1) {
                return valueOf;
            }
            return valueOf + " x " + count;
        }
    }

    public static boolean a(Multiset<?> multiset, @NullableDecl Object obj) {
        if (obj == multiset) {
            return true;
        }
        if (!(obj instanceof Multiset)) {
            return false;
        }
        Multiset multiset2 = (Multiset) obj;
        if (!(multiset.size() == multiset2.size() && multiset.entrySet().size() == multiset2.entrySet().size())) {
            return false;
        }
        for (Multiset.Entry entry : multiset2.entrySet()) {
            if (multiset.count(entry.getElement()) != entry.getCount()) {
                return false;
            }
        }
        return true;
    }

    public static <E> boolean a(Multiset<E> multiset, Collection<? extends E> collection) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            return b(multiset, b(collection));
        }
        if (collection.isEmpty()) {
            return false;
        }
        return Iterators.addAll(multiset, collection.iterator());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <E> boolean b(Multiset<E> multiset, Multiset<? extends E> multiset2) {
        if (multiset2 instanceof e) {
            return a((Multiset) multiset, (e) multiset2);
        }
        if (multiset2.isEmpty()) {
            return false;
        }
        for (Multiset.Entry<? extends E> entry : multiset2.entrySet()) {
            multiset.add(entry.getElement(), entry.getCount());
        }
        return true;
    }

    private static <E> boolean a(Multiset<E> multiset, e<? extends E> eVar) {
        if (eVar.isEmpty()) {
            return false;
        }
        eVar.a((Multiset<? super Object>) multiset);
        return true;
    }

    public static boolean b(Multiset<?> multiset, Collection<?> collection) {
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        return multiset.elementSet().removeAll(collection);
    }

    public static boolean c(Multiset<?> multiset, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        return multiset.elementSet().retainAll(collection);
    }

    public static <E> int a(Multiset<E> multiset, E e2, int i2) {
        t.a(i2, "count");
        int count = multiset.count(e2);
        int i3 = i2 - count;
        if (i3 > 0) {
            multiset.add(e2, i3);
        } else if (i3 < 0) {
            multiset.remove(e2, -i3);
        }
        return count;
    }

    public static <E> boolean a(Multiset<E> multiset, E e2, int i2, int i3) {
        t.a(i2, "oldCount");
        t.a(i3, "newCount");
        if (multiset.count(e2) != i2) {
            return false;
        }
        multiset.setCount(e2, i3);
        return true;
    }

    public static <E> Iterator<E> a(Iterator<Multiset.Entry<E>> it) {
        return new cp<Multiset.Entry<E>, E>(it) { // from class: com.google.common.collect.Multisets.5
            @Override // com.google.common.collect.cp
            /* bridge */ /* synthetic */ Object a(Object obj) {
                return a((Multiset.Entry<Object>) obj);
            }

            /* JADX WARN: Type inference failed for: r1v1, types: [E, java.lang.Object] */
            E a(Multiset.Entry<E> entry) {
                return entry.getElement();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class c<E> extends Sets.f<E> {
        abstract Multiset<E> a();

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public abstract Iterator<E> iterator();

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            a().clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return a().contains(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            return a().containsAll(collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return a().isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return a().remove(obj, Integer.MAX_VALUE) > 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return a().entrySet().size();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class d<E> extends Sets.f<Multiset.Entry<E>> {
        abstract Multiset<E> a();

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Multiset.Entry)) {
                return false;
            }
            Multiset.Entry entry = (Multiset.Entry) obj;
            return entry.getCount() > 0 && a().count(entry.getElement()) == entry.getCount();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (obj instanceof Multiset.Entry) {
                Multiset.Entry entry = (Multiset.Entry) obj;
                E e = (E) entry.getElement();
                int count = entry.getCount();
                if (count != 0) {
                    return a().setCount(e, count, 0);
                }
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            a().clear();
        }
    }

    public static <E> Iterator<E> a(Multiset<E> multiset) {
        return new g(multiset, multiset.entrySet().iterator());
    }

    /* loaded from: classes2.dex */
    public static final class g<E> implements Iterator<E> {
        private final Multiset<E> a;
        private final Iterator<Multiset.Entry<E>> b;
        @MonotonicNonNullDecl
        private Multiset.Entry<E> c;
        private int d;
        private int e;
        private boolean f;

        g(Multiset<E> multiset, Iterator<Multiset.Entry<E>> it) {
            this.a = multiset;
            this.b = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.d > 0 || this.b.hasNext();
        }

        @Override // java.util.Iterator
        public E next() {
            if (hasNext()) {
                if (this.d == 0) {
                    this.c = this.b.next();
                    int count = this.c.getCount();
                    this.d = count;
                    this.e = count;
                }
                this.d--;
                this.f = true;
                return this.c.getElement();
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            t.a(this.f);
            if (this.e == 1) {
                this.b.remove();
            } else {
                this.a.remove(this.c.getElement());
            }
            this.e--;
            this.f = false;
        }
    }

    public static int b(Multiset<?> multiset) {
        long j = 0;
        for (Multiset.Entry<?> entry : multiset.entrySet()) {
            j += entry.getCount();
        }
        return Ints.saturatedCast(j);
    }

    public static <T> Multiset<T> b(Iterable<T> iterable) {
        return (Multiset) iterable;
    }

    @Beta
    public static <E> ImmutableMultiset<E> copyHighestCountFirst(Multiset<E> multiset) {
        Multiset.Entry[] entryArr = (Multiset.Entry[]) multiset.entrySet().toArray(new Multiset.Entry[0]);
        Arrays.sort(entryArr, b.a);
        return ImmutableMultiset.a(Arrays.asList(entryArr));
    }

    /* loaded from: classes2.dex */
    private static final class b implements Comparator<Multiset.Entry<?>> {
        static final b a = new b();

        private b() {
        }

        /* renamed from: a */
        public int compare(Multiset.Entry<?> entry, Multiset.Entry<?> entry2) {
            return entry2.getCount() - entry.getCount();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class i<E> extends h<E> {
        private i() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
        public int size() {
            return Multisets.b((Multiset<?>) this);
        }

        @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            elementSet().clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
        public Iterator<E> iterator() {
            return Multisets.a((Multiset) this);
        }

        @Override // com.google.common.collect.h
        int c() {
            return elementSet().size();
        }
    }
}
