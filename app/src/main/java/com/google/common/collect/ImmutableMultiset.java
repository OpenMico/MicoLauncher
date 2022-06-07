package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public abstract class ImmutableMultiset<E> extends bc<E> implements Multiset<E> {
    @LazyInit
    private transient ImmutableList<E> a;
    @LazyInit
    private transient ImmutableSet<Multiset.Entry<E>> b;

    abstract Multiset.Entry<E> a(int i);

    public abstract ImmutableSet<E> elementSet();

    @Override // com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    abstract Object writeReplace();

    public static <E> ImmutableMultiset<E> of() {
        return bv.a;
    }

    public static <E> ImmutableMultiset<E> of(E e) {
        return a(e);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2) {
        return a(e, e2);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3) {
        return a(e, e2, e3);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4) {
        return a(e, e2, e3, e4);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4, E e5) {
        return a(e, e2, e3, e4, e5);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        return new Builder().add((Builder) e).add((Builder<E>) e2).add((Builder<E>) e3).add((Builder<E>) e4).add((Builder<E>) e5).add((Builder<E>) e6).add((Object[]) eArr).build();
    }

    public static <E> ImmutableMultiset<E> copyOf(E[] eArr) {
        return a(eArr);
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterable<? extends E> iterable) {
        if (iterable instanceof ImmutableMultiset) {
            ImmutableMultiset<E> immutableMultiset = (ImmutableMultiset) iterable;
            if (!immutableMultiset.a()) {
                return immutableMultiset;
            }
        }
        Builder builder = new Builder(Multisets.a(iterable));
        builder.addAll((Iterable) iterable);
        return builder.build();
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterator<? extends E> it) {
        return new Builder().addAll((Iterator) it).build();
    }

    private static <E> ImmutableMultiset<E> a(E... eArr) {
        return new Builder().add((Object[]) eArr).build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E> ImmutableMultiset<E> a(Collection<? extends Multiset.Entry<? extends E>> collection) {
        Builder builder = new Builder(collection.size());
        Iterator<? extends Multiset.Entry<? extends E>> it = collection.iterator();
        while (it.hasNext()) {
            Multiset.Entry entry = (Multiset.Entry) it.next();
            builder.addCopies(entry.getElement(), entry.getCount());
        }
        return builder.build();
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<E> iterator() {
        final UnmodifiableIterator<Multiset.Entry<E>> it = entrySet().iterator();
        return new UnmodifiableIterator<E>() { // from class: com.google.common.collect.ImmutableMultiset.1
            int a;
            @MonotonicNonNullDecl
            E b;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.a > 0 || it.hasNext();
            }

            @Override // java.util.Iterator
            public E next() {
                if (this.a <= 0) {
                    Multiset.Entry entry = (Multiset.Entry) it.next();
                    this.b = (E) entry.getElement();
                    this.a = entry.getCount();
                }
                this.a--;
                return this.b;
            }
        };
    }

    @Override // com.google.common.collect.ImmutableCollection
    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList = this.a;
        if (immutableList != null) {
            return immutableList;
        }
        ImmutableList<E> asList = super.asList();
        this.a = asList;
        return asList;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    public boolean contains(@NullableDecl Object obj) {
        return count(obj) > 0;
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    @Deprecated
    public final int add(E e, int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    @Deprecated
    public final int remove(Object obj, int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    @Deprecated
    public final int setCount(E e, int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    @Deprecated
    public final boolean setCount(E e, int i, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    public int a(Object[] objArr, int i) {
        UnmodifiableIterator<Multiset.Entry<E>> it = entrySet().iterator();
        while (it.hasNext()) {
            Multiset.Entry<E> next = it.next();
            Arrays.fill(objArr, i, next.getCount() + i, next.getElement());
            i += next.getCount();
        }
        return i;
    }

    @Override // java.util.Collection, com.google.common.collect.Multiset
    public boolean equals(@NullableDecl Object obj) {
        return Multisets.a(this, obj);
    }

    @Override // java.util.Collection, com.google.common.collect.Multiset
    public int hashCode() {
        return Sets.a(entrySet());
    }

    @Override // java.util.AbstractCollection, com.google.common.collect.Multiset
    public String toString() {
        return entrySet().toString();
    }

    @Override // com.google.common.collect.Multiset
    public ImmutableSet<Multiset.Entry<E>> entrySet() {
        ImmutableSet<Multiset.Entry<E>> immutableSet = this.b;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<Multiset.Entry<E>> b2 = b();
        this.b = b2;
        return b2;
    }

    private ImmutableSet<Multiset.Entry<E>> b() {
        return isEmpty() ? ImmutableSet.of() : new a();
    }

    /* loaded from: classes2.dex */
    public final class a extends bg<Multiset.Entry<E>> {
        private static final long serialVersionUID = 0;

        private a() {
            ImmutableMultiset.this = r1;
        }

        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return ImmutableMultiset.this.a();
        }

        /* renamed from: b */
        public Multiset.Entry<E> a(int i) {
            return ImmutableMultiset.this.a(i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return ImmutableMultiset.this.elementSet().size();
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            if (!(obj instanceof Multiset.Entry)) {
                return false;
            }
            Multiset.Entry entry = (Multiset.Entry) obj;
            return entry.getCount() > 0 && ImmutableMultiset.this.count(entry.getElement()) == entry.getCount();
        }

        @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
        public int hashCode() {
            return ImmutableMultiset.this.hashCode();
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        @GwtIncompatible
        Object writeReplace() {
            return new b(ImmutableMultiset.this);
        }
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    static class b<E> implements Serializable {
        final ImmutableMultiset<E> multiset;

        b(ImmutableMultiset<E> immutableMultiset) {
            this.multiset = immutableMultiset;
        }

        Object readResolve() {
            return this.multiset.entrySet();
        }
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    /* loaded from: classes2.dex */
    public static class Builder<E> extends ImmutableCollection.Builder<E> {
        bn<E> a;
        boolean b;
        boolean c;

        public Builder() {
            this(4);
        }

        public Builder(int i) {
            this.b = false;
            this.c = false;
            this.a = bn.a(i);
        }

        public Builder(boolean z) {
            this.b = false;
            this.c = false;
            this.a = null;
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> add(E e) {
            return addCopies(e, 1);
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> add(E... eArr) {
            super.add((Object[]) eArr);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addCopies(E e, int i) {
            if (i == 0) {
                return this;
            }
            if (this.b) {
                this.a = new bn<>((bn<? extends E>) this.a);
                this.c = false;
            }
            this.b = false;
            Preconditions.checkNotNull(e);
            bn<E> bnVar = this.a;
            bnVar.a((bn<E>) e, i + bnVar.b(e));
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        public Builder<E> setCount(E e, int i) {
            if (i == 0 && !this.c) {
                this.a = new bo(this.a);
                this.c = true;
            } else if (this.b) {
                this.a = new bn<>((bn<? extends E>) this.a);
                this.c = false;
            }
            this.b = false;
            Preconditions.checkNotNull(e);
            if (i == 0) {
                this.a.c(e);
            } else {
                this.a.a((bn<E>) Preconditions.checkNotNull(e), i);
            }
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            if (iterable instanceof Multiset) {
                Multiset b = Multisets.b(iterable);
                bn a = a(b);
                if (a != null) {
                    bn<E> bnVar = this.a;
                    bnVar.f(Math.max(bnVar.c(), a.c()));
                    for (int b2 = a.b(); b2 >= 0; b2 = a.b(b2)) {
                        addCopies(a.c(b2), a.d(b2));
                    }
                } else {
                    Set<Multiset.Entry<E>> entrySet = b.entrySet();
                    bn<E> bnVar2 = this.a;
                    bnVar2.f(Math.max(bnVar2.c(), entrySet.size()));
                    for (Multiset.Entry<E> entry : b.entrySet()) {
                        addCopies(entry.getElement(), entry.getCount());
                    }
                }
            } else {
                super.addAll((Iterable) iterable);
            }
            return this;
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll((Iterator) it);
            return this;
        }

        @NullableDecl
        static <T> bn<T> a(Iterable<T> iterable) {
            if (iterable instanceof bv) {
                return (bn<E>) ((bv) iterable).b;
            }
            if (iterable instanceof e) {
                return (bn<E>) ((e) iterable).a;
            }
            return null;
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        public ImmutableMultiset<E> build() {
            if (this.a.c() == 0) {
                return ImmutableMultiset.of();
            }
            if (this.c) {
                this.a = new bn<>((bn<? extends E>) this.a);
                this.c = false;
            }
            this.b = true;
            return new bv(this.a);
        }
    }
}
