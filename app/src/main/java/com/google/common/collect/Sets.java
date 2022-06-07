package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class Sets {
    private Sets() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class f<E> extends AbstractSet<E> {
        @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            return Sets.a((Set<?>) this, collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            return super.retainAll((Collection) Preconditions.checkNotNull(collection));
        }
    }

    @GwtCompatible(serializable = true)
    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(E e2, E... eArr) {
        return ay.a(EnumSet.of((Enum) e2, (Enum[]) eArr));
    }

    @GwtCompatible(serializable = true)
    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(Iterable<E> iterable) {
        if (iterable instanceof ay) {
            return (ay) iterable;
        }
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            if (collection.isEmpty()) {
                return ImmutableSet.of();
            }
            return ay.a(EnumSet.copyOf(collection));
        }
        Iterator<E> it = iterable.iterator();
        if (!it.hasNext()) {
            return ImmutableSet.of();
        }
        EnumSet of = EnumSet.of((Enum) it.next());
        Iterators.addAll(of, it);
        return ay.a(of);
    }

    public static <E extends Enum<E>> EnumSet<E> newEnumSet(Iterable<E> iterable, Class<E> cls) {
        EnumSet<E> noneOf = EnumSet.noneOf(cls);
        Iterables.addAll(noneOf, iterable);
        return noneOf;
    }

    public static <E> HashSet<E> newHashSet() {
        return new HashSet<>();
    }

    public static <E> HashSet<E> newHashSet(E... eArr) {
        HashSet<E> newHashSetWithExpectedSize = newHashSetWithExpectedSize(eArr.length);
        Collections.addAll(newHashSetWithExpectedSize, eArr);
        return newHashSetWithExpectedSize;
    }

    public static <E> HashSet<E> newHashSet(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new HashSet<>(Collections2.a(iterable));
        }
        return newHashSet(iterable.iterator());
    }

    public static <E> HashSet<E> newHashSet(Iterator<? extends E> it) {
        HashSet<E> newHashSet = newHashSet();
        Iterators.addAll(newHashSet, it);
        return newHashSet;
    }

    public static <E> HashSet<E> newHashSetWithExpectedSize(int i2) {
        return new HashSet<>(Maps.a(i2));
    }

    public static <E> Set<E> newConcurrentHashSet() {
        return Collections.newSetFromMap(new ConcurrentHashMap());
    }

    public static <E> Set<E> newConcurrentHashSet(Iterable<? extends E> iterable) {
        Set<E> newConcurrentHashSet = newConcurrentHashSet();
        Iterables.addAll(newConcurrentHashSet, iterable);
        return newConcurrentHashSet;
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet<>();
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new LinkedHashSet<>(Collections2.a(iterable));
        }
        LinkedHashSet<E> newLinkedHashSet = newLinkedHashSet();
        Iterables.addAll(newLinkedHashSet, iterable);
        return newLinkedHashSet;
    }

    public static <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int i2) {
        return new LinkedHashSet<>(Maps.a(i2));
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet() {
        return new TreeSet<>();
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet(Iterable<? extends E> iterable) {
        TreeSet<E> newTreeSet = newTreeSet();
        Iterables.addAll(newTreeSet, iterable);
        return newTreeSet;
    }

    public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator) {
        return new TreeSet<>((Comparator) Preconditions.checkNotNull(comparator));
    }

    public static <E> Set<E> newIdentityHashSet() {
        return Collections.newSetFromMap(Maps.newIdentityHashMap());
    }

    @GwtIncompatible
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet<>();
    }

    @GwtIncompatible
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(Iterable<? extends E> iterable) {
        Collection collection;
        if (iterable instanceof Collection) {
            collection = Collections2.a(iterable);
        } else {
            collection = Lists.newArrayList(iterable);
        }
        return new CopyOnWriteArraySet<>(collection);
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection) {
        if (collection instanceof EnumSet) {
            return EnumSet.complementOf((EnumSet) collection);
        }
        Preconditions.checkArgument(!collection.isEmpty(), "collection is empty; use the other version of this method");
        return a(collection, collection.iterator().next().getDeclaringClass());
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection, Class<E> cls) {
        Preconditions.checkNotNull(collection);
        if (collection instanceof EnumSet) {
            return EnumSet.complementOf((EnumSet) collection);
        }
        return a(collection, cls);
    }

    private static <E extends Enum<E>> EnumSet<E> a(Collection<E> collection, Class<E> cls) {
        EnumSet<E> allOf = EnumSet.allOf(cls);
        allOf.removeAll(collection);
        return allOf;
    }

    @Deprecated
    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return Collections.newSetFromMap(map);
    }

    /* loaded from: classes2.dex */
    public static abstract class SetView<E> extends AbstractSet<E> {
        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public abstract UnmodifiableIterator<E> iterator();

        private SetView() {
        }

        public ImmutableSet<E> immutableCopy() {
            return ImmutableSet.copyOf((Collection) this);
        }

        @CanIgnoreReturnValue
        public <S extends Set<E>> S copyInto(S s) {
            s.addAll(this);
            return s;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        @CanIgnoreReturnValue
        @Deprecated
        public final boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        @CanIgnoreReturnValue
        @Deprecated
        public final boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        @CanIgnoreReturnValue
        @Deprecated
        public final boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        @CanIgnoreReturnValue
        @Deprecated
        public final boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        @CanIgnoreReturnValue
        @Deprecated
        public final boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        @Deprecated
        public final void clear() {
            throw new UnsupportedOperationException();
        }
    }

    public static <E> SetView<E> union(final Set<? extends E> set, final Set<? extends E> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>() { // from class: com.google.common.collect.Sets.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                int size = set.size();
                for (Object obj : set2) {
                    if (!set.contains(obj)) {
                        size++;
                    }
                }
                return size;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean isEmpty() {
                return set.isEmpty() && set2.isEmpty();
            }

            @Override // com.google.common.collect.Sets.SetView, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<E> iterator() {
                return new AbstractIterator<E>() { // from class: com.google.common.collect.Sets.1.1
                    final Iterator<? extends E> a;
                    final Iterator<? extends E> b;

                    {
                        AnonymousClass1.this = this;
                        this.a = set.iterator();
                        this.b = set2.iterator();
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r0v6, types: [E, java.lang.Object] */
                    /* JADX WARN: Type inference failed for: r0v8, types: [E, java.lang.Object] */
                    /* JADX WARN: Unknown variable types count: 1 */
                    @Override // com.google.common.collect.AbstractIterator
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    protected E computeNext() {
                        /*
                            r2 = this;
                            java.util.Iterator<? extends E> r0 = r2.a
                            boolean r0 = r0.hasNext()
                            if (r0 == 0) goto L_0x000f
                            java.util.Iterator<? extends E> r0 = r2.a
                            java.lang.Object r0 = r0.next()
                            return r0
                        L_0x000f:
                            java.util.Iterator<? extends E> r0 = r2.b
                            boolean r0 = r0.hasNext()
                            if (r0 == 0) goto L_0x0028
                            java.util.Iterator<? extends E> r0 = r2.b
                            java.lang.Object r0 = r0.next()
                            com.google.common.collect.Sets$1 r1 = com.google.common.collect.Sets.AnonymousClass1.this
                            java.util.Set r1 = r1
                            boolean r1 = r1.contains(r0)
                            if (r1 != 0) goto L_0x000f
                            return r0
                        L_0x0028:
                            java.lang.Object r0 = r2.endOfData()
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Sets.AnonymousClass1.C00941.computeNext():java.lang.Object");
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return set.contains(obj) || set2.contains(obj);
            }

            @Override // com.google.common.collect.Sets.SetView
            public <S extends Set<E>> S copyInto(S s) {
                s.addAll(set);
                s.addAll(set2);
                return s;
            }

            @Override // com.google.common.collect.Sets.SetView
            public ImmutableSet<E> immutableCopy() {
                return new ImmutableSet.Builder().addAll((Iterable) set).addAll((Iterable) set2).build();
            }
        };
    }

    public static <E> SetView<E> intersection(final Set<E> set, final Set<?> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>() { // from class: com.google.common.collect.Sets.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.common.collect.Sets.SetView, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<E> iterator() {
                return new AbstractIterator<E>() { // from class: com.google.common.collect.Sets.2.1
                    final Iterator<E> a;

                    {
                        AnonymousClass2.this = this;
                        this.a = set.iterator();
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r0v4, types: [E, java.lang.Object] */
                    /* JADX WARN: Unknown variable types count: 1 */
                    @Override // com.google.common.collect.AbstractIterator
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    protected E computeNext() {
                        /*
                            r2 = this;
                        L_0x0000:
                            java.util.Iterator<E> r0 = r2.a
                            boolean r0 = r0.hasNext()
                            if (r0 == 0) goto L_0x0019
                            java.util.Iterator<E> r0 = r2.a
                            java.lang.Object r0 = r0.next()
                            com.google.common.collect.Sets$2 r1 = com.google.common.collect.Sets.AnonymousClass2.this
                            java.util.Set r1 = r2
                            boolean r1 = r1.contains(r0)
                            if (r1 == 0) goto L_0x0000
                            return r0
                        L_0x0019:
                            java.lang.Object r0 = r2.endOfData()
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Sets.AnonymousClass2.AnonymousClass1.computeNext():java.lang.Object");
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                int i2 = 0;
                for (Object obj : set) {
                    if (set2.contains(obj)) {
                        i2++;
                    }
                }
                return i2;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean isEmpty() {
                return Collections.disjoint(set, set2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return set.contains(obj) && set2.contains(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean containsAll(Collection<?> collection) {
                return set.containsAll(collection) && set2.containsAll(collection);
            }
        };
    }

    public static <E> SetView<E> difference(final Set<E> set, final Set<?> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>() { // from class: com.google.common.collect.Sets.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.common.collect.Sets.SetView, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<E> iterator() {
                return new AbstractIterator<E>() { // from class: com.google.common.collect.Sets.3.1
                    final Iterator<E> a;

                    {
                        AnonymousClass3.this = this;
                        this.a = set.iterator();
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r0v4, types: [E, java.lang.Object] */
                    /* JADX WARN: Unknown variable types count: 1 */
                    @Override // com.google.common.collect.AbstractIterator
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    protected E computeNext() {
                        /*
                            r2 = this;
                        L_0x0000:
                            java.util.Iterator<E> r0 = r2.a
                            boolean r0 = r0.hasNext()
                            if (r0 == 0) goto L_0x0019
                            java.util.Iterator<E> r0 = r2.a
                            java.lang.Object r0 = r0.next()
                            com.google.common.collect.Sets$3 r1 = com.google.common.collect.Sets.AnonymousClass3.this
                            java.util.Set r1 = r2
                            boolean r1 = r1.contains(r0)
                            if (r1 != 0) goto L_0x0000
                            return r0
                        L_0x0019:
                            java.lang.Object r0 = r2.endOfData()
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Sets.AnonymousClass3.AnonymousClass1.computeNext():java.lang.Object");
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                int i2 = 0;
                for (Object obj : set) {
                    if (!set2.contains(obj)) {
                        i2++;
                    }
                }
                return i2;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean isEmpty() {
                return set2.containsAll(set);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return set.contains(obj) && !set2.contains(obj);
            }
        };
    }

    public static <E> SetView<E> symmetricDifference(final Set<? extends E> set, final Set<? extends E> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>() { // from class: com.google.common.collect.Sets.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.common.collect.Sets.SetView, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<E> iterator() {
                final Iterator it = set.iterator();
                final Iterator it2 = set2.iterator();
                return new AbstractIterator<E>() { // from class: com.google.common.collect.Sets.4.1
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r0v6, types: [E, java.lang.Object] */
                    /* JADX WARN: Type inference failed for: r0v8, types: [E, java.lang.Object] */
                    /* JADX WARN: Unknown variable types count: 2 */
                    @Override // com.google.common.collect.AbstractIterator
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public E computeNext() {
                        /*
                            r2 = this;
                        L_0x0000:
                            java.util.Iterator r0 = r2
                            boolean r0 = r0.hasNext()
                            if (r0 == 0) goto L_0x0019
                            java.util.Iterator r0 = r2
                            java.lang.Object r0 = r0.next()
                            com.google.common.collect.Sets$4 r1 = com.google.common.collect.Sets.AnonymousClass4.this
                            java.util.Set r1 = r2
                            boolean r1 = r1.contains(r0)
                            if (r1 != 0) goto L_0x0000
                            return r0
                        L_0x0019:
                            java.util.Iterator r0 = r3
                            boolean r0 = r0.hasNext()
                            if (r0 == 0) goto L_0x0032
                            java.util.Iterator r0 = r3
                            java.lang.Object r0 = r0.next()
                            com.google.common.collect.Sets$4 r1 = com.google.common.collect.Sets.AnonymousClass4.this
                            java.util.Set r1 = r1
                            boolean r1 = r1.contains(r0)
                            if (r1 != 0) goto L_0x0019
                            return r0
                        L_0x0032:
                            java.lang.Object r0 = r2.endOfData()
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Sets.AnonymousClass4.AnonymousClass1.computeNext():java.lang.Object");
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                int i2 = 0;
                for (Object obj : set) {
                    if (!set2.contains(obj)) {
                        i2++;
                    }
                }
                for (Object obj2 : set2) {
                    if (!set.contains(obj2)) {
                        i2++;
                    }
                }
                return i2;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean isEmpty() {
                return set.equals(set2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return set2.contains(obj) ^ set.contains(obj);
            }
        };
    }

    public static <E> Set<E> filter(Set<E> set, Predicate<? super E> predicate) {
        if (set instanceof SortedSet) {
            return filter((SortedSet) set, (Predicate) predicate);
        }
        if (!(set instanceof d)) {
            return new d((Set) Preconditions.checkNotNull(set), (Predicate) Preconditions.checkNotNull(predicate));
        }
        d dVar = (d) set;
        return new d((Set) dVar.a, Predicates.and(dVar.b, predicate));
    }

    public static <E> SortedSet<E> filter(SortedSet<E> sortedSet, Predicate<? super E> predicate) {
        if (!(sortedSet instanceof d)) {
            return new e((SortedSet) Preconditions.checkNotNull(sortedSet), (Predicate) Preconditions.checkNotNull(predicate));
        }
        d dVar = (d) sortedSet;
        return new e((SortedSet) dVar.a, Predicates.and(dVar.b, predicate));
    }

    @GwtIncompatible
    public static <E> NavigableSet<E> filter(NavigableSet<E> navigableSet, Predicate<? super E> predicate) {
        if (!(navigableSet instanceof d)) {
            return new c((NavigableSet) Preconditions.checkNotNull(navigableSet), (Predicate) Preconditions.checkNotNull(predicate));
        }
        d dVar = (d) navigableSet;
        return new c((NavigableSet) dVar.a, Predicates.and(dVar.b, predicate));
    }

    /* loaded from: classes2.dex */
    public static class d<E> extends Collections2.a<E> implements Set<E> {
        d(Set<E> set, Predicate<? super E> predicate) {
            super(set, predicate);
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(@NullableDecl Object obj) {
            return Sets.a(this, obj);
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            return Sets.a(this);
        }
    }

    /* loaded from: classes2.dex */
    public static class e<E> extends d<E> implements SortedSet<E> {
        e(SortedSet<E> sortedSet, Predicate<? super E> predicate) {
            super(sortedSet, predicate);
        }

        @Override // java.util.SortedSet
        public Comparator<? super E> comparator() {
            return ((SortedSet) this.a).comparator();
        }

        @Override // java.util.SortedSet
        public SortedSet<E> subSet(E e, E e2) {
            return new e(((SortedSet) this.a).subSet(e, e2), this.b);
        }

        @Override // java.util.SortedSet
        public SortedSet<E> headSet(E e) {
            return new e(((SortedSet) this.a).headSet(e), this.b);
        }

        @Override // java.util.SortedSet
        public SortedSet<E> tailSet(E e) {
            return new e(((SortedSet) this.a).tailSet(e), this.b);
        }

        @Override // java.util.SortedSet
        public E first() {
            return (E) Iterators.find(this.a.iterator(), this.b);
        }

        public E last() {
            SortedSet sortedSet = (SortedSet) this.a;
            while (true) {
                E e = (E) sortedSet.last();
                if (this.b.apply(e)) {
                    return e;
                }
                sortedSet = sortedSet.headSet(e);
            }
        }
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static class c<E> extends e<E> implements NavigableSet<E> {
        c(NavigableSet<E> navigableSet, Predicate<? super E> predicate) {
            super(navigableSet, predicate);
        }

        NavigableSet<E> a() {
            return (NavigableSet) this.a;
        }

        @Override // java.util.NavigableSet
        @NullableDecl
        public E lower(E e) {
            return (E) Iterators.find(a().headSet(e, false).descendingIterator(), this.b, null);
        }

        @Override // java.util.NavigableSet
        @NullableDecl
        public E floor(E e) {
            return (E) Iterators.find(a().headSet(e, true).descendingIterator(), this.b, null);
        }

        @Override // java.util.NavigableSet
        public E ceiling(E e) {
            return (E) Iterables.find(a().tailSet(e, true), this.b, null);
        }

        @Override // java.util.NavigableSet
        public E higher(E e) {
            return (E) Iterables.find(a().tailSet(e, false), this.b, null);
        }

        @Override // java.util.NavigableSet
        public E pollFirst() {
            return (E) Iterables.a(a(), this.b);
        }

        @Override // java.util.NavigableSet
        public E pollLast() {
            return (E) Iterables.a(a().descendingSet(), this.b);
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> descendingSet() {
            return Sets.filter((NavigableSet) a().descendingSet(), this.b);
        }

        @Override // java.util.NavigableSet
        public Iterator<E> descendingIterator() {
            return Iterators.filter(a().descendingIterator(), this.b);
        }

        @Override // com.google.common.collect.Sets.e, java.util.SortedSet
        public E last() {
            return (E) Iterators.find(a().descendingIterator(), this.b);
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            return Sets.filter((NavigableSet) a().subSet(e, z, e2, z2), this.b);
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> headSet(E e, boolean z) {
            return Sets.filter((NavigableSet) a().headSet(e, z), this.b);
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> tailSet(E e, boolean z) {
            return Sets.filter((NavigableSet) a().tailSet(e, z), this.b);
        }
    }

    public static <B> Set<List<B>> cartesianProduct(List<? extends Set<? extends B>> list) {
        return a.a(list);
    }

    @SafeVarargs
    public static <B> Set<List<B>> cartesianProduct(Set<? extends B>... setArr) {
        return cartesianProduct(Arrays.asList(setArr));
    }

    /* loaded from: classes2.dex */
    public static final class a<E> extends ForwardingCollection<List<E>> implements Set<List<E>> {
        private final transient ImmutableList<ImmutableSet<E>> a;
        private final transient s<E> b;

        static <E> Set<List<E>> a(List<? extends Set<? extends E>> list) {
            ImmutableList.Builder builder = new ImmutableList.Builder(list.size());
            Iterator<? extends Set<? extends E>> it = list.iterator();
            while (it.hasNext()) {
                ImmutableSet copyOf = ImmutableSet.copyOf((Collection) ((Set) it.next()));
                if (copyOf.isEmpty()) {
                    return ImmutableSet.of();
                }
                builder.add((ImmutableList.Builder) copyOf);
            }
            final ImmutableList<E> build = builder.build();
            return new a(build, new s(new ImmutableList<List<E>>() { // from class: com.google.common.collect.Sets.a.1
                @Override // com.google.common.collect.ImmutableCollection
                public boolean a() {
                    return true;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return build.size();
                }

                /* renamed from: a */
                public List<E> get(int i) {
                    return ((ImmutableSet) build.get(i)).asList();
                }
            }));
        }

        private a(ImmutableList<ImmutableSet<E>> immutableList, s<E> sVar) {
            this.a = immutableList;
            this.b = sVar;
        }

        @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Collection<List<E>> delegate() {
            return this.b;
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof a) {
                return this.a.equals(((a) obj).a);
            }
            return super.equals(obj);
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            int i = 1;
            int size = size() - 1;
            for (int i2 = 0; i2 < this.a.size(); i2++) {
                size = ~(~(size * 31));
            }
            UnmodifiableIterator<ImmutableSet<E>> it = this.a.iterator();
            while (it.hasNext()) {
                ImmutableSet<E> next = it.next();
                i = ~(~((i * 31) + ((size() / next.size()) * next.hashCode())));
            }
            return ~(~(i + size));
        }
    }

    @GwtCompatible(serializable = false)
    public static <E> Set<Set<E>> powerSet(Set<E> set) {
        return new g(set);
    }

    /* loaded from: classes2.dex */
    public static final class h<E> extends AbstractSet<E> {
        private final ImmutableMap<E, Integer> a;
        private final int b;

        h(ImmutableMap<E, Integer> immutableMap, int i) {
            this.a = immutableMap;
            this.b = i;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<E> iterator() {
            return new UnmodifiableIterator<E>() { // from class: com.google.common.collect.Sets.h.1
                final ImmutableList<E> a;
                int b;

                {
                    h.this = this;
                    this.a = h.this.a.keySet().asList();
                    this.b = h.this.b;
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.b != 0;
                }

                @Override // java.util.Iterator
                public E next() {
                    int numberOfTrailingZeros = Integer.numberOfTrailingZeros(this.b);
                    if (numberOfTrailingZeros != 32) {
                        this.b &= ~(1 << numberOfTrailingZeros);
                        return this.a.get(numberOfTrailingZeros);
                    }
                    throw new NoSuchElementException();
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Integer.bitCount(this.b);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            Integer num = this.a.get(obj);
            if (num != null) {
                if (((1 << num.intValue()) & this.b) != 0) {
                    return true;
                }
            }
            return false;
        }
    }

    /* loaded from: classes2.dex */
    public static final class g<E> extends AbstractSet<Set<E>> {
        final ImmutableMap<E, Integer> a;

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return false;
        }

        g(Set<E> set) {
            this.a = Maps.a((Collection) set);
            Preconditions.checkArgument(this.a.size() <= 30, "Too many elements to create power set: %s > 30", this.a.size());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return 1 << this.a.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Set<E>> iterator() {
            return new b<Set<E>>(size()) { // from class: com.google.common.collect.Sets.g.1
                /* renamed from: b */
                public Set<E> a(int i) {
                    return new h(g.this.a, i);
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Set)) {
                return false;
            }
            return this.a.keySet().containsAll((Set) obj);
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof g) {
                return this.a.equals(((g) obj).a);
            }
            return super.equals(obj);
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        public int hashCode() {
            return this.a.keySet().hashCode() << (this.a.size() - 1);
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            return "powerSet(" + this.a + ")";
        }
    }

    @Beta
    public static <E> Set<Set<E>> combinations(Set<E> set, int i2) {
        ImmutableMap a2 = Maps.a((Collection) set);
        t.a(i2, "size");
        Preconditions.checkArgument(i2 <= a2.size(), "size (%s) must be <= set.size() (%s)", i2, a2.size());
        if (i2 == 0) {
            return ImmutableSet.of(ImmutableSet.of());
        }
        if (i2 == a2.size()) {
            return ImmutableSet.of(a2.keySet());
        }
        return new AnonymousClass5(i2, a2);
    }

    /* renamed from: com.google.common.collect.Sets$5 */
    /* loaded from: classes2.dex */
    public static class AnonymousClass5 extends AbstractSet<Set<E>> {
        final /* synthetic */ int a;
        final /* synthetic */ ImmutableMap b;

        AnonymousClass5(int i, ImmutableMap immutableMap) {
            this.a = i;
            this.b = immutableMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Set)) {
                return false;
            }
            Set set = (Set) obj;
            return set.size() == this.a && this.b.keySet().containsAll(set);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: com.google.common.collect.Sets$5$1 */
        /* loaded from: classes2.dex */
        public class AnonymousClass1 extends AbstractIterator<Set<E>> {
            final BitSet a;

            AnonymousClass1() {
                AnonymousClass5.this = r2;
                this.a = new BitSet(AnonymousClass5.this.b.size());
            }

            /* renamed from: a */
            public Set<E> computeNext() {
                if (this.a.isEmpty()) {
                    this.a.set(0, AnonymousClass5.this.a);
                } else {
                    int nextSetBit = this.a.nextSetBit(0);
                    int nextClearBit = this.a.nextClearBit(nextSetBit);
                    if (nextClearBit == AnonymousClass5.this.b.size()) {
                        return (Set) endOfData();
                    }
                    int i = (nextClearBit - nextSetBit) - 1;
                    this.a.set(0, i);
                    this.a.clear(i, nextClearBit);
                    this.a.set(nextClearBit);
                }
                final BitSet bitSet = (BitSet) this.a.clone();
                return new AbstractSet<E>() { // from class: com.google.common.collect.Sets.5.1.1
                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                    public boolean contains(@NullableDecl Object obj) {
                        Integer num = (Integer) AnonymousClass5.this.b.get(obj);
                        return num != null && bitSet.get(num.intValue());
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                    public Iterator<E> iterator() {
                        return new AbstractIterator<E>() { // from class: com.google.common.collect.Sets.5.1.1.1
                            int a = -1;

                            @Override // com.google.common.collect.AbstractIterator
                            protected E computeNext() {
                                this.a = bitSet.nextSetBit(this.a + 1);
                                if (this.a == -1) {
                                    return endOfData();
                                }
                                return AnonymousClass5.this.b.keySet().asList().get(this.a);
                            }
                        };
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                    public int size() {
                        return AnonymousClass5.this.a;
                    }
                };
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Set<E>> iterator() {
            return new AnonymousClass1();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return IntMath.binomial(this.b.size(), this.a);
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            return "Sets.combinations(" + this.b.keySet() + ", " + this.a + ")";
        }
    }

    public static int a(Set<?> set) {
        Iterator<?> it = set.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Object next = it.next();
            i2 = ~(~(i2 + (next != null ? next.hashCode() : 0)));
        }
        return i2;
    }

    public static boolean a(Set<?> set, @NullableDecl Object obj) {
        if (set == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set2 = (Set) obj;
        try {
            if (set.size() == set2.size()) {
                if (set.containsAll(set2)) {
                    return true;
                }
            }
            return false;
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    public static <E> NavigableSet<E> unmodifiableNavigableSet(NavigableSet<E> navigableSet) {
        return ((navigableSet instanceof ImmutableCollection) || (navigableSet instanceof i)) ? navigableSet : new i(navigableSet);
    }

    /* loaded from: classes2.dex */
    public static final class i<E> extends ForwardingSortedSet<E> implements Serializable, NavigableSet<E> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        private transient i<E> a;
        private final NavigableSet<E> delegate;
        private final SortedSet<E> unmodifiableDelegate;

        i(NavigableSet<E> navigableSet) {
            this.delegate = (NavigableSet) Preconditions.checkNotNull(navigableSet);
            this.unmodifiableDelegate = Collections.unmodifiableSortedSet(navigableSet);
        }

        @Override // com.google.common.collect.ForwardingSortedSet, com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public SortedSet<E> delegate() {
            return this.unmodifiableDelegate;
        }

        @Override // java.util.NavigableSet
        public E lower(E e) {
            return this.delegate.lower(e);
        }

        @Override // java.util.NavigableSet
        public E floor(E e) {
            return this.delegate.floor(e);
        }

        @Override // java.util.NavigableSet
        public E ceiling(E e) {
            return this.delegate.ceiling(e);
        }

        @Override // java.util.NavigableSet
        public E higher(E e) {
            return this.delegate.higher(e);
        }

        @Override // java.util.NavigableSet
        public E pollFirst() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.NavigableSet
        public E pollLast() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> descendingSet() {
            i<E> iVar = this.a;
            if (iVar != null) {
                return iVar;
            }
            i<E> iVar2 = new i<>(this.delegate.descendingSet());
            this.a = iVar2;
            iVar2.a = this;
            return iVar2;
        }

        @Override // java.util.NavigableSet
        public Iterator<E> descendingIterator() {
            return Iterators.unmodifiableIterator(this.delegate.descendingIterator());
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            return Sets.unmodifiableNavigableSet(this.delegate.subSet(e, z, e2, z2));
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> headSet(E e, boolean z) {
            return Sets.unmodifiableNavigableSet(this.delegate.headSet(e, z));
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> tailSet(E e, boolean z) {
            return Sets.unmodifiableNavigableSet(this.delegate.tailSet(e, z));
        }
    }

    @GwtIncompatible
    public static <E> NavigableSet<E> synchronizedNavigableSet(NavigableSet<E> navigableSet) {
        return cn.a(navigableSet);
    }

    public static boolean a(Set<?> set, Iterator<?> it) {
        boolean z = false;
        while (it.hasNext()) {
            z |= set.remove(it.next());
        }
        return z;
    }

    public static boolean a(Set<?> set, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        if (!(collection instanceof Set) || collection.size() <= set.size()) {
            return a(set, collection.iterator());
        }
        return Iterators.removeAll(set.iterator(), collection);
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    static class b<E> extends ForwardingNavigableSet<E> {
        private final NavigableSet<E> a;

        public b(NavigableSet<E> navigableSet) {
            this.a = navigableSet;
        }

        @Override // com.google.common.collect.ForwardingNavigableSet, com.google.common.collect.ForwardingSortedSet, com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public NavigableSet<E> delegate() {
            return this.a;
        }

        @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
        public E lower(E e) {
            return this.a.higher(e);
        }

        @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
        public E floor(E e) {
            return this.a.ceiling(e);
        }

        @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
        public E ceiling(E e) {
            return this.a.floor(e);
        }

        @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
        public E higher(E e) {
            return this.a.lower(e);
        }

        @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
        public E pollFirst() {
            return this.a.pollLast();
        }

        @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
        public E pollLast() {
            return this.a.pollFirst();
        }

        @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
        public NavigableSet<E> descendingSet() {
            return this.a;
        }

        @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
        public Iterator<E> descendingIterator() {
            return this.a.iterator();
        }

        @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            return this.a.subSet(e2, z2, e, z).descendingSet();
        }

        @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
        public SortedSet<E> subSet(E e, E e2) {
            return standardSubSet(e, e2);
        }

        @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
        public NavigableSet<E> headSet(E e, boolean z) {
            return this.a.tailSet(e, z).descendingSet();
        }

        @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
        public SortedSet<E> headSet(E e) {
            return standardHeadSet(e);
        }

        @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
        public NavigableSet<E> tailSet(E e, boolean z) {
            return this.a.headSet(e, z).descendingSet();
        }

        @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
        public SortedSet<E> tailSet(E e) {
            return standardTailSet(e);
        }

        @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
        public Comparator<? super E> comparator() {
            Comparator<? super E> comparator = this.a.comparator();
            if (comparator == null) {
                return Ordering.natural().reverse();
            }
            return a(comparator);
        }

        private static <T> Ordering<T> a(Comparator<T> comparator) {
            return Ordering.from(comparator).reverse();
        }

        @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
        public E first() {
            return this.a.last();
        }

        @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
        public E last() {
            return this.a.first();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable
        public Iterator<E> iterator() {
            return this.a.descendingIterator();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public Object[] toArray() {
            return standardToArray();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) standardToArray(tArr);
        }

        @Override // com.google.common.collect.ForwardingObject
        public String toString() {
            return standardToString();
        }
    }

    @Beta
    @GwtIncompatible
    public static <K extends Comparable<? super K>> NavigableSet<K> subSet(NavigableSet<K> navigableSet, Range<K> range) {
        boolean z = true;
        if (navigableSet.comparator() != null && navigableSet.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
            Preconditions.checkArgument(navigableSet.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0, "set is using a custom comparator which is inconsistent with the natural ordering.");
        }
        if (range.hasLowerBound() && range.hasUpperBound()) {
            K lowerEndpoint = range.lowerEndpoint();
            boolean z2 = range.lowerBoundType() == BoundType.CLOSED;
            K upperEndpoint = range.upperEndpoint();
            if (range.upperBoundType() != BoundType.CLOSED) {
                z = false;
            }
            return navigableSet.subSet(lowerEndpoint, z2, upperEndpoint, z);
        } else if (range.hasLowerBound()) {
            K lowerEndpoint2 = range.lowerEndpoint();
            if (range.lowerBoundType() != BoundType.CLOSED) {
                z = false;
            }
            return navigableSet.tailSet(lowerEndpoint2, z);
        } else if (!range.hasUpperBound()) {
            return (NavigableSet) Preconditions.checkNotNull(navigableSet);
        } else {
            K upperEndpoint2 = range.upperEndpoint();
            if (range.upperBoundType() != BoundType.CLOSED) {
                z = false;
            }
            return navigableSet.headSet(upperEndpoint2, z);
        }
    }
}
