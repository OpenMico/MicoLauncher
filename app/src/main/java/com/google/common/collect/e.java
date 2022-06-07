package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractMapBasedMultiset.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public abstract class e<E> extends h<E> implements Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    transient bn<E> a;
    transient long b;

    abstract void a(int i);

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(int i) {
        a(i);
    }

    @Override // com.google.common.collect.Multiset
    public final int count(@NullableDecl Object obj) {
        return this.a.b(obj);
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public final int add(@NullableDecl E e, int i) {
        if (i == 0) {
            return count(e);
        }
        boolean z = true;
        Preconditions.checkArgument(i > 0, "occurrences cannot be negative: %s", i);
        int a2 = this.a.a(e);
        if (a2 == -1) {
            this.a.a((bn<E>) e, i);
            this.b += i;
            return 0;
        }
        int d = this.a.d(a2);
        long j = i;
        long j2 = d + j;
        if (j2 > 2147483647L) {
            z = false;
        }
        Preconditions.checkArgument(z, "too many occurrences: %s", j2);
        this.a.b(a2, (int) j2);
        this.b += j;
        return d;
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public final int remove(@NullableDecl Object obj, int i) {
        if (i == 0) {
            return count(obj);
        }
        Preconditions.checkArgument(i > 0, "occurrences cannot be negative: %s", i);
        int a2 = this.a.a(obj);
        if (a2 == -1) {
            return 0;
        }
        int d = this.a.d(a2);
        if (d > i) {
            this.a.b(a2, d - i);
        } else {
            this.a.h(a2);
            i = d;
        }
        this.b -= i;
        return d;
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public final int setCount(@NullableDecl E e, int i) {
        t.a(i, "count");
        int c = i == 0 ? this.a.c(e) : this.a.a((bn<E>) e, i);
        this.b += i - c;
        return c;
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    public final boolean setCount(@NullableDecl E e, int i, int i2) {
        t.a(i, "oldCount");
        t.a(i2, "newCount");
        int a2 = this.a.a(e);
        if (a2 == -1) {
            if (i != 0) {
                return false;
            }
            if (i2 > 0) {
                this.a.a((bn<E>) e, i2);
                this.b += i2;
            }
            return true;
        } else if (this.a.d(a2) != i) {
            return false;
        } else {
            if (i2 == 0) {
                this.a.h(a2);
                this.b -= i;
            } else {
                this.a.b(a2, i2);
                this.b += i2 - i;
            }
            return true;
        }
    }

    @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        this.a.d();
        this.b = 0L;
    }

    /* compiled from: AbstractMapBasedMultiset.java */
    /* loaded from: classes2.dex */
    abstract class a<T> implements Iterator<T> {
        int b;
        int c = -1;
        int d;

        abstract T a(int i);

        a() {
            this.b = e.this.a.b();
            this.d = e.this.a.d;
        }

        private void a() {
            if (e.this.a.d != this.d) {
                throw new ConcurrentModificationException();
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            a();
            return this.b >= 0;
        }

        @Override // java.util.Iterator
        public T next() {
            if (hasNext()) {
                T a = a(this.b);
                this.c = this.b;
                this.b = e.this.a.b(this.b);
                return a;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            a();
            t.a(this.c != -1);
            e.this.b -= e.this.a.h(this.c);
            this.b = e.this.a.a(this.b, this.c);
            this.c = -1;
            this.d = e.this.a.d;
        }
    }

    @Override // com.google.common.collect.h
    final Iterator<E> a() {
        return new e<E>.a() { // from class: com.google.common.collect.e.1
            @Override // com.google.common.collect.e.a
            E a(int i) {
                return e.this.a.c(i);
            }
        };
    }

    @Override // com.google.common.collect.h
    final Iterator<Multiset.Entry<E>> b() {
        return new e<E>.a() { // from class: com.google.common.collect.e.2
            /* JADX INFO: Access modifiers changed from: package-private */
            /* renamed from: b */
            public Multiset.Entry<E> a(int i) {
                return e.this.a.e(i);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Multiset<? super E> multiset) {
        Preconditions.checkNotNull(multiset);
        int b = this.a.b();
        while (b >= 0) {
            multiset.add((E) this.a.c(b), this.a.d(b));
            b = this.a.b(b);
        }
    }

    @Override // com.google.common.collect.h
    final int c() {
        return this.a.c();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
    public final Iterator<E> iterator() {
        return Multisets.a((Multiset) this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public final int size() {
        return Ints.saturatedCast(this.b);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        cc.a(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int a2 = cc.a(objectInputStream);
        a(3);
        cc.a(this, objectInputStream, a2);
    }
}
