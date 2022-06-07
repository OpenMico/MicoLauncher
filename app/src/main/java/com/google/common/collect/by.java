package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RegularImmutableSortedSet.java */
@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public final class by<E> extends ImmutableSortedSet<E> {
    static final by<Comparable> c = new by<>(ImmutableList.of(), Ordering.natural());
    @VisibleForTesting
    final transient ImmutableList<E> d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public by(ImmutableList<E> immutableList, Comparator<? super E> comparator) {
        super(comparator);
        this.d = immutableList;
    }

    @Override // com.google.common.collect.ImmutableSortedSet, com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<E> iterator() {
        return this.d.iterator();
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    @GwtIncompatible
    public UnmodifiableIterator<E> descendingIterator() {
        return this.d.reverse().iterator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.d.size();
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    public boolean contains(@NullableDecl Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return b(obj) >= 0;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        if (!cg.a(comparator(), collection) || collection.size() <= 1) {
            return super.containsAll(collection);
        }
        UnmodifiableIterator<E> it = iterator();
        Iterator<?> it2 = collection.iterator();
        if (!it.hasNext()) {
            return false;
        }
        Object next = it2.next();
        E next2 = it.next();
        while (true) {
            try {
                int a = a(next2, next);
                if (a < 0) {
                    if (!it.hasNext()) {
                        return false;
                    }
                    next2 = it.next();
                } else if (a == 0) {
                    if (!it2.hasNext()) {
                        return true;
                    }
                    next = it2.next();
                } else if (a > 0) {
                    return false;
                }
            } catch (ClassCastException | NullPointerException unused) {
                return false;
            }
        }
    }

    private int b(Object obj) throws ClassCastException {
        return Collections.binarySearch(this.d, obj, c());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean a() {
        return this.d.a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public int a(Object[] objArr, int i) {
        return this.d.a(objArr, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0034 A[Catch: ClassCastException -> 0x0047, NoSuchElementException -> 0x0046, TryCatch #2 {ClassCastException -> 0x0047, NoSuchElementException -> 0x0046, blocks: (B:17:0x002a, B:18:0x002e, B:20:0x0034, B:22:0x003e), top: B:30:0x002a }] */
    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean equals(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r6) {
        /*
            r5 = this;
            r0 = 1
            if (r6 != r5) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r6 instanceof java.util.Set
            r2 = 0
            if (r1 != 0) goto L_0x000a
            return r2
        L_0x000a:
            java.util.Set r6 = (java.util.Set) r6
            int r1 = r5.size()
            int r3 = r6.size()
            if (r1 == r3) goto L_0x0017
            return r2
        L_0x0017:
            boolean r1 = r5.isEmpty()
            if (r1 == 0) goto L_0x001e
            return r0
        L_0x001e:
            java.util.Comparator r1 = r5.a
            boolean r1 = com.google.common.collect.cg.a(r1, r6)
            if (r1 == 0) goto L_0x0048
            java.util.Iterator r6 = r6.iterator()
            com.google.common.collect.UnmodifiableIterator r1 = r5.iterator()     // Catch: ClassCastException -> 0x0047, NoSuchElementException -> 0x0046
        L_0x002e:
            boolean r3 = r1.hasNext()     // Catch: ClassCastException -> 0x0047, NoSuchElementException -> 0x0046
            if (r3 == 0) goto L_0x0045
            java.lang.Object r3 = r1.next()     // Catch: ClassCastException -> 0x0047, NoSuchElementException -> 0x0046
            java.lang.Object r4 = r6.next()     // Catch: ClassCastException -> 0x0047, NoSuchElementException -> 0x0046
            if (r4 == 0) goto L_0x0044
            int r3 = r5.a(r3, r4)     // Catch: ClassCastException -> 0x0047, NoSuchElementException -> 0x0046
            if (r3 == 0) goto L_0x002e
        L_0x0044:
            return r2
        L_0x0045:
            return r0
        L_0x0046:
            return r2
        L_0x0047:
            return r2
        L_0x0048:
            boolean r6 = r5.containsAll(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.by.equals(java.lang.Object):boolean");
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.SortedSet
    public E first() {
        if (!isEmpty()) {
            return this.d.get(0);
        }
        throw new NoSuchElementException();
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.SortedSet
    public E last() {
        if (!isEmpty()) {
            return this.d.get(size() - 1);
        }
        throw new NoSuchElementException();
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public E lower(E e) {
        int c2 = c(e, false) - 1;
        if (c2 == -1) {
            return null;
        }
        return this.d.get(c2);
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public E floor(E e) {
        int c2 = c(e, true) - 1;
        if (c2 == -1) {
            return null;
        }
        return this.d.get(c2);
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public E ceiling(E e) {
        int d = d(e, true);
        if (d == size()) {
            return null;
        }
        return this.d.get(d);
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public E higher(E e) {
        int d = d(e, false);
        if (d == size()) {
            return null;
        }
        return this.d.get(d);
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    ImmutableSortedSet<E> b(E e, boolean z) {
        return b(0, c(e, z));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int c(E e, boolean z) {
        int binarySearch = Collections.binarySearch(this.d, Preconditions.checkNotNull(e), comparator());
        return binarySearch >= 0 ? z ? binarySearch + 1 : binarySearch : ~binarySearch;
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    ImmutableSortedSet<E> a(E e, boolean z, E e2, boolean z2) {
        return a((by<E>) e, z).b((ImmutableSortedSet<E>) e2, z2);
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    ImmutableSortedSet<E> a(E e, boolean z) {
        return b(d(e, z), size());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int d(E e, boolean z) {
        int binarySearch = Collections.binarySearch(this.d, Preconditions.checkNotNull(e), comparator());
        return binarySearch >= 0 ? z ? binarySearch : binarySearch + 1 : ~binarySearch;
    }

    Comparator<Object> c() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public by<E> b(int i, int i2) {
        if (i == 0 && i2 == size()) {
            return this;
        }
        if (i < i2) {
            return new by<>(this.d.subList(i, i2), this.a);
        }
        return a(this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableSortedSet
    public int a(@NullableDecl Object obj) {
        if (obj == null) {
            return -1;
        }
        try {
            int binarySearch = Collections.binarySearch(this.d, obj, c());
            if (binarySearch >= 0) {
                return binarySearch;
            }
            return -1;
        } catch (ClassCastException unused) {
            return -1;
        }
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
    public ImmutableList<E> asList() {
        return this.d;
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    ImmutableSortedSet<E> b() {
        Comparator reverseOrder = Collections.reverseOrder(this.a);
        if (isEmpty()) {
            return a(reverseOrder);
        }
        return new by(this.d.reverse(), reverseOrder);
    }
}
