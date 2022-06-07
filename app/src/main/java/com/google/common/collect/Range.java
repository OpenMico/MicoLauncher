package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public final class Range<C extends Comparable> extends bq implements Predicate<C>, Serializable {
    private static final Range<Comparable> a = new Range<>(ab.d(), ab.e());
    private static final long serialVersionUID = 0;
    final ab<C> lowerBound;
    final ab<C> upperBound;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.base.Predicate
    @Deprecated
    public /* bridge */ /* synthetic */ boolean apply(Object obj) {
        return apply((Range<C>) ((Comparable) obj));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class a implements Function<Range, ab> {
        static final a a = new a();

        a() {
        }

        /* renamed from: a */
        public ab apply(Range range) {
            return range.lowerBound;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class c implements Function<Range, ab> {
        static final c a = new c();

        c() {
        }

        /* renamed from: a */
        public ab apply(Range range) {
            return range.upperBound;
        }
    }

    public static <C extends Comparable<?>> Function<Range<C>, ab<C>> a() {
        return a.a;
    }

    public static <C extends Comparable<?>> Function<Range<C>, ab<C>> b() {
        return c.a;
    }

    public static <C extends Comparable<?>> Ordering<Range<C>> c() {
        return (Ordering<Range<C>>) b.a;
    }

    public static <C extends Comparable<?>> Range<C> a(ab<C> abVar, ab<C> abVar2) {
        return new Range<>(abVar, abVar2);
    }

    public static <C extends Comparable<?>> Range<C> open(C c2, C c3) {
        return a(ab.c(c2), ab.b(c3));
    }

    public static <C extends Comparable<?>> Range<C> closed(C c2, C c3) {
        return a(ab.b(c2), ab.c(c3));
    }

    public static <C extends Comparable<?>> Range<C> closedOpen(C c2, C c3) {
        return a(ab.b(c2), ab.b(c3));
    }

    public static <C extends Comparable<?>> Range<C> openClosed(C c2, C c3) {
        return a(ab.c(c2), ab.c(c3));
    }

    public static <C extends Comparable<?>> Range<C> range(C c2, BoundType boundType, C c3, BoundType boundType2) {
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        return a(boundType == BoundType.OPEN ? ab.c(c2) : ab.b(c2), boundType2 == BoundType.OPEN ? ab.b(c3) : ab.c(c3));
    }

    public static <C extends Comparable<?>> Range<C> lessThan(C c2) {
        return a(ab.d(), ab.b(c2));
    }

    public static <C extends Comparable<?>> Range<C> atMost(C c2) {
        return a(ab.d(), ab.c(c2));
    }

    public static <C extends Comparable<?>> Range<C> upTo(C c2, BoundType boundType) {
        switch (boundType) {
            case OPEN:
                return lessThan(c2);
            case CLOSED:
                return atMost(c2);
            default:
                throw new AssertionError();
        }
    }

    public static <C extends Comparable<?>> Range<C> greaterThan(C c2) {
        return a(ab.c(c2), ab.e());
    }

    public static <C extends Comparable<?>> Range<C> atLeast(C c2) {
        return a(ab.b(c2), ab.e());
    }

    public static <C extends Comparable<?>> Range<C> downTo(C c2, BoundType boundType) {
        switch (boundType) {
            case OPEN:
                return greaterThan(c2);
            case CLOSED:
                return atLeast(c2);
            default:
                throw new AssertionError();
        }
    }

    public static <C extends Comparable<?>> Range<C> all() {
        return (Range<C>) a;
    }

    public static <C extends Comparable<?>> Range<C> singleton(C c2) {
        return closed(c2, c2);
    }

    public static <C extends Comparable<?>> Range<C> encloseAll(Iterable<C> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof SortedSet) {
            SortedSet a2 = a(iterable);
            Comparator comparator = a2.comparator();
            if (Ordering.natural().equals(comparator) || comparator == null) {
                return closed((Comparable) a2.first(), (Comparable) a2.last());
            }
        }
        Iterator<C> it = iterable.iterator();
        Comparable comparable = (Comparable) Preconditions.checkNotNull(it.next());
        Comparable comparable2 = comparable;
        while (it.hasNext()) {
            Comparable comparable3 = (Comparable) Preconditions.checkNotNull(it.next());
            comparable = (Comparable) Ordering.natural().min(comparable, comparable3);
            comparable2 = (Comparable) Ordering.natural().max(comparable2, comparable3);
        }
        return closed(comparable, comparable2);
    }

    private Range(ab<C> abVar, ab<C> abVar2) {
        this.lowerBound = (ab) Preconditions.checkNotNull(abVar);
        this.upperBound = (ab) Preconditions.checkNotNull(abVar2);
        if (abVar.a((ab) abVar2) > 0 || abVar == ab.e() || abVar2 == ab.d()) {
            throw new IllegalArgumentException("Invalid range: " + b(abVar, abVar2));
        }
    }

    public boolean hasLowerBound() {
        return this.lowerBound != ab.d();
    }

    public C lowerEndpoint() {
        return this.lowerBound.c();
    }

    public BoundType lowerBoundType() {
        return this.lowerBound.a();
    }

    public boolean hasUpperBound() {
        return this.upperBound != ab.e();
    }

    public C upperEndpoint() {
        return this.upperBound.c();
    }

    public BoundType upperBoundType() {
        return this.upperBound.b();
    }

    public boolean isEmpty() {
        return this.lowerBound.equals(this.upperBound);
    }

    public boolean contains(C c2) {
        Preconditions.checkNotNull(c2);
        return this.lowerBound.a((ab<C>) c2) && !this.upperBound.a((ab<C>) c2);
    }

    @Deprecated
    public boolean apply(C c2) {
        return contains(c2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean containsAll(Iterable<? extends C> iterable) {
        if (Iterables.isEmpty(iterable)) {
            return true;
        }
        if (iterable instanceof SortedSet) {
            SortedSet a2 = a(iterable);
            Comparator comparator = a2.comparator();
            if (Ordering.natural().equals(comparator) || comparator == null) {
                return contains((Comparable) a2.first()) && contains((Comparable) a2.last());
            }
        }
        Iterator<? extends C> it = iterable.iterator();
        while (it.hasNext()) {
            if (!contains((Comparable) it.next())) {
                return false;
            }
        }
        return true;
    }

    public boolean encloses(Range<C> range) {
        return this.lowerBound.a((ab) range.lowerBound) <= 0 && this.upperBound.a((ab) range.upperBound) >= 0;
    }

    public boolean isConnected(Range<C> range) {
        return this.lowerBound.a((ab) range.upperBound) <= 0 && range.lowerBound.a((ab) this.upperBound) <= 0;
    }

    public Range<C> intersection(Range<C> range) {
        int a2 = this.lowerBound.a((ab) range.lowerBound);
        int a3 = this.upperBound.a((ab) range.upperBound);
        if (a2 >= 0 && a3 <= 0) {
            return this;
        }
        if (a2 <= 0 && a3 >= 0) {
            return range;
        }
        return a((ab) (a2 >= 0 ? this.lowerBound : range.lowerBound), (ab) (a3 <= 0 ? this.upperBound : range.upperBound));
    }

    public Range<C> span(Range<C> range) {
        int a2 = this.lowerBound.a((ab) range.lowerBound);
        int a3 = this.upperBound.a((ab) range.upperBound);
        if (a2 <= 0 && a3 >= 0) {
            return this;
        }
        if (a2 >= 0 && a3 <= 0) {
            return range;
        }
        return a((ab) (a2 <= 0 ? this.lowerBound : range.lowerBound), (ab) (a3 >= 0 ? this.upperBound : range.upperBound));
    }

    public Range<C> canonical(DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(discreteDomain);
        ab<C> c2 = this.lowerBound.c(discreteDomain);
        ab<C> c3 = this.upperBound.c(discreteDomain);
        return (c2 == this.lowerBound && c3 == this.upperBound) ? this : a((ab) c2, (ab) c3);
    }

    @Override // com.google.common.base.Predicate
    public boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof Range)) {
            return false;
        }
        Range range = (Range) obj;
        return this.lowerBound.equals(range.lowerBound) && this.upperBound.equals(range.upperBound);
    }

    public int hashCode() {
        return (this.lowerBound.hashCode() * 31) + this.upperBound.hashCode();
    }

    public String toString() {
        return b(this.lowerBound, this.upperBound);
    }

    private static String b(ab<?> abVar, ab<?> abVar2) {
        StringBuilder sb = new StringBuilder(16);
        abVar.a(sb);
        sb.append("..");
        abVar2.b(sb);
        return sb.toString();
    }

    private static <T> SortedSet<T> a(Iterable<T> iterable) {
        return (SortedSet) iterable;
    }

    Object readResolve() {
        return equals(a) ? all() : this;
    }

    public static int a(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class b extends Ordering<Range<?>> implements Serializable {
        static final Ordering<Range<?>> a = new b();
        private static final long serialVersionUID = 0;

        private b() {
        }

        /* renamed from: a */
        public int compare(Range<?> range, Range<?> range2) {
            return ComparisonChain.start().compare(range.lowerBound, range2.lowerBound).compare(range.upperBound, range2.upperBound).result();
        }
    }
}
