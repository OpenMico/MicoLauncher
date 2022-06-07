package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Cut.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class ab<C extends Comparable> implements Serializable, Comparable<ab<C>> {
    private static final long serialVersionUID = 0;
    @NullableDecl
    final C endpoint;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract BoundType a();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract ab<C> a(BoundType boundType, DiscreteDomain<C> discreteDomain);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract C a(DiscreteDomain<C> discreteDomain);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(StringBuilder sb);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean a(C c2);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract BoundType b();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract ab<C> b(BoundType boundType, DiscreteDomain<C> discreteDomain);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract C b(DiscreteDomain<C> discreteDomain);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void b(StringBuilder sb);

    /* JADX INFO: Access modifiers changed from: package-private */
    public ab<C> c(DiscreteDomain<C> discreteDomain) {
        return this;
    }

    public abstract int hashCode();

    @Override // java.lang.Comparable
    public /* synthetic */ int compareTo(Object obj) {
        return a((ab) ((ab) obj));
    }

    ab(@NullableDecl C c2) {
        this.endpoint = c2;
    }

    public int a(ab<C> abVar) {
        if (abVar == d()) {
            return 1;
        }
        if (abVar == e()) {
            return -1;
        }
        int a2 = Range.a(this.endpoint, abVar.endpoint);
        return a2 != 0 ? a2 : Booleans.compare(this instanceof b, abVar instanceof b);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public C c() {
        return this.endpoint;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ab) {
            try {
                return a((ab) ((ab) obj)) == 0;
            } catch (ClassCastException unused) {
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <C extends Comparable> ab<C> d() {
        return c.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Cut.java */
    /* loaded from: classes2.dex */
    public static final class c extends ab<Comparable<?>> {
        private static final c a = new c();
        private static final long serialVersionUID = 0;

        @Override // com.google.common.collect.ab
        /* renamed from: a */
        public int compareTo(ab<Comparable<?>> abVar) {
            return abVar == this ? 0 : -1;
        }

        @Override // com.google.common.collect.ab
        boolean a(Comparable<?> comparable) {
            return true;
        }

        public String toString() {
            return "-∞";
        }

        private c() {
            super(null);
        }

        @Override // com.google.common.collect.ab
        Comparable<?> c() {
            throw new IllegalStateException("range unbounded on this side");
        }

        @Override // com.google.common.collect.ab
        BoundType a() {
            throw new IllegalStateException();
        }

        @Override // com.google.common.collect.ab
        BoundType b() {
            throw new AssertionError("this statement should be unreachable");
        }

        @Override // com.google.common.collect.ab
        ab<Comparable<?>> a(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        @Override // com.google.common.collect.ab
        ab<Comparable<?>> b(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError("this statement should be unreachable");
        }

        @Override // com.google.common.collect.ab
        void a(StringBuilder sb) {
            sb.append("(-∞");
        }

        @Override // com.google.common.collect.ab
        void b(StringBuilder sb) {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.ab
        Comparable<?> a(DiscreteDomain<Comparable<?>> discreteDomain) {
            return discreteDomain.minValue();
        }

        @Override // com.google.common.collect.ab
        Comparable<?> b(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.ab
        ab<Comparable<?>> c(DiscreteDomain<Comparable<?>> discreteDomain) {
            try {
                return ab.b(discreteDomain.minValue());
            } catch (NoSuchElementException unused) {
                return this;
            }
        }

        @Override // com.google.common.collect.ab
        public int hashCode() {
            return System.identityHashCode(this);
        }

        private Object readResolve() {
            return a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <C extends Comparable> ab<C> e() {
        return a.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Cut.java */
    /* loaded from: classes2.dex */
    public static final class a extends ab<Comparable<?>> {
        private static final a a = new a();
        private static final long serialVersionUID = 0;

        @Override // com.google.common.collect.ab
        /* renamed from: a */
        public int compareTo(ab<Comparable<?>> abVar) {
            return abVar == this ? 0 : 1;
        }

        @Override // com.google.common.collect.ab
        boolean a(Comparable<?> comparable) {
            return false;
        }

        public String toString() {
            return "+∞";
        }

        private a() {
            super(null);
        }

        @Override // com.google.common.collect.ab
        Comparable<?> c() {
            throw new IllegalStateException("range unbounded on this side");
        }

        @Override // com.google.common.collect.ab
        BoundType a() {
            throw new AssertionError("this statement should be unreachable");
        }

        @Override // com.google.common.collect.ab
        BoundType b() {
            throw new IllegalStateException();
        }

        @Override // com.google.common.collect.ab
        ab<Comparable<?>> a(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError("this statement should be unreachable");
        }

        @Override // com.google.common.collect.ab
        ab<Comparable<?>> b(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        @Override // com.google.common.collect.ab
        void a(StringBuilder sb) {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.ab
        void b(StringBuilder sb) {
            sb.append("+∞)");
        }

        @Override // com.google.common.collect.ab
        Comparable<?> a(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.ab
        Comparable<?> b(DiscreteDomain<Comparable<?>> discreteDomain) {
            return discreteDomain.maxValue();
        }

        @Override // com.google.common.collect.ab
        public int hashCode() {
            return System.identityHashCode(this);
        }

        private Object readResolve() {
            return a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <C extends Comparable> ab<C> b(C c2) {
        return new d(c2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Cut.java */
    /* loaded from: classes2.dex */
    public static final class d<C extends Comparable> extends ab<C> {
        private static final long serialVersionUID = 0;

        @Override // com.google.common.collect.ab, java.lang.Comparable
        public /* synthetic */ int compareTo(Object obj) {
            return ab.super.a((ab) ((ab) obj));
        }

        d(C c) {
            super((Comparable) Preconditions.checkNotNull(c));
        }

        @Override // com.google.common.collect.ab
        boolean a(C c) {
            return Range.a(this.endpoint, c) <= 0;
        }

        @Override // com.google.common.collect.ab
        BoundType a() {
            return BoundType.CLOSED;
        }

        @Override // com.google.common.collect.ab
        BoundType b() {
            return BoundType.OPEN;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ab
        ab<C> a(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            switch (boundType) {
                case CLOSED:
                    return this;
                case OPEN:
                    Comparable previous = discreteDomain.previous(this.endpoint);
                    return previous == null ? ab.d() : new b(previous);
                default:
                    throw new AssertionError();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ab
        ab<C> b(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            switch (boundType) {
                case CLOSED:
                    Comparable previous = discreteDomain.previous(this.endpoint);
                    return previous == null ? ab.e() : new b(previous);
                case OPEN:
                    return this;
                default:
                    throw new AssertionError();
            }
        }

        @Override // com.google.common.collect.ab
        void a(StringBuilder sb) {
            sb.append('[');
            sb.append(this.endpoint);
        }

        @Override // com.google.common.collect.ab
        void b(StringBuilder sb) {
            sb.append(this.endpoint);
            sb.append(')');
        }

        @Override // com.google.common.collect.ab
        C a(DiscreteDomain<C> discreteDomain) {
            return (C) this.endpoint;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ab
        C b(DiscreteDomain<C> discreteDomain) {
            return (C) discreteDomain.previous(this.endpoint);
        }

        @Override // com.google.common.collect.ab
        public int hashCode() {
            return this.endpoint.hashCode();
        }

        public String toString() {
            return "\\" + this.endpoint + "/";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <C extends Comparable> ab<C> c(C c2) {
        return new b(c2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Cut.java */
    /* loaded from: classes2.dex */
    public static final class b<C extends Comparable> extends ab<C> {
        private static final long serialVersionUID = 0;

        @Override // com.google.common.collect.ab, java.lang.Comparable
        public /* synthetic */ int compareTo(Object obj) {
            return ab.super.a((ab) ((ab) obj));
        }

        b(C c) {
            super((Comparable) Preconditions.checkNotNull(c));
        }

        @Override // com.google.common.collect.ab
        boolean a(C c) {
            return Range.a(this.endpoint, c) < 0;
        }

        @Override // com.google.common.collect.ab
        BoundType a() {
            return BoundType.OPEN;
        }

        @Override // com.google.common.collect.ab
        BoundType b() {
            return BoundType.CLOSED;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ab
        ab<C> a(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            switch (boundType) {
                case CLOSED:
                    Comparable next = discreteDomain.next(this.endpoint);
                    return next == null ? ab.d() : b(next);
                case OPEN:
                    return this;
                default:
                    throw new AssertionError();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ab
        ab<C> b(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            switch (boundType) {
                case CLOSED:
                    return this;
                case OPEN:
                    Comparable next = discreteDomain.next(this.endpoint);
                    return next == null ? ab.e() : b(next);
                default:
                    throw new AssertionError();
            }
        }

        @Override // com.google.common.collect.ab
        void a(StringBuilder sb) {
            sb.append('(');
            sb.append(this.endpoint);
        }

        @Override // com.google.common.collect.ab
        void b(StringBuilder sb) {
            sb.append(this.endpoint);
            sb.append(']');
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ab
        C a(DiscreteDomain<C> discreteDomain) {
            return (C) discreteDomain.next(this.endpoint);
        }

        @Override // com.google.common.collect.ab
        C b(DiscreteDomain<C> discreteDomain) {
            return (C) this.endpoint;
        }

        @Override // com.google.common.collect.ab
        ab<C> c(DiscreteDomain<C> discreteDomain) {
            C a = a(discreteDomain);
            return a != null ? b(a) : ab.e();
        }

        @Override // com.google.common.collect.ab
        public int hashCode() {
            return ~this.endpoint.hashCode();
        }

        public String toString() {
            return "/" + this.endpoint + "\\";
        }
    }
}
