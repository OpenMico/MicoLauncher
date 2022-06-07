package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class Converter<A, B> implements Function<A, B> {
    @LazyInit
    @MonotonicNonNullDecl
    private transient Converter<B, A> a;
    private final boolean handleNullAutomatically;

    @ForOverride
    protected abstract A doBackward(B b2);

    @ForOverride
    protected abstract B doForward(A a2);

    public Converter() {
        this(true);
    }

    Converter(boolean z) {
        this.handleNullAutomatically = z;
    }

    @CanIgnoreReturnValue
    @NullableDecl
    public final B convert(@NullableDecl A a2) {
        return a((Converter<A, B>) a2);
    }

    @NullableDecl
    B a(@NullableDecl A a2) {
        if (!this.handleNullAutomatically) {
            return doForward(a2);
        }
        if (a2 == null) {
            return null;
        }
        return (B) Preconditions.checkNotNull(doForward(a2));
    }

    @NullableDecl
    A b(@NullableDecl B b2) {
        if (!this.handleNullAutomatically) {
            return doBackward(b2);
        }
        if (b2 == null) {
            return null;
        }
        return (A) Preconditions.checkNotNull(doBackward(b2));
    }

    @CanIgnoreReturnValue
    public Iterable<B> convertAll(final Iterable<? extends A> iterable) {
        Preconditions.checkNotNull(iterable, "fromIterable");
        return new Iterable<B>() { // from class: com.google.common.base.Converter.1
            @Override // java.lang.Iterable
            public Iterator<B> iterator() {
                return new Iterator<B>() { // from class: com.google.common.base.Converter.1.1
                    private final Iterator<? extends A> b;

                    {
                        AnonymousClass1.this = this;
                        this.b = iterable.iterator();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.b.hasNext();
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Iterator
                    public B next() {
                        return (B) Converter.this.convert(this.b.next());
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        this.b.remove();
                    }
                };
            }
        };
    }

    @CanIgnoreReturnValue
    public Converter<B, A> reverse() {
        Converter<B, A> converter = this.a;
        if (converter != null) {
            return converter;
        }
        d dVar = new d(this);
        this.a = dVar;
        return dVar;
    }

    /* loaded from: classes2.dex */
    private static final class d<A, B> extends Converter<B, A> implements Serializable {
        private static final long serialVersionUID = 0;
        final Converter<A, B> original;

        d(Converter<A, B> converter) {
            this.original = converter;
        }

        @Override // com.google.common.base.Converter
        protected A doForward(B b) {
            throw new AssertionError();
        }

        @Override // com.google.common.base.Converter
        protected B doBackward(A a) {
            throw new AssertionError();
        }

        @Override // com.google.common.base.Converter
        @NullableDecl
        A a(@NullableDecl B b) {
            return this.original.b(b);
        }

        @Override // com.google.common.base.Converter
        @NullableDecl
        B b(@NullableDecl A a) {
            return this.original.a((Converter<A, B>) a);
        }

        @Override // com.google.common.base.Converter
        public Converter<A, B> reverse() {
            return this.original;
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof d) {
                return this.original.equals(((d) obj).original);
            }
            return false;
        }

        public int hashCode() {
            return ~this.original.hashCode();
        }

        public String toString() {
            return this.original + ".reverse()";
        }
    }

    public final <C> Converter<A, C> andThen(Converter<B, C> converter) {
        return a((Converter) converter);
    }

    <C> Converter<A, C> a(Converter<B, C> converter) {
        return new a(this, (Converter) Preconditions.checkNotNull(converter));
    }

    /* loaded from: classes2.dex */
    public static final class a<A, B, C> extends Converter<A, C> implements Serializable {
        private static final long serialVersionUID = 0;
        final Converter<A, B> first;
        final Converter<B, C> second;

        a(Converter<A, B> converter, Converter<B, C> converter2) {
            this.first = converter;
            this.second = converter2;
        }

        @Override // com.google.common.base.Converter
        protected C doForward(A a) {
            throw new AssertionError();
        }

        @Override // com.google.common.base.Converter
        protected A doBackward(C c) {
            throw new AssertionError();
        }

        @Override // com.google.common.base.Converter
        @NullableDecl
        C a(@NullableDecl A a) {
            return this.second.a((Converter<B, C>) this.first.a((Converter<A, B>) a));
        }

        @Override // com.google.common.base.Converter
        @NullableDecl
        A b(@NullableDecl C c) {
            return this.first.b(this.second.b(c));
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.first.equals(aVar.first) && this.second.equals(aVar.second);
        }

        public int hashCode() {
            return (this.first.hashCode() * 31) + this.second.hashCode();
        }

        public String toString() {
            return this.first + ".andThen(" + this.second + ")";
        }
    }

    @Override // com.google.common.base.Function
    @CanIgnoreReturnValue
    @NullableDecl
    @Deprecated
    public final B apply(@NullableDecl A a2) {
        return convert(a2);
    }

    @Override // com.google.common.base.Function
    public boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    public static <A, B> Converter<A, B> from(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
        return new b(function, function2);
    }

    /* loaded from: classes2.dex */
    private static final class b<A, B> extends Converter<A, B> implements Serializable {
        private final Function<? super B, ? extends A> backwardFunction;
        private final Function<? super A, ? extends B> forwardFunction;

        private b(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
            this.forwardFunction = (Function) Preconditions.checkNotNull(function);
            this.backwardFunction = (Function) Preconditions.checkNotNull(function2);
        }

        @Override // com.google.common.base.Converter
        protected B doForward(A a) {
            return (B) this.forwardFunction.apply(a);
        }

        @Override // com.google.common.base.Converter
        protected A doBackward(B b) {
            return (A) this.backwardFunction.apply(b);
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return this.forwardFunction.equals(bVar.forwardFunction) && this.backwardFunction.equals(bVar.backwardFunction);
        }

        public int hashCode() {
            return (this.forwardFunction.hashCode() * 31) + this.backwardFunction.hashCode();
        }

        public String toString() {
            return "Converter.from(" + this.forwardFunction + ", " + this.backwardFunction + ")";
        }
    }

    public static <T> Converter<T, T> identity() {
        return c.a;
    }

    /* loaded from: classes2.dex */
    private static final class c<T> extends Converter<T, T> implements Serializable {
        static final c a = new c();
        private static final long serialVersionUID = 0;

        /* renamed from: a */
        public c<T> reverse() {
            return this;
        }

        @Override // com.google.common.base.Converter
        protected T doBackward(T t) {
            return t;
        }

        @Override // com.google.common.base.Converter
        protected T doForward(T t) {
            return t;
        }

        public String toString() {
            return "Converter.identity()";
        }

        private c() {
        }

        @Override // com.google.common.base.Converter
        <S> Converter<T, S> a(Converter<T, S> converter) {
            return (Converter) Preconditions.checkNotNull(converter, "otherConverter");
        }

        private Object readResolve() {
            return a;
        }
    }
}
