package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public abstract class ImmutableSet<E> extends ImmutableCollection<E> implements Set<E> {
    @RetainedWith
    @NullableDecl
    @LazyInit
    private transient ImmutableList<E> a;

    public static boolean b(int i, int i2) {
        return i < (i2 >> 1) + (i2 >> 2);
    }

    boolean e() {
        return false;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public abstract UnmodifiableIterator<E> iterator();

    public static <E> ImmutableSet<E> of() {
        return bw.a;
    }

    public static <E> ImmutableSet<E> of(E e) {
        return new cd(e);
    }

    public static <E> ImmutableSet<E> of(E e, E e2) {
        return b(2, e, e2);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3) {
        return b(3, e, e2, e3);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4) {
        return b(4, e, e2, e3, e4);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5) {
        return b(5, e, e2, e3, e4, e5);
    }

    @SafeVarargs
    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        Preconditions.checkArgument(eArr.length <= 2147483641, "the total number of elements must fit in an int");
        Object[] objArr = new Object[eArr.length + 6];
        objArr[0] = e;
        objArr[1] = e2;
        objArr[2] = e3;
        objArr[3] = e4;
        objArr[4] = e5;
        objArr[5] = e6;
        System.arraycopy(eArr, 0, objArr, 6, eArr.length);
        return b(objArr.length, objArr);
    }

    public static <E> ImmutableSet<E> b(int i, Object... objArr) {
        switch (i) {
            case 0:
                return of();
            case 1:
                return of(objArr[0]);
            default:
                int c = c(i);
                Object[] objArr2 = new Object[c];
                int i2 = c - 1;
                int i3 = 0;
                int i4 = 0;
                for (int i5 = 0; i5 < i; i5++) {
                    Object a2 = ObjectArrays.a(objArr[i5], i5);
                    int hashCode = a2.hashCode();
                    int a3 = au.a(hashCode);
                    while (true) {
                        int i6 = a3 & i2;
                        Object obj = objArr2[i6];
                        if (obj == null) {
                            i4++;
                            objArr[i4] = a2;
                            objArr2[i6] = a2;
                            i3 += hashCode;
                        } else if (obj.equals(a2)) {
                            break;
                        } else {
                            a3++;
                        }
                    }
                }
                Arrays.fill(objArr, i4, i, (Object) null);
                if (i4 == 1) {
                    return new cd(objArr[0], i3);
                }
                if (c(i4) < c / 2) {
                    return b(i4, objArr);
                }
                if (b(i4, objArr.length)) {
                    objArr = Arrays.copyOf(objArr, i4);
                }
                return new bw(objArr, i3, objArr2, i2, i4);
        }
    }

    @VisibleForTesting
    public static int c(int i) {
        int max = Math.max(i, 2);
        boolean z = true;
        if (max < 751619276) {
            int highestOneBit = Integer.highestOneBit(max - 1) << 1;
            while (highestOneBit * 0.7d < max) {
                highestOneBit <<= 1;
            }
            return highestOneBit;
        }
        if (max >= 1073741824) {
            z = false;
        }
        Preconditions.checkArgument(z, "collection too large");
        return 1073741824;
    }

    public static <E> ImmutableSet<E> copyOf(Collection<? extends E> collection) {
        if ((collection instanceof ImmutableSet) && !(collection instanceof SortedSet)) {
            ImmutableSet<E> immutableSet = (ImmutableSet) collection;
            if (!immutableSet.a()) {
                return immutableSet;
            }
        }
        Object[] array = collection.toArray();
        return b(array.length, array);
    }

    public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return copyOf((Collection) iterable);
        }
        return copyOf(iterable.iterator());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> it) {
        if (!it.hasNext()) {
            return of();
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return of(next);
        }
        return new Builder().add((Builder) next).addAll((Iterator) it).build();
    }

    public static <E> ImmutableSet<E> copyOf(E[] eArr) {
        switch (eArr.length) {
            case 0:
                return of();
            case 1:
                return of((Object) eArr[0]);
            default:
                return b(eArr.length, (Object[]) eArr.clone());
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ImmutableSet) || !e() || !((ImmutableSet) obj).e() || hashCode() == obj.hashCode()) {
            return Sets.a(this, obj);
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        return Sets.a(this);
    }

    @Override // com.google.common.collect.ImmutableCollection
    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList = this.a;
        if (immutableList != null) {
            return immutableList;
        }
        ImmutableList<E> f = f();
        this.a = f;
        return f;
    }

    ImmutableList<E> f() {
        return ImmutableList.a(toArray());
    }

    /* loaded from: classes2.dex */
    private static class a implements Serializable {
        private static final long serialVersionUID = 0;
        final Object[] elements;

        a(Object[] objArr) {
            this.elements = objArr;
        }

        Object readResolve() {
            return ImmutableSet.copyOf(this.elements);
        }
    }

    @Override // com.google.common.collect.ImmutableCollection
    Object writeReplace() {
        return new a(toArray());
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    @Beta
    public static <E> Builder<E> builderWithExpectedSize(int i) {
        t.a(i, "expectedSize");
        return new Builder<>(i);
    }

    /* loaded from: classes2.dex */
    public static class Builder<E> extends ImmutableCollection.a<E> {
        @VisibleForTesting
        @NullableDecl
        Object[] d;
        private int e;

        public Builder() {
            super(4);
        }

        Builder(int i) {
            super(i);
            this.d = new Object[ImmutableSet.c(i)];
        }

        @Override // com.google.common.collect.ImmutableCollection.a, com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> add(E e) {
            Preconditions.checkNotNull(e);
            if (this.d == null || ImmutableSet.c(this.b) > this.d.length) {
                this.d = null;
                super.add((Builder<E>) e);
                return this;
            }
            a((Builder<E>) e);
            return this;
        }

        @Override // com.google.common.collect.ImmutableCollection.a, com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> add(E... eArr) {
            if (this.d != null) {
                for (E e : eArr) {
                    add((Builder<E>) e);
                }
            } else {
                super.add((Object[]) eArr);
            }
            return this;
        }

        private void a(E e) {
            int length = this.d.length - 1;
            int hashCode = e.hashCode();
            int a = au.a(hashCode);
            while (true) {
                int i = a & length;
                Object[] objArr = this.d;
                Object obj = objArr[i];
                if (obj == null) {
                    objArr[i] = e;
                    this.e += hashCode;
                    super.add((Builder<E>) e);
                    return;
                } else if (!obj.equals(e)) {
                    a = i + 1;
                } else {
                    return;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableCollection.a, com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            Preconditions.checkNotNull(iterable);
            if (this.d != null) {
                Iterator<? extends E> it = iterable.iterator();
                while (it.hasNext()) {
                    add((Builder<E>) it.next());
                }
            } else {
                super.addAll((Iterable) iterable);
            }
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> it) {
            Preconditions.checkNotNull(it);
            while (it.hasNext()) {
                add((Builder<E>) it.next());
            }
            return this;
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        public ImmutableSet<E> build() {
            ImmutableSet<E> immutableSet;
            switch (this.b) {
                case 0:
                    return ImmutableSet.of();
                case 1:
                    return ImmutableSet.of(this.a[0]);
                default:
                    if (this.d == null || ImmutableSet.c(this.b) != this.d.length) {
                        immutableSet = ImmutableSet.b(this.b, this.a);
                        this.b = immutableSet.size();
                    } else {
                        Object[] copyOf = ImmutableSet.b(this.b, this.a.length) ? Arrays.copyOf(this.a, this.b) : this.a;
                        int i = this.e;
                        Object[] objArr = this.d;
                        immutableSet = new bw<>(copyOf, i, objArr, objArr.length - 1, this.b);
                    }
                    this.c = true;
                    this.d = null;
                    return immutableSet;
            }
        }
    }
}
