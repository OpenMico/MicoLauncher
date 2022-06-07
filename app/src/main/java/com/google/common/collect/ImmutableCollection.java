package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public abstract class ImmutableCollection<E> extends AbstractCollection<E> implements Serializable {
    private static final Object[] a = new Object[0];

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean a();

    @Override // java.util.AbstractCollection, java.util.Collection
    public abstract boolean contains(@NullableDecl Object obj);

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public abstract UnmodifiableIterator<E> iterator();

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Object[] toArray() {
        int size = size();
        if (size == 0) {
            return a;
        }
        Object[] objArr = new Object[size];
        a(objArr, 0);
        return objArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @CanIgnoreReturnValue
    public final <T> T[] toArray(T[] tArr) {
        Preconditions.checkNotNull(tArr);
        int size = size();
        if (tArr.length < size) {
            tArr = (T[]) ObjectArrays.newArray(tArr, size);
        } else if (tArr.length > size) {
            tArr[size] = null;
        }
        a(tArr, 0);
        return tArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @CanIgnoreReturnValue
    @Deprecated
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @CanIgnoreReturnValue
    @Deprecated
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @CanIgnoreReturnValue
    @Deprecated
    public final boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @CanIgnoreReturnValue
    @Deprecated
    public final boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @CanIgnoreReturnValue
    @Deprecated
    public final boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public ImmutableList<E> asList() {
        return isEmpty() ? ImmutableList.of() : ImmutableList.a(toArray());
    }

    @CanIgnoreReturnValue
    public int a(Object[] objArr, int i) {
        UnmodifiableIterator<E> it = iterator();
        while (it.hasNext()) {
            i++;
            objArr[i] = it.next();
        }
        return i;
    }

    Object writeReplace() {
        return new ImmutableList.c(toArray());
    }

    /* loaded from: classes2.dex */
    public static abstract class Builder<E> {
        @CanIgnoreReturnValue
        public abstract Builder<E> add(E e);

        public abstract ImmutableCollection<E> build();

        public static int a(int i, int i2) {
            if (i2 >= 0) {
                int i3 = i + (i >> 1) + 1;
                if (i3 < i2) {
                    i3 = Integer.highestOneBit(i2 - 1) << 1;
                }
                if (i3 < 0) {
                    return Integer.MAX_VALUE;
                }
                return i3;
            }
            throw new AssertionError("cannot store more than MAX_VALUE elements");
        }

        @CanIgnoreReturnValue
        public Builder<E> add(E... eArr) {
            for (E e : eArr) {
                add((Builder<E>) e);
            }
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            Iterator<? extends E> it = iterable.iterator();
            while (it.hasNext()) {
                add((Builder<E>) it.next());
            }
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> it) {
            while (it.hasNext()) {
                add((Builder<E>) it.next());
            }
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class a<E> extends Builder<E> {
        Object[] a;
        int b = 0;
        boolean c;

        public a(int i) {
            t.a(i, "initialCapacity");
            this.a = new Object[i];
        }

        private void a(int i) {
            Object[] objArr = this.a;
            if (objArr.length < i) {
                this.a = Arrays.copyOf(objArr, a(objArr.length, i));
                this.c = false;
            } else if (this.c) {
                this.a = (Object[]) objArr.clone();
                this.c = false;
            }
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public a<E> add(E e) {
            Preconditions.checkNotNull(e);
            a(this.b + 1);
            Object[] objArr = this.a;
            int i = this.b;
            this.b = i + 1;
            objArr[i] = e;
            return this;
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> add(E... eArr) {
            ObjectArrays.a(eArr);
            a(this.b + eArr.length);
            System.arraycopy(eArr, 0, this.a, this.b, eArr.length);
            this.b += eArr.length;
            return this;
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            if (iterable instanceof Collection) {
                Collection collection = (Collection) iterable;
                a(this.b + collection.size());
                if (collection instanceof ImmutableCollection) {
                    this.b = ((ImmutableCollection) collection).a(this.a, this.b);
                    return this;
                }
            }
            super.addAll(iterable);
            return this;
        }
    }
}
