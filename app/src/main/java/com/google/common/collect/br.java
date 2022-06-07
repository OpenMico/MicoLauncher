package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RegularContiguousSet.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class br<C extends Comparable> extends ContiguousSet<C> {
    private static final long serialVersionUID = 0;
    private final Range<C> range;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean a() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ContiguousSet, com.google.common.collect.ImmutableSortedSet
    /* synthetic */ ImmutableSortedSet a(Object obj, boolean z) {
        return b((br<C>) ((Comparable) obj), z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ContiguousSet, com.google.common.collect.ImmutableSortedSet
    /* bridge */ /* synthetic */ ImmutableSortedSet a(Object obj, boolean z, Object obj2, boolean z2) {
        return a((boolean) ((Comparable) obj), z, (boolean) ((Comparable) obj2), z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ContiguousSet, com.google.common.collect.ImmutableSortedSet
    /* synthetic */ ImmutableSortedSet b(Object obj, boolean z) {
        return a((br<C>) ((Comparable) obj), z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public br(Range<C> range, DiscreteDomain<C> discreteDomain) {
        super(discreteDomain);
        this.range = range;
    }

    private ContiguousSet<C> a(Range<C> range) {
        return this.range.isConnected(range) ? ContiguousSet.create(this.range.intersection(range), this.domain) : new ag(this.domain);
    }

    @Override // com.google.common.collect.ContiguousSet
    ContiguousSet<C> a(C c, boolean z) {
        return a((Range) Range.upTo(c, BoundType.a(z)));
    }

    @Override // com.google.common.collect.ContiguousSet
    ContiguousSet<C> a(C c, boolean z, C c2, boolean z2) {
        if (c.compareTo(c2) != 0 || z || z2) {
            return a((Range) Range.range(c, BoundType.a(z), c2, BoundType.a(z2)));
        }
        return new ag(this.domain);
    }

    @Override // com.google.common.collect.ContiguousSet
    ContiguousSet<C> b(C c, boolean z) {
        return a((Range) Range.downTo(c, BoundType.a(z)));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet
    @GwtIncompatible
    public int a(Object obj) {
        if (contains(obj)) {
            return (int) this.domain.distance(first(), (Comparable) obj);
        }
        return -1;
    }

    @Override // com.google.common.collect.ImmutableSortedSet, com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<C> iterator() {
        return new AbstractSequentialIterator<C>(first()) { // from class: com.google.common.collect.br.1
            final Comparable a;

            {
                this.a = br.this.last();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public C computeNext(C c) {
                if (br.b(c, this.a)) {
                    return null;
                }
                return (C) br.this.domain.next(c);
            }
        };
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    @GwtIncompatible
    public UnmodifiableIterator<C> descendingIterator() {
        return new AbstractSequentialIterator<C>(last()) { // from class: com.google.common.collect.br.2
            final Comparable a;

            {
                this.a = br.this.first();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public C computeNext(C c) {
                if (br.b(c, this.a)) {
                    return null;
                }
                return (C) br.this.domain.previous(c);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(Comparable<?> comparable, @NullableDecl Comparable<?> comparable2) {
        return comparable2 != null && Range.a(comparable, comparable2) == 0;
    }

    /* renamed from: c */
    public C first() {
        return this.range.lowerBound.a(this.domain);
    }

    /* renamed from: d */
    public C last() {
        return this.range.upperBound.b(this.domain);
    }

    @Override // com.google.common.collect.ImmutableSet
    ImmutableList<C> f() {
        if (this.domain.supportsFastOffset) {
            return new av<C>() { // from class: com.google.common.collect.br.3
                /* JADX INFO: Access modifiers changed from: package-private */
                /* renamed from: c */
                public ImmutableSortedSet<C> b() {
                    return br.this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* renamed from: a */
                public C get(int i) {
                    Preconditions.checkElementIndex(i, size());
                    return (C) br.this.domain.a(br.this.first(), i);
                }
            };
        }
        return super.f();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        long distance = this.domain.distance(first(), last());
        if (distance >= 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return ((int) distance) + 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    public boolean contains(@NullableDecl Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return this.range.contains((Comparable) obj);
        } catch (ClassCastException unused) {
            return false;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean containsAll(Collection<?> collection) {
        return Collections2.a((Collection<?>) this, collection);
    }

    @Override // com.google.common.collect.ContiguousSet
    public ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet) {
        Preconditions.checkNotNull(contiguousSet);
        Preconditions.checkArgument(this.domain.equals(contiguousSet.domain));
        if (contiguousSet.isEmpty()) {
            return contiguousSet;
        }
        Comparable comparable = (Comparable) Ordering.natural().max(first(), contiguousSet.first());
        Comparable comparable2 = (Comparable) Ordering.natural().min(last(), contiguousSet.last());
        return comparable.compareTo(comparable2) <= 0 ? ContiguousSet.create(Range.closed(comparable, comparable2), this.domain) : new ag(this.domain);
    }

    @Override // com.google.common.collect.ContiguousSet
    public Range<C> range() {
        return range(BoundType.CLOSED, BoundType.CLOSED);
    }

    @Override // com.google.common.collect.ContiguousSet
    public Range<C> range(BoundType boundType, BoundType boundType2) {
        return Range.a((ab) this.range.lowerBound.a(boundType, this.domain), (ab) this.range.upperBound.b(boundType2, this.domain));
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof br) {
            br brVar = (br) obj;
            if (this.domain.equals(brVar.domain)) {
                return first().equals(brVar.first()) && last().equals(brVar.last());
            }
        }
        return super.equals(obj);
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return Sets.a(this);
    }

    /* compiled from: RegularContiguousSet.java */
    @GwtIncompatible
    /* loaded from: classes2.dex */
    private static final class a<C extends Comparable> implements Serializable {
        final DiscreteDomain<C> domain;
        final Range<C> range;

        private a(Range<C> range, DiscreteDomain<C> discreteDomain) {
            this.range = range;
            this.domain = discreteDomain;
        }

        private Object readResolve() {
            return new br(this.range, this.domain);
        }
    }

    @Override // com.google.common.collect.ImmutableSortedSet, com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    Object writeReplace() {
        return new a(this.range, this.domain);
    }
}
